package slim3.controller.tools.userManage.reserve;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.controller.AbstractController;
import slim3.controller.Const.RegexType;
import slim3.model.reserve.Menu;
/**
 * メニュー編集終了後のコントローラです。
 * @author uedadaiki
 *
 */
public class DoneEditMenuController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        Validators v = new Validators(request);
        validate(v, "menuTitle", 1, 50, false, null, null);
        validate(v, "time", 1, 10, false, RegexType.NUMBER, null);
        validate(v, "price", 1, 10, false, RegexType.NUMBER, null);
        validate(v, "content", 1, 600, false, null, null);
        validate(v, "menuImg", 1, 400, false, null, null);

        if (errors.size() != 0) {
            log.info("記入エラー");
            return forward("/tools/userManage/reserve/editMenu.jsp");
        }
        
        //該当するメニューを編集する。
        Key menuKey = asKey("menuKey");
        Menu menu = menuService.get(menuKey);
        menu.setMenuTitle(asString("menuTitle"));
        menu.setMenuImg(asString("menuImg"));
        if (asInteger("price") != null) {
        menu.setPrice(asInteger("price"));
        }
        menu.setContent(asString("content"));
        menu.setTime(asString("time"));
        Datastore.put(menu);
        
        //TODO 完成したメニューページのURLを指定する。今は顧客情報のページに。
        return forward("/tools/userManage/customerList");
    }
}
