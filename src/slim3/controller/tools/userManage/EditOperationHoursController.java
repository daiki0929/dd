package slim3.controller.tools.userManage;

import java.util.HashMap;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.controller.AbstractController;
import slim3.controller.Const;
import slim3.meta.MsShopMeta;
import slim3.model.MsShop;
import slim3.model.MsUser;
/**
 * 定休日・営業時間の編集画面を表示します。
 * @author uedadaiki
 *
 */
public class EditOperationHoursController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            //TODO リクエストに応じたログイン画面を返す。AbstractController showLoginPage()
            return forward("/tools/userManage/login");
        }
        
        //データベースからクッキー情報(userId)でデータを1つ取得。
        MsUser msUser = msUserService.getSingleByCookie(request, Const.MS_AUTH_COOKIE_NAME, MS_USER_META);
        if (msUser == null) {
            return forward("/tools/userManage/login");
        }

        //お店の情報を取得
        Key msUserKey = msUser.getKey();
        MsShopMeta msShopMeta = MsShopMeta.get();
        MsShop msShop = Datastore
        .query(msShopMeta)
        .filter(msShopMeta.msUserRef.equal(msUserKey))
        .asSingle();
        //ユーザーが所持するお店の情報
        HashMap<String, HashMap<String, Object>> shopStatusByDays = msShop.getStatusByDays();
        if (shopStatusByDays == null) {
            //店舗情報のデフォルト値を保存
            log.info("店舗情報のデフォルト値を登録します。");
            setShopDefaultService.setShopDefault(msUser);
        }else {
            request.setAttribute("statusByDays", shopStatusByDays);
        }
        
        return forward("editOperationHours.jsp");
    }
}
