package slim3.model.reserve;

import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import slim3.model.AbstractModel;
import slim3.model.MsUser;
import slim3.model.customerManage.Customer;

@Model(schemaVersion = 1)
/**
 * 予約情報
 * @author uedadaiki
 *
 */
public class Reserve extends AbstractModel {

    private static final long serialVersionUID = 1L;
    
    // ------------------------------------------------------
    // MsUserへの多対1の宣言(子)
    private ModelRef<MsUser> msUserRef = new ModelRef<MsUser>(MsUser.class);

    public ModelRef<MsUser> getMsUserRef() {
        return msUserRef;
    }
    
    public MsUser getMsUser() {
        return MsUser;
    }
    public void setMsUser(MsUser MsUser) {
        this.MsUser = MsUser;
    }
    
    @Attribute(persistent = false)
    private MsUser MsUser;    
    
    // ------------------------------------------------------
    
    // ------------------------------------------------------
    // Customerへの多対1の宣言(子)
    private ModelRef<Customer> customerRef = new ModelRef<Customer>(Customer.class);

    public ModelRef<Customer> getCustomerRef() {
        return customerRef;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    @Attribute(persistent = false)
    private Customer customer;    
    
    // ------------------------------------------------------

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
    //予約を確定した日時
    private Date noticeDate;
    
    //TODO Customerに紐付けたので必要無し。ReserveServiceの修正必要
    private String customerName;
    private String customerMailaddress;
    private String customerPhone;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCustomerMailaddress() {
        return customerMailaddress;
    }

    public void setCustomerMailaddress(String customerMailaddress) {
        this.customerMailaddress = customerMailaddress;
    }

    public Date getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(Date noticeDate) {
        this.noticeDate = noticeDate;
    }
}
