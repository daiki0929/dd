package slim3.controller.tools.rese.comeAndGo.twitter;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpSession;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.Const;
import slim3.controller.AbstractController;
import slim3.exception.MyException;
import slim3.meta.MsUserMeta;
import slim3.model.MsUser;
import twitter4j.auth.RequestToken;
import util.Base64Util;
import util.CookieUtil;
import util.DateUtil;
/**
 * TwitterAPIのコールバックコントローラ
 * @author uedadaiki
 *
 */
public class CallBackController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        log.info("ReseのTwitterAPIコールバックです。");
        HttpSession session = request.getSession();
        RequestToken reqToken = (RequestToken) session.getAttribute("RequestToken");
        log.info("reqToken：" + reqToken.toString());
        twitterService.callback(request, response, reqToken);
        
        String accessToken = session.getAttribute("AccessToken").toString();
        String accessTokenSecret = session.getAttribute("AccessTokenSecret").toString();
        log.info("AccessToken：" + accessToken);
        log.info("AccessTokenSecret：" + accessTokenSecret);
        
        
        MsUser msUser = new MsUser();
        MsUserMeta msUserMeta = MsUserMeta.get();
        //既に会員登録済みだった場合
        MsUser doneEntryUser = Datastore
                .query(msUserMeta)
                .filter(msUserMeta.twAccessToken.equal(accessToken))
                .filter(msUserMeta.twAccessTokenSecret.equal(accessTokenSecret))
                .asSingle();
        if (doneEntryUser != null) {
            log.info(doneEntryUser.getName() + "：連携済みのアカウントでした。ユーザー情報を取得します。");
         // クッキーを保存します。
//            String encrypt;
//            Key userKey = doneEntryUser.getKey();
//            try {
//                encrypt = Base64Util.encodeWebSafe(String.format("%s_%s", userKey, DateUtil.getDateString("yyyyMMddHHmm")).getBytes("UTF-8"), false);
////                log.info("暗号化しました。 " + encrypt);
//            } catch (UnsupportedEncodingException e) {
//                writeErrorLog(e);
//                throw new MyException(e);
//            }
            String doneUserEncrypt = createCookieStr(doneEntryUser.getKey());
            //３時間有効
            CookieUtil.deleteCookie(response, Const.MS_AUTH_COOKIE_NAME);
            CookieUtil.setCookie(response, Const.MS_AUTH_COOKIE_NAME, doneUserEncrypt, 10800);
            doneEntryUser.setUserId(doneUserEncrypt);
            Datastore.put(doneEntryUser);
            log.info("クッキーを保存しました：" + doneUserEncrypt);
            
            return redirect("/tools/rese/reserve/reserveList");
        }
        
        
        //会員登録がまだだった場合
        log.info("会員登録をします。");
        msUser.setTwAccessToken(accessToken);
        msUser.setTwAccessTokenSecret(accessTokenSecret);
        
        // クッキー値を作成
//        String encrypt;
//        Key userKey = msUser.getKey();
//        try {
//            encrypt = Base64Util.encodeWebSafe(String.format("%s_%s", userKey, DateUtil.getDateString("yyyyMMddHHmm")).getBytes("UTF-8"), false);
////            CookieUtil.setCookie(response, Const.MS_AUTH_COOKIE_DISP_TIME, CypherUtil.encodeBrowfish("" + DateUtil.getDate().getTime()));
//            log.info("暗号化しました。 " + encrypt);
//        } catch (UnsupportedEncodingException e) {
//            writeErrorLog(e);
//            throw new MyException(e);
//        }
        String encrypt = createCookieStr(msUser.getKey());
        // 認証情報をCokkieに保存
        CookieUtil.setCookie(response, Const.MS_AUTH_COOKIE_NAME, encrypt, 3600);
        
        //クッキーをuserIdに保存
        msUser.setUserId(encrypt);
        //ユーザー情報保存
        Datastore.put(msUser);
        
        //店舗情報のデフォルト値を保存
        setShopDefaultService.setShopDefault(msUser);
        log.info("名前/メールアドレス記入フォームへ移動します。");
        return redirect("/tools/rese/comeAndGo/twitter/entry");
    }
    
    //冗長になるので、とりあえずコントローラにメソッドを作成しています。
    private String createCookieStr(Key msUserKey){
        // クッキーを保存します。
        String encrypt;
        try {
            encrypt = Base64Util.encodeWebSafe(String.format("%s_%s", msUserKey, DateUtil.getDateString("yyyyMMddHHmm")).getBytes("UTF-8"), false);
//            log.info("暗号化しました。 " + encrypt);
        } catch (UnsupportedEncodingException e) {
            writeErrorLog(e);
            throw new MyException(e);
        }
        return encrypt;
    }
}
