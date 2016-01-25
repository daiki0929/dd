package slim3.controller.tools.rese.reserve;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

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
        validate(v, "reserveKey", 1, 50, true, null, null);
        validate(v, "reserveDate", 1, 10, true, RegexType.YEAR_DATE, null);
        validate(v, "reserveMoments", 1, 10, true, RegexType.MOMENTS, null);

        if (errors.size() != 0) {
            log.info("記入エラー");
            log.info(errors.toString());
            return forward("/tools/rese/reserve/reserveList");
        }
        
        Key reserveKey = asKey("reserveKey");
        String rsvStartDateStr = asString("reserveDate");
        String reserveMomentsStr = asString("reserveMoments");
        
        //メニューの時間を取得します。
        Reserve reserve = reserveService.get(reserveKey);
        
        DateTime start = new DateTime(reserve.getStartTime());
        DateTime end = new DateTime(reserve.getEndTime());
        Duration duration = new Duration(start,end);
        int menuTime = (int) duration.getStandardMinutes();
        log.info("メニュー時間：" + Integer.toString(menuTime));
        log.info(String.format("%s %s", rsvStartDateStr, reserveMomentsStr));
        
        //開始時刻
        DateTime rsvStartDateTime = DateTimeFormat
        .forPattern("yyyy/MM/dd HH:mm")
        .parseDateTime(String.format("%s %s", rsvStartDateStr, reserveMomentsStr));
        //終了時刻
        DateTime rsvEndDateTime = rsvStartDateTime.plusMinutes(menuTime);
        
        //編集後の予約時刻
        Date rsvStartDate = rsvStartDateTime.toDate();
        Date rsvEndDate = rsvEndDateTime.toDate();
        
        
        //予約情報を更新します。
        reserve.setStartTime(rsvStartDate);
        reserve.setEndTime(rsvEndDate);
        
        Datastore.put(reserve);
        log.info("予約情報を更新しました。");
        
        return forward("/tools/rese/reserve/reserveList");
    }
}
