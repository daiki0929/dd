package slim3.controller.tools.rese.reserve.customer;

import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

import slim3.controller.AbstractController;
import slim3.meta.reserve.MenuMeta;
import slim3.model.MsUser;
import slim3.model.reserve.Menu;
import slim3.model.reserve.MenuPage;
/**
 * カスタマーがメニューを選択する画面を表示します。
 * @author uedadaiki
 *
 */
public class SelectMenuController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        Key menuPageKey = asKey("id");
        if (menuPageKey == null) {
            log.info("メニューページのkeyを取得出来ませんでした。");
            return forward("/tools/rese/reserve/createMenuPage.jsp");
        }
        MenuPage menuPage = menuPageService.get(menuPageKey);
        
        //ユーザーが所持するメニューを取り出す
        MenuMeta menuMeta = MenuMeta.get();
        List<Menu> menuList = Datastore
                .query(menuMeta)
                .filter(menuMeta.menuPageRef.equal(menuPage.getKey()))
                .asList();
        
        //ユーザーID
        ModelRef<MsUser> msUser = menuPage.getMsUserRef();
        Key msUserKey = msUser.getKey();
        
        request.setAttribute("msUserKey", msUserKey);
        request.setAttribute("menuPage", menuPage);
        request.setAttribute("menuList", menuList);
        
        return forward("selectMenu.jsp");
    }
}
