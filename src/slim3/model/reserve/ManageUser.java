package slim3.model.reserve;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

import slim3.model.MsUser;

@Model(schemaVersion = 1)
public class ManageUser implements Serializable {

    private static final long serialVersionUID = 1L;
    // ------------------------------------------------------
    // GroupManageUserへの多対1の宣言(子)
    
    private ModelRef<GroupManageUser> GroupManageUserRef = new ModelRef<GroupManageUser>(GroupManageUser.class);
    
    public ModelRef<GroupManageUser> getGroupManageUserRef() {
        return GroupManageUserRef;
    }
    
    public GroupManageUser getGroupManageUser() {
        return groupManageUser;
    }
    public void  setGroupManageUser(GroupManageUser groupManageUser) {
        this.groupManageUser = groupManageUser;
    }
    @Attribute(persistent = false)
    private GroupManageUser groupManageUser;    
    
    // ------------------------------------------------------
    
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


    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    //お客様情報
    private String name;
    private int age;
    private String address;
    private String phone;
    private String mailaddress;
    private Date visitDate;
    private String orderMenu;
    
    
    
    
    
    
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
        ManageUser other = (ManageUser) obj;
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
