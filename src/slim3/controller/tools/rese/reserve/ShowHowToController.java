package slim3.controller.tools.rese.reserve;

import org.slim3.controller.Navigation;

import slim3.controller.tools.rese.AbstractReseController;
/**
 * 使い方ガイドを表示します。
 * @author uedadaiki
 *
 */
public class ShowHowToController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }
        
        return forward("/tools/rese/dashboard/howTo.jsp");
    }
}
