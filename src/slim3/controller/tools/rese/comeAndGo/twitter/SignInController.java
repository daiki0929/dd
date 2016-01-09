package slim3.controller.tools.rese.comeAndGo.twitter;

import org.slim3.controller.Navigation;

import slim3.Const;
import slim3.controller.tools.rese.AbstractReseController;
/**
 * Twitterアカウントでログインします。
 * @author uedadaiki
 *
 */
public class SignInController extends AbstractReseController {
    
    @Override
    public Navigation run() throws Exception {
        String callbackURL = Const.TwCallbackEnum.Rese.getCallbackURL();
        log.info("callbackURL：" + callbackURL);
        twitterService.signIn(request, response, callbackURL, Const.TWAPI.Rese);
        
        return null;
    }
}
