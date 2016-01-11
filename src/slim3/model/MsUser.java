package slim3.model;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import slim3.Const.Role;



@Model(schemaVersion = 1)
public class MsUser extends AbstractModel {

    private static final long serialVersionUID = 1L;

    @Attribute(version = true)
    private Long version;

    //ユーザー情報
    private String userId;
    //権限
    private Role role;
    //名前(個人名 or 店舗名)
    private String name;
    //住所
    private String address;
    //電話番号
    private String phone;
    //メールアドレス
    private String mailaddress;
    //パスワード
    private String password;
    
    //TwitterAPI
    private String twAccessToken;
    private String twAccessTokenSecret;
    
    //FacebookAPI
    private String fbAccessToken;
    private String fbAccessTokenSecret;
    
    
    
    
    
    public String getFbAccessToken() {
        return fbAccessToken;
    }

    public void setFbAccessToken(String fbAccessToken) {
        this.fbAccessToken = fbAccessToken;
    }

    public String getFbAccessTokenSecret() {
        return fbAccessTokenSecret;
    }

    public void setFbAccessTokenSecret(String fbAccessTokenSecret) {
        this.fbAccessTokenSecret = fbAccessTokenSecret;
    }

    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMailaddress() {
        return mailaddress;
    }

    public void setMailaddress(String mailaddress) {
        this.mailaddress = mailaddress;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTwAccessToken() {
        return twAccessToken;
    }

    public void setTwAccessToken(String twAccessToken) {
        this.twAccessToken = twAccessToken;
    }

    public String getTwAccessTokenSecret() {
        return twAccessTokenSecret;
    }

    public void setTwAccessTokenSecret(String twAccessTokenSecret) {
        this.twAccessTokenSecret = twAccessTokenSecret;
    }


}
