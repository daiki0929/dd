package slim3.service.datastore;

import com.google.appengine.api.datastore.Key;

import slim3.meta.reserve.MenuPageMeta;
import slim3.model.reserve.MenuPage;
/**
 * メニューページを取得するサービスです。
 * @author uedadaiki
 *
 */
public class MenuPageService extends AbstractDatastoreService{
    
    
    /**
     * メニューページをkeyで1つ取得する。
     * @param id
     * @return
     */
    public MenuPage get(Key id){
        return dsService.getSingle(MenuPage.class, MenuPageMeta.get(),id);
    }
    
    
    
}