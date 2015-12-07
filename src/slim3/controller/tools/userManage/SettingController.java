package slim3.controller.tools.userManage;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;
/**
 * ユーザー・店舗情報の設定画面を表示します。
 * @author uedadaiki
 *
 */
public class SettingController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        return forward("setting.jsp");
    }
}
