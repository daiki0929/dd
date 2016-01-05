package slim3.controller.api.twitter;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;
/**
 * Twitterアカウントでログインします。
 * @author uedadaiki
 *
 */
public class SignInController extends AbstractController {
    // ================================
    //TODO 他のコントローラでも使う場合はConstに移動する。
    // TwitterAPIのコールバックURL
    public static enum CallbackEnum{
        Rese("http://127.0.0.1:8888/tools/rese/comeAndGo/twitter/CallBack");
        private final String callbackURL;
        private CallbackEnum(final String callbackURL){
            this.callbackURL = callbackURL;
        }
        public String getCallbackURL() {
            return callbackURL;
        }
    }
    
    @Override
    public Navigation run() throws Exception {
        //Rese
        if (asString("s").equals("Rese")) {
            String callbackURL = CallbackEnum.Rese.getCallbackURL();
            log.info("callbackURL：" + callbackURL);
            twitterService.signIn(request, response, callbackURL);
        }
        
        return null;
    }
}
