package slim3.controller.tools.userManage;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
/**
 * カスタマー登録画面を表示します。
 * @author uedadaiki
 *
 */
public class RegistCustomerController extends Controller {

    @Override
    public Navigation run() throws Exception {
        return forward("registCustomer.jsp");
    }
}
