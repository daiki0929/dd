package slim3.controller.tools.rese.reserve;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import slim3.Const;
import slim3.controller.AbstractController;
import slim3.meta.MsUserMeta;
import slim3.meta.reserve.MenuPageMeta;
import slim3.meta.reserve.ReserveMeta;
import slim3.model.MsUser;
import slim3.model.reserve.Menu;
import slim3.model.reserve.Menu.Status;
import slim3.model.reserve.MenuPage;
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
        
        MsUserMeta msUserMeta = MsUserMeta.get();
        //データベースからクッキー情報(userId)でデータを1つ取得。
        MsUser msUser = msUserService.getSingleByCookie(request, Const.MS_AUTH_COOKIE_NAME, msUserMeta);
        if (msUser == null) {
            return forward("/tools/rese/comeAndGo/login");
        }
        
        //作成したメニューを全て取り出す。(公開しているメニューのみ)
        ArrayList<Menu> allMenuList = new ArrayList<Menu>();
        MenuPageMeta menuPageMeta = new MenuPageMeta();
        List<MenuPage> menuPageList = Datastore
                .query(menuPageMeta)
                .filter(menuPageMeta.msUserRef.equal(msUser.getKey()))
//                .filter(menuPageMeta.status.equal(Status.PUBLIC.getStatus()))
                .asList();
        for (MenuPage menuPage : menuPageList) {
            List<Menu> menuList = menuService.getListByMenuPageKey(menuPage.getKey());
            for (Menu menu : menuList) {
                if (menu.getStatus().equals(Status.CLOSED.getStatus())) {
                    continue;
                }
                allMenuList.add(menu);
            }
        }
        
        
        //予約を全て取り出す。
        ReserveMeta reserveMeta = ReserveMeta.get();
        List<Reserve> reserveList = Datastore
                .query(reserveMeta)
                .filter(reserveMeta.msUserRef.equal(msUser.getKey()))
                .asList();
        ArrayList<String> timeList = new ArrayList<String>();
        for (Reserve reserve : reserveList) {
            String startTimeStr = new DateTime(reserve.getStartTime()).toString("yyyy/MM/dd HH:mm");
            String endTimeStr = new DateTime(reserve.getEndTime()).toString("yyyy/MM/dd HH:mm");
            timeList.add(startTimeStr);
            timeList.add(endTimeStr);
//            log.info(startTimeStr);
//            log.info(endTimeStr);
//            
//            DateTime parseStartTime = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss").parseDateTime(startTimeStr);
//            DateTime parseEndTime = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss").parseDateTime(endTimeStr);
//            
//            reserve.setStartTime(parseStartTime.withTimeAtStartOfDay().toDate());
//            reserve.setEndTime(parseEndTime.toDate());
//            log.info("ここ"+reserve.getStartTime().toString());
        }
        request.setAttribute("reserveList", reserveList);
        request.setAttribute("timeList", timeList);
        request.setAttribute("allMenuList", allMenuList);
        
        return forward("reserveList.jsp");

    }
}
