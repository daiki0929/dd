package slim3.controller.tools.userManage.reserve;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import com.google.appengine.api.datastore.Key;

import slim3.controller.AbstractController;
import slim3.controller.Const;
import slim3.controller.Const.RegexType;
import slim3.meta.reserve.ManageUserMeta;
import slim3.model.MsUser;
import slim3.model.reserve.ManageUser;
import slim3.model.reserve.MenuPage;
import slim3.service.datastore.MsUserService;
import util.CookieUtil;
import util.StackTraceUtil;

public class DoneCreateMenuPageController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            //TODO リクエストに応じたログイン画面を返す。AbstractController showLoginPage()
            return forward("/tools/userManage/login");
        }
        
        //データベースからクッキー情報(userId)でデータを1つ取得。
        MsUser msUser = msUserService.getSingleByCookie(request, Const.MS_AUTH_COOKIE_NAME, MS_USER_META);
        if (msUser == null) {
            return forward("/tools/userManage/login");
        }
        
        Validators v = new Validators(request);
        validate(v, "pageTitle", 1, 50, true, null, null);
        validate(v, "description", 1, 600, true, null, null);
        validate(v, "topImg", 1, 400, false, null, null);
        //承認制 or 先着順
        validate(v, "reserveSystem", 1, 20, true, null, null);
        //公開 or 非公開
        validate(v, "status", 1, 20, true, null, null);
        //予約受け付け期間
        validate(v, "reserveStartDay", 1, 10, false, null, null);
        validate(v, "reserveStopDay", 1, 10, false, null, null);
        //キャンセル受付
        validate(v, "cancelTime", 1, 10, false, null, null);
        //予約不可の日程(定休日以外)
        validate(v, "noReserveDate", 1, 1000, false, null, null);
        
        
        if (errors.size() != 0) {
            log.info("記入エラー");
            return forward("/tools/userManage/entry.jsp");
        }
        
        MenuPage menuPage = new MenuPage();
        menuPage.getMsUserRef().setKey(msUser.getKey());
        menuPage.setPageTitle(asString("pageTitle"));
        menuPage.setDescription(asString("description"));
        menuPage.setTopImg(asString("topImg"));
        menuPage.setReserveSystem(asString("reserveSystem"));
        menuPage.setStatus(asString("status"));
        menuPage.setReserveStartDay(asString("reserveStartDay"));
        menuPage.setReserveStopDay(asString("reserveStopDay"));
        menuPage.setCancelTime(asString("cancelTime"));
        menuPage.setNoReserveDate(asString("noReserveDate"));
        Datastore.put(menuPage);
        
        request.setAttribute("id", menuPage.getKey());
        
        return forward("/tools/userManage/customerList.jsp");
    }
}
