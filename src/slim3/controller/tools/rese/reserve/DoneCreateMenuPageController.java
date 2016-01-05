package slim3.controller.tools.rese.reserve;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import slim3.Const;
import slim3.controller.AbstractController;
import slim3.meta.MsUserMeta;
import slim3.model.MsUser;
import slim3.model.reserve.MenuPage;
import slim3.model.reserve.Menu.Status;
/**
 * メニューページ作成完了後のコントローラです。
 * @author uedadaiki
 *
 */
public class DoneCreateMenuPageController extends AbstractController {
    
    @Override
    public Navigation run() throws Exception {
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }
        
        MsUserMeta msUserMeta = MsUserMeta.get();
        //データベースからクッキー情報(userId)でデータを1つ取得。
        MsUser msUser = msUserService.getSingleByCookie(request, Const.MS_AUTH_COOKIE_NAME, msUserMeta);
        if (msUser == null) {
            return forward("/tools/rese/comeAndGo/login");
        }
        
        Validators v = new Validators(request);
        validate(v, "pageTitle", 1, 50, true, null, null);
        validate(v, "description", 1, 600, false, null, null);
        validate(v, "topImg", 1, 400, false, null, null);
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
        validate(v, "noReserveDate", 1, 1000, false, null, null);
        
        if (errors.size() != 0) {
            log.info("記入エラー");
            return forward("/tools/rese/reserve/createMenuPage");
        }
        
        MenuPage menuPage = new MenuPage();
        menuPage.getMsUserRef().setKey(msUser.getKey());
        menuPage.setPageTitle(asString("pageTitle"));
        menuPage.setDescription(asString("description"));
        menuPage.setTopImg(asString("topImg"));
        menuPage.setReserveSystem(asString("reserveSystem"));
        menuPage.setInterval(asInteger("reserveInterval"));
        if (asString("status").equals("closed")) {
            menuPage.setStatus(Status.CLOSED.getStatus());
        }else if(asString("status").equals("public")){
            menuPage.setStatus(Status.PUBLIC.getStatus());
        }
        //asIntegerがnullだとエラーが表示されるので、エラーページへ飛ばします。
        try {
            menuPage.setReserveStartTime(asInteger("reserveStartTime"));
            menuPage.setReserveEndTime(asInteger("reserveEndTime"));
            menuPage.setCancelTime(asInteger("cancelTime"));
        } catch (Exception e) {
            log.info("エラーが発生しました。");
            e.printStackTrace();
            return forward("/tools/rese/common/errorPage.jsp");
        }
        menuPage.setNoReserveDate(asString("noReserveDate"));
        Datastore.put(menuPage);
        //TODO getで渡せないので、メニューページ一覧に飛ばします。
//        Key menuPageKey = menuPage.getKey();
//        String keyToString = Datastore.keyToString(menuPageKey);
//        log.info("menuPageKey" + menuPageKey);
//
//        return forward(String.format("%s%s", "/tools/userManage/reserve/menuList?id=", keyToString));
        return forward("/tools/rese/reserve/menuPageList");
    }
}
