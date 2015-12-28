package slim3.controller.tools.rese.reserve;

import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import slim3.Const;
import slim3.controller.AbstractController;
import slim3.meta.reserve.MenuPageMeta;
import slim3.model.MsUser;
import slim3.model.reserve.MenuPage;
/**
 * メニューページの一覧を表示します。
 * @author uedadaiki
 *
 */
public class MenuPageListController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }
        
        //データベースからクッキー情報(userId)でデータを1つ取得。
        MsUser msUser = msUserService.getSingleByCookie(request, Const.MS_AUTH_COOKIE_NAME, MS_USER_META);
        if (msUser == null) {
            return forward("/tools/rese/comeAndGo/login");
        }
        
        //ユーザーが所持するメニューページを取り出す
        MenuPageMeta menuPageMeta = MenuPageMeta.get();
        List<MenuPage> menuPageList = Datastore
                .query(menuPageMeta)
                .filter(menuPageMeta.msUserRef.equal(msUser.getKey()))
                .asList();
        request.setAttribute("menuPageList", menuPageList);
        
        
        return forward("menuPageList.jsp");
    }
}
