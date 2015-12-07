package slim3.service.datastore;

import com.google.appengine.api.datastore.Key;

import slim3.meta.reserve.MenuMeta;
import slim3.model.reserve.Menu;
/**
 * メニューを取得するサービスです。
 * @author uedadaiki
 *
 */
public class MenuService extends AbstractDatastoreService{
    
    /**
     * メニューをkeyで1つ取得する。
     * @param id
     * @return
     */
    public Menu get(Key id){
        return dsService.getSingle(Menu.class, MenuMeta.get(),id);
    }
    
    
    
}