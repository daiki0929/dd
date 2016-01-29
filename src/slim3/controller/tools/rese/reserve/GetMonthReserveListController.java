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
import slim3.controller.tools.rese.AbstractReseController;
import slim3.meta.reserve.ReserveMeta;
import slim3.model.MsUser;
import slim3.model.reserve.Reserve;
/**
 * 1月の予約情報を取り出します。
 * @author uedadaiki
 *
 */
public class GetMonthReserveListController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        String startDate = asString("startDate");
        String endDate = asString("endDate");

        //データベースからクッキー情報(userId)でデータを1つ取得。
        MsUser msUser = msUserService.getSingleByCookie(request);
        if (msUser == null) {
            log.info("ユーザー情報がありませんでした。");
            return forward("/tools/rese/comeAndGo/login");
        }

        //予約を全て取り出す。
        ReserveMeta reserveMeta = ReserveMeta.get();
        List<Reserve> reserveList = Datastore
                .query(reserveMeta)
                .filter(reserveMeta.msUserRef.equal(msUser.getKey()))
                .asList();

        DateTime startDateTime = DateTimeFormat
                .forPattern("yyyy-MM-dd")
                .withLocale(Locale.JAPAN)
                .parseDateTime(startDate)
                .withTime(00, 00, 00, 0);
        DateTime endDateTime = DateTimeFormat
                .forPattern("yyyy-MM-dd")
                .withLocale(Locale.JAPAN)
                .parseDateTime(endDate)
                .withTime(23, 59, 00, 0);

        log.info("開始日時" + startDateTime.toString());
        log.info("終了日時" + endDateTime.toString());
        

        //期間を絞り込った予約リスト
        ArrayList<HashMap<String, String>> eventList = reserveService.getReserveByRange(reserveList, startDateTime, endDateTime);
        

        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, eventList));
    }
}
