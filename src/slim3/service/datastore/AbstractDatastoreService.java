package slim3.service.datastore;

import java.util.logging.Logger;

import slim3.service.factory.ServiceFactory;


/**
 * DatastoreServiceクラスの親クラスです。
 * datastoreパッケージ配下は全て継承させるルールで設計してください。
 * @author uedadaiki
 *
 */
public abstract class AbstractDatastoreService {

	protected final static Logger log = Logger.getLogger(AbstractDatastoreService.class.getName());
	//データサービス
	protected DsService dsService = ServiceFactory.getService(DsService.class);
	
	protected static final int FETCH_SIZE = 3000;
}
