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
import slim3.exception.MyException;
import slim3.meta.MsUserMeta;
import slim3.model.MsUser;
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
//        // 認証
//        if(!authService.isMsAuth(request, msUserDto, null)) {
//            throw new MyException("認証情報が存在しません。");
//        }
//
//        Validators validators = new Validators(request);
//        validate(validators, "state", 1, 200, true, null, null);
//        if(errors.size() != 0) {
//            showParametor(request);
//            return returnResponse(errors);
//        }
//        MsUser msUser = msUserService.get(KeyFactory.stringToKey(asString("state")));
//        if(msUser == null) {
//            throw new MyException("bmMwsProperty is null");
//        }

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
            //同じリフレッシュトークンを持つユーザーがいるか調べます。
//            MsUserMeta msUserMeta = MsUserMeta.get();
//            MsUser msUser = Datastore
//                    .query(msUserMeta)
//                    .filter(msUserMeta.gmailRefreshToken.equal(refreshToken))
//                    .asSingle();
            MsUser sameMsUser = googleService.getSameMsUser(userInfo.getEmail());

            if(sameMsUser != null) {
                log.info("既に登録済みでした。新しいアクセストークンを保存します。");
//                msUser.setGmailAddress(sameMsUser.getGmailAddress());
                sameMsUser.setGmailAccessToken(credential.getAccessToken());
//                msUser.setGmailRefleshToken(sameMsUser.getGmailRefleshToken());
                dsService.put(sameMsUser);
                
                log.info("クッキーを保存します");
                String msUserEncrypt = CookieUtil.createCookieStr(sameMsUser.getKey());
                //３時間有効
                CookieUtil.deleteCookie(response, Const.MS_AUTH_COOKIE_NAME);
                CookieUtil.setCookie(response, Const.MS_AUTH_COOKIE_NAME, msUserEncrypt, 10800);
                sameMsUser.setUserId(msUserEncrypt);
                Datastore.put(sameMsUser);
                //TODO 保存後すぐに取得するとエラーになるので書いてます。
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
                
                log.info("クッキーを保存します");
                String msUserEncrypt = CookieUtil.createCookieStr(msUser.getKey());
                //３時間有効
                CookieUtil.deleteCookie(response, Const.MS_AUTH_COOKIE_NAME);
                CookieUtil.setCookie(response, Const.MS_AUTH_COOKIE_NAME, msUserEncrypt, 10800);
                msUser.setUserId(msUserEncrypt);
                
                dsService.put(msUser);
                //TODO 保存後すぐに取得するとエラーになるので書いてます。
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
            newMsUser.setGmailAccessToken(credential.getAccessToken());
            newMsUser.setGmailRefreshToken(credential.getRefreshToken());
            
            log.info("クッキーを保存します");
            String msUserEncrypt = CookieUtil.createCookieStr(newMsUser.getKey());
            //３時間有効
            CookieUtil.deleteCookie(response, Const.MS_AUTH_COOKIE_NAME);
            CookieUtil.setCookie(response, Const.MS_AUTH_COOKIE_NAME, msUserEncrypt, 10800);
            newMsUser.setUserId(msUserEncrypt);
            
            dsService.put(newMsUser);
            //TODO 保存後すぐに取得するとエラーになるので書いてます。
            Datastore.getOrNull(newMsUser.getKey());
            
        } catch (Exception e) {
            writeErrorLog(e);
            return null;
        }
        
        return redirect("/tools/rese/reserve/reserveList");
    }
}
