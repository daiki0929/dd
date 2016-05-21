package slim3.service.datastore.rese;

import java.util.List;

import slim3.model.MsUser;
import slim3.model.MsUser.Role;
import slim3.model.customerManage.Customer;
import slim3.model.reserve.Reserve;
import slim3.service.datastore.AbstractDatastoreService;
/**
 * 会員クラスに関するサービスです。
 * @author uedadaiki
 *
 */
public class RoleService extends AbstractDatastoreService{
    
    protected MenuPageService menuPageService = new MenuPageService();
    
    /**
     * 会員クラスによって制限が違うサービスのElem
     * @author uedadaiki
     *
     */
    public static enum CheckLimitElem{
        menuPage("menuPage");
        
        private final String elem;
        
        private CheckLimitElem(String elem){
            this.elem = elem;
        }
        
        public String getElem() {
            return elem;
        }
    }
    
    /**
     * メニューページ制限数を超えていないかチェックします。
     * @param MsUser
     * @param menuPageListSize
     * @return
     */
    public boolean checkMenuPageLimit(MsUser MsUser, int menuPageListSize){
        if (MsUser.getRole() == Role.FREE) {
            if (menuPageListSize >= 5) {
                return true;
            }
        }
        if (MsUser.getRole() == Role.PRO) {
            if (menuPageListSize >= 20) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 予約管理制限数を超えていないかチェックします。
     * 制限を超えていたらtrueを返します。
     * @param MsUser
     * @return
     */
    public boolean checkReserveLimit(MsUser MsUser, List<Reserve> reserveList){
        if (MsUser.getRole() == Role.FREE) {
            if (reserveList.size() >= 50) {
                return true;
            }
        }
        if (MsUser.getRole() == Role.PRO) {
            if (reserveList.size() >= 200) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 顧客管理制限数を超えていないかチェックします。
     * 制限を超えていたらtrueを返します。
     * @param MsUser
     * @return
     */
    public boolean checkCustomerLimit(MsUser MsUser, List<Customer> customerList){
        if (MsUser.getRole() == Role.FREE) {
            if (customerList.size() >= 100) {
                return true;
            }
        }
        if (MsUser.getRole() == Role.PRO) {
            if (customerList.size() >= 500) {
                return true;
            }
        }
        return false;
    }
    
    
    
}