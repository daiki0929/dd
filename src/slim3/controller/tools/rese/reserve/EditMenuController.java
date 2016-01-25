package slim3.controller.tools.rese.reserve;

import org.slim3.controller.Navigation;

import com.google.appengine.api.datastore.Key;

import slim3.controller.tools.rese.AbstractReseController;
import slim3.model.reserve.Menu;
/**
 * メニュー編集画面を表示します。
 * @author uedadaiki
 *
 */
public class EditMenuController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }
        
        Key menuKey = asKey("id");
        Menu menu = menuService.get(menuKey);
        
        request.setAttribute("menu", menu);
        
        return forward("/tools/rese/dashboard/reserve/editMenu.jsp");
    }
}
