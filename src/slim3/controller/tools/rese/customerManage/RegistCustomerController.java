package slim3.controller.tools.rese.customerManage;

import org.slim3.controller.Navigation;

import slim3.controller.tools.rese.AbstractReseController;
/**
 * カスタマー登録画面を表示します。
 * @author uedadaiki
 *
 */
public class RegistCustomerController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }
        return forward("registCustomer.jsp");
    }
}
