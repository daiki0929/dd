package slim3.controller.tools.rese.comeAndGo;

import java.io.UnsupportedEncodingException;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.Const;
import slim3.Const.RegexType;
import slim3.controller.AbstractController;
import slim3.exception.MyException;
import slim3.model.MsUser;
import util.Base64Util;
import util.CookieUtil;
import util.DateUtil;

/**
 * ログイン完了後のコントローラです。
 * @author uedadaiki
 *
 */
//TODO kitazawa ログイン系の処理よね。userManageのパッケージではない気がする。
public class DoneLoginController extends AbstractController {
    
    @Override
    public Navigation run() throws Exception {
        
        Validators v = new Validators(request);
        validate(v, "mailaddress", 1, 100, true, RegexType.MAIL_ADDRESS, null);
        validate(v, "password", 8, 20, true, null, null);
        
        if (errors.size() != 0) {
            log.info("エラー");
            return forward("login.jsp");
        }

        String mailaddress = asString("mailaddress");
        String password = asString("password");
        
        MsUser msUser = msUserService.getSingleByEmail(mailaddress);
        if (msUser == null) {
            errors.put("null", "記入したメールアドレスは登録されていません。");
            request.setAttribute("mailaddress", mailaddress);
            return forward("login.jsp");
        }
        String msPassword = msUser.getPassword();
        if (!msPassword.equals(password)) {
            errors.put("misPassword", "パスワードが間違っています。");
            request.setAttribute("password", password);
            return forward("login.jsp");
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
        //３時間有効
        CookieUtil.setCookie(response, Const.MS_AUTH_COOKIE_NAME, encrypt, 10800);
        msUser.setUserId(encrypt);
        Datastore.put(msUser);
        log.info("クッキーを保存しました：" + encrypt);
        
        //リクエストURL
        String requestURL = CookieUtil.getCookie(request, Const.MS_REQUEST_URL);
        if (!requestURL.isEmpty()) {
            return redirect(requestURL);
        }
        CookieUtil.deleteCookie(response, Const.MS_REQUEST_URL);
        return redirect("/tools/rese/customerManage/customerList");
    }
}
