package slim3.controller.tools.rese;

import org.slim3.controller.Navigation;
/**
 * ユーザー・店舗情報の設定画面を表示します。
 * @author uedadaiki
 *
 */
public class SettingController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }
        
        return forward("setting.jsp");
    }
}
