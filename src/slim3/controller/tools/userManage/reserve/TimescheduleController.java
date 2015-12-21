package slim3.controller.tools.userManage.reserve;

import java.util.HashMap;
import java.util.List;

import org.slim3.controller.Navigation;

import com.google.appengine.api.datastore.Key;

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
        int reserveStartTime = menuPage.getReserveStartTime();
        //受付開始(秒)
        int reserveEndTime = menuPage.getReserveEndTime();
        
        //メニューのリスト
        List<Menu> menuList = menuService.getListByMenuPageKey(menuPage.getKey());
        
        //最初に設定している予約可能時間
        MsShop usersShopInfo = msShopService.getByMsUserKey(userKey);
        HashMap<String, HashMap<String, Object>> statusByDays = usersShopInfo.getStatusByDays();
        
        request.setAttribute("orderMenu", orderMenu);
        request.setAttribute("statusByDays", statusByDays);
        request.setAttribute("reserveStartTime", Integer.toString(reserveStartTime));
        request.setAttribute("reserveEndTime", Integer.toString(reserveEndTime));
        request.setAttribute("selectedMeuKey", selectedMeuKey);
        request.setAttribute("menuList", menuList);
        
        
        return forward("timeschedule.jsp");
    }
}
