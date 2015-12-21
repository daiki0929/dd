package slim3.controller.tools.userManage.reserve;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.controller.AbstractController;
import slim3.controller.Const;
import slim3.model.reserve.Menu;
/**
 * メニューを公開にするコントローラです。
 * @author uedadaiki
 *
 */
public class DoPublicMenuController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }
        
        Key menuKey = asKey("menuKey");
        Menu menu = menuService.get(menuKey);
        menu.setStatus(Const.MENU_PUBLIC);
        
        Datastore.put(menu);
        log.info("メニューを公開に変更しました。");
        
        String menuPageKeyStr = Datastore.keyToString(menu.getMenuPageRef().getKey());
        
        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, menuPageKeyStr));
    }
}
