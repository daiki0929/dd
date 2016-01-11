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
 * ヤマト宅急便の配送状況を調べるサービスです。
 * @author uedadaiki
 *
 */
public class YamatoService{
    //TODO メソッドの戻り値が異なるので、インターフェースの継承を切ってます。parseDocument
    //継承を使うタイミングがいまいち理解出来ていない。
    //今は、使うメソッドを明記するためだけにインターフェースクラスを作成している。
    //インターフェースクラスにある値などの利用も無し。
    
    public final static Logger log = Logger.getLogger(YamatoService.class.getName());
    
    public static final String YAMATO_URL = "http://toi.kuronekoyamato.co.jp/cgi-bin/tneko";
    public static final String YAMATO_HOST_NAME = "toi.kuronekoyamato.co.jp";
    Map<String, String> cookies = new HashMap<String, String>();
    
    /**
     * クッキーを作成します。
     */
    public void createCookie(){
            cookies = new HashMap<String, String>();
            cookies.put("s2_ntrl", "1");
            cookies.put("s_camntrl", "1");
            cookies.put("s2_camntrl", "1");
            cookies.put("s_cc", "true");
            cookies.put("s_cpm1", "1");
            cookies.put("s_cpm2", "1");
            cookies.put("s_nr", "1452000790797");
            cookies.put("s_path1", "1");
            cookies.put("s_path2", "1");
            cookies.put("s_path3", "1");
            cookies.put("s_path4", "1");
            cookies.put("s_ppv", "27");
            cookies.put("s_pv", "no%20value");
            cookies.put("s_sq", "%5B%5BB%5D%5D");
            cookies.put("s_sc_url", "http%3A//www.kuronekoyamato.co.jp/top.html");
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
                .connect(YAMATO_URL)
                .data("number01", inquiryNO)
                .header("Accept", "*/*")
                .header("Host", YAMATO_HOST_NAME)
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
    public Connection createConnection(String[] inquiryNoList){
        //クッキーを作成します。
        createCookie();

        Connection connectYamato = Jsoup
        .connect(YAMATO_URL)
        .header("Accept", "*/*")
        .header("Host", YAMATO_HOST_NAME)
        .header("Accept-Language", "ja,en-US;q=0.8,en;q=0.6")
        .header("Connection", "keep-alive")
        .header("User-Agent", Const.UA.getRandom().getName())
        .cookies(cookies)
        .method(Method.GET)
        .timeout(10000);
        
        String[] numberList = {"number01", "number02", "number03", "number04", "number05", "number06", "number07", "number08", "number09", "number10"};
        for (int i = 0; i < inquiryNoList.length; i++) {
            log.info(numberList[i] + inquiryNoList[i]);
            connectYamato.data(numberList[i], inquiryNoList[i]);
        }
                
        return connectYamato;
    }
    
    
    /**
     * 必要なデータをパースしてマップに詰めます。
     */
    public ArrayList<ArrayMap<String, String>> parseDocument(Document doc){
        ArrayList<ArrayMap<String, String>> goodsInfoList = new ArrayList<ArrayMap<String, String>>();
        
        Elements goodsDetailRow = doc.select(".ichiran tr");
        
        for (Element row : goodsDetailRow) {
            ArrayMap<String, String> goodsStatusMap = new ArrayMap<String, String>();
            String shippingDate = row.select(".hiduke font").text();
            
            if (shippingDate.isEmpty()) {
                log.info("ヤマトに該当商品はありませんでした。");
                continue;
            }

            String status = StringUtil.trim(row.select(".ct font").text());

            String inquiryNo = row.select(".input input").val();
            goodsStatusMap.put("status", status);
            goodsStatusMap.put("inquiryNo", inquiryNo);
            goodsStatusMap.put("shippingDate", shippingDate);
            goodsStatusMap.put("arriveDate", "☓");
            goodsStatusMap.put("company", "クロネコヤマト");
            goodsInfoList.add(goodsStatusMap);
            
            for(Map.Entry<String, String> e : goodsStatusMap.entrySet()) {
                System.out.println(e.getKey() + " : " + e.getValue());
            }
        }

        return goodsInfoList;
    }
    
    
}