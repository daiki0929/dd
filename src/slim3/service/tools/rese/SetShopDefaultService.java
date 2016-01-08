package slim3.service.tools.rese;



import java.util.logging.Logger;

import org.slim3.util.ArrayMap;

import slim3.Const;
import slim3.controller.AbstractController;
import slim3.model.MsShop;
import slim3.model.MsUser;

public class SetShopDefaultService {
    
    public final static Logger log = Logger.getLogger(AbstractController.class.getName());
    
    /**
     * 営業時間のデフォルト値をセットして返します。
     * @param msUser
     * @return
     */
    //TODO 質問：AbstractControllerを継承したRese用のコントローラを作成し、そこに書くべき？(AbstractControllerでインスタンス化してるサービスクラスも)
    public MsShop setShopDefault(MsUser msUser){
        
        MsShop msShop = new MsShop();
        msShop.getMsUserRef().setKey(msUser.getKey());
        log.info("店舗情報のデフォルト値を登録します。");
        
        ArrayMap<String, ArrayMap<String, Object>> shopStatusByDays = new ArrayMap<String, ArrayMap<String,Object>>();
        ArrayMap<String, Object> weekMap = new ArrayMap<String, Object>();
        weekMap.put("shopStatus", Const.OPEN);
        weekMap.put("startTime", "0:00");
        weekMap.put("endTime", "23:59");
        String[] daysOfTheWeekList = {Const.SUNDAY, Const.MONDAY, Const.TUESDAY, Const.WEDNESDAY, Const.THURSDAY, Const.FRIDAY, Const.SATURDAY};
        for (String daysOfTheWeek : daysOfTheWeekList) {
            shopStatusByDays.put(daysOfTheWeek, weekMap);
        }
//        log.info(shopStatusByDays.toString());
        msShop.setStatusByDays(shopStatusByDays);
        return msShop;
    }
    
}