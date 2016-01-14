package slim3.model.reserve;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;

import slim3.model.MsUser;

@Model(schemaVersion = 1)
/**
 * GCSに保存する画像ファイルのモデル
 * @author uedadaiki
 *
 */
public class FileGCS implements Serializable {

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

    /** BlobKey */
    @Attribute(primaryKey = true)
    Key key;

    String filename;

    String contentType;

    Long size;

    Date creation;

    String md5Hash;


    /**
     * {@link Key} 生成
     * @param blobKey 
     * @return {@link Key}
     * @author sinmetal
     */
    public static Key createKey(BlobKey blobKey) {
        return Datastore.createKey(FileGCS.class, blobKey.getKeyString());
    }

    public String getMd5Hash() {
        return md5Hash;
    }

    public void setMd5Hash(String md5Hash) {
        this.md5Hash = md5Hash;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    /**
     * the constructor.
     * @category constructor
     */
    public FileGCS() {
    }

    /**
     * the constructor.
     * @param info
     * @category constructor
     */
    public FileGCS(BlobInfo info) {
        this.key = createKey(info.getBlobKey());
        this.filename = info.getFilename();
        this.contentType = info.getContentType();
        this.md5Hash = info.getMd5Hash();
        this.creation = info.getCreation();
        this.size = info.getSize();
    }
}
