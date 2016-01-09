package slim3.controller.tools.rese;

import slim3.controller.AbstractController;
import slim3.dto.JsonDto;
import slim3.dto.ManageUserDto;
import slim3.dto.MsUserDto;
import slim3.service.datastore.rese.CustomerService;
import slim3.service.datastore.rese.MenuPageService;
import slim3.service.datastore.rese.MenuService;
import slim3.service.datastore.rese.ShopService;
import slim3.service.datastore.rese.MsUserService;
import slim3.service.datastore.rese.ReserveService;
import slim3.service.tools.rese.SetShopDefaultService;

/**
 * Reseの共通親クラスです。
 * @author uedadaiki
 *
 */
public abstract class AbstractReseController extends AbstractController {
    
    // ================================================================
    // Service
    /** Datastoreサービス */
    protected ManageUserDto manageUserDto = new ManageUserDto();
    protected CustomerService customerService = new CustomerService();
    protected MsUserDto msUserDto = new MsUserDto();
    protected JsonDto jsonDto = new JsonDto();
    protected MsUserService msUserService = new MsUserService();
    protected MenuPageService menuPageService = new MenuPageService();
    protected MenuService menuService = new MenuService();
    protected ShopService shopService = new ShopService();
    protected ReserveService reserveService = new ReserveService();
    protected SetShopDefaultService setShopDefaultService = new SetShopDefaultService();
        
}




