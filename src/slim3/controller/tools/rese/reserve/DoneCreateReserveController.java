package slim3.controller.tools.rese.reserve;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

import slim3.Const.RegexType;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.meta.customerManage.CustomerMeta;
import slim3.model.MsUser;
import slim3.model.customerManage.Customer;
import slim3.model.reserve.Menu;
import slim3.model.reserve.MenuPage;
import slim3.model.reserve.Reserve;
import util.StringUtil;

/**
 * 予約を作成するコントローラです。
 * @author uedadaiki
 *
 */
public class DoneCreateReserveController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        Validators v = new Validators(request);
        validate(v, "orderMenu", 1, 50, false, null, null);
        validate(v, "reserveDate", 1, 10, false, RegexType.YEAR_DATE, null);
        validate(v, "reserveMoments", 1, 10, false, RegexType.MOMENTS, null);
        validate(v, "customerName", 1, 20, false, null, null);
        validate(v, "customerMailaddress", 1, 30, false, RegexType.MAIL_ADDRESS, null);
        validate(v, "customerPhone", 1, 15, false, RegexType.PHONE, null);
        
        String customerName = asString("customerName");
        String customerMailaddress = asString("customerMailaddress");
        String customerPhone = asString("customerPhone");
        
        //登録済みの顧客を選択した場合
        if (asString("customerKey") != null) {
            Key customerKey = asKey("customerKey");
            Customer customer = customerService.get(customerKey);
            customerName = customer.getName();
            customerMailaddress = customer.getMailaddress();
            customerPhone = customer.getPhone();
        }
        
        String reserveDateStr = asString("reserveDate");
        String reserveMoments = asString("reserveMoments");
        //(例)2015/12/16 9:00
        String reserveTime = String.format("%s %s", reserveDateStr,reserveMoments);
        
        Key menuKey = asKey("menuKey");
        Menu orderMenu = menuService.get(menuKey);
        
        //メニューからMsUserのkeyを取得します。
        ModelRef<MenuPage> menuPageRef = orderMenu.getMenuPageRef();
        MenuPage menuPage = menuPageService.get(menuPageRef.getKey());
        Key msUserKey = menuPage.getMsUserRef().getKey();
        
        //制限を超えていたらエラーページに飛ばします。
        List<Reserve> reserveThisMonth = reserveService.getReserveThisMonth(msUserKey);
        log.info("今月の予約管理数：" + Integer.toString(reserveThisMonth.size()));
        MsUser msUser = msUserService.get(msUserKey);
        if (roleService.checkReserveLimit(msUser, reserveThisMonth)) {
            return forward("/tools/rese/errorPage");
        }
        
        //注文回数
        orderMenu.setOrderNumber(orderMenu.getOrderNumber() + 1);
        Datastore.put(orderMenu);
        
        //メニュー終了時刻を計算します。
        DateTime reserveDateTime = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm").parseDateTime(reserveTime);
        log.info("予約開始時刻：" + reserveDateTime.toString());
        int menuTimeInt = Integer.parseInt(orderMenu.getTime());
        log.info("メニューにかかる時間：" + Integer.toString(menuTimeInt/60) + "分");
        //メニュー終了時刻
        DateTime menuEndDateTime = reserveDateTime.plusMinutes(menuTimeInt/60);
        String menuEndTimeStr = String.format("%s/%s/%s %s:%s", menuEndDateTime.getYear(), menuEndDateTime.getMonthOfYear(), menuEndDateTime.getDayOfMonth(), menuEndDateTime.getHourOfDay(), menuEndDateTime.getMinuteOfHour());
        String parseMenuEndTime = StringUtil.parseRegex(menuEndTimeStr, ":[0-9]$", ":00");
        String menuEndTime = parseMenuEndTime;
        log.info("予約終了時刻：" + menuEndTime);
        
      
        
        //Date型に変更します。
        Date reserveDate = 
                DateTimeFormat
                .forPattern("yyyy/MM/dd HH:mm")
                .parseDateTime(reserveTime)
                .toDate();
        Date menuEndDate = 
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
        customer.setTotalPayment(customer.getTotalPayment() + orderMenu.getPrice());
        
        
        //予約を保存します。
        Reserve reserve = new Reserve();
        reserve.getMsUserRef().setKey(msUserKey);
        reserve.setMenuTitle(orderMenu.getTitle());
        reserve.setTime(orderMenu.getTime());
        reserve.setPrice(orderMenu.getPrice());
        reserve.setStartTime(reserveDate);
        reserve.setEndTime(menuEndDate);
        reserve.setCustomerName(customerName);
        reserve.setCustomerMailaddress(customerMailaddress);
        reserve.setCustomerPhone(customerPhone);
        
        
        boolean repeater = false;
        //リピーターの場合
        if (asString("customerKey") != null) {
            CustomerMeta customerMeta = CustomerMeta.get();
            List<Customer> CustomerList = Datastore
                    .query(customerMeta)
                    .filter(customerMeta.MsUserRef.equal(msUserKey))
                    .asList();
            log.info("CustomerList"+CustomerList.toString());
            for (Customer savedCustomer : CustomerList) {
                log.info("保存されてるメールアドレス：" + savedCustomer.getMailaddress());
                Key savedCustomerKey = savedCustomer.getKey();
                if (savedCustomerKey.equals(asKey("customerKey"))) {
                    log.info("リピーター客として保存します。");
                    reserve.getCustomerRef().setKey(savedCustomerKey);
                    //合計金額・来店回数をプラスします。
                    savedCustomer.setTotalPayment(savedCustomer.getTotalPayment() + orderMenu.getPrice());
                    savedCustomer.setVisitNumber(savedCustomer.getVisitNumber() + 1);
                    Datastore.put(savedCustomer);
                    repeater = true;
                    break;
                }
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
        
        dsService.put(reserve);
        log.info(String.format("%s%s", customerName, "様の予約を保存しました"));
        
        //キャッシュを最新にするため削除します。
        if (cacheService.exist(msUser.getMailaddress())) {
            log.info("キャッシュを最新にするため削除します。");
            cacheService.delete(msUser.getMailaddress());
        }
        
        return redirect("/tools/rese/reserve/reserveList");
    }
}
