package slim3.controller.tools.rese.reserve;

import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.controller.tools.rese.AbstractReseController;
import slim3.meta.reserve.MenuMeta;
import slim3.meta.reserve.MenuPageMeta;
/**
 * 非公開のメニューページを削除するコントローラです。
 * 毎週日曜日の0時にクーロンで動かします。
 * @author uedadaiki
 *
 */
public class DeleteMenuPageController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        //非公開のメニューページを削除
        MenuPageMeta menuPageMeta = MenuPageMeta.get();
        List<Key> menuPageKeyList = Datastore
                .query(menuPageMeta)
                .filter(menuPageMeta.status.equal(CLOSED))
                .asKeyList();
        Datastore.delete(menuPageKeyList);
        
        //非公開のメニューページにあるメニューを削除
        MenuMeta menuMeta = MenuMeta.get();
        for (Key key : menuPageKeyList) {
            List<Key> menuKeyList = Datastore
                    .query(menuMeta)
                    .filter(menuMeta.menuPageRef.equal(key))
                    .asKeyList();
            Datastore.delete(menuKeyList);
        }
        
        
        
        return null;
    }
}
