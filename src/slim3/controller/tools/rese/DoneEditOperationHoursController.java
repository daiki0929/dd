package slim3.controller.tools.rese;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;
import org.slim3.util.ArrayMap;

import com.google.appengine.api.datastore.Key;

import slim3.Const;
import slim3.model.MsShop;
import slim3.model.MsUser;
/**
 * 店舗の営業日時の編集完了後のコントローラです。
 * 定休日と営業時間をJsonで保存します。
 * @author uedadaiki
 *
 */
public class DoneEditOperationHoursController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }


        Validators v = new Validators(request);
        //曜日
        validate(v, "daysOfTheWeek", 1, 50, false, null, null);
        validate(v, "shopStatus", 1, 50, false, null, null);
        validate(v, "startTime", 1, 50, false, null, null);
        validate(v, "endTime", 1, 50, false, null, null);

        if (errors.size() != 0) {
            log.info("記入エラー");
            return forward("/tools/rese/reserve/editOperationHours");
        }

        String daysOfTheWeek = asString("daysOfTheWeek");
        String shopStatus = asString("shopStatus");
        if (shopStatus != null) {
            if (shopStatus.equals("open")) {
                shopStatus = Const.OPEN;
            }else if (shopStatus.equals("notOpen")) {
                shopStatus = Const.NOT_OPEN;
            }
        }
            

        String startTime = asString("startTime");
        String endTime = asString("endTime");
        DateTime startDateTime = DateTimeFormat.forPattern("HH:mm").parseDateTime(startTime);
        DateTime endDateTime = DateTimeFormat.forPattern("HH:mm").parseDateTime(endTime);
        
        if (startDateTime.isAfter(endDateTime)) {
            log.info("開始時刻が終了時刻より後に設定されています。");
            return returnResponse(createJsonDto(Const.JSON_STATUS_ERROR, "開始時刻は終了時刻より前に設定してください。", "error"));
        }

        //データベースからクッキー情報(userId)でデータを1つ取得。
        MsUser msUser = msUserService.getSingleByCookie(request);
        if (msUser == null) {
            log.info("ユーザー情報がありませんでした。");
            return forward("/tools/rese/comeAndGo/login");
        }

        Key msUserKey = msUser.getKey();
        //ユーザーが所持するお店の情報を取り出す。
        MsShop msShop = shopService.getByMsUserKey(msUserKey);
        ArrayMap<String, ArrayMap<String, Object>> shopStatusByDays = msShop.getStatusByDays();
        //曜日別のデータを取得
        ArrayMap<String, Object> weekMap = new ArrayMap<String, Object>();
        
        //データをMapに詰める
        weekMap.put("shopStatus", shopStatus);
        weekMap.put("startTime", startTime);
        weekMap.put("endTime", endTime);
        log.info("曜日：" + daysOfTheWeek + weekMap.toString());
        shopStatusByDays.put(daysOfTheWeek, weekMap);
        msShop.setStatusByDays(shopStatusByDays);

        Datastore.put(msShop);
        log.info("保存しました：" + shopStatusByDays);

        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, daysOfTheWeek));
    }
}
