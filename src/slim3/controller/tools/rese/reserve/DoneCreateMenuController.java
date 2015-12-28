package slim3.controller.tools.rese.reserve;

import java.util.HashMap;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.Const;
import slim3.Const.RegexType;
import slim3.controller.AbstractController;
import slim3.model.reserve.Menu;
import slim3.model.reserve.Menu.Status;
/**
 * メニュー作成完了後のコントローラです。
 * @author uedadaiki
 *
 */
public class DoneCreateMenuController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        Validators v = new Validators(request);
        validate(v, "title", 1, 50, true, null, null);
        validate(v, "time", 1, 10, false, RegexType.NUMBER, null);
        validate(v, "price", 1, 10, false, RegexType.NUMBER, null);
        validate(v, "content", 1, 600, false, null, null);
        validate(v, "img", 1, 400, false, null, null);

        if (errors.size() != 0) {
            log.info("記入エラー");
            return forward("/tools/rese/reserve/createMenu.jsp");
        }
        
        Menu menu = new Menu();
        menu.setTitle(asString("title"));
        menu.setImg(asString("img"));
        if (asInteger("price") != null) {
        menu.setPrice(asInteger("price"));
        }
        menu.setContent(asString("content"));
        menu.setTime(asString("time"));
        menu.setStatus(Status.PUBLIC.getStatus());
        Key menuPageKey = asKey("menuPageKey");
//        log.info("menuPageKey：" + menuPageKey.toString());
        menu.getMenuPageRef().setKey(menuPageKey);
        Datastore.put(menu);
        log.info("保存しました");
        
        HashMap<String, String> menuMap = new HashMap<String, String>();
        menuMap.put("title", asString("title"));
        menuMap.put("img", asString("img"));
        menuMap.put("price", asString("price"));
        menuMap.put("content", asString("content"));
        menuMap.put("time", asString("time"));
        
        
        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, menuMap));
    }
}
