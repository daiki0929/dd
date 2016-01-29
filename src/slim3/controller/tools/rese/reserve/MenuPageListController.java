package slim3.controller.tools.rese.reserve;

import java.util.List;

import org.slim3.controller.Navigation;

import slim3.controller.tools.rese.AbstractReseController;
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
        List<MenuPage> openMenuPageList = menuPageService.getByMsUser(msUser.getKey(), MenuPageStatus.PUBLIC);
        request.setAttribute("openMenuPageList", openMenuPageList);
        
        List<MenuPage> closedMenuPageList = menuPageService.getByMsUser(msUser.getKey(), MenuPageStatus.CLOSED);
        request.setAttribute("closedMenuPageList", closedMenuPageList);
        
        int openListSize = openMenuPageList.size();
        request.setAttribute("limitOver", false);
        //有料会員
        if (msUser.getRole() == MsUser.Role.PRO) {
            request.setAttribute("role", MsUser.Role.PRO);
            if (openListSize >= 20) {
                request.setAttribute("limitOver", true);
            }
        }
        //無料会員
        if (msUser.getRole() == MsUser.Role.FREE) {
            if (openListSize >= 5) {
                request.setAttribute("limitOver", true);
            }
        }
        request.setAttribute("openListSize", openListSize);
        return forward("/tools/rese/dashboard/reserve/menuPageList.jsp");
    }
}
