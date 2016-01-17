package slim3.controller.tools.rese.reserve.customer;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.joda.time.format.DateTimeFormat;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

import slim3.controller.tools.rese.AbstractReseController;
import slim3.meta.MsUserMeta;
import slim3.meta.customerManage.CustomerMeta;
import slim3.model.MsUser;
import slim3.model.customerManage.Customer;
import slim3.model.reserve.Menu;
import slim3.model.reserve.MenuPage;
import slim3.model.reserve.Reserve;

/**
 * メニュー予約完了後のコントローラです。
 * @author uedadaiki
 *
 */
public class DoneReserveController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        Key menuKey = asKey("menuKey");
        Menu orderMenu = menuService.get(menuKey);
        
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
        googleService.sendMessage(adm, "0929dddd@gmail.com", null, "予約が確定しました", customerContent);
        //ユーザーへのメール
        googleService.sendMessage(adm, "0929dddd@gmail.com", null, "[Rese]予約が入りました", userContent);
        
        //重複予約しないようにリロードします。
        return redirect("/finish");
    }
}
