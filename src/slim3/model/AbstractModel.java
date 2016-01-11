



package slim3.model;

import java.io.Serializable;
import java.util.logging.Logger;

import org.slim3.datastore.Attribute;

import com.google.appengine.api.datastore.Key;

import slim3.service.datastore.AbstractDatastoreService;

/**
 * 全モデルの共通親クラスです。
 * @author uedadaiki
 *
 */
public class AbstractModel implements Serializable {

	private static final long serialVersionUID = 1L;
	protected final static Logger log = Logger.getLogger(AbstractDatastoreService.class.getName());

	@Attribute(primaryKey = true)
	private Key key;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}


}