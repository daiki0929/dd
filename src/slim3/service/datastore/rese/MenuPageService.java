package slim3.service.datastore.rese;

import java.util.List;

import com.google.appengine.api.datastore.Key;

import slim3.meta.reserve.MenuPageMeta;
import slim3.model.MsUser;
import slim3.model.reserve.MenuPage;
import slim3.service.datastore.AbstractDatastoreService;
/**
 * メニューページを取得するサービスです。
 * @author uedadaiki
 *
 */
public class MenuPageService extends AbstractDatastoreService{
    
    private final static MenuPageMeta MENUPAGE_META = MenuPageMeta.get();
    
    /**
     * メニューページをkeyで1つ取得する。
     * @param id
     * @return
     */
    public MenuPage get(Key id){
        return dsService.getSingle(MenuPage.class, MenuPageMeta.get(),id);
    }
    
    public MenuPage getInPublic(Key id){
        return dsService.getSingle(MenuPage.class, MenuPageMeta.get(),id);
    }
    
    /**
     * MsUserでメニューページを全て取り出します。
     * @param msUser
     * @return
     */
    public List<MenuPage> getByMsUser(MsUser msUser){
        return dsService.getList(MenuPage.class, MENUPAGE_META, null, MENUPAGE_META.msUserRef.equal(msUser.getKey()));
    }
    
    
    
    
}