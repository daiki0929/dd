package slim3.controller.tools.rese.reserve;

import java.util.Date;

import org.joda.time.format.DateTimeFormat;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.Const;
import slim3.Const.RegexType;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.model.reserve.Reserve;
/**
 * 予約情報の編集終了後コントローラです。
 * @author uedadaiki
 *
 */
public class DoneEditReserveController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        Validators v = new Validators(request);
        validate(v, "orderMenu", 1, 50, false, null, null);
        validate(v, "reserveDate", 1, 10, false, RegexType.YEAR_DATE, null);
        validate(v, "reserveMoments", 1, 10, false, RegexType.MOMENTS, null);
        validate(v, "customerName", 1, 20, false, null, null);
        validate(v, "customerMailaddress", 1, 30, false, RegexType.MAIL_ADDRESS, null);
        validate(v, "customerPhone", 1, 15, false, RegexType.PHONE, null);

        if (errors.size() != 0) {
            log.info("記入エラー");
            log.info(errors.toString());
            return forward("/tools/rese/reserve/customer/timeschedule.jsp");
        }
        
        Key reserveKey = asKey("key");
        String menuTitle = asString("menu");
        String name = asString("name");
        String mailaddress = asString("mailaddress");
        String phone = asString("phone");
        String startTime = asString("startTime");
        String endTime = asString("endTime");
        Date reserveDateTime = 
                DateTimeFormat
                .forPattern("yyyy/MM/dd HH:mm")
                .parseDateTime(startTime)
                .toDate();
        Date menuEndDateTime = 
                DateTimeFormat
                .forPattern("yyyy/MM/dd HH:mm")
                .parseDateTime(endTime)
                .toDate();
        
        //予約情報を更新します。
        Reserve reserve = reserveService.get(reserveKey);
        reserve.setMenuTitle(menuTitle);
        reserve.setStartTime(reserveDateTime);
        reserve.setEndTime(menuEndDateTime);
        reserve.setCustomerName(name);
        reserve.setCustomerMailaddress(mailaddress);
        reserve.setCustomerPhone(phone);
        
        Datastore.put(reserve);
        log.info(String.format("%s%s", name, "様の予約情報を更新しました。"));
        
        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, null));
    }
}
