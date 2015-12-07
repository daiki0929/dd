package slim3.controller.tools.userManage.reserve;

import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.controller.AbstractController;
import slim3.meta.reserve.MenuMeta;
import slim3.model.reserve.Menu;
import slim3.model.reserve.MenuPage;
/**
 * メニューの一覧を表示します。
 * @author uedadaiki
 *
 */
public class MenuListController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        Key menuPageKey = asKey("id");
        MenuPage menuPage = menuPageService.get(menuPageKey);
        
        //ユーザーが所持するメニューを取り出す
        MenuMeta menuMeta = MenuMeta.get();
        List<Menu> menuList = Datastore
                .query(menuMeta)
                .filter(menuMeta.menuPageRef.equal(menuPage.getKey()))
                .asList();
        
        log.info("menuListをセット：" + menuList.toString());
        request.setAttribute("menuList", menuList);
        
        request.setAttribute("menuPageKey", menuPageKey);
        
        return forward("menuList.jsp");
    }
}
