package slim3.controller.tools.rese.reserve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import slim3.Const;
import slim3.controller.AbstractController;
import slim3.meta.reserve.ReserveMeta;
import slim3.model.MsUser;
import slim3.model.reserve.Reserve;
/**
 * 翌月のカレンダー初日の日程を取得します。
 * @author uedadaiki
 *
 */
public class GetNxtCalStartDayController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        String today = asString("today");
        log.info(today);
        
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
        
        //カレンダー開始日時
        DateTime todayDateTime = DateTimeFormat
                .forPattern("yyyy-MM-dd")
                .withLocale(Locale.ENGLISH)
                .parseDateTime(today)
                .withTime(00, 00, 00, 0);
        
        //翌月の初日・末日
        DateTime preMonthStartDay = todayDateTime.plusMonths(1).dayOfMonth().withMinimumValue();
        DateTime preMonthEndDay = todayDateTime.plusMonths(1).dayOfMonth().withMaximumValue();
        
        DateTime startDate = preMonthStartDay.plusDays(-25);
        DateTime endDate = preMonthEndDay.plusDays(25);
        log.info("開始日時" + startDate.toString());
        log.info("終了日時" + endDate.toString());
        
        //期間を絞り込った予約リスト
        ArrayList<HashMap<String, String>> eventList = msReserveService.getReserveByRange(reserveList, startDate, endDate);
        
        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, eventList));
    }
}
