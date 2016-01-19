package slim3.controller.tools.rese.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.memcache.Memcache;
import org.slim3.util.ArrayMap;

import com.google.appengine.api.datastore.Key;

import main.java.org.jsoup.Jsoup;
import main.java.org.jsoup.nodes.Document;
import slim3.Const;
import slim3.controller.AbstractController;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.exception.MyException;
import slim3.meta.customerManage.CustomerMeta;
import slim3.meta.reserve.MenuMeta;
import slim3.meta.reserve.ReserveMeta;
import slim3.model.MsUser;
import slim3.model.customerManage.Customer;
import slim3.model.reserve.Menu;
import slim3.model.reserve.MenuPage;
import slim3.model.reserve.Reserve;
import slim3.service.CacheService.ExpireKbn;

/**
 * レポートを表示するコントローラです。
 * @author uedadaiki
 *
 */
public class ChartController extends AbstractReseController {
    
    
    @Override
    public Navigation run() throws Exception { 
        //認証機能
        if (!authService.isMsAuth(request, msUserDto, errors)) {
            return super.showLoginPage();
        }
        
        //データベースからクッキー情報(userId)でデータを1つ取得。
        MsUser msUser = msUserService.getSingleByCookie(request);
        if (msUser == null) {
            log.info("ユーザー情報がありませんでした。");
            return forward("/tools/rese/comeAndGo/login");
        }
        
        CustomerMeta customerMeta = CustomerMeta.get();
        
        int chartNumber = 0;
        if (asInteger("chartNumber") != null) {
            chartNumber = asInteger("chartNumber");
            
        }
        
        //TODO 全てキャッシュに残すようにする。
        switch (chartNumber) {
        case 0:
            return forward("/tools/rese/report/chart.jsp");
            
        case 1:
            //売上合計(月別)
            //キャッシュ
            if (cacheService.exist("revenuList")) {
                log.info("キャッシュにありました");
                ArrayList<Integer> revenuListInCache = Memcache.get("revenuList");
                return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, "revenu", revenuListInCache));
            }
            
            //日付順で取り出す。
            ReserveMeta reserveMeta = ReserveMeta.get();
            List<Reserve> reserveList = Datastore
                    .query(reserveMeta)
                    .filter(reserveMeta.msUserRef.equal(msUser.getKey()))
                    .sort(reserveMeta.startTime.asc)
                    .asList();
            
            ArrayMap<String, Integer> revenuByMonthMap = new ArrayMap<String, Integer>();
            revenuByMonthMap.put("1月", 0);
            revenuByMonthMap.put("2月", 0);
            revenuByMonthMap.put("3月", 0);
            revenuByMonthMap.put("4月", 0);
            revenuByMonthMap.put("5月", 0);
            revenuByMonthMap.put("6月", 0);
            revenuByMonthMap.put("7月", 0);
            revenuByMonthMap.put("8月", 0);
            revenuByMonthMap.put("9月", 0);
            revenuByMonthMap.put("10月", 0);
            revenuByMonthMap.put("11月", 0);
            revenuByMonthMap.put("12月", 0);
            
            for (Reserve reserve : reserveList) {
                Date reserveDate = reserve.getStartTime();
                
                DateTime reserveDateTime = new DateTime(reserveDate);
                //1月が0なので+1してます。
                int monthOfYear = reserveDateTime.getMonthOfYear() + 1;
                //その月の現在合計金額
                int integer = revenuByMonthMap.get(monthOfYear);
                int totalRevenu = integer + reserve.getPrice();
                
                String monthOfYearStr = Integer.toString(monthOfYear);
                //その月の現在合計金額 + 予約メニューの金額
                revenuByMonthMap.put(monthOfYearStr + "月", totalRevenu);
            }
            ArrayList<Integer> revenuList = new ArrayList<Integer>();
            for(Map.Entry<String, Integer> e : revenuByMonthMap.entrySet()) {
                revenuList.add(e.getValue());
            }
            
            
            
            cacheService.put("revenuList", revenuList, ExpireKbn.HOUR, 3);
            return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, "revenu", revenuList));
            
        case 2:
            //来店回数ランキング
            
            //キャッシュ
            if (cacheService.exist("visitRanking")) {
                log.info("キャッシュにありました");
                ArrayList<Customer> visitRankingCache = Memcache.get("visitRanking");
                return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, "visit", visitRankingCache));
            }
            
            List<Customer> customerByVisitList = Datastore
                    .query(customerMeta)
                    .filter(customerMeta.MsUserRef.equal(msUser.getKey()))
                    .sort(customerMeta.visitNumber.asc)
                    .asList();
            ArrayList<Customer> visitRanking = new ArrayList<Customer>();
            int n = 0;
            for (Customer customer : customerByVisitList) {
                n++;
                if (n > 5) {
                    break;
                }
                visitRanking.add(customer);
            }
            
            cacheService.put("visitRanking", visitRanking, ExpireKbn.HOUR, 3);
            return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, "visit", visitRanking));
            
        case 3:
            //合計金額ランキング
            
            //キャッシュ
            if (cacheService.exist("payRanking")) {
                log.info("キャッシュにありました");
                ArrayList<Customer> payRankingCache = Memcache.get("payRanking");
                return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, "pay", payRankingCache));
            }
            
            List<Customer> customerByPayList = Datastore
                    .query(customerMeta)
                    .filter(customerMeta.MsUserRef.equal(msUser.getKey()))
                    .sort(customerMeta.totalPayment.asc)
                    .asList();
            ArrayList<Customer> payRanking = new ArrayList<Customer>();
            for (Customer customer : customerByPayList) {
                payRanking.add(customer);
            }
            
            cacheService.put("payRanking", payRanking, ExpireKbn.HOUR, 3);
            return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, "pay", payRanking));
            
            //TODO 4以下は現在利用していません。
        case 4:
            //注文数ランキング(ページ別)
            List<MenuPage> menuPageList = menuPageService.getByMsUser(msUser.getKey());
            ArrayMap<String, List<Menu>> orderRankingByMenuPage = new ArrayMap<String, List<Menu>>();
            MenuMeta menuMeta = MenuMeta.get();
            for (MenuPage menuPage : menuPageList) {
                List<Menu> menuByOrderNumberList = Datastore
                        .query(menuMeta)
                        .filter(menuMeta.menuPageRef.equal(menuPage.getKey()))
                        .sort(menuMeta.orderNumber.asc)
                        .asList();
                orderRankingByMenuPage.put(menuPage.getPageTitle(), menuByOrderNumberList);
            }
            return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, "order", orderRankingByMenuPage));
            
        case 5:
            
            break;

        default:
            break;
        }
        
        
        
        //時間帯(来店)
        
        
        
        //時間帯(予約)
        
        
        
        
        return null;
        
    }
}
