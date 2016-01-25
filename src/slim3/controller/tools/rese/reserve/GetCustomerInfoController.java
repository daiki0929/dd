package slim3.controller.tools.rese.reserve;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.Const;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.model.customerManage.Customer;
/**
 * カスタマー情報をajaxで返します。
 * @author uedadaiki
 *
 */
public class GetCustomerInfoController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        if (asString("customerKey") == null) {
            return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, null));
        }
        Key customerKey = asKey("customerKey");
        Customer customer = customerService.get(customerKey);
        
        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, customer));
    }
}
