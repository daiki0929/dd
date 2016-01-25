package slim3.model;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;



@Model(schemaVersion = 1)
public class MsUser extends AbstractModel {

    private static final long serialVersionUID = 1L;

    @Attribute(version = true)
    private Long version;

    //ユーザー情報
    private String userId;
    //権限
    private Role role;
    public enum Role{
        FREE("free"),   
        PRO("pro");
        
        private final String role;
        
        //コンストラクタ
        private Role(String role) {
            this.role = role;
        }
        
        public String getRole(){
            return role;
        }
    }
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
    //予約ページのユーザーパス
    private String userPath;
    //ユーザー画像
    private String userImgPath;
    
    
    //TwitterAPI
    private String twAccessToken;
    private String twAccessTokenSecret;
    
    //FacebookAPI
    private String fbAccessToken;
    private String fbAccessTokenSecret;
    
    //Gmail用アクセストークン
    private String gmailAccessToken;
    //Gmailアドレス
    private String gmailAddress;
    //リフレッシュトークン
    private String gmailRefreshToken;
    
    
    
    
    
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

    public String getGmailAccessToken() {
        return gmailAccessToken;
    }

    public void setGmailAccessToken(String gmailAccessToken) {
        this.gmailAccessToken = gmailAccessToken;
    }

    public String getGmailAddress() {
        return gmailAddress;
    }

    public void setGmailAddress(String gmailAddress) {
        this.gmailAddress = gmailAddress;
    }

    public String getGmailRefreshToken() {
        return gmailRefreshToken;
    }

    public void setGmailRefreshToken(String gmailRefreshToken) {
        this.gmailRefreshToken = gmailRefreshToken;
    }

    public String getUserPath() {
        return userPath;
    }

    public void setUserPath(String userPath) {
        this.userPath = userPath;
    }

    public String getUserImgPath() {
        return userImgPath;
    }

    public void setUserImgPath(String userImgPath) {
        this.userImgPath = userImgPath;
    }


}
