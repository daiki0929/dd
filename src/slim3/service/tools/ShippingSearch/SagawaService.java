package slim3.service.tools.ShippingSearch;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.slim3.util.ArrayMap;

import main.java.org.jsoup.Connection;
import main.java.org.jsoup.Connection.Method;
import main.java.org.jsoup.Jsoup;
import main.java.org.jsoup.nodes.Document;
import main.java.org.jsoup.nodes.Element;
import main.java.org.jsoup.select.Elements;
import slim3.Const;
import util.StringUtil;

/**
 * 佐川宅急便の配送状況を調べるサービスです。
 * @author uedadaiki
 *
 */
public class SagawaService implements SearchService {
    
    public final static Logger log = Logger.getLogger(SagawaService.class.getName());
    
    public static final String SAGAWA_URL = "http://k2k.sagawa-exp.co.jp/p/web/okurijosearch.do";
    public static final String SAGAWA_HOST_NAME = "www.sagawa-exp.co.jp";
    Map<String, String> cookies = new HashMap<String, String>();
    
    /**
     * 佐川宅急便のクッキーを作成します。
     */
    public void createCookie(){
            cookies = new HashMap<String, String>();
            cookies.put("_ga", "GA1.3.966834884.1451369455");
            cookies.put("__utma", "1.966834884.1451369455.1451369455.1451890145.2");
            cookies.put("__utma", "1.966834884.1451369455.1451890145.1451913029.3");
            cookies.put("__utmb", "1.13.10.1451913029");
            cookies.put("__utmc", "1");
            cookies.put("__utmz", "1.1451913029.3.3.utmcsr=k2k.sagawa-exp.co.jp|utmccn=(referral)|utmcmd=referral|utmcct=/p/sagawa/web/okurijoinput.jsp");
     }
    
    
    /**
     * 佐川宅急便にHttp通信するConnectionを作成します。
     * @param url
     * @param cookies
     * @param hostName
     * @return
     */
    public Connection createConnection(String inquiryNO){
        //クッキーを作成します。
        createCookie();

        return
                Jsoup
                .connect(SAGAWA_URL)
                .data("okurijoNo", inquiryNO)
                .header("Accept", "*/*")
                .header("Host", SAGAWA_HOST_NAME)
                .header("Accept-Language", "ja,en-US;q=0.8,en;q=0.6")
                .header("Connection", "keep-alive")
                .header("User-Agent", Const.UA.getRandom().getName())
                .cookies(cookies)
                .method(Method.GET)
                .timeout(10000);
    }
    
    /**
     * 必要なデータをパースしてマップに詰めます。
     */
    public ArrayMap<String, String> parseDocument(Document doc){
        ArrayMap<String, String> goodsStatusMap = new ArrayMap<String, String>();
        
        String latestStatus = StringUtil.trim(doc.getElementsByClass("state2").text());
        if (latestStatus.equals("該当なし")) {
            log.info("佐川に該当商品はありませんでした。");
            return null;
        }
        
        Element goodsInfoElems = doc.getElementById("MainList");
        String arriveDate = goodsInfoElems.select("tr:nth-child(4) td:nth-child(1)").text();
        String ReDeliveryDate = goodsInfoElems.select("tr:nth-child(4) td:nth-child(2)").text();
        goodsStatusMap.put("arriveDate", arriveDate);
        goodsStatusMap.put("ReDeliveryDate", ReDeliveryDate);
        
        Elements goodsDetailElems = doc.select(".table_okurijo_detail");
        for (Element elem : goodsDetailElems) {
            String inquiryNo = elem.select("tr:nth-child(1) td").text();
            String shippingDate = elem.select("tr:nth-child(2) td").text();
            String pickupOffice = elem.select("tr:nth-child(3) td").text();
            String shippingOffice = elem.select("tr:nth-child(4) td").text();
            String amount = elem.select("tr:nth-child(5) td").text();
            
            goodsStatusMap.put("inquiryNo", inquiryNo);
            goodsStatusMap.put("shippingDate", shippingDate);
            goodsStatusMap.put("pickupOffice", pickupOffice);
            goodsStatusMap.put("shippingOffice", shippingOffice);
            goodsStatusMap.put("amount", amount);
            break;
        }
        
        Elements goodsStatusElems = doc.select(".table_okurijo_detail2");
        for (Element elem : goodsStatusElems) {
            String status = elem.select("tbody tr:last-child td:nth-child(1)").text().replace("⇒", "");
//            String date = elem.select("tbody tr:last-child td:nth-child(2)").text();
            String office = elem.select("tbody tr:last-child td:nth-child(3)").text();
            
            goodsStatusMap.put("status", status);
//            goodsStatusMap.put("date", date);
            goodsStatusMap.put("office", office);
            goodsStatusMap.put("company", "佐川急便");
            break;
        }
        
        for(Map.Entry<String, String> e : goodsStatusMap.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
        
        return goodsStatusMap;
    }
    
    
}