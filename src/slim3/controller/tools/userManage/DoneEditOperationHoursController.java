package slim3.controller.tools.userManage;

import java.util.HashMap;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.controller.AbstractController;
import slim3.controller.Const;
import slim3.model.MsShop;
import slim3.model.MsUser;
/**
 * 店舗の営業日時の編集完了後のコントローラです。
 * 定休日と営業時間をJsonで保存します。
 * @author uedadaiki
 *
 */
public class DoneEditOperationHoursController extends AbstractController {

    @Override
    public Navigation run() throws Exception {

        if (!authService.isMsAuth(request, msUserDto, errors)) {
            //TODO リクエストに応じたログイン画面を返す。AbstractController showLoginPage()
            return forward("/tools/userManage/login");
        }
        
        
        Validators v = new Validators(request);
        //曜日
        validate(v, "daysOfTheWeek", 1, 50, false, null, null);
        validate(v, "shopStatus", 1, 50, false, null, null);
        validate(v, "startTime", 1, 50, false, null, null);
        validate(v, "endTime", 1, 50, false, null, null);
        
        if (errors.size() != 0) {
            log.info("記入エラー");
            return forward("/tools/userManage/reserve/editOperationHours");
        }
        
        String daysOfTheWeek = asString("daysOfTheWeek");
        String shopStatus = asString("shopStatus");
        if (shopStatus.equals("open")) {
            shopStatus = Const.OPEN;
        }else if (shopStatus.equals("notOpen")) {
            shopStatus = Const.NOT_OPEN;
        }else {
            log.info("不正なデータが記入されました。");
            return forward("/tools/userManage/reserve/editOperationHours");
        }
        
        String startTime = asString("startTime");
        String endTime = asString("endTime");
        
        //クッキー情報(userId)でユーザーを取得
        MsUser msUser = msUserService.getSingleByCookie(request, Const.MS_AUTH_COOKIE_NAME, MS_USER_META);
        if (msUser == null) {
            return forward("/tools/userManage/login");
        }
        
        Key msUserKey = msUser.getKey();
        //ユーザーが所持するお店の情報を取り出す。
        MsShop msShop = msShopService.getByMsUserKey(msUserKey);
        HashMap<String, HashMap<String, Object>> shopStatusByDays = msShop.getStatusByDays();
        //曜日別のデータを取得
        HashMap<String, Object> weekMap = shopStatusByDays.get(daysOfTheWeek);
        //データをMapに詰める
        weekMap.put("shopStatus", shopStatus);
        weekMap.put("startTime", startTime);
        weekMap.put("endTime", endTime);
        log.info("曜日：" + daysOfTheWeek);
        shopStatusByDays.put(daysOfTheWeek, weekMap);
        
        msShop.setStatusByDays(shopStatusByDays);
        
        Datastore.put(msShop);
        log.info("保存しました：" + shopStatusByDays);

        //TODO 後から変更する可能性があるので、/dd01/war/tools/userManage/editOperationHours.jspは日曜日しか設定していない。
        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, daysOfTheWeek));
    }
}
