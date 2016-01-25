package slim3.controller.tools.rese.reserve.customer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelRef;
import org.slim3.util.ArrayMap;

import com.google.appengine.api.datastore.Key;

import slim3.Const;
import slim3.Const.RegexType;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.meta.MsUserMeta;
import slim3.meta.customerManage.CustomerMeta;
import slim3.model.MsShop;
import slim3.model.MsUser;
import slim3.model.customerManage.Customer;
import slim3.model.reserve.Menu;
import slim3.model.reserve.MenuPage;
import slim3.model.reserve.Reserve;
import slim3.service.CacheService.ExpireKbn;

/**
 * メニュー予約完了後のコントローラです。
 * @author uedadaiki
 *
 */
//TODO DoReserve
public class DoneReserveController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        Key menuKey = asKey("menuKey");
        Menu orderMenu = menuService.get(menuKey);
        //注文回数
        orderMenu.setOrderNumber(orderMenu.getOrderNumber() + 1);
        Datastore.put(orderMenu);
        
        ModelRef<MenuPage> menuPageRef = orderMenu.getMenuPageRef();
        MenuPage menuPage = menuPageService.get(menuPageRef.getKey());
        Key msUserKey = menuPage.getMsUserRef().getKey();
        //バリデートはConfirmReserveで行っています。
        String reserveTime = asString("reserveTime");
        String menuEndTime = asString("menuEndTime");
        String customerName = asString("customerName");
        String customerMailaddress = asString("customerMailaddress");
        String customerPhone = asString("customerPhone");
        
        Date reserveDateTime = 
                DateTimeFormat
                .forPattern("yyyy/MM/dd HH:mm")
                .parseDateTime(reserveTime)
                .toDate();
        Date menuEndDateTime = 
                DateTimeFormat
                .forPattern("yyyy/MM/dd HH:mm")
                .parseDateTime(menuEndTime)
                .toDate();
        
        //カスタマー情報を保存します。
        Customer customer = new Customer();
        customer.setName(customerName);
        customer.setPhone(customerPhone);
        customer.setMailaddress(customerMailaddress);
        customer.setVisitNumber(1);
        customer.setTotalPayment(orderMenu.getPrice());
        
        
        //予約を保存します。
        Reserve reserve = new Reserve();
        reserve.getMsUserRef().setKey(msUserKey);
        reserve.setMenuTitle(orderMenu.getTitle());
        reserve.setTime(orderMenu.getTime());
        reserve.setPrice(orderMenu.getPrice());
        reserve.setStartTime(reserveDateTime);
        reserve.setEndTime(menuEndDateTime);
        reserve.setCustomerName(customerName);
        reserve.setCustomerMailaddress(customerMailaddress);
        reserve.setCustomerPhone(customerPhone);
        
        //重複チェック
        boolean b = reserveTimeService.checkDoubleBooking(msUserKey, menuPage, reserveDateTime, menuEndDateTime);
        if (!b) {
            log.info("予約前に既存予約と重複してました。");
            MsUser msUser = msUserService.get(msUserKey);
            List<Menu> menuList = menuService.getListByMenuPageKey(menuPage.getKey());
            
            Random rnd = new Random();
            String customerID = Integer.toString(rnd.nextInt(999999));
            
            request.setAttribute("error", "error");
            
            request.setAttribute("customerID", customerID);
            request.setAttribute("selectedMeuKey", orderMenu.getKey());
            request.setAttribute("customerName", customerName);
            request.setAttribute("customerMailaddress", customerMailaddress);
            request.setAttribute("customerPhone", customerPhone);
            request.setAttribute("menuList", menuList);
            
            //受付開始(日)
            int reserveTo = menuPage.getReserveStartTime();
            //締め切り時間(秒)
            int reserveFrom = menuPage.getReserveEndTime();

            DateTime today = new DateTime();
            //1月が0なので、-1する。
            DateTime reserveToDateTime = today.plusDays(reserveTo).plusMonths(-1);
            DateTime reserveFromDateTime = today.plusSeconds(reserveFrom).plusMonths(-1);
            
            request.setAttribute("reserveTo", reserveToDateTime.toString("yyyy,M,d"));
            request.setAttribute("reserveFrom", reserveFromDateTime.toString("yyyy,M,d"));
            
            if (reserveTo == 0) {
                //予約開始日を設定していない場合は、fullcalendarのmaxを3000年に設定して対応します。(minだけ設定するのは無理そう)
                log.info("予約開始日が設定されていません。");
                request.setAttribute("reserveTo", "3000,1,1");
            }
            
            
            MsShop usersShopInfo = shopService.getByMsUserKey(msUserKey);
            //予約可能時間
            ArrayMap<String, ArrayMap<String, Object>> statusByDays = usersShopInfo.getStatusByDays();
            log.info("statusByDays："+statusByDays.toString());
            
            @SuppressWarnings("rawtypes")
            Iterator iterator = statusByDays.keySet().iterator();
            ArrayList<Integer> offDaysOfTheWeekNum = new ArrayList<Integer>();
            Integer n = -1;
            while(iterator.hasNext()) {
                n++;
                try {
                    Object status = statusByDays.get(iterator.next()).get("shopStatus");
                    if (status.equals(Const.NOT_OPEN)) {
                        log.info("notOpenでした。追加します。");
                        offDaysOfTheWeekNum.add(n);
                    }
                } catch (NoSuchElementException e) {
                    break;
                }
            }
            request.setAttribute("offDaysOfTheWeekNum", offDaysOfTheWeekNum);
            
            //TODO テスト用
            String timeScheduleURL = String.format("%s%s%s%s", "/r/reserve/", msUser.getUserPath(), "/", menuPage.getPagePath());
            return forward(timeScheduleURL);
        }
        
        //リピーターの場合
        CustomerMeta customerMeta = CustomerMeta.get();
        List<Customer> CustomerList = Datastore
                .query(customerMeta)
                .filter(customerMeta.MsUserRef.equal(msUserKey))
                .asList();
        log.info("CustomerList"+CustomerList.toString());
        boolean repeater = false;
        for (Customer savedCustomer : CustomerList) {
            log.info("保存されてるメールアドレス：" + savedCustomer.getMailaddress());
            if (savedCustomer.getMailaddress().equals(customerMailaddress)) {
                log.info("リピーター客として保存します。");
                //合計金額
                savedCustomer.setTotalPayment(savedCustomer.getTotalPayment() + orderMenu.getPrice());
                savedCustomer.setVisitNumber(savedCustomer.getVisitNumber() + 1);
                Datastore.put(savedCustomer);
                Key savedCustomerKey = savedCustomer.getKey();
                reserve.getCustomerRef().setKey(savedCustomerKey);
                repeater = true;
                break;
            }
        }
        
        
        //新規顧客の場合
        if (!repeater) {
            log.info("新規顧客として保存します。");
            customer.getMsUserRef().setKey(msUserKey);
            customer.getMenuPageRef().setKey(menuPage.getKey());
            //顧客のページURLを作成します。
            Random rnd = new Random();
            String pagePath = Integer.toString(rnd.nextInt(999999));
            customer.setCustomerPath(pagePath);
            Datastore.put(customer);
            
            Key customerKey = customer.getKey();
            log.info("key：" + Datastore.keyToString(customerKey));
            reserve.getCustomerRef().setKey(customerKey);
        }
        
        //予約確定日時を保存
        DateTime now = new DateTime();
        reserve.setNoticeDate(now.toDate());

        Datastore.put(reserve);
        log.info(String.format("%s%s", customerName, "様の予約を保存しました"));
        
        //メニューの時間
        int menuTime = Integer.parseInt(orderMenu.getTime())/60;
        //円をカンマ区切りに
        NumberFormat nfNum = NumberFormat.getNumberInstance();  
        String menuPrice = nfNum.format(orderMenu.getPrice());
        //キャンセルのリンク
        String menuPageKeyStr = Datastore.keyToString(menuPage.getKey());
        String reserveKeyStr = Datastore.keyToString(reserve.getKey());
        //ユーザー
        MsUserMeta msUserMeta = MsUserMeta.get();
        MsUser msUser = Datastore
                .query(msUserMeta)
                .filter(msUserMeta.key.equal(menuPage.getMsUserRef().getKey()))
                .asSingle();
        //顧客
        String customerPath = customer.getCustomerPath();
        
        //TODO テスト環境用にしてます。
        String cancelURL = String.format("%s%s%s%s", "http://localhost:8888/tools/rese/reserve/customer/cancel?menuPageKey=", menuPageKeyStr, "&reserveKey=", reserveKeyStr);
        String customerContent = String.format("%s様\n\n以下の通り、ご予約が確定しました。\n\n◆ご予約内容\n%s(%s分) %s円\n\n◆予約日時\n%s〜%s\n\n◆キャンセル\n予約をキャンセルする場合はこちらのリンクから\n%s", customerName, orderMenu.getTitle(), menuTime, menuPrice, reserveTime, menuEndTime, cancelURL);
        String userContent = String.format("%s様\n\n予約が入りました。予約内容をご確認ください。\n\n◆ご予約内容\n%s(%s分) %s円\n\n◆予約日時\n%s〜%s\n\n◆予約者の情報\n%s様\n%s/%s", msUser.getName(), orderMenu.getTitle(), menuTime, menuPrice, reserveTime, menuEndTime, customerName, "http://localhost:8888/c/customer", customerPath);
        
        
        //admアカウントから送信します。
        MsUser adm = msUserService.getSingleByEmail("reseinfomail@gmail.com");
        
        //TODO テスト環境用にしてます。
        //カスタマーへのメール
//        googleService.sendMessage(adm, "0929dddd@gmail.com", null, "予約が確定しました", customerContent);
        //ユーザーへのメール
//        googleService.sendMessage(adm, "0929dddd@gmail.com", null, "[Rese]予約が入りました", userContent);
        
        //重複予約しないようにリロードします。
        //TODO 重複の確認(jsでもあり)
        return redirect("/finish");
    }
}
