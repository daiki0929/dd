package slim3.service.datastore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.FilterCriterion;
import org.slim3.datastore.ModelMeta;
import org.slim3.datastore.ModelQuery;
import org.slim3.datastore.SortCriterion;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import slim3.model.AbstractModel;
import slim3.service.CacheService;
import slim3.service.CacheService.ExpireKbn;
import util.CollectionUtils;
/**
 * データストアを扱うサービスです。
 * @author uedadaiki
 *
 */
public class DsService {

    /** キャッシュサービス */
    private CacheService cacheService = new CacheService();
    private final static Logger log = Logger.getLogger(CacheService.class.getName());
    public String CACHE_NAME = "%s_%s";
    private static final int DEFAULT_CACHE_ALIVE_HOUR = 8;
    private static final int FETCH_SIZE = 3000;


    public <T extends AbstractModel> Key put(T dto){
        return put(dto, false, ExpireKbn.HOUR, DEFAULT_CACHE_ALIVE_HOUR);
    }

    /**
     * データストアに書き込みます。
     * @param 更新したいエンティティ
     * @param id
     * @param 非同期にするかどうか（True：非同期/False：同期）
     */
    public <T extends AbstractModel> Key put(T dto, boolean async){
        return put(dto, async, ExpireKbn.HOUR, DEFAULT_CACHE_ALIVE_HOUR);
    }

    /**
     * データストアに書き込みます。
     * @param 更新したいエンティティ
     * @param 非同期にするかどうか（True：非同期/False：同期）
     * @param 期限区分
     * @param 期限
     * @return
     */
    public <T extends AbstractModel> Key put(T dto, boolean async, ExpireKbn expireKbn, Integer expire){

        Key key = null;
        if(async){
            log.fine("非同期で保存しました。");
            Datastore.putAsync(dto);
        }else{
            log.fine("同期で保存しました。");
            key = Datastore.put(dto);
        }

        if(key != null){
            String cacheKey = createCacheKey(key);
            if(cacheService.exist(cacheKey)){
                cacheService.delete(cacheKey);
            }

//          cacheService.put(
//                  createCacheKey(dto.getId()),
//                  dto,
//                  expireKbn,
//                  expire
//                  );
        }
        return key;
    }


    /**
     * データストアに書き込みます。
     * ※Entityとして保存されるためキャッシュは行いません。
     * @param 更新したいエンティティ
     * @param 期限区分
     * @param 期限
     * @return
     */
    public Key put(Entity dto, ExpireKbn expireKbn, Integer expire){
        log.fine("同期で保存しました。");
        Key key = Datastore.put(dto);
        if(key != null){
            //キャッシュがあれば削除する
            String cacheKey = createCacheKey(key);
            if(cacheService.exist(cacheKey)){
                cacheService.delete(cacheKey);
            }
        }
        return key;
    }

    /**
     * リストをデータストアに保存します。
     * 同期して保存します。
     * @param Collection
     */
    public <T extends AbstractModel> void putList(Collection<T> list){
        putList(list, false,ExpireKbn.HOUR, DEFAULT_CACHE_ALIVE_HOUR);
    }

    /**
     * リストをデータストアに保存します。
     * @param Collection
     * @param 非同期にするかどうか（True：非同期/False：同期）
     */
    public <T extends AbstractModel> void putList(Collection<T> list, boolean async){
        putList(list, async,ExpireKbn.HOUR, DEFAULT_CACHE_ALIVE_HOUR);
    }



    /**
     * リストをデータストアに書き込みます。
     * @param Collection
     * @param 非同期にするかどうか（True：非同期/False：同期）
     * @param 期限区分
     * @param 期限
     */
    public <T extends AbstractModel> void putList(Collection<T> list, boolean async, ExpireKbn expireKbn, Integer expire){

        //ListはまとめてPut
        if(async){
            Datastore.putAsync(list);
        }else{
            Datastore.put(list);
        }
        //キャッシュは1件づつ
        for (AbstractModel t : list) {
            if(t.getKey() != null){
                cacheService.delete(createCacheKey(t.getKey()));
            }
        }
    }


    /**
     * データストアから取得します。
     * @param 返却するエンティティのClass
     * @param Metaクラス
     * @param キー
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getSingle(Class<T> clazz, ModelMeta<?> meta, Key id){

        if(id == null){
            return null;
        }
        //TODO キャッシュが遅いのでやめる
//        String cacheKey = createCacheKey(id);
        //キャッシュから取得、ない場合はDB
//        if(cacheService.exist(cacheKey)){
//          log.fine("cache");
//          try {
//              return cacheService.getSingle(cacheKey, clazz);
//          } catch (Exception e) {
//              e.printStackTrace();
//          }
//        }
        log.fine("datastore");
        Object obj = Datastore.getOrNull(meta, id);
        //キャッシュに登録
//        if(obj != null){
//          cacheService.put(cacheKey, obj, ExpireKbn.HOUR, 12);
//        }
        return (T)obj;
    }


    /**
     * データを1件取得します。
     * @param 取得するエンティティ
     * @param METAクラス
     * @param ソート（Inメモリは使いません。）
     * @param フィルター
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T,M> T get(Class<T> clazz, ModelMeta<M> meta, SortCriterion sort, FilterCriterion... filterCriterion) {

        QueryDto queryDto = createQuery(Type.LIST, meta, sort, filterCriterion);
        if(cacheService.exist(queryDto.getCacheName())){
            return cacheService.getSingle(queryDto.getCacheName(), clazz);
        }

        Object obj = queryDto.getQuery().asSingle();
        cacheService.put(queryDto.getCacheName(), obj, ExpireKbn.HOUR, DEFAULT_CACHE_ALIVE_HOUR);
        return (T)obj;
    }

    /**
     * キャッシュをクリアします。
     * @param id
     */
    public void cacheClear(Key id){
        String createCacheKey = createCacheKey(id);
        if(cacheService.exist(createCacheKey)){
            log.fine("キャッシュをクリアしました");
            cacheService.delete(createCacheKey);
        }
    }

    /**
     * データを削除します。
     * @param id
     */
    public void delete(Key id){
        delete(id, false);
    }
    /**
     * データを削除します。
     * @param キー
     * @param 非同期にするかどうか
     */
    public void delete(Key id, boolean async){
        if(id == null){
            return;
        }
        if(async){
            Datastore.deleteAsync(id);
        }else{
            Datastore.delete(id);
        }
        String createCacheKey = createCacheKey(id);
        if(cacheService.exist(createCacheKey)){
            cacheService.delete(createCacheKey);
        }
    }

    /**
     * エンティティをリストで削除します。
     * @param idList
     */
    public void deleteList(Collection<Key> idList) {
        deleteList(idList, false);
    }

    /**
     * エンティティをリストで削除します。
     * @param idList
     * @param 非同期にするかどうか
     */
    public void deleteList(Collection<Key> idList, boolean async) {
        if(CollectionUtils.isEmpty(idList)){
            return ;
        }
        if(async){
            Datastore.deleteAsync(idList);
        }else{
            Datastore.delete(idList);
        }
        for (Key id : idList) {
            cacheClear(id);
        }
    }


    /**
     * キーのリストで取得します。
     * @param METAクラス
     * @return
     */
    public <T,M> List<Key> getKeyList(ModelMeta<M> meta) {
        return getKeyList(meta, null);
    }


    /**
     * キーリストを取得します。InMemoryは利用しません。
     * @param METAクラス
     * @param ソート
     * @param フィルター
     * @return
     */
    public <T> List<Key> getKeyList(ModelMeta<T> meta, SortCriterion sort, FilterCriterion... filterCriterion) {

        QueryDto queryDto = createQuery(Type.LIST, meta, sort, filterCriterion);
        if(cacheService.exist(queryDto.getCacheName())){
            return cacheService.getList(queryDto.getCacheName(), Key.class);
        }

        List<Key> asList = queryDto
                .getQuery()
                .prefetchSize(FETCH_SIZE)
                .chunkSize(FETCH_SIZE)
                .asKeyList();
        cacheService.put(queryDto.getCacheName(), asList, ExpireKbn.HOUR, DEFAULT_CACHE_ALIVE_HOUR);
        return asList;
    }

    /**
     * リストを取得します。
     * @param 取得するエンティティクラス
     * @param METAクラス
     * @return
     */
    public <T,M> List<T> getList(Class<T> clazz, ModelMeta<M> meta) {
        return getList(clazz, meta, null);
    }


    /**
     * リストを取得します。
     * @param 取得するエンティティ
     * @param METAクラス
     * @param ソート
     * @param フィルター
     * @return
     */
    public <T,M> List<T> getList(Class<T> clazz, ModelMeta<M> meta, SortCriterion sort, FilterCriterion... filterCriterion) {
        //キャッシュの名前、クエリが詰まって返ってくる
        QueryDto queryDto = createQuery(Type.LIST, meta, sort, filterCriterion);

        List<Key> keyList = queryDto
                .getQuery()
                .prefetchSize(FETCH_SIZE)
                .chunkSize(FETCH_SIZE)
                .asKeyList();

        List<T> retList = new ArrayList<>();
        for (Key t : keyList) {
            T dto = getSingle(clazz, meta, t);
            if(dto != null){
                retList.add(dto);
            }
        }
        return retList;
    }


    /**
     * クエリーとキャッシュ名を組み立てます。
     * 一度Queryに変換すると、詰めた内容が取り出せないためクエリー組み立てと同時にキャッシュ名を組みてています。
     * @param single
     * @param clazz
     * @param meta
     * @param sort
     * @param filterCriterion
     * @return
     */
    private <T,M> QueryDto createQuery(Type type, ModelMeta<M> meta, SortCriterion sort, FilterCriterion... filterCriterion) {

        QueryDto queryDto = new QueryDto();
        StringBuffer sb = new StringBuffer();
        if(type.equals(Type.LIST)){
            sb.append("List");
        }else if(type.equals(Type.KEY_LIST)){
            sb.append("KeyList");
        }

        ModelQuery<M> query = Datastore.query(meta);
        if(sort != null){
            query.sort(sort);
            sb.append("_" + sort.toString().replaceAll(" ", "_"));
        }

        if(filterCriterion != null){
            for (FilterCriterion s : filterCriterion) {
                query.filter(s);
                org.slim3.datastore.Filter[] arr = s.getFilters();
                for (org.slim3.datastore.Filter filter : arr) {
                    sb.append("_" + filter.toString().replaceAll(" ", ""));
                }
            }
        }
        //TODO getFiltersがエラーになる。
//        if(filterCriterion != null){
//            for (FilterCriterion s : filterCriterion) {
//                query.filter(s);
//                Filter[] arr = s.getFilters();
//                for (Filter filter : arr) {
//                    sb.append("_" + filter.toString().replaceAll(" ", ""));
//                }
//            }
//        }
        queryDto.setQuery(query);
        queryDto.setCacheName(sb.toString());
        return queryDto;
    }
    

    private String createCacheKey(Key id){
        String keyName = String.format(CACHE_NAME, id.getKind(), KeyFactory.keyToString(id));
        return keyName;
    }

    private class QueryDto{
        private ModelQuery<?> query;
        private String cacheName;

        public ModelQuery<?> getQuery() {
            return query;
        }
        public void setQuery(ModelQuery<?> query) {
            this.query = query;
        }
        public String getCacheName() {
            return cacheName;
        }
        public void setCacheName(String cacheName) {
            this.cacheName = cacheName;
        }
    }

    private enum Type{
        SINGLE,
        LIST,
        KEY_LIST;
    }

}