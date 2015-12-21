package slim3.model.reserve;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

import slim3.model.MsUser;

@Model(schemaVersion = 1)
public class Reserve implements Serializable {

    private static final long serialVersionUID = 1L;
    
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


    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    //予約メニュー情報
    private String menuTitle;
    private int price;
    private String time;
    //開始日時
    private Date startTime;
    //終了日時
    private Date endTime;

    //予約者情報
    private String customerName;
    private String customerMailadress;
    private String customerPhone;
    
    
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    

    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerMailadress() {
        return customerMailadress;
    }

    public void setCustomerMailadress(String customerMailadress) {
        this.customerMailadress = customerMailadress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }
    
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
        Reserve other = (Reserve) obj;
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
