package slim3.controller.tools.userManage.reserve;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import slim3.controller.AbstractController;
import slim3.model.reserve.Menu;
import slim3.model.reserve.MenuPage;

public class DoneCreateMenuController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        Validators v = new Validators(request);
        validate(v, "menuTitle", 1, 50, true, null, null);
        validate(v, "time", 1, 10, true, null, null);
        validate(v, "price", 1, 10, false, null, null);
        validate(v, "content", 1, 600, true, null, null);
        validate(v, "menuImg", 1, 400, false, null, null);

        if (errors.size() != 0) {
            log.info("記入エラー");
            return forward("/tools/userManage/entry.jsp");
        }
        
        Menu menu = new Menu();
        menu.setMenuTitle(asString("menuTitle"));
        menu.setMenuImg(asString("menuImg"));
        if (asInteger("price") != null) {
        menu.setPrice(asInteger("price"));
        }
        menu.setContent(asString("content"));
        menu.setTime(asString("time"));
        String idStr = asString("id");
        menu.getMenuPageRef().setKey(Datastore.stringToKey(idStr));
        Datastore.put(menu);
        
        //TODO 完成したメニューページのURLを指定する。
        return forward("/tools/userManage/reserve/CreateMenuPage");
    }
}
