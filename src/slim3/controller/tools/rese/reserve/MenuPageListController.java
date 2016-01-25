package slim3.controller.tools.rese.reserve;

import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import slim3.controller.tools.rese.AbstractReseController;
import slim3.meta.reserve.MenuPageMeta;
import slim3.model.MsUser;
import slim3.model.reserve.MenuPage;
/**
 * メニューページの一覧を表示します。
 * @author uedadaiki
 *
 */
public class MenuPageListController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }
        
        //データベースからクッキー情報(userId)でデータを1つ取得。
        MsUser msUser = msUserService.getSingleByCookie(request);
        if (msUser == null) {
            log.info("ユーザー情報がありませんでした。");
            return forward("/tools/rese/comeAndGo/login");
        }
        request.setAttribute("msUser", msUser);
        
        //ユーザーが所持するメニューページを取り出す
        MenuPageMeta menuPageMeta = MenuPageMeta.get();
        List<MenuPage> openMenuPageList = Datastore
                .query(menuPageMeta)
                .filter(menuPageMeta.msUserRef.equal(msUser.getKey()))
                .filter(menuPageMeta.status.equal(PUBLIC))
                .asList();
        request.setAttribute("openMenuPageList", openMenuPageList);
        
        List<MenuPage> closedMenuPageList = Datastore
                .query(menuPageMeta)
                .filter(menuPageMeta.msUserRef.equal(msUser.getKey()))
                .filter(menuPageMeta.status.equal(CLOSED))
                .asList();
        request.setAttribute("closedMenuPageList", closedMenuPageList);
        
        
        return forward("/tools/rese/dashboard/reserve/menuPageList.jsp");
    }
}
