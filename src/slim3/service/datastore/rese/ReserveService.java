package slim3.service.datastore.rese;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.meta.reserve.ReserveMeta;
import slim3.model.reserve.Reserve;
import slim3.service.datastore.AbstractDatastoreService;
/**
 * 予約情報を取得するサービスです。
 * @author uedadaiki
 *
 */
public class ReserveService extends AbstractDatastoreService{
    
    private final static ReserveMeta RESERVE_META = ReserveMeta.get();
    
    /**
     * 予約情報をkeyで1つ取得する。
     * @param id
     * @return
     */
    public Reserve get(Key id){
        return dsService.getSingle(Reserve.class, ReserveMeta.get(),id);
    }
    
    /**
     * MsUserのKeyで、予約情報を全て取り出します。
     * @param msUserKey
     * @return
     */
    public List<Reserve> getListByMsUserKey(Key msUserKey){
        return Datastore.query(RESERVE_META).filter(RESERVE_META.msUserRef.equal(msUserKey)).asList();
    }
    
    
    
    /**
     * 予約情報を期間で絞り込みます。
     * @param reserveList
     * @param startDateTime
     * @param endDateTime
     * @return
     */
    public ArrayList<HashMap<String, String>> getReserveByRange(List<Reserve> reserveList, DateTime startDateTime, DateTime endDateTime){
        ArrayList<HashMap<String, String>> eventList = new ArrayList<HashMap<String, String>>();
        //ひと月の予約情報に絞る。
        for (Reserve reserve : reserveList) {
            HashMap<String, String> menuDetail = new HashMap<String, String>();
            //(例)Wed Dec 30 09:00:00 JST 2015
            DateTime reserveStartDateTime = new DateTime(reserve.getStartTime());
            DateTime reserveEndDateTime = new DateTime(reserve.getEndTime());
            
            //完全に省くパターン
            //①メニュー開始and終了が当月初日より前
            //②メニュー開始が当月末日より後
            if (reserveStartDateTime.isBefore(startDateTime)) {
                if (reserveEndDateTime.isBefore(endDateTime)) {
//                    log.info("当月に該当しません。");
                    continue;
                }
            }else if (reserveStartDateTime.isAfter(endDateTime)) {
//                log.info("当月に該当しません。");
                continue;
            }
            //一部だけ表示するパターン
            //①メニュー開始が当月初日より前and終了が当月初日より後
            //②メニュー開始が当月末日より前and終了が当月末日より後
            if (reserveStartDateTime.isBefore(startDateTime)) {
                if (reserveEndDateTime.isAfter(startDateTime)) {
//                    log.info("一部だけ表示①");
                    //カレンダー開始からメニュー終了まで
                    menuDetail.put("title", reserve.getMenuTitle());
                    menuDetail.put("start", startDateTime.toString());
                    menuDetail.put("end", reserve.getEndTime().toString());
                    menuDetail.put("customerName", reserve.getCustomerName().toString());
                    if (reserve.getCustomerMailaddress() != null) {
                        menuDetail.put("customerMailadress", reserve.getCustomerMailaddress());
                    }
                    if (reserve.getCustomerPhone() != null) {
                        menuDetail.put("customerPhone", reserve.getCustomerPhone());
                    }
                    menuDetail.put("key", "#" + Datastore.keyToString(reserve.getKey()));
                    eventList.add(menuDetail);
                    continue;
                }
            }else if (reserveStartDateTime.isBefore(endDateTime)) {
                if (reserveEndDateTime.isAfter(endDateTime)) {
//                    log.info("一部だけ表示②");
                    //メニュー開始からカレンダー終了まで
                    menuDetail.put("title", reserve.getMenuTitle());
                    menuDetail.put("start", reserve.getStartTime().toString());
                    menuDetail.put("end", endDateTime.toString());
                    menuDetail.put("customerName", reserve.getCustomerName().toString());
                    if (reserve.getCustomerMailaddress() != null) {
                        menuDetail.put("customerMailadress", reserve.getCustomerMailaddress());
                    }
                    if (reserve.getCustomerPhone() != null) {
                        menuDetail.put("customerPhone", reserve.getCustomerPhone());
                    }
                    menuDetail.put("key", "#" + Datastore.keyToString(reserve.getKey()));
                    eventList.add(menuDetail);
                    continue;
                }
            }
            
            //完全に表示するパターン
            //メニュー開始が当月初日より後or同じandメニュー終了が当月末日より前or同じ
            if (reserveStartDateTime.isAfter(startDateTime) || reserveStartDateTime.isEqual(startDateTime)) {
//                log.info("完全表示1");
                menuDetail.put("title", reserve.getMenuTitle());
                menuDetail.put("start", reserveStartDateTime.toString());
                menuDetail.put("end", reserveEndDateTime.toString());
                menuDetail.put("customerName", reserve.getCustomerName().toString());
                if (reserve.getCustomerMailaddress() != null) {
                    menuDetail.put("customerMailadress", reserve.getCustomerMailaddress());
                }
                if (reserve.getCustomerPhone() != null) {
                    menuDetail.put("customerPhone", reserve.getCustomerPhone());
                }
                menuDetail.put("key", "#" + Datastore.keyToString(reserve.getKey()));
                eventList.add(menuDetail);
            }else if (reserveEndDateTime.isBefore(endDateTime) || reserveEndDateTime.isEqual(endDateTime)) {
//                log.info("完全表示2");
                menuDetail.put("title", reserve.getMenuTitle());
                menuDetail.put("start", reserve.getStartTime().toString());
                menuDetail.put("end", reserve.getEndTime().toString());
                menuDetail.put("customerName", reserve.getCustomerName().toString());
                if (reserve.getCustomerMailaddress() != null) {
                    menuDetail.put("customerMailadress", reserve.getCustomerMailaddress());
                }
                if (reserve.getCustomerPhone() != null) {
                    menuDetail.put("customerPhone", reserve.getCustomerPhone());
                }
                menuDetail.put("key", "#" + Datastore.keyToString(reserve.getKey()));
                eventList.add(menuDetail);
            }
            
        }
        log.info("イベントリストを返却します。");
        return eventList;
        
    }
    
}