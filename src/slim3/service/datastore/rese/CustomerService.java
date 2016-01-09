package slim3.service.datastore.rese;

import java.util.List;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.dto.ManageUserDto;
import slim3.meta.customerManage.CustomerMeta;
import slim3.model.customerManage.Customer;
import slim3.service.datastore.AbstractDatastoreService;
/**
 * ユーザーが所持するカスタマーを取得するサービスです。
 * @author uedadaiki
 *
 */
public class CustomerService extends AbstractDatastoreService{
    
    private final static CustomerMeta MANAGE_USER_META = CustomerMeta.get();
    
    /**
     * 重複しているかをチェックします。
     * @param mailaddress 登録するメールアドレス
     * @param customer 
     * @return
     */
    public boolean duplicateMailAddress(String mailaddress, ManageUserDto manageUserDto){
        //登録済みチェック
        List<Customer> dupulicateUserList = Datastore
            .query(MANAGE_USER_META)
            .filter(MANAGE_USER_META.mailaddress.equal(mailaddress))
            .asList();
        for (Customer t : dupulicateUserList) {
            if (!t.getKey().equals(manageUserDto.getManageUserId())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * カスタマー情報をkeyで1つ取得する。
     * @param id
     * @return
     */
    public Customer get(Key id){
        return dsService.getSingle(Customer.class, CustomerMeta.get(),id);
    }
    
}