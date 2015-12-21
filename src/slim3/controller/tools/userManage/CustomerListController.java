package slim3.controller.tools.userManage;

import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import slim3.controller.AbstractController;
import slim3.controller.Const;
import slim3.meta.reserve.ManageUserMeta;
import slim3.model.MsUser;
import slim3.model.reserve.ManageUser;
import util.CookieUtil;
import util.StackTraceUtil;
/**
 * カスタマーの一覧を表示します。
 * @author uedadaiki
 *
 */
public class CustomerListController extends AbstractController {
    
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
            
            //ユーザーが所持する顧客情報を取り出す
            ManageUserMeta manageUserDataMeta = ManageUserMeta.get();
            List<ManageUser> manageUser = Datastore
                    .query(manageUserDataMeta)
                    .filter(manageUserDataMeta.MsUserRef.equal(msUser.getKey()))
                    .asList();
            request.setAttribute("customerList", manageUser);
            
            
        } catch (Exception e) {
            StackTraceUtil.toString(e);
            return null;
        }
        
        
        return forward("customerList.jsp");
    }
}
