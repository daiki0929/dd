package slim3.service.datastore.rese;

import java.util.List;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.meta.reserve.MenuMeta;
import slim3.model.reserve.Menu;
import slim3.service.datastore.AbstractDatastoreService;
/**
 * メニューを取得するサービスです。
 * @author uedadaiki
 *
 */
public class MenuService extends AbstractDatastoreService{
    
    private final static MenuMeta MENU_META = MenuMeta.get();
    
    /**
     * メニューをkeyで1つ取得する。
     * @param id
     * @return
     */
    public Menu get(Key id){
        return dsService.getSingle(Menu.class, MenuMeta.get(),id);
    }
    
    /**
     * メニューページのkeyでメニューを全て取得
     * @param menuPageKey
     * @return
     */
    public List<Menu> getListByMenuPageKey(Key menuPageKey){
        return Datastore.query(MENU_META).filter(MENU_META.menuPageRef.equal(menuPageKey)).asList();
    }
    
    
    
}