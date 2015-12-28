package slim3.controller.tools.rese.reserve.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import org.joda.time.DateTime;
import org.slim3.controller.Navigation;

import com.google.appengine.api.datastore.Key;

import slim3.Const;
import slim3.controller.AbstractController;
import slim3.model.MsShop;
import slim3.model.reserve.Menu;
import slim3.model.reserve.MenuPage;
/**
 * カスタマーに予約できる日時を表示します。
 * @author uedadaiki
 *
 */
//TODO kitazawa ひとつのユースケースに対して、1パッケージにしたほうが見やすくない？
public class TimescheduleController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        Key selectedMeuKey = asKey("menuId");
        Key userKey = asKey("userId");
        
        //メニューの時間
        Menu orderMenu = menuService.get(selectedMeuKey);
        
        //メニューのkeyでメニューページにある受付開始・終了を取得します。
        Key MenuPageRefKey = orderMenu.getMenuPageRef().getKey();
        MenuPage menuPage = menuPageService.get(MenuPageRefKey);
        //受付開始(日)
        int reserveTo = menuPage.getReserveStartTime();
        //締め切り時間(秒)
        int reserveFrom = menuPage.getReserveEndTime();
        
        //メニューのリスト
        List<Menu> menuList = menuService.getListByMenuPageKey(menuPage.getKey());
        
        //最初に設定している予約可能時間
        MsShop usersShopInfo = msShopService.getByMsUserKey(userKey);
        //TODO 日曜日始まりで、順番にリストに入れておく。foreachで取り出して、notOpenの曜日の番号をリストにしてjspに渡す。
        HashMap<String, HashMap<String, Object>> statusByDays = usersShopInfo.getStatusByDays();
        log.info("statusByDays："+statusByDays.toString());
        Iterator iterator = statusByDays.keySet().iterator();
        ArrayList<Integer> offDaysOfTheWeekNum = new ArrayList<Integer>();
        Integer n = -1;
        while(iterator.hasNext()) {
            n++;
            try {
                System.out.println(iterator.next());
                Object status = statusByDays.get(iterator.next()).get("shopStatus");
                if (status.equals(Const.NOT_OPEN)) {
                    log.info("notOpenでした。追加します。");
                    offDaysOfTheWeekNum.add(n);
                }
            } catch (NoSuchElementException e) {
                break;
            }
        }
//        log.info(statusByDays.toString());
        log.info("offDaysOfTheWeekNum" + offDaysOfTheWeekNum.toString());
        
        DateTime today = new DateTime();
        //1月が0なので、-1する。
        DateTime reserveToDateTime = today.plusDays(reserveTo).plusMonths(-1);
        DateTime reserveFromDateTime = today.plusSeconds(reserveFrom).plusMonths(-1);
        
        log.info("from：" + reserveFromDateTime.toString("yyyy,M,d"));
        log.info("to：" + reserveToDateTime.toString("yyyy,M,d"));
        
        request.setAttribute("orderMenu", orderMenu);
        request.setAttribute("statusByDays", statusByDays);
        request.setAttribute("reserveTo", reserveToDateTime.toString("yyyy,M,d"));
        request.setAttribute("reserveFrom", reserveFromDateTime.toString("yyyy,M,d"));
        request.setAttribute("selectedMeuKey", selectedMeuKey);
        request.setAttribute("menuList", menuList);
        
        
        
        return forward("timeschedule.jsp");
    }
}
