package slim3.controller.tools.userManage;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class RegistCustomerController extends Controller {

    @Override
    public Navigation run() throws Exception {
        return forward("registCustomer.jsp");
    }
}
