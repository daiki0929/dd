package slim3.model;

import java.io.Serializable;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;

import slim3.Const.Role;



@Model(schemaVersion = 1)
public class MsUser implements Serializable {

    private static final long serialVersionUID = 1L;


    @Attribute(primaryKey = true)
    private Key key;

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

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MsUser other = (MsUser) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
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
