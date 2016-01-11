package slim3.controller.tools.rese.reserve;

import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.controller.tools.rese.AbstractReseController;
import slim3.meta.reserve.MenuMeta;
import slim3.model.reserve.Menu;
import slim3.model.reserve.Menu.Status;
import slim3.model.reserve.MenuPage;
/**
 * メニューの一覧・追加画面を表示します。
 * @author uedadaiki
 *
 */
public class MenuListController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }
        
        try {
            Key menuPageKey = asKey("id");
            MenuPage menuPage = menuPageService.get(menuPageKey);
            
            if (menuPage != null) {
                String closedParam = asString("s");
                if (closedParam == null) {
                    //公開メニューを取り出す
                    MenuMeta menuMeta = MenuMeta.get();
                    List<Menu> publicMenuList = Datastore
                            .query(menuMeta)
                            .filter(menuMeta.menuPageRef.equal(menuPage.getKey()))
                            .filter(menuMeta.status.equal(Status.PUBLIC.getStatus()))
                            .asList();
                    log.info("公開しているメニュー：" + publicMenuList.toString());
                    request.setAttribute("publicMenuList", publicMenuList);
                }else if(closedParam != null) {
                    //非公開メニューを取り出す
                    MenuMeta menuMeta = MenuMeta.get();
                    List<Menu> closedMenuList = Datastore
                            .query(menuMeta)
                            .filter(menuMeta.menuPageRef.equal(menuPage.getKey()))
                            .filter(menuMeta.status.equal(Status.PUBLIC.getStatus()))
                            .asList();
                    log.info("非公開のメニュー：" + closedMenuList.toString());
                    
                    request.setAttribute("closedMenuList", closedMenuList);
                }
                
            }else {
                log.info("メニューはありませんでした。");
            }
            
            request.setAttribute("menuPageKey", menuPageKey);
            
        } catch (Exception e) {
            log.info("エラーが発生しました。");
            e.printStackTrace();
            return forward("/tools/rese/common/errorPage.jsp");
        }
        
        return forward("menuList.jsp");
    }
}
