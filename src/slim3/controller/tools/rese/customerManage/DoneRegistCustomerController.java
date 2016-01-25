package slim3.controller.tools.rese.customerManage;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import slim3.Const;
import slim3.Const.RegexType;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.meta.MsUserMeta;
import slim3.model.MsUser;
import slim3.model.customerManage.Customer;
import util.CookieUtil;
import util.StackTraceUtil;

/**
 * 顧客登録完了後のコントローラです。
 * @author uedadaiki
 *
 */
public class DoneRegistCustomerController extends AbstractReseController {
    
    protected static MsUserMeta MS_USER_META = MsUserMeta.get();
    
    @Override
    public Navigation run() throws Exception {
        log.info("kita");
        
        Validators v = new Validators(request);
        validate(v, "customerName", 1, 20, true, null, null);
        validate(v, "customerAddress", 1, 40, false, null, null);
        validate(v, "customerPhone", 1, 50, false, RegexType.PHONE, null);
        validate(v, "customerMailaddress", 1, 100, false, RegexType.MAIL_ADDRESS, null);
        
        if (errors.size() != 0) {
            log.info("エラー");
            return forward("/tools/rese/dashboard/customerManage/createCustomer.jsp");
        }

        if (asString("customerMailaddress") != null) {
//            log.info("メールアドレス重複確認");
            String mailaddress = asString("customerMailaddress");
            boolean duplicate = customerService.duplicateMailAddress(mailaddress, manageUserDto);
            if (duplicate) {
                errors.put("duplicate", "すでに登録されているメールアドレスです。");
                return forward("/tools/rese/dashboard/customerManage/createCustomer.jsp");
            }
        }
        
        String customerName = asString("customerName");
        String customerAddress = asString("customerAddress");
        String customerPhone = asString("customerPhone");
        String customerMailaddress = asString("customerMailaddress");
        
        Customer customer = new Customer();
        customer.setName(customerName);
        customer.setAddress(customerAddress);
        customer.setPhone(customerPhone);
        customer.setMailaddress(customerMailaddress);
        
        //クッキー取得
        String cookie = CookieUtil.getCookie(request, Const.MS_AUTH_COOKIE_NAME);
        //クッキーが無かった場合
        if (StringUtil.isEmpty(cookie)) {
            log.info("クッキーがありませんでした。");
            return forward("/tools/rese/comeAndGo/login");
        }
        try {
            //データベースにあるか検索
            MsUser msUser = Datastore
                    .query(MS_USER_META)
                    .filter(MS_USER_META.userId.equal(cookie))
                    .asSingle();
            
            //MsUserに存在しない場合
            if (msUser == null) {
                log.info("MsUserに存在しません。");
                return forward("/tools/rese/comeAndGo/login");
            }
            
            //顧客情報のモデルにユーザーIDをセットします。
            customer.getMsUserRef().setKey(msUser.getKey());
            
            //保存
            Datastore.put(customer);
            log.info("保存しました。");
            
            return forward("/tools/rese/customerManage/customerList");
            
        } catch (Exception e) {
            log.info("保存に失敗しました。");
            StackTraceUtil.toString(e);
            return null;
        }
        
    }
}
