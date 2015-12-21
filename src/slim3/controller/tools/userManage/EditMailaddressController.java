package slim3.controller.tools.userManage;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import slim3.controller.AbstractController;
import slim3.controller.Const;
import slim3.model.MsUser;
import util.CookieUtil;
/**
 * ユーザーのアカウント情報を表示します。
 * @author uedadaiki
 *
 */
public class EditMailaddressController extends AbstractController {

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
        
        //TODO kitazawa ココで取り出したものと、 msUserDto.getMsUser()って一緒じゃない？
        try {
            MsUser msUser = Datastore
                    .query(MS_USER_META)
                    .filter(MS_USER_META.userId.equal(cookie))
                    .asSingle();
            if (msUser == null) {
                log.info("MsUserに存在しません。");
                return null;
            }

            //ユーザー情報
            request.setAttribute("msUser", msUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return forward("editMailaddress.jsp");
    }
}