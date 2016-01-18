package slim3.controller.tools.rese.reserve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.Const;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.meta.reserve.MenuMeta;
import slim3.model.reserve.Menu;
import slim3.model.reserve.Menu.Status;
import slim3.model.reserve.MenuPage;
/**
 * メニューを取得し、ajaxで返却します。
 * 予約の新規作成で利用します。
 * @author uedadaiki
 *
 */
public class GetMenuController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        if (asString("menuPageKey") == null) {
            return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, null));
        }
        
        
        try {
            Key menuPageKey = asKey("menuPageKey");
            MenuPage menuPage = menuPageService.get(menuPageKey);
            MenuMeta menuMeta = MenuMeta.get();
            List<Menu> publicMenuList = Datastore
                    .query(menuMeta)
                    .filter(menuMeta.menuPageRef.equal(menuPage.getKey()))
                    .filter(menuMeta.status.equal(Status.PUBLIC.getStatus()))
                    .asList();
//            log.info("公開しているメニュー：" + publicMenuList.toString());
            //keyがString型じゃないとエラーになるので、mapを作ってます。
            ArrayList<HashMap<String, Object>> menuList = new ArrayList<HashMap<String, Object>>();
            for (Menu menu : publicMenuList) {
                HashMap<String, Object> menuMap = new HashMap<String, Object>();
                menuMap.put("title", menu.getTitle());
                menuMap.put("key", Datastore.keyToString(menu.getKey()));
                int parseInt = Integer.parseInt(menu.getTime());
                menuMap.put("time", parseInt/60);
                menuMap.put("price", menu.getPrice());
                menuList.add(menuMap);
            }
            
            return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, menuList));
        } catch (Exception e) {
            log.info("エラーが発生しました。");
            e.printStackTrace();
            return returnResponse(createJsonDto(Const.JSON_STATUS_ERROR, null, null));
        }
        
    }
}
