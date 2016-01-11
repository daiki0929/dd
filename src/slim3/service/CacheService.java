package slim3.service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.slim3.memcache.Memcache;
import org.slim3.util.IntegerUtil;
import org.slim3.util.StringUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.InvalidValueException;

import slim3.exception.MyException;
import util.CollectionUtils;
import util.StackTraceUtil;
/**
 * 店舗情報を取得するサービスです。
 * @author uedadaiki
 *
 */
public class CacheService{
    /**
     * ロック実行の最大回数
     */
    public static final int LOCK_TRY_MAX_COUNT = 20;

    /**
     * ロックを再試行する間隔
     */
    private static final long LOCK_TRY_INTERVAL = 100L;

    private final static Logger log = Logger.getLogger(CacheService.class.getName());

    // ==================================
    // グラフ キャッシュ名（ 命名規則[ADM/BCC] + アクション + データ名称）
    // ==================================
    /** BCCトップの配送ランキングデータ*/
    public static final String BCC_TOP_BAR_GRAPH_DELIVERY_CACHE = "bccTopBarGraphDeliveryCache_";
    /** BCCトップのアフィリエイトランキングデータ */
    public static final String BCC_TOP_AFFILIATE_RANKING_GRAPH_CACHE = "bccTopAffiliateRankingGraphCache_";
    /** BCCトップのカテゴリーごとの点数データ */
    public static final String BCC_TOP_DELIVERY_DATA_CACHE = "bccTopDeliveryDataCache_";
    /** BCCトップの売り上げグラフ */
    public static final String BCC_TOP_PROCEED_LINE_GRAPH_CACHE = "bccTopProceedLineGraphCache_";
    /** BCCトップの最近の注文データグラフ */
    public static final String BCC_TOP_RENEWAL_PAYMENT_DATA_CACHE= "bccTopPaymentDataCache_";

    /** ADMトップの折れ線グラフの売上データ*/
    public static final String ADM_PROCEED_LINE_GRAPH_CACHE = "admProceedLineGraphCache_";
    /** ADMトップの円グラフ用のカテゴリーデータ*/
    public static final String ADM_CATEGORY_LINE_GRAPH_CACHE = "admCategoryLineGraphCache_";
    /** ADMトップの納品数折れ線データ*/
    public static final String ADM_TOP_DELIVERY_RANKING_BAR_GRAPH_CACHE = "admTopDeliveryRankingBarGraphCache_";
    /** ADMトップの売上折れ線データ*/
    public static final String ADM_TOP_PROCEED_LINE_GRAPH_CACHE = "admTopProceedLineGraphCache_";
    /** ADMトップの棒グラフ用のアフィリエイトデータ（BCC兼用）*/
    public static final String ADM_TOP_AFFILIATE_RANKING_BAR_GRAPH_CACHE = "admTopAffiliateRankingBarGraphCache_";
    /** ADMトップの会員データ*/
    public static final String ADM_TOP_USER_LINE_GRAPH_CACHE = "admTopUserLineGraphCache_";

    /** MWSで取得したASIN毎の最低価格（国別） */
    public static final String MWS_LOWEST_PRICE_FOR_ASIN_COUNTRIES_CACHE = "mwsLowestPriceForAsinCountriesCache_";
    /** MWSで取得した商品情報（国別） */
    public static final String MWS_PRODUCT_COUNTRIES_CACHE = "mwsProductCountriesCache_";

    /** 価格改定 */
    public static final String REVISION_RESERVE_LIST = "REVISION_RESERVE_LIST_";

    // ================================
    // 有効期限区分
    public enum ExpireKbn {
        /** 秒 putする際には、有効期限が1→1秒、10→10秒となる*/
        SECOND(1)
        /** 分 putする際には、有効期限が1→1分、10→10分となる*/
        ,MINUTE(1 * 60)
        /** 時間  putする際には、有効期限が1→1時間、10→10時間となる*/
        ,HOUR( 1 * 60 * 60)
        /** 日  putする際には、有効期限が1→1日、10→10日となる*/
        ,DAY( 1 * 60 * 60 * 24)
        /** 無期限 */
        ,EXCLUDE( null)
        ;
        private Integer value;
        private ExpireKbn(Integer _value ) {
            this.value = _value;
        }
        public Integer getValue() {
            return value;
        }
    }

    /**
     * キャッシュが存在するかをチェックします。
     * ApiProxy系の例外が発生するため、Exceptionが発生した場合は、falseを返却します。
     * @param keyName
     * @return
     */
    public  <T> boolean exist(String key){
        try {
            return Memcache.contains(key);
        } catch (Exception e) {
            //ログを出したところで、エラーがキーに起因しないのでログは出力しない。
            log.fine("cache check abort. key=" + key);
            return false;
        }
    }

    /**
     * キャッシュを登録します。
     * @param キャッシュ登録名
     * @param 登録するオブジェクト
     * @param 有効期限区分
     * @param 保持期間
     *
     * ex)
     *  1時間=ExpireKbn.HOUR, 1
     *  60秒=ExpireKbn.MINUTE, 1
     *
     * 有効期限を指定したくない場合には、ExpireKbn.EXCLUDEで呼び出して下さい。
     */
    public void put(String keyName, Object o, ExpireKbn expireKbn, Integer expire){
        try {
            if(ExpireKbn.EXCLUDE.equals(expireKbn)){
                Memcache.put(keyName, o);
            }else if(expireKbn != null && expire != null){
                int time = expire * expireKbn.getValue();
                Memcache.put(keyName, o, Expiration.byDeltaSeconds(time));
            }else{
                Memcache.put(keyName, o);
            }

        } catch (Exception e) {
            //まれに例外を吐くため保存に失敗しても無視。
            log.fine(StackTraceUtil.toString(e));
        }
        return;
    }



    /**
     * キャッシュに1MBを超えるオブジェクトを登録します。
     * キャッシュ名は分割サイズを値として登録し、キャッシュ名＋SEQで分割したリストを登録します。
     * @param キャッシュ登録名
     * @param 登録するオブジェクト
     * @param 有効期限区分
     * @param 保持期間
     *
     */
    public <T> void putLargeList(String keyName, List<T> o, int divideSize, ExpireKbn expireKbn, Integer expire){

        if(CollectionUtils.isEmpty(o)){
            return ;
        }
        //サイズをセット
        List<List<T>> listList = CollectionUtils.divide(o, divideSize);
        delete(keyName);
        put(keyName, listList.size(), expireKbn, expire);
        log.fine(listList.size() + "分割で親をputしました。");

        int i = 0;
        for (List<T> list : listList) {
            //事前削除
            delete(keyName + i);
            //登録
            put(keyName + i, list, expireKbn, expire);
            i++;
            log.fine(i + "回putしました。");
        }
        return;
    }


    /**
     * 1MB以上を格納したキャッシュを取り出します。
     * ひとつでも欠損している場合にはNullを返却するので、呼び出し側でデータストアから再取得するなり
     * 適宜ロジックを組む必要があります。
     * @param keyName
     * @param clazz
     * @return
     */
    public <T> List<T> getLargeList(String keyName, Class<T> clazz){

        Integer size = getSingle(keyName, Integer.class);
        if(size == null || size == 0){
            log.fine("存在しませんでした。");
            return null;
        }

        List<T> returnList = new ArrayList<T>();
        for (int i = 0; i < size; i++) {
            List<T> tmpList = getList(keyName + i, clazz);
            //欠損している場合はNull
            if(CollectionUtils.isEmpty(tmpList)){
                delete(keyName);
                return null;
            }
            log.fine(tmpList.size() + "件getしました。");
            returnList.addAll(tmpList);

        }
        log.fine(returnList.size() + "件getしました。");
        return  returnList;
    }


    /**
     * キャッシュから取得します。
     * @param <T>
     * @param keyName
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getSingle(String key, Class<T> clazz){
        T ret = null;
        try {
            ret =  (T) Memcache.get(key);
            if(ret == null){
                return null;
            }

            if(!clazz.equals(ret.getClass())){
                throw new ClassCastException("class cast error.");
            }
            return ret;

        } catch (ClassCastException e) {
            //キャストの例外は実装ミスの可能性が高いため例外を出力する。
            log.warning(String.format("ClassCastException clazz[%s] object[%s] key=[%s]", clazz.getSimpleName(), ret.getClass().getSimpleName(), key));
            throw new MyException(e);
        } catch (InvalidValueException e) {
            log.warning(String.format("InvalidValueException clazz[%s] object[%s] key=[%s]", clazz.getSimpleName(), ret.getClass(), key));
            log.warning(StackTraceUtil.toString(e));
            return null;
        } catch (Exception e){
            //ホントは出力したいがでまくるため致し方なくコメントアウト
//            log.warning(String.format("Exception clazz[%s] object[%s] key=[%s]", clazz.getSimpleName(), clazz.getSimpleName(), key));
//            log.warning(StackTraceUtil.toString(e));
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getList(String keyName, Class<T> clazz){

        List<T> dataToReturn = null;
        try {
            dataToReturn = (List<T>) Memcache.get(keyName);
            if(CollectionUtils.isEmpty(dataToReturn)){
                return null;
            }

            if(!clazz.equals(dataToReturn.get(0).getClass())){
                throw new ClassCastException("class cast error.");
            }

        } catch (ClassCastException e) {
            //キャストの例外は実装ミスの可能性が高いため例外を出力する。
            log.warning(String.format("ClassCastException clazz[%s] object[%s] key=[%s]", clazz.getSimpleName(), dataToReturn.getClass().getSimpleName(), keyName));
            throw new MyException(e);
        } catch (Exception e) {
            log.warning("Exception key=" + keyName);
            log.warning(StackTraceUtil.toString(e));
            return null;
        }
        return  dataToReturn;
    }


    /**
     * キャッシュをクリアします。
     * @param キャッシュ名
     */
    public  void delete(String key){
        if(key != null){
            for (int i = 0; i < 3; i++) {
                try {
                    Memcache.delete(key);
                    return ;
                } catch (Exception e) {
                    try {
                        Thread.sleep(1000L);
                    } catch (Exception e2) {
                    }
                }
            }
            log.warning("cache delete abort. key=" + key);
            return ;
        }
    }


    /**
     * キャッシュをインクリメントします。
     * @param keyName
     * @return
     */
    public Long increment(String keyName){

        for (int i = 0; i < 3; i++) {
            try {
                Long increment = Memcache.increment(keyName, 1L, 1L);
                log.fine(String.format(keyName + "をインクリメントしました。[%s]" ,NumberFormat.getNumberInstance().format(IntegerUtil.toPrimitiveInt(increment))));
                return increment;
            } catch (Exception e) {
                try {
                    Thread.sleep(100L);
                } catch (Exception e2) {
                }
                log.warning("increment is abort." + keyName);
            }
        }
        throw new MyException("increment is abort." + keyName);
    }

    /**
     * 排他用のキャッシュを初期化します。
     * @param keyName
     * @return
     */
    public void excludeInit(String lockKey, String cacheName){
        delete(lockKey);
        delete(cacheName);
        return ;
    }

    /**
     * 引数のargsをアンダースコア区切りで連結してキャッシュキーを生成します。
     * @param cacheAction
     * @param args
     * @return keyName
     */
    public static String createCacheKey(Object... args){
        StringBuffer keyName = new StringBuffer();
        for (Object obj : args) {
            if(obj.getClass().equals(Key.class)){
                keyName.append(KeyFactory.keyToString((Key)obj) + "_");
            }else{
                keyName.append(StringUtil.toString(obj) + "_");
            }
        }
        return keyName.toString();
    }

    /**
     * キャッシュに要素を追加します。
     * 排他を行いList.addします。マルチスレッド時以外利用しません。
     * @param lockKey
     * @param obj
     * @param clazz
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> addList(String lockKey, String cacheKey, Object obj, Class<T> clazz) {

        // ロックを取得
        if (tryLock(lockKey, LOCK_TRY_MAX_COUNT)) {
            List<T> list = getList(cacheKey, clazz);
            try {
                if(list == null){
                    log.fine("リストが空のため初期化します。");
                    list = new ArrayList<T>();
                }
                //要素を追加する
                list.add((T)obj);
                Memcache.put(cacheKey, list);
            } catch (Exception e) {
                log.warning("Lockが取得出来ませんでした。");
                log.warning(StackTraceUtil.toString(e));
                throw new MyException(e);
            } finally {
                // ロックを開放する
                decrement(lockKey);
            }
            return list;
        } else {
            throw new LockFailed();
        }
    }

    /**
     * 指定回数までロックの取得を試行
     * @param lockKey ロックに使用するキー
     * @param maxTryCount ロックの試行回数
     * @return true:ロック成功, false:ロック失敗
     */
    public boolean tryLock(Object lockKey, int maxTryCount) {
        boolean locked = false;
        int tryCount = 0;

        while ((locked = acquireLock(lockKey)) == false) {
            tryCount++;

            if(tryCount >= maxTryCount){
                break;
            }
            try {
                Thread.sleep(LOCK_TRY_INTERVAL);
            } catch (InterruptedException e) {
                log.warning(StackTraceUtil.toString(e));
            }
        }
        if(!locked){
            throw new MyException("lock error ロックが取得できませんでした。" + lockKey);
        }
        return locked;
    }

    /**
     * 指定されたキーに対するロックを取得します。
     *
     * @param lockKey ロックを識別するキー
     * @return true:ロック成功 false:ロック失敗
     */
    private boolean acquireLock(Object lockKey) {
        try {
            Long lockValue = Memcache.increment(lockKey, 1L, 0L);

            if(lockValue.longValue() == 1L){
                return true;
            }else{
                //元にもどす。
                Memcache.put(lockKey, 0L);
                return false;
            }

        } catch (Exception e) {
            //元にもどす。
            try {
                Thread.sleep(LOCK_TRY_INTERVAL);
                Memcache.put(lockKey, 0L);
            } catch (Exception e2) {
                log.warning(StackTraceUtil.toString(e));
                log.fine("Cache initialize error");
            }
            return false;
        }
    }

    /**
     * 指定されたキーに対するロックを開放します。
     * @param lockKey ロックを識別するキー
     */
    public void decrement(Object lockKey) {


        for (int i = 0; i < 3; i++) {
            try {
                Memcache.increment(lockKey, -1L);
                return ;
            } catch (Exception e) {
                try {
                    Thread.sleep(100L);
                } catch (Exception e2) {
                }
                log.warning("decrement is abort." + lockKey);
            }
        }
        throw new MyException("decrement is abort." + lockKey);
    }

    /**
     * 排他エラー時の例外クラス
     */
    @SuppressWarnings("serial")
    public static final class LockFailed extends RuntimeException {
    }

}