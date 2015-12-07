package slim3.controller.tools.userManage.reserve;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;
/**
 * メニューページ作成画面を表示します。
 * @author uedadaiki
 *
 */
public class CreateMenuPageController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        return forward("createMenuPage.jsp");
    }
}
