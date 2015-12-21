package slim3.controller.tools.userManage;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;
/**
 * ログイン画面を表示します。
 * @author uedadaiki
 *
 */
public class LoginController extends AbstractController {
    
    @Override
    public Navigation run() throws Exception {
        return forward("login.jsp");
    }
    
}
