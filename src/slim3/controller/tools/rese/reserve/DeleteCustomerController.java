package slim3.controller.tools.rese.reserve;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.controller.tools.rese.AbstractReseController;
/**
 * 顧客を削除します。
 * @author uedadaiki
 *
 */
public class DeleteCustomerController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        Key customerKey = asKey("id");
        Datastore.delete(customerKey);
        log.info("顧客を削除しました。");
        
        return forward("/tools/rese/customerManage/customerList");
    }
}
