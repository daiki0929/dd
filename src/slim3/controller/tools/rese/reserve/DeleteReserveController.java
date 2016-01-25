package slim3.controller.tools.rese.reserve;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.controller.tools.rese.AbstractReseController;
/**
 * 予約を削除します。
 * @author uedadaiki
 *
 */
public class DeleteReserveController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        Key reserveKey = asKey("id");
        Datastore.delete(reserveKey);
        log.info("予約を削除しました。");
        
        return forward("/tools/rese/reserve/reserveList");
    }
}
