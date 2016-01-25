package slim3.model.reserve;

import java.util.Date;
import java.util.List;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import slim3.model.AbstractModel;
import slim3.model.MsUser;

@Model(schemaVersion = 1)
public class MenuPage extends AbstractModel {
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

    @Attribute(version = true)
    private Long version;
    
    //予約ページのURLパス(7桁の乱数)
    private String pagePath;
    //メニューコンテンツ情報
    private String pageTitle;
    private String description;
    private String topImgPath;
    //承認制 or 先着順
    private String reserveSystem;
    //公開 or 非公開
    private String status;
    public enum Status{
        PUBLIC("public"),   
        CLOSED("closed");
        
        private final String value;
        
        //コンストラクタ
        private Status(String value) {
            this.value = value;
        }
        
        public String getStatus(){
            return value;
        }
    }
    //予約受け付け間隔
    private int interval;
    //予約受け付け開始
    private int reserveStartTime;
    //予約受け付け終了
    private int reserveEndTime;
    //キャンセル期間
    private int cancelTime;
    //予約不可の日程(定休日以外)
    private List<Date> noReserveDate;
    
    
    
    

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

    public String getTopImgPath() {
        return topImgPath;
    }

    public void setTopImgPath(String topImgPath) {
        this.topImgPath = topImgPath;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    public List<Date> getNoReserveDate() {
        return noReserveDate;
    }

    public void setNoReserveDate(List<Date> noReserveDate) {
        this.noReserveDate = noReserveDate;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }






}
