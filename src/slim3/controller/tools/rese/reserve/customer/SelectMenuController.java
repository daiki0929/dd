package slim3.controller.tools.rese.reserve.customer;

import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import slim3.controller.tools.rese.AbstractReseController;
import slim3.meta.MsUserMeta;
import slim3.meta.reserve.MenuMeta;
import slim3.meta.reserve.MenuPageMeta;
import slim3.model.MsUser;
import slim3.model.MsUser.Role;
import slim3.model.reserve.Menu;
import slim3.model.reserve.MenuPage;
import slim3.model.reserve.Reserve;
/**
 * カスタマーがメニューを選択する画面を表示します。
 * @author uedadaiki
 *
 */
//TODO showmenuなど
public class SelectMenuController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        String pagePath = asString("pagePath");

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
        
        MsUserMeta msUserMeta = MsUserMeta.get();
        MsUser msUser = Datastore
                .query(msUserMeta)
                .filter(msUserMeta.key.equal(menuPage.getMsUserRef().getKey()))
                .asSingle();
        
        request.setAttribute("msUser", msUser);
        request.setAttribute("menuPage", menuPage);
        request.setAttribute("menuList", menuList);
        
        
        //制限の確認
        List<Reserve> reserveList = reserveService.getListByMsUserKey(msUser.getKey());
        request.setAttribute("limitOver", false);
        if (msUser.getRole() == Role.FREE) {
            if (reserveList.size() > 50) {
                request.setAttribute("limitOver", true);
            }
        }
        if (msUser.getRole() == Role.PRO) {
            if (reserveList.size() > 200) {
                request.setAttribute("limitOver", true);
            }
        }
        
        return forward("selectMenu.jsp");
    }
}
