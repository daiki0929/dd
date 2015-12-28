package slim3.controller.tools.rese.comeAndGo;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.Const;
import util.CookieUtil;
/**
 * ログアウトのコントローラです。
 * @author uedadaiki
 *
 */
public class LogoutController extends Controller {

    @Override
    public Navigation run() throws Exception {
        CookieUtil.deleteCookie(response, Const.MS_AUTH_COOKIE_NAME);
        
        return forward("login.jsp");
    }
}