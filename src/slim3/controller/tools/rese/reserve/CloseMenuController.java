package slim3.controller.tools.rese.reserve;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.Const;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.model.reserve.Menu;
import slim3.model.reserve.Menu.Status;
/**
 * メニューを非公開にするコントローラです。
 * @author uedadaiki
 *
 */
public class CloseMenuController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        try {
            Key menuKey = asKey("menuKey");
            Menu menu = menuService.get(menuKey);
            menu.setStatus(Status.CLOSED.getStatus());
            
            Datastore.put(menu);
            log.info("メニューを非公開に変更しました。");
            
            String menuPageKeyStr = Datastore.keyToString(menu.getMenuPageRef().getKey());
            
            return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, menuPageKeyStr));
        } catch (Exception e) {
            return returnResponse(createJsonDto(Const.JSON_STATUS_ERROR, null, null));
        }
    }
}
