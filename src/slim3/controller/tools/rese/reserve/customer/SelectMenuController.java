package slim3.controller.tools.rese.reserve.customer;

import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

import slim3.controller.tools.rese.AbstractReseController;
import slim3.meta.MsUserMeta;
import slim3.meta.reserve.MenuMeta;
import slim3.meta.reserve.MenuPageMeta;
import slim3.model.MsUser;
import slim3.model.reserve.Menu;
import slim3.model.reserve.MenuPage;
/**
 * カスタマーがメニューを選択する画面を表示します。
 * @author uedadaiki
 *
 */
public class SelectMenuController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        String pagePath = asString("pagePath");
//        String userPath = asString("userPath");
        
//        
//        if (pagePath == null) {
//            log.info("メニューページのkeyを取得出来ませんでした。");
//            return forward("/tools/rese/reserve/createMenuPage.jsp");
//        }
//        

        MenuPageMeta menuPageMeta = MenuPageMeta.get();
        MenuPage menuPage = Datastore
                .query(menuPageMeta)
                .filter(menuPageMeta.pagePath.equal(pagePath))
                .asSingle();
        
        MenuMeta menuMeta = MenuMeta.get();
        List<Menu> menuList = Datastore
                .query(menuMeta)
                .filter(menuMeta.menuPageRef.equal(menuPage.getKey()))
                .asList();
        
        //ユーザーID
//        ModelRef<MsUser> msUser = menuPage.getMsUserRef();
//        Key msUserKey = msUser.getKey();
        MsUserMeta msUserMeta = MsUserMeta.get();
        MsUser msUser = Datastore
                .query(msUserMeta)
                .filter(msUserMeta.key.equal(menuPage.getMsUserRef().getKey()))
                .asSingle();
        
        request.setAttribute("msUser", msUser);
        request.setAttribute("menuPage", menuPage);
        request.setAttribute("menuList", menuList);
        
        return forward("selectMenu.jsp");
    }
}
