package slim3.model.customerManage;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

import slim3.model.MsUser;
import slim3.model.reserve.MenuPage;

/**
 * カスタマー(顧客)情報のモデル
 * @author uedadaiki
 *
 */
@Model(schemaVersion = 1)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    // ------------------------------------------------------
    // MsUserへの多対1の宣言(子)
    
    private ModelRef<MsUser> MsUserRef = new ModelRef<MsUser>(MsUser.class);
    
    public ModelRef<MsUser> getMsUserRef() {
        return MsUserRef;
    }
    
    public MsUser getMsUser() {
        return msUser;
    }
    public void  setMsUser(MsUser msUser) {
        this.msUser = msUser;
    }
    @Attribute(persistent = false)
    private MsUser msUser;    
    
    // ------------------------------------------------------
    
    // ------------------------------------------------------
    // MenuPageへの多対1の宣言(子)
    
    private ModelRef<MenuPage> MenuPageRef = new ModelRef<MenuPage>(MenuPage.class);
    
    public ModelRef<MenuPage> getMenuPageRef() {
        return MenuPageRef;
    }
    
    public MenuPage getMenuPage() {
        return menuPage;
    }
    public void  setMenuPage(MenuPage menuPage) {
        this.menuPage = menuPage;
    }
    @Attribute(persistent = false)
    private MenuPage menuPage;    
    
    // ------------------------------------------------------


    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    //カスタマー情報
    private String name;
    @Attribute(unindexed = true)
    private String kanaName;
    @Attribute(unindexed = true)
    private int age;
    @Attribute(unindexed = true)
    private String address;
    private String phone;
    private String mailaddress;
    private Date visitDate;
    private String orderMenu;
    private String sex;
    //TODO 使う必要無し。
    private String customerPath;
    
    /**
     * レポート用
     */
    //来店回数
    private int visitNumber;
    //支払い金額
    private int totalPayment;
    
    
    
    
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getOrderMenu() {
        return orderMenu;
    }

    public void setOrderMenu(String orderMenu) {
        this.orderMenu = orderMenu;
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
        Customer other = (Customer) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public String getKanaName() {
        return kanaName;
    }

    public void setKanaName(String kanaName) {
        this.kanaName = kanaName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCustomerPath() {
        return customerPath;
    }

    public void setCustomerPath(String customerPath) {
        this.customerPath = customerPath;
    }

    public int getVisitNumber() {
        return visitNumber;
    }

    public void setVisitNumber(int visitNumber) {
        this.visitNumber = visitNumber;
    }

    public int getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(int totalPayment) {
        this.totalPayment = totalPayment;
    }


}
