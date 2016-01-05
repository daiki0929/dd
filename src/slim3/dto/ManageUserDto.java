package slim3.dto;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;

import slim3.model.customerManage.Customer;

public class ManageUserDto implements Serializable {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 2859900311803832199L;

    // 氏名
    public String nickname;
    // メールアドレス
    public String email;
    //ManageUserData
    public Customer customer;
    public Key manageUserId;
    
    
    
    
    
    public Customer getManageUser() {
        return customer;
    }
    public void setManageUser(Customer customer) {
        this.customer = customer;
    }
    public Key getManageUserId() {
        return manageUserId;
    }
    public void setManageUserId(Key manageUserId) {
        this.manageUserId = manageUserId;
    }
    
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Customer getManageUserData() {
        return customer;
    }
    public void setManageUserData(Customer customer) {
        this.customer = customer;
    }
    
    
}