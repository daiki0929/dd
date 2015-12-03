package slim3.controller.dto;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;

import slim3.model.reserve.ManageUser;

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
    public ManageUser manageUser;
    public Key manageUserId;
    
    
    
    
    
    public ManageUser getManageUser() {
        return manageUser;
    }
    public void setManageUser(ManageUser manageUser) {
        this.manageUser = manageUser;
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
    public ManageUser getManageUserData() {
        return manageUser;
    }
    public void setManageUserData(ManageUser manageUser) {
        this.manageUser = manageUser;
    }
    
    
}