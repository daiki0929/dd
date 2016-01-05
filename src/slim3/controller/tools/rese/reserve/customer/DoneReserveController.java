package slim3.controller.tools.rese.reserve.customer;

import java.util.Date;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

import slim3.Const;
import slim3.controller.AbstractController;
import slim3.meta.customerManage.CustomerMeta;
import slim3.model.customerManage.Customer;
import slim3.model.reserve.Menu;
import slim3.model.reserve.MenuPage;
import slim3.model.reserve.Reserve;

/**
 * メニュー予約完了後のコントローラです。
 * @author uedadaiki
 *
 */
public class DoneReserveController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        Key menuKey = asKey("menuKey");
        Menu orderMenu = menuService.get(menuKey);
        
        ModelRef<MenuPage> menuPageRef = orderMenu.getMenuPageRef();
        MenuPage menuPage = menuPageService.get(menuPageRef.getKey());
        Key msUserKey = menuPage.getMsUserRef().getKey();
        //バリデートはConfirmReserveで行っています。
        String reserveTime = asString("reserveTime");
        String menuEndTime = asString("menuEndTime");
        String customerName = asString("customerName");
        String customerMailaddress = asString("customerMailaddress");
        String customerPhone = asString("customerPhone");
        
        Date reserveDateTime = 
                DateTimeFormat
                .forPattern("yyyy/MM/dd HH:mm")
                .parseDateTime(reserveTime)
                .toDate();
        Date menuEndDateTime = 
                DateTimeFormat
                .forPattern("yyyy/MM/dd HH:mm")
                .parseDateTime(menuEndTime)
                .toDate();
        
        //カスタマー情報を保存します。
        Customer customer = new Customer();
        customer.setName(customerName);
        customer.setPhone(customerPhone);
        customer.setMailaddress(customerMailaddress);
        
        //予約を保存します。
        Reserve reserve = new Reserve();
        reserve.getMsUserRef().setKey(msUserKey);
        reserve.setMenuTitle(orderMenu.getTitle());
        reserve.setTime(orderMenu.getTime());
        reserve.setPrice(orderMenu.getPrice());
        reserve.setStartTime(reserveDateTime);
        reserve.setEndTime(menuEndDateTime);
        reserve.setCustomerName(customerName);
        reserve.setCustomerMailaddress(customerMailaddress);
        reserve.setCustomerPhone(customerPhone);
        
        
        //リピーターの場合
        CustomerMeta customerMeta = CustomerMeta.get();
        List<Customer> CustomerList = Datastore
                .query(customerMeta)
                .filter(customerMeta.MsUserRef.equal(msUserKey))
                .asList();
        log.info("CustomerList"+CustomerList.toString());
        boolean repeater = false;
        for (Customer savedCustomer : CustomerList) {
            log.info("保存されてるメールアドレス：" + savedCustomer.getMailaddress());
            if (savedCustomer.getMailaddress().equals(customerMailaddress)) {
                log.info("リピーター客として保存します。");
                Key savedCustomerKey = savedCustomer.getKey();
                reserve.getCustomerRef().setKey(savedCustomerKey);
                repeater = true;
                break;
            }
        }
        
        
        //新規顧客の場合
        if (!repeater) {
            log.info("新規顧客として保存します。");
            customer.getMsUserRef().setKey(msUserKey);
            customer.getMenuPageRef().setKey(menuPage.getKey());
            Datastore.put(customer);
            
            Key customerKey = customer.getKey();
            log.info("key：" + Datastore.keyToString(customerKey));
            reserve.getCustomerRef().setKey(customerKey);
        }
        
        Datastore.put(reserve);
        log.info(String.format("%s%s", customerName, "様の予約を保存しました"));
        
        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, "success"));
    }
}
