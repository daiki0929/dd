package slim3.controller.tools.rese.customerManage;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.Const.RegexType;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.model.customerManage.Customer;
/**
 * カスタマーの編集完了のコントローラです。
 * @author uedadaiki
 *
 */
public class DoneEditCustomerController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        Validators v = new Validators(request);
        validate(v, "kanaName", 1, 20, false, RegexType.KANA_HIRA, null);
        validate(v, "mailaddress", 1, 100, false, RegexType.MAIL_ADDRESS, null);
        
        if (errors.size() != 0) {
            log.info("エラー");
            return forward("customerList.jsp");
        }
        
        Key customerKey = asKey("customerKey");
        String name = asString("name");
        String mailaddress = asString("mailaddress");
        String phone = asString("phone");
        
        Customer customer = customerService.get(customerKey);
        customer.setName(name);
        customer.setMailaddress(mailaddress);
        customer.setPhone(phone);
        
        Datastore.put(customer);
        log.info(String.format("%s%s", name, "様の情報を変更しました。"));
        //変更完了後にページを表示するために取得しています。
        Datastore.getOrNull(customerKey);
        
        return redirect(String.format("%s%s", "/tools/rese/customerManage/customerDetail?id=", Datastore.keyToString(customerKey)));
    }
}
