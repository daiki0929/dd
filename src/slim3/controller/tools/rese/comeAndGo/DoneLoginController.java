package slim3.controller.tools.rese.comeAndGo;

import java.io.UnsupportedEncodingException;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.Const;
import slim3.Const.RegexType;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.exception.MyException;
import slim3.meta.MsUserMeta;
import slim3.model.MsUser;
import util.Base64Util;
import util.CookieUtil;
import util.DateUtil;

/**
 * ログイン完了後のコントローラです。
 * @author uedadaiki
 *
 */
public class DoneLoginController extends AbstractReseController {
    
    @Override
    public Navigation run() throws Exception {
        
        Validators v = new Validators(request);
        validate(v, "mailaddress", 1, 100, true, RegexType.MAIL_ADDRESS, null);
        validate(v, "password", 8, 20, true, null, null);
        
        if (errors.size() != 0) {
            log.info("エラー");
            return forward("/tools/rese/dashboard/comeAndGo/login.jsp");
        }

        String mailaddress = asString("mailaddress");
        String password = asString("password");
        
        MsUser msUser = msUserService.getSingleByEmail(mailaddress);
        if (msUser == null) {
            errors.put("null", "記入したメールアドレスは登録されていません。");
            request.setAttribute("mailaddress", mailaddress);
            return forward("/tools/rese/dashboard/comeAndGo/login.jsp");
        }
        if (msUser.getPassword() == null) {
            errors.put("null", "記入したメールアドレス・パスワードは登録されていません。");
            request.setAttribute("mailaddress", mailaddress);
            return forward("/tools/rese/dashboard/comeAndGo/login.jsp");
        }
        String msPassword = msUser.getPassword();
        if (!msPassword.equals(password)) {
            errors.put("misPassword", "パスワードが間違っています。");
            request.setAttribute("password", password);
            return forward("/tools/rese/dashboard/comeAndGo/login.jsp");
        }
        
        // クッキーを保存します。
        String encrypt;
        Key userKey = msUser.getKey();
        try {
            encrypt = Base64Util.encodeWebSafe(String.format("%s_%s", userKey, DateUtil.getDateString("yyyyMMddHHmm")).getBytes("UTF-8"), false);
//            log.info("暗号化しました。 " + encrypt);
        } catch (UnsupportedEncodingException e) {
            writeErrorLog(e);
            throw new MyException(e);
        }
        //300時間有効
        CookieUtil.setCookie(response, Const.MS_AUTH_COOKIE_NAME, encrypt, 108000);
        msUser.setUserId(encrypt);
        Datastore.put(msUser);
        log.info("クッキーを保存しました：" + encrypt);
        
        //クッキーの変更後に処理を進めるため、getSingle(getOrNull)を実行しています。
        MsUserMeta msUserMeta = MsUserMeta.get();
        dsService.getSingle(MsUser.class, msUserMeta, msUser.getKey());
        
        //リクエストURL
        String requestURL = CookieUtil.getCookie(request, Const.MS_REQUEST_URL);
        if (requestURL != null) {
            return redirect(requestURL);
        }
        CookieUtil.deleteCookie(response, Const.MS_REQUEST_URL);
        
        return redirect("/tools/rese/reserve/reserveList");
    }
}
