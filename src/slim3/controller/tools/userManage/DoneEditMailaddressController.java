package slim3.controller.tools.userManage;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import slim3.controller.AbstractController;
import slim3.controller.Const.RegexType;
/**
 * ユーザーのメールアドレス変更画面を表示します。
 * @author uedadaiki
 *
 */
public class DoneEditMailaddressController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }

        Validators v = new Validators(request);
        validate(v, "mailaddress", 1, 100, true, RegexType.MAIL_ADDRESS, null);

        if (errors.size() != 0) {
            log.info("記入エラー");
            return forward("/tools/userManage/editAcount");
        }
        
//        String mailaddress = asString("mailaddress");
//        //TODO 確認メールを送信する。
//        log.info("変更後のメールアドレスに確認メールを送信しました。");
        
        return forward("editMailaddress.jsp");
    }
}