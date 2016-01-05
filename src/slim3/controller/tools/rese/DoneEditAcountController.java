package slim3.controller.tools.rese;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import slim3.Const.RegexType;
import slim3.controller.AbstractController;
import slim3.model.MsUser;
/**
 * ユーザーのアカウント情報を表示します。
 * @author uedadaiki
 *
 */
public class DoneEditAcountController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }
        
        Validators v = new Validators(request);
        validate(v, "name", 1, 20, true, null, null);
        validate(v, "phone", 1, 50, false, null, null);
        validate(v, "address", 1, 50, false, null, null);

        if (errors.size() != 0) {
            log.info("記入エラー");
            return forward("/tools/rese/editAcount");
        }
        
        String name = asString("name");
        String mailaddress = asString("mailaddress");
        String phone = asString("phone");
        String address = asString("address");
        
        MsUser msUser = new MsUser();
        msUser.setName(name);
        msUser.setMailaddress(mailaddress);
        msUser.setPhone(phone);
        msUser.setAddress(address);
        
        Datastore.put(msUser);
        log.info("アカウント情報を変更しました。");

        return forward("account.jsp");
    }
}