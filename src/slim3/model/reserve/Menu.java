package slim3.model.reserve;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import slim3.model.AbstractModel;

@Model(schemaVersion = 1)
public class Menu extends AbstractModel {

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

    @Attribute(version = true)
    private Long version;

    //メニュー情報
    private String title;
    private String imgPath;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
