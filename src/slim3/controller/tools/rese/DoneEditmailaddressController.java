package slim3.controller.tools.rese;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import slim3.Const.RegexType;
import slim3.controller.AbstractController;
/**
 * ユーザーのメールアドレス変更画面を表示します。
 * @author uedadaiki
 *
 */
public class DoneEditmailaddressController extends AbstractController {

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
            return forward("/tools/rese/editAcount");
        }
        
//        String mailaddress = asString("mailaddress");
//        //TODO 確認メールを送信する。
//        log.info("変更後のメールアドレスに確認メールを送信しました。");
        
        return forward("editMailaddress.jsp");
    }
}