package slim3.controller.tools.rese;

import java.util.Random;

import org.slim3.datastore.Datastore;
import org.slim3.util.ArrayMap;

import slim3.Const;
import slim3.controller.AbstractController;
import slim3.dto.JsonDto;
import slim3.dto.ManageUserDto;
import slim3.dto.MsUserDto;
import slim3.model.MsShop;
import slim3.model.MsUser;
import slim3.model.reserve.Menu;
import slim3.model.reserve.Menu.Status;
import slim3.model.reserve.MenuPage;
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
    
    // ================================================================
    // モデル
    MsShop msShop = new MsShop();
    MenuPage menuPage = new MenuPage();
    Menu menu = new Menu();
    
    
    /**
     * 営業時間のデフォルト値をセットして返します。
     * @param msUser
     * @return
     */
    protected MsShop setShopDefault(MsUser msUser){
        
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
    
    /**
     * サンプルの予約ページ・メニューを作成します。
     * @param msUser
     * @return
     */
    protected void createSampleMenuPage(MsUser msUser){
        
        menuPage.getMsUserRef().setKey(msUser.getKey());
        menuPage.setPageTitle("予約ページのサンプル");
        menuPage.setDescription("予約ページのサンプルです。必要無い場合は「メニューページ編集」から、非公開にして下さい。");
        //TODO topImgPathはモデルから削除。reserveSystemは未実装。
//        menuPage.setTopImgPath(asString("topImgPath"));
//        menuPage.setReserveSystem(asString("reserveSystem"));
        menuPage.setInterval(1800);
        menuPage.setStatus(Status.PUBLIC.getStatus());
        menuPage.setReserveStartTime(2592000);
        menuPage.setReserveEndTime(3600);
        menuPage.setCancelTime(86400);
        //TODO 未実装
//        ArrayList<Date> noReserveDateList = new ArrayList<Date>();
//        menuPage.setNoReserveDate(noReserveDateList);
      //メニューページのURLパス
        Random rnd = new Random();
        String pagePath = Integer.toString(rnd.nextInt(999999));
        menuPage.setPagePath(pagePath);
        Datastore.put(menuPage);
        
        
        menu.setTitle("メニュー1");
//        menu.setImgPath(asString("imgPath"));
        menu.setPrice(4000);
        menu.setContent("メニュー1です。");
        menu.setTime("1800");
        menu.setStatus(Status.PUBLIC.getStatus());
        menu.getMenuPageRef().setKey(menuPage.getKey());
        Datastore.put(menu);
        
        log.info("サンプルの予約ページ・メニューを作成しました。");
        
    }
    
    
        
}




