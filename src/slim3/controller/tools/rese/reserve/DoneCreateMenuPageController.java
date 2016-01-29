package slim3.controller.tools.rese.reserve;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.joda.time.format.DateTimeFormat;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import slim3.controller.tools.rese.AbstractReseController;
import slim3.model.MsUser;
import slim3.model.MsUser.Role;
import slim3.model.reserve.Menu.Status;
import slim3.model.reserve.MenuPage;
/**
 * メニューページ作成完了後のコントローラです。
 * @author uedadaiki
 *
 */
public class DoneCreateMenuPageController extends AbstractReseController {
    
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
        validate(v, "pageTitle", 1, 50, true, null, null);
        validate(v, "description", 1, 600, false, null, null);
        //TODO 画像のサイズでバリデートする。
//        validate(v, "topImgPath", 1, 400, false, null, null);
        //承認制 or 先着順
        validate(v, "reserveSystem", 1, 20, true, null, null);
        //公開 or 非公開
        validate(v, "status", 1, 20, true, null, null);
        //予約受付け間隔
        validate(v, "reserveInterval", 1, 4, false, null, null);
        //予約受け付け期間
        validate(v, "reserveStartDay", 1, 10, false, null, null);
        validate(v, "reserveStopDay", 1, 10, false, null, null);
        //キャンセル受付
        validate(v, "cancelTime", 1, 10, false, null, null);
        //予約不可の日程(定休日以外)
//        validate(v, "noReserveDate", 1, 1000, false, RegexType.YEAR_DATE, null);
        
        if (errors.size() != 0) {
            log.info("記入エラー");
            return forward("/tools/rese/reserve/createMenuPage");
        }
        
        MenuPage menuPage = new MenuPage();
        menuPage.getMsUserRef().setKey(msUser.getKey());
        menuPage.setPageTitle(asString("pageTitle"));
        menuPage.setDescription(asString("description"));
        menuPage.setTopImgPath(asString("topImgPath"));
        menuPage.setReserveSystem(asString("reserveSystem"));
        menuPage.setInterval(asInteger("reserveInterval"));
        if (asString("status").equals("closed")) {
            menuPage.setStatus(Status.CLOSED.getStatus());
        }else if(asString("status").equals("public")){
            menuPage.setStatus(Status.PUBLIC.getStatus());
        }
        //asIntegerがnullだとエラーが表示されるので、エラーページへ飛ばします。
        //nullだった場合、
        try {
            menuPage.setReserveStartTime(asInteger("reserveStartTime"));
            menuPage.setReserveEndTime(asInteger("reserveEndTime"));
            menuPage.setCancelTime(asInteger("cancelTime"));
        } catch (Exception e) {
            log.info("エラーが発生しました。");
            e.printStackTrace();
            return forward("/tools/rese/common/errorPage.jsp");
        }
        
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
        
        //メニューページのURLパス
        Random rnd = new Random();
        String pagePath = Integer.toString(rnd.nextInt(999999));
        menuPage.setPagePath(pagePath);
        
        //制限を超えていたらエラーページに飛ばします。
        int menuPageListSize = menuPageService.getByMsUser(msUser.getKey(), MenuPageStatus.PUBLIC).size();
        if (msUser.getRole() == Role.FREE) {
            if (menuPageListSize > 5) {
                return forward("/tools/rese/errorPage");
            }
        }
        if (msUser.getRole() == Role.PRO) {
            if (menuPageListSize > 20) {
                return forward("/tools/rese/errorPage");
            }
        }
        
        Datastore.put(menuPage);
        Datastore.getOrNull(menuPage.getKey());
        return redirect("/tools/rese/reserve/menuPageList");
    }
}
