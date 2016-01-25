package slim3.controller.tools.rese;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.util.ArrayMap;

import com.google.appengine.api.datastore.Key;

import slim3.meta.MsShopMeta;
import slim3.model.MsShop;
import slim3.model.MsUser;
/**
 * 定休日・営業時間の編集画面を表示します。
 * @author uedadaiki
 *
 */
public class EditOperationHoursController extends AbstractReseController {

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

        //お店の情報を取得
        Key msUserKey = msUser.getKey();
        MsShopMeta msShopMeta = MsShopMeta.get();
      
        MsShop msShop = Datastore
            .query(msShopMeta)
            .filter(msShopMeta.msUserRef.equal(msUserKey))
            .asSingle();
        
        //ユーザーが所持するお店の情報
        ArrayMap<String, ArrayMap<String, Object>> shopStatusByDays = msShop.getStatusByDays();
        request.setAttribute("statusByDays", shopStatusByDays);
        
        return forward("/tools/rese/dashboard/editOperationHour.jsp");
    }
}
