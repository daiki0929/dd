package slim3.controller.tools.userManage;

import java.io.UnsupportedEncodingException;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.controller.AbstractController;
import slim3.controller.Const;
import slim3.controller.Const.RegexType;
import slim3.exception.MyException;
import slim3.model.MsUser;
import util.Base64Util;
import util.CookieUtil;
import util.DateUtil;

/**
 * 会員登録完了のコントローラです。
 * @author uedadaiki
 *
 */
public class DoneEntryController extends AbstractController {
    
    @Override
    public Navigation run() throws Exception {
        
        Validators v = new Validators(request);
        validate(v, "name", 1, 20, true, null, null);
        validate(v, "kanaName", 1, 20, true, RegexType.HIRAGANA, null);
        validate(v, "mailaddress", 1, 100, true, RegexType.MAIL_ADDRESS, null);
        validate(v, "password", 8, 20, true, null, null);
        validate(v, "passwordConfirm", 8, 20, true, null, null);
        
        if (errors.size() != 0) {
            log.info("記入エラー");
            return forward("/tools/userManage/entry.jsp");
        }
        
        String name = asString("name");
        String kanaName = asString("kanaName");
        String mailaddress = asString("mailaddress");
        String password = asString("password");
        String passwordConfirm = asString("passwordConfirm");
        
        
        request.setAttribute("name", name);
        request.setAttribute("kanaName", kanaName);
        request.setAttribute("mailaddress", mailaddress);
        request.setAttribute("password", password);
        
        
        if (!password.equals(passwordConfirm)) {
            errors.put("misConfirm", "パスワードが異なります。");
            return forward("/tools/userManage/entry.jsp");
        }
        
        boolean duplicate = msUserService.duplicateMailAddress(mailaddress, msUserDto);
        if (duplicate) {
            errors.put("duplicate", "すでに登録されているメールアドレスです。");
            return forward("/tools/userManage/entry.jsp");
        }
        
        MsUser msUser = new MsUser();
        msUser.setName(name);
        msUser.setKanaName(kanaName);
        msUser.setMailaddress(mailaddress);
        msUser.setPassword(password);
        
        // クッキー値を作成
        String encrypt;
        Key userKey = msUser.getKey();
        try {
            encrypt = Base64Util.encodeWebSafe(String.format("%s_%s", userKey, DateUtil.getDateString("yyyyMMddHHmm")).getBytes("UTF-8"), false);
//            CookieUtil.setCookie(response, Const.MS_AUTH_COOKIE_DISP_TIME, CypherUtil.encodeBrowfish("" + DateUtil.getDate().getTime()));
            log.info("暗号化しました。 " + encrypt);
        } catch (UnsupportedEncodingException e) {
            writeErrorLog(e);
            throw new MyException(e);
        }
        // 認証情報をCokkieに保存
        CookieUtil.setCookie(response, Const.MS_AUTH_COOKIE_NAME, encrypt, 3600);
        
        //クッキーをuserIdに保存
        msUser.setUserId(encrypt);
        //ユーザー情報保存
        Datastore.put(msUser);
        
        //店舗情報のデフォルト値を保存
        setShopDefaultService.setShopDefault(msUser);
        
        return redirect("/tools/userManage/customerList");
//        log.info("会員登録完了しました");
    }
}
