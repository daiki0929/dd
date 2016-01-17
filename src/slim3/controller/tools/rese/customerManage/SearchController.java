package slim3.controller.tools.rese.customerManage;

import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.Const;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.meta.MsUserMeta;
import slim3.meta.customerManage.CustomerMeta;
import slim3.meta.reserve.MenuPageMeta;
import slim3.model.MsUser;
import slim3.model.customerManage.Customer;
import slim3.model.reserve.MenuPage;
import util.CookieUtil;
/**
 * カスタマーを検索します。
 * @author uedadaiki
 *
 */
public class SearchController extends AbstractReseController {
    
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
        
        //ユーザー情報
        request.setAttribute("msUser", msUser);
        
        MenuPageMeta menuPageMeta = MenuPageMeta.get();
        List<MenuPage> menuPageList = Datastore
                .query(menuPageMeta)
                .filter(menuPageMeta.msUserRef.equal(msUser.getKey()))
                .asList();
        
        request.setAttribute("menuPageList", menuPageList);
        
        //ユーザーが所持する顧客情報を取り出す
        CustomerMeta customerMeta = CustomerMeta.get();
        if (asKey("id") != null) {
            log.info("予約ページで絞り込みます。");
            Key menuPageKey = asKey("id");
            List<Customer> customerList = Datastore
                    .query(customerMeta)
                    .filter(customerMeta.MsUserRef.equal(msUser.getKey()))
                    .filter(customerMeta.MenuPageRef.equal(menuPageKey))
                    .asList();
            request.setAttribute("customerList", customerList);
            request.setAttribute("menuPageKey", menuPageKey);
            
            return forward("customerList.jsp");
        }else {
            List<Customer> customerList = Datastore
                    .query(customerMeta)
                    .filter(customerMeta.MsUserRef.equal(msUser.getKey()))
                    .asList();
            request.setAttribute("customerList", customerList);
        }
        return forward("customerList.jsp");
    }
}
