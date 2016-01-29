package slim3.controller.tools.rese;

import org.slim3.controller.Navigation;
import org.slim3.util.ArrayMap;

import slim3.Const;
import slim3.controller.AbstractController;
import slim3.dto.JsonDto;
import slim3.dto.ManageUserDto;
import slim3.dto.MsUserDto;
import slim3.model.MsShop;
import slim3.model.MsUser;
import slim3.service.datastore.rese.CustomerService;
import slim3.service.datastore.rese.MenuPageService;
import slim3.service.datastore.rese.MenuService;
import slim3.service.datastore.rese.MsUserService;
import slim3.service.datastore.rese.ReserveService;
import slim3.service.datastore.rese.RoleService;
import slim3.service.datastore.rese.ShopService;
import slim3.service.tools.rese.ReserveTimeService;
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
    
    /** 予約時間のサービス */
    protected ReserveTimeService reserveTimeService = new ReserveTimeService();
    
    
    /** 会員クラスのサービス */
    protected RoleService roleService = new RoleService();
    
    
    
    // ================================================================
    /**
     * メニューページの公開・非公開
     * @author uedadaiki
     *
     */
    public static enum MenuPageStatus{
        PUBLIC("public"),
        CLOSED("closed");
        
        private final String status;
        
        private MenuPageStatus(String status){
            this.status = status;
        }
        
        public String getMenuPageStatus() {
            return status;
        }
    }
    
    // ================================================================
    // 正規表現
    public final static String USER_PATH = "@.*";
    
    
    /**
     * 営業時間のデフォルト値をセットして返します。
     * @param msUser
     * @return
     */
    protected MsShop setShopDefault(MsUser msUser){
        
        MsShop msShop = new MsShop();
        msShop.getMsUserRef().setKey(msUser.getKey());
        log.info("店舗情報のデフォルト値を登録します。");
        
        ArrayMap<String, ArrayMap<String, Object>> shopStatusByDays = new ArrayMap<String, ArrayMap<String,Object>>();
        ArrayMap<String, Object> weekMap = new ArrayMap<String, Object>();
        weekMap.put("shopStatus", Const.OPEN);
        weekMap.put("startTime", "0:00");
        weekMap.put("endTime", "23:59");
        String[] daysOfTheWeekList = {Const.SUNDAY, Const.MONDAY, Const.TUESDAY, Const.WEDNESDAY, Const.THURSDAY, Const.FRIDAY, Const.SATURDAY};
        for (String daysOfTheWeek : daysOfTheWeekList) {
            shopStatusByDays.put(daysOfTheWeek, weekMap);
        }
//        log.info(shopStatusByDays.toString());
        msShop.setStatusByDays(shopStatusByDays);
        return msShop;
    }
    
    
        
}




