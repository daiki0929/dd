package slim3.controller.tools.rese.comeAndGo.twitter;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import slim3.Const;
import slim3.Const.RegexType;
import slim3.controller.AbstractController;
import slim3.meta.MsUserMeta;
import slim3.model.MsUser;
import util.CookieUtil;
/**
 * Twitterアカウントでの会員登録完了のコントローラ
 * 名前とメールアドレスを保存します。
 * @author uedadaiki
 *
 */
public class DoneEntryController extends AbstractController {
    
    @Override
    public Navigation run() throws Exception {
        
        Validators v = new Validators(request);
        validate(v, "name", 1, 20, true, null, null);
        validate(v, "mailaddress", 1, 100, true, RegexType.MAIL_ADDRESS, null);
        
        if (errors.size() != 0) {
            log.info("記入エラー");
            return forward("/tools/rese/comeAndGo/entry.jsp");
        }
        
        String name = asString("name");
        String mailaddress = asString("mailaddress");
        
        String msUserKeyStr = CookieUtil.getCookie(request, Const.MS_AUTH_COOKIE_NAME);
        MsUserMeta msUserMeta = MsUserMeta.get();
        //データベースにあるか検索
        MsUser msUser = Datastore
                .query(msUserMeta)
                .filter(msUserMeta.userId.equal(msUserKeyStr))
                .asSingle();
        msUser.setName(name);
        msUser.setMailaddress(mailaddress);
        
        Datastore.put(msUser);
        log.info("名前/メールアドレスを保存しました。");
        
        return forward("/tools/rese/reserve/reserveList");
    }
    
}
