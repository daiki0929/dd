package slim3.controller.tools.rese.reserve;

import org.slim3.controller.Navigation;

import slim3.controller.tools.rese.AbstractReseController;
/**
 * メニューページ作成画面を表示します。
 * @author uedadaiki
 *
 */
public class CreateMenuPageController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }
        return forward("/tools/rese/dashboard/reserve/createMenuPage.jsp");
    }
}
