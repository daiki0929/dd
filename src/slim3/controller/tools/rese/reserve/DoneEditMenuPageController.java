package slim3.controller.tools.rese.reserve;

import java.util.ArrayList;
import java.util.Date;

import org.joda.time.format.DateTimeFormat;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.controller.tools.rese.AbstractReseController;
import slim3.model.MsUser;
import slim3.model.reserve.MenuPage;

/**
 * メニューページ編集完了後のコントローラです。
 * @author uedadaiki
 *
 */
public class DoneEditMenuPageController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }
        
        //データベースからクッキー情報(userId)でデータを1つ取得。
        MsUser msUser = msUserService.getSingleByCookie(request);
        if (msUser == null) {
            log.info("ユーザー情報がありませんでした。");
            return forward("/tools/rese/comeAndGo/login");
        }
        
        Validators v = new Validators(request);
        validate(v, "pageTitle", 1, 50, false, null, null);
        validate(v, "description", 1, 600, false, null, null);
        validate(v, "topImgPath", 1, 400, false, null, null);
        //承認制 or 先着順
        validate(v, "reserveSystem", 1, 20, false, null, null);
        //公開 or 非公開
        validate(v, "status", 1, 20, false, null, null);
        //予約受付け間隔
        validate(v, "reserveInterval", 1, 4, false, null, null);
        //予約受け付け期間
        validate(v, "reserveStartTime", 1, 10, false, null, null);
        validate(v, "reserveEndTime", 1, 10, false, null, null);
        //キャンセル受付
        validate(v, "cancelTime", 1, 10, false, null, null);
        //予約不可の日程(定休日以外)
//        validate(v, "noReserveDate", 1, 1000, false, null, null);
        
        if (errors.size() != 0) {
            log.info("記入エラー");
            return forward("/tools/rese/reserve/editMenuPage");
        }
        
        Key menuPageKey = Datastore.stringToKey(asString("id"));
        MenuPage menuPage = menuPageService.get(menuPageKey);
        
        Key key = menuPage.getKey();
        log.info("key：" + key.toString());
        
        menuPage.setPageTitle(asString("pageTitle"));
        menuPage.setDescription(asString("description"));
        menuPage.setTopImgPath(asString("topImgPath"));
        menuPage.setReserveSystem(asString("reserveSystem"));
        menuPage.setInterval(asInteger("reserveInterval"));
        menuPage.setStatus(asString("status"));
        menuPage.setReserveStartTime(asInteger("reserveStartTime"));
        menuPage.setReserveEndTime(asInteger("reserveEndTime"));
        menuPage.setCancelTime(asInteger("cancelTime"));
        
        if (asString("noReserveDate") != null) {
            ArrayList<Date> noReserveDateList = new ArrayList<Date>();
            String[] splitedNoReserveDateStr = asString("noReserveDate").split(",", 0);
            
            for (String noReserveDateStr : splitedNoReserveDateStr) {
                Date noReserveDate = 
                        DateTimeFormat
                        .forPattern("yyyy/MM/dd")
                        .parseDateTime(noReserveDateStr)
                        .toDate();
                noReserveDateList.add(noReserveDate);
                log.info(noReserveDateStr);
            }
            log.info(noReserveDateList.toString());
            
            menuPage.setNoReserveDate(noReserveDateList);
        }
        
        Datastore.put(menuPage);

        
        return forward("/tools/rese/reserve/menuPageList");
    }
}
