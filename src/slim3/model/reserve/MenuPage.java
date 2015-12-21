package slim3.model.reserve;

import java.io.Serializable;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

import slim3.model.MsUser;

@Model(schemaVersion = 1)
public class MenuPage implements Serializable {
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
    

    //メニューコンテンツ情報
    private String pageTitle;
    private String description;
    private String topImg;
    //承認制 or 先着順
    private String reserveSystem;
    //公開 or 非公開
    private String status;
    //予約受け付け間隔
    private int interval;
    //予約受け付け開始
    private int reserveStartTime;
    //予約受け付け終了
    private int reserveEndTime;
    //キャンセル期間
    private int cancelTime;
    //TODO kitazawa 複数日はNG?
    //予約不可の日程(定休日以外)
    private String noReserveDate;
    
    
    
    

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTopImg() {
        return topImg;
    }

    public void setTopImg(String topImg) {
        this.topImg = topImg;
    }

    public String getReserveSystem() {
        return reserveSystem;
    }

    public void setReserveSystem(String reserveSystem) {
        this.reserveSystem = reserveSystem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getReserveStartTime() {
        return reserveStartTime;
    }

    public void setReserveStartTime(int reserveStartTime) {
        this.reserveStartTime = reserveStartTime;
    }

    public int getReserveEndTime() {
        return reserveEndTime;
    }

    public void setReserveEndTime(int reserveEndTime) {
        this.reserveEndTime = reserveEndTime;
    }

    public String getNoReserveDate() {
        return noReserveDate;
    }

    public void setNoReserveDate(String noReserveDate) {
        this.noReserveDate = noReserveDate;
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
        MenuPage other = (MenuPage) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }


    public int getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(int cancelTime) {
        this.cancelTime = cancelTime;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }



}
