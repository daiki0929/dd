package slim3.service.datastore;

import java.util.logging.Logger;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelMeta;

import com.google.appengine.api.datastore.Key;

public class DsService {
    protected final static Logger log = Logger.getLogger(DsService.class.getName());
    
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
    
    
    //TODO ユーザーが顧客にメールを送信出来るようにする。
}