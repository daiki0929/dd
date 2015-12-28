package slim3.controller.tools.rese.reserve.customer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.google.appengine.api.datastore.Key;

import slim3.Const.RegexType;
import slim3.controller.AbstractController;
import slim3.model.reserve.Menu;
import util.StringUtil;

/**
 * 予約確認(confirm)のコントローラです。
 * @author uedadaiki
 *
 */
public class ConfirmReserveController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        log.info(asString("reserveMoments"));
        
        Validators v = new Validators(request);
        validate(v, "orderMenu", 1, 50, false, null, null);
        validate(v, "reserveDate", 1, 10, false, RegexType.YEAR_DATE, null);
        validate(v, "reserveMoments", 1, 10, false, RegexType.MOMENTS, null);
        validate(v, "customerName", 1, 20, false, null, null);
        validate(v, "customerMailadress", 1, 30, false, RegexType.MAIL_ADDRESS, null);
        validate(v, "customerPhone", 1, 15, false, RegexType.PHONE, null);

        if (errors.size() != 0) {
            log.info("記入エラー");
            log.info(errors.toString());
            return forward("/tools/rese/reserve/customer/timeschedule.jsp");
        }
        
        Key menuKey = asKey("orderMenu");
        Menu orderMenu = menuService.get(menuKey);
        
        String reserveDate = asString("reserveDate");
        String reserveMoments = asString("reserveMoments");
        //(例)2015/12/16 9:00
        String reserveTime = String.format("%s %s", reserveDate,reserveMoments);
 
        //初期化します。
        DateTime reserveDateTime = new DateTime();
        reserveDateTime = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm").parseDateTime(reserveTime);
        log.info("予約開始時刻：" + reserveDateTime.toString());
        int menuTimeInt = Integer.parseInt(orderMenu.getTime());
        log.info("メニューにかかる時間：" + Integer.toString(menuTimeInt/60) + "分");
        //メニュー終了時刻
        DateTime menuEndTime = reserveDateTime.plusMinutes(menuTimeInt/60);
        String menuEndTimeStr = String.format("%s/%s/%s %s:%s", menuEndTime.getYear(), menuEndTime.getMonthOfYear(), menuEndTime.getDayOfMonth(), menuEndTime.getHourOfDay(), menuEndTime.getMinuteOfHour());
        String parseMenuEndTime = StringUtil.parseRegex(menuEndTimeStr, ":[0-9]$", ":00");
        log.info("予約終了時刻：" + parseMenuEndTime);
        
        String customerName = asString("customerName");
        //苗字と名前の間にあるスペースを取り除きます。
        String tirmCustomerName = customerName.replaceAll(" ", "").replaceAll("　", "");
        String customerMailadress = asString("customerMailadress");
        String customerPhone = asString("customerPhone");
        
        request.setAttribute("menu", orderMenu);
        request.setAttribute("reserveTime", reserveTime);
        request.setAttribute("menuEndTime", parseMenuEndTime);
        
        request.setAttribute("customerName", tirmCustomerName);
        request.setAttribute("customerMailadress", customerMailadress);
        request.setAttribute("customerPhone", customerPhone);
        
        return forward("confirmReserve.jsp");
    }
}
