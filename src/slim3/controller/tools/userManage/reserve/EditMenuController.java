package slim3.controller.tools.userManage.reserve;

import org.slim3.controller.Navigation;

import com.google.appengine.api.datastore.Key;

import slim3.controller.AbstractController;
import slim3.model.reserve.Menu;
/**
 * メニュー編集画面を表示します。
 * @author uedadaiki
 *
 */
public class EditMenuController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        Key menuKey = asKey("id");
        Menu menu = menuService.get(menuKey);
        
        request.setAttribute("menu", menu);
        
        return forward("editMenu.jsp");
    }
}
