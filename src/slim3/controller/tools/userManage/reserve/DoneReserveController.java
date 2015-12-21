package slim3.controller.tools.userManage.reserve;

import java.util.Date;

import org.joda.time.format.DateTimeFormat;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

import slim3.controller.AbstractController;
import slim3.controller.Const;
import slim3.model.reserve.Menu;
import slim3.model.reserve.MenuPage;
import slim3.model.reserve.Reserve;

/**
 * メニュー予約完了後のコントローラです。
 * @author uedadaiki
 *
 */
public class DoneReserveController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        Key menuKey = asKey("menuKey");
        Menu orderMenu = menuService.get(menuKey);
        
        ModelRef<MenuPage> menuPageRef = orderMenu.getMenuPageRef();
        MenuPage menuPage = menuPageService.get(menuPageRef.getKey());
        Key msUserKey = menuPage.getMsUserRef().getKey();
        
        String reserveTime = asString("reserveTime");
        String menuEndTime = asString("menuEndTime");
        String name = asString("name");
        String mailaddress = asString("mailaddress");
        String phone = asString("phone");
        
        Date reserveDateTime = 
                DateTimeFormat
                .forPattern("yyyy/MM/dd HH:mm")
                .parseDateTime(reserveTime)
                .toDate();
        Date menuEndDateTime = 
                DateTimeFormat
                .forPattern("yyyy/MM/dd HH:mm")
                .parseDateTime(menuEndTime)
                .toDate();
        
        //予約を保存します。
        Reserve reserve = new Reserve();
        reserve.getMsUserRef().setKey(msUserKey);
        reserve.setMenuTitle(orderMenu.getTitle());
        reserve.setTime(orderMenu.getTime());
        reserve.setPrice(orderMenu.getPrice());
        reserve.setStartTime(reserveDateTime);
        reserve.setEndTime(menuEndDateTime);
        reserve.setCustomerName(name);
        reserve.setCustomerMailadress(mailaddress);
        reserve.setCustomerPhone(phone);
        
        
        Datastore.put(reserve);
        log.info("予約を保存しました。" + reserve.toString());
        
        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, "success"));
    }
}
