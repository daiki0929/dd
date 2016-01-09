package slim3.controller.tools.rese.reserve;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.Const.RegexType;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.model.reserve.Menu;
/**
 * メニュー編集終了後のコントローラです。
 * @author uedadaiki
 *
 */
public class DoneEditMenuController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        Validators v = new Validators(request);
        validate(v, "menuTitle", 1, 50, false, null, null);
        validate(v, "time", 1, 10, false, RegexType.NUMBER, null);
        validate(v, "price", 1, 10, false, RegexType.NUMBER, null);
        validate(v, "content", 1, 600, false, null, null);
        validate(v, "imgPath", 1, 400, false, null, null);

        if (errors.size() != 0) {
            log.info("記入エラー");
            return forward("/tools/rese/reserve/editMenu.jsp");
        }
        
        //該当するメニューを編集する。
        Key menuKey = asKey("menuKey");
        Menu menu = menuService.get(menuKey);
        menu.setTitle(asString("menuTitle"));
        menu.setImgPath(asString("imgPath"));
        if (asInteger("price") != null) {
        menu.setPrice(asInteger("price"));
        }
        menu.setContent(asString("content"));
        menu.setTime(asString("time"));
        Datastore.put(menu);
        
        Key menuPageKey = menu.getMenuPageRef().getKey();
        
        return redirect(String.format("%s%s", "/tools/rese/reserve/menuList?id=", Datastore.keyToString(menuPageKey)));
    }
}
