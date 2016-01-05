package slim3.model;

import java.io.Serializable;
import java.util.HashMap;

import javax.jdo.annotations.Persistent;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;
import org.slim3.util.ArrayMap;

import com.google.appengine.api.datastore.Key;
/**
 * お店の情報を入れるモデルです。
 * @author uedadaiki
 *
 */
@Model(schemaVersion = 1)
public class MsShop implements Serializable {
    // ------------------------------------------------------
    // MsUserへの多対1の宣言(子)
    private ModelRef<MsUser> msUserRef = new ModelRef<MsUser>(MsUser.class);

    public ModelRef<MsUser> getMsUserRef() {
        return msUserRef;
    }
    
    public MsUser getMsUser() {
        return msUser;
    }
    public void setMsUser(MsUser msUser) {
        this.msUser = msUser;
    }
    
    @Attribute(persistent = false)
    private MsUser msUser;    
    
    // ------------------------------------------------------

    private static final long serialVersionUID = 1L;


    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    //名前
    private String shopName;
    //ふりがな
    @Attribute(unindexed = true)
    private String kanaName;
    //住所
    private String address;
    //電話番号
    private String phone;
    //メールアドレス
    private String mailaddress;
    
    
    //曜日ごとの営業の有無、営業開始・終了時間
    @Attribute(lob = true)
    @Persistent(serialized = "true", defaultFetchGroup = "true")
    private ArrayMap<String, ArrayMap<String, Object>> statusByDays;

    
    public ArrayMap<String, ArrayMap<String, Object>> getStatusByDays() {
        return statusByDays;
    }

    public void setStatusByDays(
            ArrayMap<String, ArrayMap<String, Object>> statusByDays) {
        this.statusByDays = statusByDays;
    }

    public String getShopName() {
        return shopName;
    }
    
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    
    public String getKanaName() {
        return kanaName;
    }
    
    public void setKanaName(String kanaName) {
        this.kanaName = kanaName;
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
        MsShop other = (MsShop) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }


}
