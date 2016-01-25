package slim3.controller.tools.rese.reserve.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slim3.controller.Navigation;
import org.slim3.datastore.ModelRef;
import org.slim3.util.ArrayMap;

import com.google.appengine.api.datastore.Key;

import slim3.Const;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.model.MsShop;
import slim3.model.MsUser;
import slim3.model.reserve.Menu;
import slim3.model.reserve.MenuPage;
import slim3.model.reserve.Reserve;
import util.StringUtil;
/**
 * カスタマーに予約できる日時を表示します。
 * @author uedadaiki
 *
 */
//TODO それぞれをメソッドにして、管理しやすいようにする。不要な部分を確認して削除。
public class CalculateTimeController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        //選択された日程
        DateTime selectReserveDate = DateTimeFormat.forPattern("yyyy/MM/dd").parseDateTime(asString("reserveDate"));
        log.info("選択された日程" + selectReserveDate.toString());
        int dayOfWeek = selectReserveDate.getDayOfWeek();
        log.info(Integer.toString(dayOfWeek));
        //曜日
        String daysOfTheWeek = Const.DAYS_OF_THE_WEEK[dayOfWeek-1];
        log.info(daysOfTheWeek);

        //メニューの時間
        Key meuKey = asKey("menuKey");
        Menu orderMenu = menuService.get(meuKey);
        int orderMenuTime = Integer.parseInt(orderMenu.getTime());

        //-----------------------------------------
        //メニューページの予約受け付け締切
        Key MenuPageRefKey = orderMenu.getMenuPageRef().getKey();
        MenuPage menuPage = menuPageService.get(MenuPageRefKey);
        int reserveEndTime = menuPage.getReserveEndTime();
        int reserveDeadline = reserveEndTime;

        //-----------------------------------------

        
        //-----------------------------------------
        //営業時間
        ModelRef<MsUser> msUserRef = menuPage.getMsUserRef();
        Key userKey = msUserRef.getKey();
        MsShop usersShopInfo = shopService.getByMsUserKey(userKey);
        ArrayMap<String, ArrayMap<String, Object>> statusByDays = usersShopInfo.getStatusByDays();

        ArrayMap<String, Object> shopDataByDaysOfTheWeek = statusByDays.get(daysOfTheWeek);
        if (shopDataByDaysOfTheWeek.get("shopStatus").equals("notOpen")) {
            log.info("閉店日が選択されました");
            return null;
        }

        //開店・閉店時刻をセットします。(選択された日程から曜日を出し、その曜日の開店・閉店時刻をセットする)
        //開店時間
        //開店時間をセットし、選択された日程に変更する
        String shopStartMomentsStr = shopDataByDaysOfTheWeek.get("startTime").toString();
        DateTime shopStartMoments = DateTimeFormat.forPattern("HH:mm").parseDateTime(shopStartMomentsStr);
        DateTime shopStartTime = new DateTime(selectReserveDate.getYear(),selectReserveDate.getMonthOfYear(),selectReserveDate.getDayOfMonth(),shopStartMoments.getHourOfDay(),shopStartMoments.getMinuteOfHour(),0);
//        log.info("開店時間" + shopStartTime.toString());
        
        //開店時間
        //閉店時間をセットし、選択された日程に変更する
        String shopEndMomentsStr = shopDataByDaysOfTheWeek.get("endTime").toString();
        DateTime shopEndMoments = DateTimeFormat.forPattern("HH:mm").parseDateTime(shopEndMomentsStr);
        DateTime shopEndTime = new DateTime(selectReserveDate.getYear(),selectReserveDate.getMonthOfYear(),selectReserveDate.getDayOfMonth(),shopEndMoments.getHourOfDay(),shopEndMoments.getMinuteOfHour(),0);
//        log.info("閉店時間" + shopEndTime.toString());
        //-----------------------------------------


        //-----------------------------------------
        //既に入ってる予約
        int interval = menuPage.getInterval();
        List<Reserve> reserveList = reserveService.getListByMsUserKey(userKey);
        List<DateTime> reservedMenuStartList = new ArrayList<DateTime>();
        List<DateTime> reservedMenuEndList = new ArrayList<DateTime>();
        //既に入ってる予約の開始・終了時間をリストへ追加していきます。
        //TODO 全て取り出してるので、範囲を指定して取り出すようにする。
        for (Reserve reserve : reserveList) {
            Date startTime = reserve.getStartTime();
            Date endTime = reserve.getEndTime();
            
            DateTime startDateTime = new DateTime(startTime);
            DateTime endDateTime = new DateTime(endTime);
            
            startDateTime.plusMinutes(-interval/60);
            endDateTime.plusMinutes(interval/60);
            
            reservedMenuStartList.add(startDateTime);
            reservedMenuEndList.add(endDateTime);
//            log.info(String.format("%s%s%s%s%s", "既に入ってる予約：" , "開始", startTime.toString(), "終了", endTime.toString()));
        }
        //-----------------------------------------
        

        //-----------------------------------------
        //予約受け付け間隔
        List<String> intervalTimeList = new ArrayList<String>();

        //現在時刻
        DateTime nowDateTime = new DateTime();
        
        //開店時間
//        String shopStartTimeStr = String.format("%s:%s", Integer.toString(shopStartTime.getHourOfDay()), Integer.toString(shopStartTime.getMinuteOfHour()));
//        String parseShopStartTime = null;
//        if (shopStartTime.plusHours(-reserveDeadline/3600).isAfter(nowDateTime)){
////            parseShopStartTime = StringUtil.parseRegex(shopStartTimeStr, ":[0-9]$", ":00");
//            parseShopStartTime = shopStartTimeStr + "0";
//            log.info("開店時間を追加します。");
//            intervalTimeList.add(parseShopStartTime);
//        }
        
        DateTime intervalTime = new DateTime();
//        int j = 0;
        intervalTimeLoop: for (int i = -1; i < 97; i++) {
            //予約間隔を加算
            intervalTime = shopStartTime.plusMinutes(interval/60*(i+1));
//            log.info("予約間隔：" + intervalTime);
            log.info("予約時間" + intervalTime.toString());
            //閉店を過ぎてたらストップ
            if (intervalTime.isAfter(shopEndTime)){
                log.info("メニュー開始時刻が閉店を過ぎているので、ストップします。");
                break;
            }
            DateTime menuEndTime = intervalTime.plusMinutes(orderMenuTime/60);
//            log.info("メニュー終了時間" + menuEndTime.toString());
            if (menuEndTime.isAfter(shopEndTime)){
                log.info("メニュー終了時刻が閉店を過ぎているので、ストップします。");
                break;
            }

            //予約締め切り時刻
            //予約間隔から締め切り時間を引く
            DateTime reserveDeadLine = intervalTime.plusHours(-reserveDeadline/3600);
            if (nowDateTime.isAfter(reserveDeadLine)) {
                log.info(String.format("予約間隔→%s,締め切り時刻→%s", intervalTime, reserveDeadLine));
                log.info("予約受け付け時間を過ぎているのでスキップします。");
                continue;
            }
            
            //他の予約と被ってたらスキップする。
            //既存予約と被ってるパターンの図
            //
            //         |-間隔--|--------------既存予約----------|--間隔-|
            // |-----パターン１-----| |------パターン２-----| |------パターン３-----|
            // |---------------------------パターン４-------------------------|
            //         |--５---|                               |--６--|
            //
            for (DateTime reservedMenuStart : reservedMenuStartList) {
//                log.info("j：" + Integer.toString(j));
                //開店時間と被ってるかチェック
//                if (reservedMenuStart.getDayOfMonth() == selectReserveDate.getDayOfMonth()) {
//                    if (j < 1) {
//                        log.info(reservedMenuStart.toString());
//                        int hour = Integer.parseInt(StringUtil.parseRegex(parseShopStartTime, ":[0-9][0-9]$", ""));
//                        int minute = Integer.parseInt(StringUtil.parseRegex(parseShopStartTime, "[0-9][0-9]*:", ""));
//                        DateTime shopStartDateTime = new DateTime(reservedMenuStart).withTime(hour, minute, 0, 0);
//                        if (reservedMenuStart.isEqual(shopStartDateTime)) {
//                            log.info("開店時間を削除します。");
//                            j++;
//                            intervalTimeList.remove(0);
//                        }
//                    }
//                }
                
                //メニュー終了時刻 = メニュー開始時刻 + メニューの時間 + 予約間隔
                DateTime reservedMenuEnd = reservedMenuStart.plusMinutes(orderMenuTime/60);
                //分かりづらいので、命名を変更します。
                //予約間隔の開始時刻
                DateTime interStart = intervalTime;
                //予約間隔の終了時刻
                DateTime interEnd = intervalTime.plusMinutes(orderMenuTime/60);
                //既存予約の開始時刻(受け付け間隔時間を減算)
                DateTime rsvStart = reservedMenuStart.plusMinutes(-interval/60);
                DateTime rsvEnd = reservedMenuEnd.plusMinutes(interval/60);
                
                //パターン１：メニューの終わりが被るパターン
                if (interStart.isBefore(rsvStart)){
                    if (interEnd.isAfter(rsvStart)) {
                            log.info("パターン１：他の予約と被ってるのでスキップします。" + rsvStart);
                            continue intervalTimeLoop;
                    }
                }
                //パターン２：メニューの始まりが被るパターン
                if (interStart.isBefore(rsvEnd)){
                    if (interEnd.isAfter(rsvEnd)) {
                        log.info("パターン２：他の予約と被ってるのでスキップします。" + rsvStart);
                        continue intervalTimeLoop;
                    }
                }
                
                //パターン３：メニューの始まり・終わりが被るパターン
                if (interStart.isAfter(rsvStart)){
                    if (interEnd.isBefore(rsvEnd)) {
                        log.info("パターン３：他の予約と被ってるのでスキップします。" + rsvStart);
                        continue intervalTimeLoop;
                    }
                }
                //パターン４：メニューの途中が被るパターン
                if (interStart.isBefore(rsvStart)){
                    if (interEnd.isAfter(rsvEnd)) {
                        log.info("パターン４：他の予約と被ってるのでスキップします。" + rsvStart);
                        continue intervalTimeLoop;
                    }
                }
                
                //パターン５：メニューの始まりが同じパターン
                if (interStart.isEqual(rsvStart)){
                    log.info("パターン５：他の予約と被ってるのでスキップします。" + rsvStart);
                    continue intervalTimeLoop;
                    
                }
                //パターン６：メニューの終わりが同じパターン
                if (interEnd.isEqual(rsvEnd)) {
                    log.info("パターン６：他の予約と被ってるのでスキップします。" + rsvStart);
                    continue intervalTimeLoop;
                }
                
                
//                //パターン１
//                if (interStart.isBefore(rsvStart)){
//                    if (interEnd.isAfter(rsvStart)) {
//                        if (interEnd.isBefore(rsvEnd)) {
//                            log.info("パターン１：他の予約と被ってるのでスキップします。" + rsvStart);
//                            continue intervalTimeLoop;
//                        }
//                    }else if (interEnd.isEqual(rsvStart)) {
//                        log.info("パターン１：他の予約と被ってるのでスキップします。");
//                        continue intervalTimeLoop;
//                    }
//                }
//                //パターン２
//                if (interStart.isAfter(rsvStart)){
//                    if (interEnd.isBefore(rsvEnd)) {
//                        log.info("パターン２：他の予約と被ってるのでスキップします。");
//                        continue intervalTimeLoop;
//                    }else if (interEnd.isEqual(rsvEnd)) {
//                        log.info("パターン２：他の予約と被ってるのでスキップします。");
//                        continue intervalTimeLoop;
//                    }
//                }else if (interStart.isEqual(rsvStart)) {
//                    log.info("パターン２：他の予約と被ってるのでスキップします。");
//                    continue;
//                }
//                //パターン３
//                if (interStart.isAfter(rsvStart)){
//                    if (interEnd.isAfter(rsvEnd)) {
//                        if (interStart.isBefore(rsvEnd)) {
//                            log.info("パターン３：他の予約と被ってるのでスキップします。" + rsvStart);
//                            continue intervalTimeLoop;
//                        }else if (interStart.isEqual(rsvEnd)) {
//                            log.info("パターン３：他の予約と被ってるのでスキップします。");
//                            continue intervalTimeLoop;
//                        }
//                    }
//                }
//                
//                //パターン４
//                if (interStart.isEqual(rsvStart)){
//                    if (interEnd.isBefore(rsvEnd)) {
//                        log.info("パターン４：他の予約と被ってるのでスキップします。" + rsvStart);
//                        continue intervalTimeLoop;
//                    }else if (interEnd.isEqual(rsvEnd)) {
//                        log.info("パターン４：他の予約と被ってるのでスキップします。" + rsvStart);
//                        continue intervalTimeLoop;
//                    }else if (interEnd.isAfter(rsvEnd)) {
//                        log.info("パターン４：他の予約と被ってるのでスキップします。" + rsvStart);
//                        continue intervalTimeLoop;
//                    }
//                }
//                
//                //パターン５
//                if (interStart.isBefore(rsvStart)){
//                    if (interEnd.isEqual(rsvEnd)) {
//                        log.info("パターン５：他の予約と被ってるのでスキップします。" + rsvStart);
//                        continue intervalTimeLoop;
//                    }else if (interEnd.isAfter(rsvEnd)) {
//                        log.info("パターン５：他の予約と被ってるのでスキップします。" + rsvStart);
//                        continue intervalTimeLoop;
//                    }
//                }
                
            }

            String intervalTimeStr = String.format("%s:%s", intervalTime.getHourOfDay(),intervalTime.getMinuteOfHour());
            String parseIntervalTime = StringUtil.parseRegex(intervalTimeStr, ":[0-9]$", ":00");
            log.info("予約間隔を追加します。 " + parseIntervalTime);
            intervalTimeList.add(parseIntervalTime);
        }
        //-----------------------------------------

        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, intervalTimeList));
    }
}
