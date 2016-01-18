package slim3.controller.tools.rese.reserve;

import java.util.List;

import org.slim3.controller.Navigation;

import com.google.appengine.api.datastore.Key;

import slim3.controller.tools.rese.AbstractReseController;
import slim3.model.MsUser;
import slim3.model.customerManage.Customer;
import slim3.model.reserve.Menu;
import slim3.model.reserve.MenuPage;

/**
 * 予約作成ページを表示するコントローラです。
 * @author uedadaiki
 *
 */
public class CreateReserveController extends AbstractReseController {

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
        
//        Key menuPageKey = asKey("menuPageKey");
//        List<Menu> menuList = menuService.getListByMenuPageKey(menuPageKey);
//        MenuPage menuPage = menuPageService.get(menuPageKey);
        //TODO ソートする
        List<Customer> customerList = customerService.getByMsUser(msUser);
        List<MenuPage> menuPageList = menuPageService.getByMsUser(msUser);
        
        request.setAttribute("customerList", customerList);
        request.setAttribute("menuPageList", menuPageList);
//        request.setAttribute("menuList", menuList);
                
        return forward("/tools/rese/reserve/createReserve.jsp");
    }
}
