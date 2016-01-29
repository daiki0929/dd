package slim3.controller.tools.rese.comeAndGo.google;

import org.slim3.controller.Navigation;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.appengine.api.datastore.KeyFactory;

import slim3.controller.tools.rese.AbstractReseController;
import slim3.model.MsUser;
/**
 * googleアカウントでログインします。
 * @author uedadaiki
 *
 */
public class SignInController extends AbstractReseController {
    
    @Override
    public Navigation run() throws Exception {
        //データベースからクッキー情報(userId)でデータを1つ取得。
        MsUser msUser = msUserService.getSingleByCookie(request);
        
        String redirectUri = "http://localhost:8888/tools/rese/comeAndGo/google/callBack";
        if (isCommerce(request)) {
            redirectUri = "http://rese.space/tools/rese/comeAndGo/google/callBack";
        }
        GoogleAuthorizationCodeFlow flow = googleService.newFlow();

        // リダイレクトURL生成
        //登録済みでGoogleアカウントに連携する場合
        if (msUser != null) {
            String url = flow.newAuthorizationUrl().setState(KeyFactory.keyToString(msUser.getKey())).setRedirectUri(redirectUri).build();
            return redirect(url);
        }
        
        String url = flow.newAuthorizationUrl().setRedirectUri(redirectUri).build();
        log.info("リダイレクト先URL:" + url);
        return redirect(url);
        
    }
}
