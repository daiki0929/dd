package slim3.controller.dto;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;

import slim3.controller.Const.Role;
import slim3.model.MsUser;
import slim3.model.reserve.ManageUser;

public class MsUserDto implements Serializable {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 2859900311803832199L;

    public Key msUserId;
    // 氏名
    public String name;
    // メールアドレス
    public String email;
    //ManageUserData
    public MsUser msUser;
    public Role role; 
    
    
    
    
    
    public Role getRole() {
        return role;
    }
    public void setRole(Role allAdm) {
        this.role = allAdm;
    }
    public MsUser getMsUser() {
        return msUser;
    }
    public void setMsUser(MsUser msUser) {
        this.msUser = msUser;
    }
    public Key getMsUserId() {
        return msUserId;
    }
    public void setMsUserId(Key msUserId) {
        this.msUserId = msUserId;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public MsUser getManageUserData() {
        return msUser;
    }
    public void setMsUserData(MsUser msUser) {
        this.msUser = msUser;
    }
    
    
}