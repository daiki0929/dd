package slim3.controller.tools.rese.comeAndGo.twitter;

import javax.servlet.http.HttpSession;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import slim3.Const;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.meta.MsUserMeta;
import slim3.model.MsShop;
import slim3.model.MsUser;
import twitter4j.auth.RequestToken;
import util.CookieUtil;
/**
 * TwitterAPIのコールバックコントローラ
 * @author uedadaiki
 *
 */
//TODO クッキー保存とかをDoneEntryControllerに移動した方がいいかも。
public class CallBackController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        log.info("ReseのTwitterAPIコールバックです。");
        HttpSession session = request.getSession();
        RequestToken reqToken = (RequestToken) session.getAttribute("RequestToken");
        log.info("reqToken：" + reqToken.toString());
        twitterService.callback(request, response, reqToken, Const.TWAPI.Rese);
        
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
            String doneUserEncrypt = CookieUtil.createCookieStr(doneEntryUser.getKey());
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
        String encrypt = CookieUtil.createCookieStr(msUser.getKey());
        // 認証情報をCokkieに保存
        CookieUtil.setCookie(response, Const.MS_AUTH_COOKIE_NAME, encrypt, 3600);
        
        msUser.setUserId(encrypt);
        Datastore.put(msUser);
        
        //店舗情報のデフォルト値を保存
        MsShop shopDefaultHour = setShopDefault(msUser);
        Datastore.put(shopDefaultHour);
        
        
        log.info("名前/メールアドレス記入フォームへ移動します。");
        return redirect("/tools/rese/comeAndGo/entryBySNS");
    }
}
