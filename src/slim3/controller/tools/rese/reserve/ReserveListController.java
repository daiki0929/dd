package slim3.controller.tools.rese.reserve;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.memcache.Memcache;

import slim3.controller.tools.rese.AbstractReseController;
import slim3.meta.reserve.MenuPageMeta;
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
public class ReserveListController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }
        
        //データベースからクッキー情報(userId)でデータを1つ取得。
        MsUser msUser = msUserService.getSingleByCookie(request);
        if (msUser == null) {
            log.info("ユーザー情報がありませんでした。");
            return forward("/tools/rese/comeAndGo/login");
        }
        
        //作成したメニューを全て取り出す。
        ArrayList<Menu> allMenuList = new ArrayList<Menu>();
        MenuPageMeta menuPageMeta = MenuPageMeta.get();
        List<MenuPage> menuPageList = Datastore
                .query(menuPageMeta)
                .filter(menuPageMeta.msUserRef.equal(msUser.getKey()))
                .filter(menuPageMeta.status.equal(Status.PUBLIC.getStatus()))
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
        
        //キャッシュから予約情報を取り出す。無い場合はデータストアから取り出す。
        List<Reserve> reserveList = Memcache.get(msUser.getMailaddress());
        //キャッシュにある場合
        if (cacheService.exist(msUser.getMailaddress())) {
            log.info("キャッシュにありました。");
            ArrayList<String> timeList = getReserveTime(reserveList);
            request.setAttribute("reserveList", reserveList);
            request.setAttribute("timeList", timeList);
            request.setAttribute("allMenuList", allMenuList);
            
            return forward("reserveList.jsp");
        }
        //キャッシュに無い場合
        log.info("データストアから取り出します。");
        reserveList = reserveService.getListByMsUserKey(msUser.getKey());
        ArrayList<String> timeList = getReserveTime(reserveList);
        request.setAttribute("reserveList", reserveList);
        request.setAttribute("timeList", timeList);
        request.setAttribute("allMenuList", allMenuList);
        //キャッシュに予約リストを保存
        Memcache.put(msUser.getMailaddress(), reserveList);
        
        return forward("reserveList.jsp");
        

    }
    
    /**
     * 予約時間を取得し、リストで返します。
     * @param reserveList
     * @return
     */
    private ArrayList<String> getReserveTime(List<Reserve> reserveList){
        ArrayList<String> timeList = new ArrayList<String>();
        for (Reserve reserve : reserveList) {
            String startTimeStr = new DateTime(reserve.getStartTime()).toString("yyyy/MM/dd HH:mm");
            String endTimeStr = new DateTime(reserve.getEndTime()).toString("yyyy/MM/dd HH:mm");
            timeList.add(startTimeStr);
            timeList.add(endTimeStr);
        }
        return timeList;
    }
}
