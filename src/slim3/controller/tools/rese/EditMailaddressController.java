package slim3.controller.tools.rese;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import slim3.Const;
import slim3.meta.MsUserMeta;
import slim3.model.MsUser;
import util.CookieUtil;
/**
 * ユーザーのメールアドレスを変更します。
 * @author uedadaiki
 *
 */
//TODO 変更に認証は要らないかも。
public class EditMailaddressController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }

        //クッキー取得
        String cookie = CookieUtil.getCookie(request, Const.MS_AUTH_COOKIE_NAME);
        log.info("クッキーを取り出しました：" + cookie);
        if (StringUtil.isEmpty(cookie)) {
            log.info("クッキーがありませんでした。");
            return null;
        }
        
        return forward("editMailaddress.jsp");
    }
}