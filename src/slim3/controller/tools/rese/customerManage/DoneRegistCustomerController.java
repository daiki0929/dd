package slim3.controller.tools.rese.customerManage;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import slim3.Const;
import slim3.Const.RegexType;
import slim3.controller.AbstractController;
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
public class DoneRegistCustomerController extends AbstractController {
    
    protected static MsUserMeta MS_USER_META = MsUserMeta.get();
    
    @Override
    public Navigation run() throws Exception {
        
        Validators v = new Validators(request);
        validate(v, "name", 1, 20, true, null, null);
        validate(v, "kanaName", 1, 20, false, RegexType.KANA_HIRA, null);
        validate(v, "age", 1, 3, false, null, null);
        validate(v, "address", 1, 40, false, null, null);
        validate(v, "phone", 1, 50, false, RegexType.PHONE, null);
        validate(v, "mailaddress", 1, 100, false, RegexType.MAIL_ADDRESS, null);
        
        if (errors.size() != 0) {
            log.info("エラー");
            return forward("customerList.jsp");
        }

        if (asString("mailaddress") != null) {
//            log.info("メールアドレス重複確認");
            String mailaddress = asString("mailaddress");
            boolean duplicate = customerService.duplicateMailAddress(mailaddress, manageUserDto);
            if (duplicate) {
                errors.put("duplicate", "すでに登録されているメールアドレスです。");
                return forward("customerList.jsp");
            }
        }
        
        String customerName = asString("customerName");
        String customerKanaName = asString("customerKanaName");
        String customerAddress = asString("customerAddress");
        String customerPhone = asString("customerPhone");
        String customerMailaddress = asString("customerMailaddress");
        
        Customer manageUserData = new Customer();
        
        if (asInteger("age") != null) {
            int age = asInteger("age");
            manageUserData.setAge(age);
        }
        
        manageUserData.setName(customerName);
        manageUserData.setKanaName(customerKanaName);
        manageUserData.setAddress(customerAddress);
        manageUserData.setPhone(customerPhone);
        manageUserData.setMailaddress(customerMailaddress);
        
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
            manageUserData.getMsUserRef().setKey(msUser.getKey());
            
            //保存
            Datastore.put(manageUserData);
            log.info("保存しました。");
            
            return redirect("/tools/rese/customerManage/customerList");
            
        } catch (Exception e) {
            log.info("保存に失敗しました。");
            StackTraceUtil.toString(e);
            return null;
        }
        
    }
}
