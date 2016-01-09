package slim3.service.tools.ShippingSearch;

import java.util.ArrayList;
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
 * 日本郵政の配送状況を調べるサービスです。
 * @author uedadaiki
 *
 */
public class YuseiService {
//TODO メソッドの戻り値が異なるので、インターフェースの継承を切ってます。parseDocument
    
    public final static Logger log = Logger.getLogger(YuseiService.class.getName());
    
    public static final String YUSEI_URL = "https://trackings.post.japanpost.jp/services/srv/search/";
    public static final String YUSEI_HOST_NAME = "trackings.post.japanpost.jp";
    Map<String, String> cookies = new HashMap<String, String>();
    
    /**
     * クッキーを作成します。
     */
    public void createCookie(){
            cookies = new HashMap<String, String>();
            cookies.put("JSESSIONID", "vZwLWQMMSgJKdYBzjlYdFvfYyGyftrvvQDxg7GkYyrNJ2x6G5v1L!-861887782");
            cookies.put("TS014d1b44", "016de3e52e25fd610ce1c4f2ed73e073effb2882a823ca7f33573910119aa8119934ad0d1bf70e1586a6fc986abde81288784214f9");
            cookies.put("trackings.3", "126200000.23835.0000");
            cookies.put("trackings", "2206574784.37151.0000");
            cookies.put("TS0175c202", "016de3e52e1e936c2461faa39900a78fb2efc60f41f024fa9fb6d1044ca7f2624c085359daa968324b877b86b91f6fafe1e2e7105cf11834596fc468ef543c0c900bba764d");
            cookies.put("ALACookieChecker", "true");
            cookies.put("ala_uid", "168756138145231380687213462338");
            cookies.put("ala_sid", "1452313806");
            cookies.put("ala_la", "2016010913");
     }
    
    
    /**
     * Http通信するConnectionを作成します。
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
                .connect(YUSEI_URL)
                .header("Accept", "*/*")
                .header("Host", YUSEI_HOST_NAME)
                .header("Accept-Language", "ja,en-US;q=0.8,en;q=0.6")
                .header("Connection", "keep-alive")
                .header("User-Agent", Const.UA.getRandom().getName())
                .cookies(cookies)
                .method(Method.GET)
                .timeout(10000);
    }
    
    /**
     * Http通信するConnectionを作成します。
     * @param inquiryNoList
     * @return
     */
    //TODO 質問：メソッド名同じで引数によって変えてる。インターフェースを作成したが、あまり上手く使えていない。
    public Connection createConnection(String[] inquiryNoList){
        //クッキーを作成します。
        createCookie();

        Connection connectYusei = Jsoup
        .connect(YUSEI_URL)
        .data("search", "追跡スタート")
        .data("locale", "ja")
        .header("Accept", "*/*")
        .header("Host", YUSEI_HOST_NAME)
        .header("Accept-Language", "ja,en-US;q=0.8,en;q=0.6")
        .header("Connection", "keep-alive")
        .header("User-Agent", Const.UA.getRandom().getName())
        .cookies(cookies)
        .method(Method.GET)
        .timeout(10000);
        
        String[] numberList = {"requestNo1", "requestNo2", "requestNo3", "requestNo4", "requestNo5", "requestNo6", "requestNo7", "requestNo8", "requestNo9", "requestNo10"};
        for (int i = 0; i < inquiryNoList.length; i++) {
            log.info(numberList[i] + inquiryNoList[i]);
            connectYusei.data(numberList[i], inquiryNoList[i]);
        }
                
        return connectYusei;
    }
    
    
    /**
     * 必要なデータをパースしてマップに詰めます。
     */
    public ArrayList<ArrayMap<String, String>> parseDocument(Document doc){
        ArrayList<ArrayMap<String, String>> goodsInfoList = new ArrayList<ArrayMap<String, String>>();
        
        String pageTitle = StringUtil.trim(doc.select("h1").get(0).text());
        log.info(pageTitle);
        //複数検索
        Elements goodsDetailRow = doc.select(".tableType01 tr:gt(3)");
        if (pageTitle.equals("個別番号検索結果")) {
            for (Element row : goodsDetailRow) {
                ArrayMap<String, String> goodsStatusMap = new ArrayMap<String, String>();
                String inquiryNo = row.select("td:nth-child(1) a").text();
                String status = row.select("td:nth-child(4)").text();
                if (status.isEmpty()) {
                    log.info("日本郵政に該当商品はありませんでした。");
                    continue;
                }
                log.info(inquiryNo);
                log.info(status);
                goodsStatusMap.put("inquiryNo", inquiryNo);
                goodsStatusMap.put("status", status);
                goodsStatusMap.put("company", "日本郵政");
                goodsStatusMap.put("shippingDate", "☓");
                goodsStatusMap.put("arriceDate", "☓");
                goodsInfoList.add(goodsStatusMap);
                for(Map.Entry<String, String> e : goodsStatusMap.entrySet()) {
                    System.out.println(e.getKey() + " : " + e.getValue());
                }
            }
        }else {
            //単体検索
            for (Element row : goodsDetailRow) {
                ArrayMap<String, String> goodsStatusMap = new ArrayMap<String, String>();
                String inquiryNo = row.select("td:nth-child(1)").get(0).text();
                String status = row.select(".w_150:last-child").get(0).text();
                if (status.isEmpty()) {
                    log.info("日本郵政に該当商品はありませんでした。");
                    continue;
                }
                goodsStatusMap.put("inquiryNo", inquiryNo);
                goodsStatusMap.put("status", status);
                goodsInfoList.add(goodsStatusMap);
                for(Map.Entry<String, String> e : goodsStatusMap.entrySet()) {
                    System.out.println(e.getKey() + " : " + e.getValue());
                }
            }
        }
        

        return goodsInfoList;
    }
    
    
}