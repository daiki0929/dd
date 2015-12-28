package slim3.controller.tools.rese.reserve;

import org.slim3.controller.Navigation;

import com.google.appengine.api.datastore.Key;

import slim3.controller.AbstractController;
/**
 * メニュー作成画面を表示します。
 * @author uedadaiki
 *
 */
public class CreateMenuController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        //idはメニューページのkey
        Key menuPageKey = asKey("id");
        log.info("menuPageKey：" + menuPageKey);
        
        request.setAttribute("menuPageKey", menuPageKey);
        
        return forward("createMenu.jsp");
    }
}
