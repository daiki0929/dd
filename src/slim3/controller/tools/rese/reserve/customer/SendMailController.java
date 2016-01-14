package slim3.controller.tools.rese.reserve.customer;

import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import slim3.Const;
import slim3.controller.tools.AbstractOAuthController;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.dto.MsUserDto;
import slim3.meta.reserve.MenuMeta;
import slim3.model.MsUser;
import slim3.model.reserve.Menu;
import slim3.model.reserve.Menu.Status;
import slim3.model.reserve.MenuPage;
import slim3.service.datastore.rese.MsUserService;
/**
 * メールを送信するコントローラです。
 * @author uedadaiki
 *
 */
public class SendMailController extends AbstractOAuthController {

    @Override
    public Navigation run() throws Exception {
        
        MsUserDto msUserDto = new MsUserDto();
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }
        
        MsUserService msUserService = new MsUserService();
        //データベースからクッキー情報(userId)でデータを1つ取得。
        MsUser msUser = msUserService.getSingleByCookie(request);
        if (msUser == null) {
            log.info("ユーザー情報がありませんでした。");
            return forward("/tools/rese/comeAndGo/login");
        }

        String redirectUri = "http://localhost:8888/tools/rese/comeAndGo/google/CallBack";
        GoogleAuthorizationCodeFlow flow = newFlow();

        // リダイレクトURL生成
        String url = flow.newAuthorizationUrl().setState(KeyFactory.keyToString(msUser.getKey())).setRedirectUri(redirectUri).build();

        log.info("リダイレクト先URL:" + url);
        return redirect(url);
        
//        sendMessage(msUser, "0929dddd@gmail.com", null, "テスト", "テスト");
        
//        return null;
    }
}
