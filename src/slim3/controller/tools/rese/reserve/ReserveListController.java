package slim3.controller.tools.rese.reserve;

import java.util.List;

import org.joda.time.DateTime;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import slim3.Const;
import slim3.controller.AbstractController;
import slim3.meta.reserve.ReserveMeta;
import slim3.model.MsUser;
import slim3.model.reserve.Reserve;
/**
 * 予約管理ページを表示します。
 * @author uedadaiki
 *
 */
public class ReserveListController extends AbstractController {

    @Override
    public Navigation run() throws Exception {

        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }

        //データベースからクッキー情報(userId)でデータを1つ取得。
        MsUser msUser = msUserService.getSingleByCookie(request, Const.MS_AUTH_COOKIE_NAME, MS_USER_META);
        if (msUser == null) {
            return forward("/tools/rese/comeAndGo/login");
        }

        //予約を全て取り出す。
        ReserveMeta reserveMeta = ReserveMeta.get();
        List<Reserve> reserveList = Datastore
                .query(reserveMeta)
                .filter(reserveMeta.msUserRef.equal(msUser.getKey()))
                .asList();
        request.setAttribute("reserveList", reserveList);
        
        DateTime today = new DateTime();
        String todayStr = today.toString("yyyy-MM-dd");
        request.setAttribute("today", todayStr);
        

        return forward("reserveList.jsp");

    }
}
