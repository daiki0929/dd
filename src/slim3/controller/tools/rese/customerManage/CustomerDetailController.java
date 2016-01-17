package slim3.controller.tools.rese.customerManage;

import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.controller.tools.rese.AbstractReseController;
import slim3.meta.customerManage.CustomerMeta;
import slim3.meta.reserve.ReserveMeta;
import slim3.model.customerManage.Customer;
import slim3.model.reserve.Reserve;
/**
 * カスタマーの詳細を表示します。
 * @author uedadaiki
 *
 */
public class CustomerDetailController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }
        
        Key customerKey = asKey("id");
//        String customerPath = asString("customerPath");
//        CustomerMeta customerMeta = CustomerMeta.get();
//        Customer customer = Datastore
//                .query(customerMeta)
//                .filter(customerMeta.customerPath.equal(customerPath))
//                .asSingle();
        
        Customer customer = customerService.get(customerKey);
        
        ReserveMeta reserveMeta = ReserveMeta.get();
        List<Reserve> reserveList = Datastore
                .query(reserveMeta)
                .filter(reserveMeta.customerRef.equal(customer.getKey()))
                .asList();
        
        request.setAttribute("customer", customer);
        request.setAttribute("reserveList", reserveList);
        return forward("customerDetail.jsp");
    }
}
