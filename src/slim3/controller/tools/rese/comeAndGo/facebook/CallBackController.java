package slim3.controller.tools.rese.comeAndGo.facebook;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.auth.AccessToken;
import slim3.Const;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.exception.MyException;
import slim3.meta.MsUserMeta;
import slim3.model.MsShop;
import slim3.model.MsUser;
import util.CookieUtil;
/**
 * FacebookAPIのコールバックコントローラ
 * @author uedadaiki
 *
 */
public class CallBackController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        log.info("ReseのFacebookAPIコールバックです。");
        
        Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
        String oauthCode = request.getParameter("code");
        try {
            AccessToken oAuthAccessToken = facebook.getOAuthAccessToken(oauthCode);
            String token = oAuthAccessToken.getToken();
//            log.info("ユーザーのアクセストークン：" + token);
            
            MsUser msUser = new MsUser();
            MsUserMeta msUserMeta = MsUserMeta.get();
            
            //既に会員登録済みだった場合
            MsUser doneEntryUser = Datastore
                      .query(msUserMeta)
                      .filter(msUserMeta.fbAccessToken.equal(token))
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
            msUser.setFbAccessToken(token);
              
            String encrypt = CookieUtil.createCookieStr(msUser.getKey());
            CookieUtil.setCookie(response, Const.MS_AUTH_COOKIE_NAME, encrypt, 3600);
              
            msUser.setUserId(encrypt);
            Datastore.put(msUser);
              
            //店舗情報のデフォルト値を保存
            MsShop shopDefaultHour = setShopDefaultService.setShopDefault(msUser);
            Datastore.put(shopDefaultHour);
            
            log.info("名前/メールアドレス記入フォームへ移動します。");
            return redirect("/tools/rese/comeAndGo/entryBySNS");
            
        } catch (FacebookException e) {
            throw new MyException(e);
        }
        
    }
}
