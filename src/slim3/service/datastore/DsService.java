package slim3.service.datastore;

import java.util.logging.Logger;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelMeta;

import com.google.appengine.api.datastore.Key;
/**
 * データストアを扱うサービスです。
 * @author uedadaiki
 *
 */
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
        log.fine("datastore");
        Object obj = Datastore.getOrNull(meta, id);
        return (T)obj;
    }
    
    
}