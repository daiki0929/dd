package slim3.controller.tools.rese.reserve;

import org.slim3.controller.Navigation;

import com.google.appengine.api.datastore.Key;

import slim3.controller.tools.rese.AbstractReseController;
import slim3.model.reserve.MenuPage;
/**
 * メニューページ編集画面を表示します。
 * @author uedadaiki
 *
 */
public class EditMenuPageController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }
        
        Key menuPageId = asKey("id");
        MenuPage menuPage = menuPageService.get(menuPageId);
        
        //新規追加するメニューにメニューページのidを入れるために活用
        request.setAttribute("menuPage", menuPage);
        
        return forward("/tools/rese/dashboard/reserve/editMenuPage.jsp");
    }
}
