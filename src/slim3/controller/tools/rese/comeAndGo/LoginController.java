package slim3.controller.tools.rese.comeAndGo;

import org.slim3.controller.Navigation;

import slim3.controller.tools.rese.AbstractReseController;
/**
 * ログイン画面を表示します。
 * @author uedadaiki
 *
 */
public class LoginController extends AbstractReseController {
    
    @Override
    public Navigation run() throws Exception {
        
        return forward("login.jsp");
    }
    
}
