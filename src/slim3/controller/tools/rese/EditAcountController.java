package slim3.controller.tools.rese;

import org.slim3.controller.Navigation;
/**
 * ユーザーのアカウント情報を表示します。
 * @author uedadaiki
 *
 */
public class EditAcountController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }

        return forward("/tools/rese/dashboard/account.jsp");
    }
}