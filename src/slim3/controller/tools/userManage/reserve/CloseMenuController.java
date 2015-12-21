package slim3.controller.tools.userManage.reserve;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.controller.AbstractController;
import slim3.controller.Const;
import slim3.model.reserve.Menu;
/**
 * メニューを非公開にするコントローラです。
 * @author uedadaiki
 *
 */
public class CloseMenuController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        Key menuKey = asKey("menuKey");
        Menu menu = menuService.get(menuKey);
        menu.setStatus(Const.MENU_CLOSED);
        
        Datastore.put(menu);
        log.info("メニューを非公開に変更しました。");
        
        String menuPageKeyStr = Datastore.keyToString(menu.getMenuPageRef().getKey());
        
        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, menuPageKeyStr));
    }
}
