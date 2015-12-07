package slim3.controller.tools.userManage.reserve;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import slim3.controller.AbstractController;
import slim3.model.reserve.MenuPage;
/**
 * メニューページ編集画面を表示します。
 * @author uedadaiki
 *
 */
public class EditMenuPageController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        String menuPageIdStr = asString("id");
        MenuPage menuPage = menuPageService.get(Datastore.stringToKey(menuPageIdStr));
        
        //新規追加するメニューにメニューページのidを入れるために活用
        request.setAttribute("menuPage", menuPage);
        
        return forward("editMenuPage.jsp");
    }
}
