package slim3.model.reserve;

import java.io.Serializable;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

@Model(schemaVersion = 1)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    
    // ------------------------------------------------------
    // MenuPageへの多対1の宣言(子)
    private ModelRef<MenuPage> menuPageRef = new ModelRef<MenuPage>(MenuPage.class);
    

    public ModelRef<MenuPage> getMenuPageRef() {
        return menuPageRef;
    }
    
    public MenuPage getMenuPage() {
        return menuPage;
    }
    public void setMenuPage(MenuPage menuPage) {
        this.menuPage = menuPage;
    }
    
    @Attribute(persistent = false)
    private MenuPage menuPage;    
    
    // ------------------------------------------------------


    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    //メニュー情報
    private String title;
    //TODO kitazawa 画像そのものではないなら、imgPathとかimgUrlとかにするべき 
    private String img;
    private int price;
    private String content;
    private String time;
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
    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        Menu other = (Menu) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
