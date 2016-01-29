package slim3.controller.tools.rese.comeAndGo.google;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.model.Userinfoplus;
import com.google.appengine.api.datastore.KeyFactory;

import slim3.Const;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.model.MsShop;
import slim3.model.MsUser;
import slim3.model.MsUser.Role;
import util.CookieUtil;
import util.StringUtil;
/**
 * GoogleAPIのコールバックコントローラ
 * @author uedadaiki
 *
 */
public class CallBackController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {

        // 認可コードを取得
        String code = request.getParameter("code");
        log.info(code);
        try {
            GoogleAuthorizationCodeFlow flow = googleService.callbackFlow();
            String redirectUri = googleService.getRedirectUri(request);

            GoogleTokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
            log.info("レス：" + tokenResponse.toString());
            GoogleCredential credential = new GoogleCredential.Builder().setTransport(GoogleNetHttpTransport.newTrustedTransport()).setJsonFactory(JacksonFactory.getDefaultInstance()).setClientSecrets(googleService.getClientCredential()).build();

            credential.setFromTokenResponse(tokenResponse);
            Userinfoplus userInfo = googleService.getUserInfo(credential);
            log.info("ユーザー情報：" + userInfo.toString());
            
            
            //===========================================
            // 既に登録済み(ログイン)
            MsUser sameMsUser = googleService.getSameMsUser(userInfo.getEmail());

            if(sameMsUser != null) {
                log.info("既に登録済みでした。新しいアクセストークンを保存します。");
                sameMsUser.setGmailAccessToken(credential.getAccessToken());
                dsService.put(sameMsUser);
                
                log.info("クッキーを保存します");
                String msUserEncrypt = CookieUtil.createCookieStr(sameMsUser.getKey());
                //300時間有効
                CookieUtil.deleteCookie(response, Const.MS_AUTH_COOKIE_NAME);
                CookieUtil.setCookie(response, Const.MS_AUTH_COOKIE_NAME, msUserEncrypt, 108000);
                sameMsUser.setUserId(msUserEncrypt);
                Datastore.put(sameMsUser);
                Datastore.getOrNull(sameMsUser.getKey());
                
                return redirect("/tools/rese/reserve/reserveList");
            }

            
            //===========================================
            //会員登録済みで、Googleアカウントの連携を行った場合
            // メールアドレスを取得する。
            Validators validators = new Validators(request);
            validate(validators, "state", 1, 200, true, null, null);
            if(errors.size() == 0) {
                MsUser msUser = msUserService.get(KeyFactory.stringToKey(asString("state")));
                msUser.setGmailAddress(userInfo.getEmail());
                msUser.setGmailAccessToken(credential.getAccessToken());
                msUser.setGmailRefreshToken(credential.getRefreshToken());
                String userImgPath = userInfo.getPicture();
                msUser.setUserImgPath(userImgPath);
                
                log.info("クッキーを保存します");
                String msUserEncrypt = CookieUtil.createCookieStr(msUser.getKey());
                //３時間有効
                CookieUtil.deleteCookie(response, Const.MS_AUTH_COOKIE_NAME);
                CookieUtil.setCookie(response, Const.MS_AUTH_COOKIE_NAME, msUserEncrypt, 108000);
                msUser.setUserId(msUserEncrypt);
                
                dsService.put(msUser);
                Datastore.getOrNull(msUser.getKey());
                log.info(String.format("Oauth success[%s][%s][%s][%s]", msUser.getName(), userInfo.getEmail(), credential.getAccessToken(), credential.getRefreshToken()));
                return redirect("/tools/rese/editAcount");
            }
            
            
            //===========================================
            //会員登録がまだの場合
            MsUser newMsUser = new MsUser();
            newMsUser.setName(userInfo.getName());
            newMsUser.setMailaddress(userInfo.getEmail());
            //@より前だけにします。
            String userPath = StringUtil.parseRegex(userInfo.getEmail(), USER_PATH, "");
            newMsUser.setUserPath(userPath);
            newMsUser.setGmailAddress(userInfo.getEmail());
            newMsUser.setName(userInfo.getName());
            newMsUser.setGmailAccessToken(credential.getAccessToken());
            newMsUser.setGmailRefreshToken(credential.getRefreshToken());
            String userImgPath = userInfo.getPicture();
            newMsUser.setUserImgPath(userImgPath);
            //TODO 全て無料会員にしてます。
            newMsUser.setRole(Role.FREE);
            
            log.info("クッキーを保存します");
            String msUserEncrypt = CookieUtil.createCookieStr(newMsUser.getKey());
            //３時間有効
            CookieUtil.deleteCookie(response, Const.MS_AUTH_COOKIE_NAME);
            CookieUtil.setCookie(response, Const.MS_AUTH_COOKIE_NAME, msUserEncrypt, 108000);
            newMsUser.setUserId(msUserEncrypt);
            dsService.put(newMsUser);
            
            //店舗情報のデフォルト値を保存
            MsShop shopDefaultHour = setShopDefault(newMsUser);
            dsService.put(shopDefaultHour);
            
            Datastore.getOrNull(newMsUser.getKey());
            
        } catch (Exception e) {
            writeErrorLog(e);
        }
        
        return redirect("/tools/rese/reserve/reserveList");
    }
}
