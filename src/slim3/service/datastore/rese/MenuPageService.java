package slim3.service.datastore.rese;

import java.util.List;

import com.google.appengine.api.datastore.Key;

import slim3.controller.tools.rese.AbstractReseController.MenuPageStatus;
import slim3.meta.reserve.MenuPageMeta;
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
     * MsUserのKeyでメニューページを全て取り出します。
     * @param msUser
     * @return
     */
    public List<MenuPage> getByMsUser(Key msUserKey){
        return dsService.getList(MenuPage.class, MENUPAGE_META, null, MENUPAGE_META.msUserRef.equal(msUserKey));
    }
    
    /**
     * MsUseのKeyでメニューページを取り出します。
     * @param msUserKey
     * @param status
     * @return
     */
    public List<MenuPage> getByMsUser(Key msUserKey, MenuPageStatus status){
        return dsService.getList(MenuPage.class, MENUPAGE_META, null, MENUPAGE_META.msUserRef.equal(msUserKey), MENUPAGE_META.status.equal(status.getMenuPageStatus()));
    }
    
    
    
    
}