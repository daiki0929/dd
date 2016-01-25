package slim3.service.tools.rese;



import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.controller.AbstractController;
import slim3.meta.reserve.ReserveMeta;
import slim3.model.reserve.MenuPage;
import slim3.model.reserve.Reserve;

/**
 * 予約時間に関連するサービス
 * @author uedadaiki
 *
 */
public class ReserveTimeService {

    public final static Logger log = Logger.getLogger(AbstractController.class.getName());

    /**
     * 既存予約と被ってないか予約直前にチェックします。
     * @param msUserKey
     * @param menuPage
     * @param selectStartTimeStr
     * @param selectEndTimeStr
     * @return
     */
    public boolean checkDoubleBooking(Key msUserKey, MenuPage menuPage, Date selectStartDate, Date selectEndDate){

        //既に入ってる予約
        int interval = menuPage.getInterval();
        ReserveMeta reserveMeta = ReserveMeta.get();
        List<Reserve> reserveList = Datastore
                .query(reserveMeta)
                .filter(reserveMeta.msUserRef.equal(msUserKey))
                .asList();

        //他の予約と被ってたらfalseを返す。
        //既存予約と被ってるパターンの図
        //
        //         |-------------------既存予約--------------------|
        // |-----パターン１-----| |------パターン２-----| |------パターン３-----|
        //
        for (Reserve reserve : reserveList) {
            //既存予約の開始・終了時刻
            Date startTimeDate = reserve.getStartTime();
            Date endTimeDate = reserve.getEndTime();

            DateTime rsvStart = new DateTime(startTimeDate).plusMinutes(-interval/60);
            DateTime rsvEnd = new DateTime(endTimeDate).plusMinutes(interval/60);

            DateTime selectStartDateTime = new DateTime(selectStartDate);
            DateTime selectEndDateTime = new DateTime(selectEndDate);
            
            //パターン１：メニューの終わりが被るパターン
            if (selectStartDateTime.isBefore(rsvStart)){
                if (selectEndDateTime.isAfter(rsvStart)) {
                        log.info("パターン１：他の予約と被ってるのでスキップします。" + rsvStart);
                        return false;
                }
            }
            //パターン２：メニューの始まりが被るパターン
            if (selectStartDateTime.isBefore(rsvEnd)){
                if (selectEndDateTime.isAfter(rsvEnd)) {
                    log.info("パターン２：他の予約と被ってるのでスキップします。" + rsvStart);
                    return false;
                }
            }
            
            //パターン３：メニューの始まり・終わりが被るパターン
            if (selectStartDateTime.isAfter(rsvStart)){
                if (selectEndDateTime.isBefore(rsvEnd)) {
                    log.info("パターン３：他の予約と被ってるのでスキップします。" + rsvStart);
                    return false;
                }
            }
            //パターン４：メニューの途中が被るパターン
            if (selectStartDateTime.isBefore(rsvStart)){
                if (selectEndDateTime.isAfter(rsvEnd)) {
                    log.info("パターン４：他の予約と被ってるのでスキップします。" + rsvStart);
                    return false;
                }
            }
            
            //パターン５：メニューの始まりが同じパターン
            if (selectStartDateTime.isEqual(rsvStart)){
                log.info("パターン５：他の予約と被ってるのでスキップします。" + rsvStart);
                return false;
                
            }
            //パターン６：メニューの終わりが同じパターン
            if (selectEndDateTime.isEqual(rsvEnd)) {
                log.info("パターン６：他の予約と被ってるのでスキップします。" + rsvStart);
                return false;
            }
        }
        log.info("重複チェックOK");
        return true;
    }

}

