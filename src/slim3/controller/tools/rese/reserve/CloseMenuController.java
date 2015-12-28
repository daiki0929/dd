package slim3.controller.tools.rese.reserve;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.Const;
import slim3.controller.AbstractController;
import slim3.model.reserve.Menu;
import slim3.model.reserve.Menu.Status;
/**
 * メニューを非公開にするコントローラです。
 * @author uedadaiki
 *
 */
//TODO kitazawa 例外吐いたら、JSP返却するよね？正常系だとJSON返すから、Javascript側が複雑にならない？どちらもJSON返す形にした方がいい。
public class CloseMenuController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        Key menuKey = asKey("menuKey");
        Menu menu = menuService.get(menuKey);
        menu.setStatus(Status.CLOSED.getStatus());
        
        Datastore.put(menu);
        log.info("メニューを非公開に変更しました。");
        
        String menuPageKeyStr = Datastore.keyToString(menu.getMenuPageRef().getKey());
        
        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, menuPageKeyStr));
    }
}
