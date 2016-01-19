package slim3.controller.tools.rese.customerManage;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.controller.tools.rese.AbstractReseController;
import slim3.meta.customerManage.CustomerMeta;
import slim3.model.MsUser;
import slim3.model.customerManage.Customer;
import slim3.model.reserve.MenuPage;
/**
 * カスタマーの一覧を表示します。
 * @author uedadaiki
 *
 */
public class CustomerListController extends AbstractReseController {
    
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
        
        List<MenuPage> menuPageList = menuPageService.getByMsUser(msUser.getKey());
        
        request.setAttribute("menuPageList", menuPageList);
        
        //ユーザーが所持する顧客情報を取り出す
        CustomerMeta customerMeta = CustomerMeta.get();
        if (asKey("p") != null) {
            log.info("予約ページで絞り込みます。");
            Key menuPageKey = asKey("p");
            List<Customer> customerList = Datastore
                    .query(customerMeta)
                    .filter(customerMeta.MsUserRef.equal(msUser.getKey()))
                    .filter(customerMeta.MenuPageRef.equal(menuPageKey))
                    .asList();
            
            request.setAttribute("customerList", customerList);
            request.setAttribute("menuPageKey", menuPageKey);
            
            return forward("customerList.jsp");
        }else if (asString("s") != null) {
            log.info("名前・メールアドレス・電話番号で絞り込みます。");
            String searchKw = asString("s");
            ArrayList<Customer> customerList = new ArrayList<Customer>();
            
            ArrayList<List<Customer>> filterCustomerList = new ArrayList<List<Customer>>();
            //名前、メールアドレス、電話番号で検索
            List<Customer> customerListByName = Datastore
                    .query(customerMeta)
                    .filterInMemory(customerMeta.name.contains(searchKw))
                    .asList();
            List<Customer> customerListByMail = Datastore
                    .query(customerMeta)
                    .filterInMemory(customerMeta.mailaddress.contains(searchKw))
                    .asList();
            List<Customer> customerListByPhone = Datastore
                    .query(customerMeta)
                    .filterInMemory(customerMeta.phone.contains(searchKw))
                    .asList();
            filterCustomerList.add(customerListByName);
            filterCustomerList.add(customerListByMail);
            filterCustomerList.add(customerListByPhone);
            
            for (List<Customer> l : filterCustomerList) {
                for (Customer customer : l) {
//                    log.info("名前：" + customer.getName());
                    customerList.add(customer);
                }
            }
            request.setAttribute("customerList", customerList);
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
