package slim3.service.tools.userManage;

import java.util.logging.Logger;

import org.slim3.datastore.Datastore;
import org.slim3.util.ArrayMap;

import slim3.Const;
import slim3.controller.AbstractController;
import slim3.model.MsShop;
import slim3.model.MsUser;
/**
 * 店舗情報のデフォルト値を保存します。
 * @author uedadaiki
 *
 */
public class SetShopDefaultService {
    
    public final static Logger log = Logger.getLogger(AbstractController.class.getName());
    
    /**
     * 店舗情報のデフォルト値を保存します。
     * @param msUser
     */
    public void setShopDefault(MsUser msUser){
        MsShop msShop = new MsShop();
        msShop.getMsUserRef().setKey(msUser.getKey());
        log.info("店舗情報のデフォルト値を登録します。");
        
        ArrayMap<String, ArrayMap<String, Object>> shopStatusByDays = new ArrayMap<String, ArrayMap<String,Object>>();
//        HashMap<String, HashMap<String, Object>> shopStatusByDays = new LinkedHashMap<String, HashMap<String,Object>>();
        //曜日別のデータ
        ArrayMap<String, Object> weekMap = new ArrayMap<String, Object>();
        //データをMapに詰める
        weekMap.put("shopStatus", Const.OPEN);
        weekMap.put("startTime", "0:00");
        weekMap.put("endTime", "23:59");
        String[] daysOfTheWeekList = {Const.SUNDAY, Const.MONDAY, Const.TUESDAY, Const.WEDNESDAY, Const.THURSDAY, Const.FRIDAY, Const.SATURDAY};
        for (String daysOfTheWeek : daysOfTheWeekList) {
//        log.info("曜日：" + daysOfTheWeek);
//        log.info("情報：" + weekMap);
            shopStatusByDays.put(daysOfTheWeek, weekMap);
        }
        log.info(shopStatusByDays.toString());
        //デフォルトの営業日時を保存
        msShop.setStatusByDays(shopStatusByDays);
        Datastore.put(msShop);
    }
}