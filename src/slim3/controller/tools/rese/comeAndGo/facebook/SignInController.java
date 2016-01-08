package slim3.controller.tools.rese.comeAndGo.facebook;

import org.slim3.controller.Navigation;

import slim3.Const;
import slim3.controller.AbstractController;
/**
 * Facebookアカウントでログインします。
 * @author uedadaiki
 *
 */
public class SignInController extends AbstractController {
    
    @Override
    public Navigation run() throws Exception {
        String callbackURL = Const.FbCallbackEnum.Rese.getCallbackURL();
        log.info("callbackURL：" + callbackURL);
        facebookService.signIn(request, response, callbackURL, Const.FbAPI.Rese);
        
        return null;
    }
}
