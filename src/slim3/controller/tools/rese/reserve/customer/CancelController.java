package slim3.controller.tools.rese.reserve.customer;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.controller.tools.rese.AbstractReseController;
import slim3.model.MsUser;
import slim3.model.customerManage.Customer;
import slim3.model.reserve.MenuPage;
import slim3.model.reserve.Reserve;

/**
 * キャンセル画面を表示するコントローラです。
 * @author uedadaiki
 *
 */
public class CancelController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        Key menuPageKey = Datastore.stringToKey(asString("menuPageKey"));
        Key reserveKey = Datastore.stringToKey(asString("reserveKey"));
        
        //キャンセル期間を取り出し、現在時間から計算。
        MenuPage menuPage = menuPageService.get(menuPageKey);
        int cancelTime = menuPage.getCancelTime();
        DateTime now = new DateTime(DateTimeZone.forID("Asia/Tokyo"));
        DateTime limitCancelTime = now.plusSeconds(-cancelTime);
        
        //キャンセル不可
        if (limitCancelTime.isAfter(now)) {
            return forward("/tools/rese/reserve/customer/cancel.jsp");
        }
        
        //キャンセル可
        Reserve reserve = reserveService.get(reserveKey);
        
        Key customerKey = reserve.getCustomerRef().getKey();
        Customer customer = customerService.get(customerKey);
        
        Key msUserKey = reserve.getMsUserRef().getKey();
        MsUser msUser = msUserService.get(msUserKey);
        
        request.setAttribute("reserve", reserve);
        request.setAttribute("menuPage", menuPage);
        request.setAttribute("customer", customer);
        request.setAttribute("msUser", msUser);
        
        return forward("/tools/rese/reserve/customer/cancel.jsp");
    }
}
