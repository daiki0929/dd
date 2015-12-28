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
 * 予約情報をカレンダーに表示します。
 * @author uedadaiki
 *
 */
public class DisplayReserveCalController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        //例：2015-11-29
        String today = asString("today");
        //今日
        DateTime todayDateTime = DateTimeFormat
                .forPattern("yyyy-MM-dd")
                .withLocale(Locale.ENGLISH)
                .parseDateTime(today)
                .withTime(00, 00, 00, 0);
        
        //カレンダー開始日を取得出来ないので、最大表示日数の41を加算・減算して表示件数を絞ります。
        //カレンダー開始日時
        DateTime startDateTime = todayDateTime
                .plusDays(-41);
        //カレンダー終了日時
        DateTime endDateTime = todayDateTime
                .plusDays(41)
                .withTime(23, 59, 00, 0);
        
        log.info("開始" + startDateTime.toString());
        log.info("終了" + endDateTime.toString());
        

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
        
        
        ArrayList<HashMap<String, String>> eventList = new ArrayList<HashMap<String, String>>();
        int n = 0;
        //ひと月の予約情報に絞る。
        for (Reserve reserve : reserveList) {
            HashMap<String, String> menuDetail = new HashMap<String, String>();
            log.info(String.format("%s%s", n, "個目スタート"));
            DateTime reserveStartDateTime = new DateTime(reserve.getStartTime());
            DateTime reserveEndDateTime = new DateTime(reserve.getEndTime());
            
            //完全に省くパターン
            //①メニュー開始and終了が当月初日より前
            //②メニュー開始が当月末日より後
            if (reserveStartDateTime.isBefore(startDateTime)) {
                if (reserveEndDateTime.isBefore(endDateTime)) {
                    log.info("当月に該当しません。");
                    continue;
                }
            }else if (reserveStartDateTime.isAfter(endDateTime)) {
                log.info("当月に該当しません。");
                continue;
            }
            //一部だけ表示するパターン
            //①メニュー開始が当月初日より前and終了が当月初日より後
            //②メニュー開始が当月末日より前and終了が当月末日より後
            if (reserveStartDateTime.isBefore(startDateTime)) {
                if (reserveEndDateTime.isAfter(startDateTime)) {
                    log.info("一部だけ表示①");
                    //カレンダー開始からメニュー終了まで
                    menuDetail.put("title", reserve.getMenuTitle());
                    menuDetail.put("start", startDateTime.toString());
                    menuDetail.put("end", reserve.getEndTime().toString());
                    menuDetail.put("id", Integer.toString(n++));
                    eventList.add(menuDetail);
                    continue;
                }
            }else if (reserveStartDateTime.isBefore(endDateTime)) {
                if (reserveEndDateTime.isAfter(endDateTime)) {
                    log.info("一部だけ表示②");
                    //メニュー開始からカレンダー終了まで
                    menuDetail.put("title", reserve.getMenuTitle());
                    menuDetail.put("start", reserve.getStartTime().toString());
                    menuDetail.put("end", endDateTime.toString());
                    menuDetail.put("id", Integer.toString(n++));
                    eventList.add(menuDetail);
                    continue;
                }
            }
            
            //完全に表示するパターン
            //メニュー開始が当月初日より後or同じandメニュー終了が当月末日より前or同じ
            if (reserveStartDateTime.isAfter(startDateTime) || reserveStartDateTime.isEqual(startDateTime)) {
                log.info("完全表示1");
                menuDetail.put("title", reserve.getMenuTitle());
                menuDetail.put("start", reserveStartDateTime.toString());
                menuDetail.put("end", reserveEndDateTime.toString());
                menuDetail.put("id", Integer.toString(n++));
                eventList.add(menuDetail);
                log.info("menuDetail.toString()：" + menuDetail.toString());
            }else if (reserveEndDateTime.isBefore(endDateTime) || reserveEndDateTime.isEqual(endDateTime)) {
                log.info("完全表示2");
                menuDetail.put("title", reserve.getMenuTitle());
                menuDetail.put("start", reserve.getStartTime().toString());
                menuDetail.put("end", reserve.getEndTime().toString());
                menuDetail.put("id", Integer.toString(n++));
                eventList.add(menuDetail);
            }
            
        }
        
        log.info(eventList.toString());
        log.info("予約情報を返却します。");
        
        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, eventList));

    }
}   
