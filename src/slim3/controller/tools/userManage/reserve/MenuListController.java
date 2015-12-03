package slim3.controller.tools.userManage.reserve;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import slim3.controller.AbstractController;
import slim3.controller.Const;
import slim3.meta.reserve.ManageUserMeta;
import slim3.meta.reserve.MenuPageMeta;
import slim3.model.MsUser;
import slim3.model.reserve.ManageUser;
import slim3.model.reserve.MenuPage;
import util.CookieUtil;
import util.StackTraceUtil;

public class MenuListController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            //TODO リクエストに応じたログイン画面を返す。AbstractController showLoginPage()
            return forward("/tools/userManage/login");
        }
        
      //データベースからクッキー情報(userId)でデータを1つ取得。
        MsUser msUser = msUserService.getSingleByCookie(request, Const.MS_AUTH_COOKIE_NAME, MS_USER_META);
        if (msUser == null) {
            return forward("/tools/userManage/login");
        }
        
        //ユーザーが所持するメニューページを取り出す
        MenuPageMeta menuPageMeta = MenuPageMeta.get();
        List<MenuPage> menuPageList = Datastore
                .query(menuPageMeta)
                .filter(menuPageMeta.msUserRef.equal(msUser.getKey()))
                .asList();
        //TODO menuPageListを暗号化して渡す。
        request.setAttribute("menuPageList", menuPageList);
        
        
        return forward("menuList.jsp");
    }
}
