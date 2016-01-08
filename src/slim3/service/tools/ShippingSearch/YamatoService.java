package slim3.service.tools.ShippingSearch;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.slim3.util.ArrayMap;

import main.java.org.jsoup.Connection;
import main.java.org.jsoup.Jsoup;
import main.java.org.jsoup.Connection.Method;
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
public class YamatoService implements SearchService {
    
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
     * 必要なデータをパースしてマップに詰めます。
     */
    public ArrayMap<String, String> parseDocument(Document doc){
        ArrayMap<String, String> goodsStatusMap = new ArrayMap<String, String>();
        Elements goodsTable = doc.getElementsByClass("ichiran");
        for (Element elem : goodsTable) {
            String status = StringUtil.trim(elem.select("tr:nth-child(3) .ct font").text());
            log.info(status);
            if (status.equals("伝票番号誤り")) {
                log.info("ヤマトに該当商品はありませんでした。");
                return null;
            }else if (status.equals("伝票番号未登録")) {
                log.info("ヤマトに該当商品はありませんでした。");
                return null;
            }

            String inquiryNo = elem.select("tr:nth-child(3) .denpyo b").text();
            String shippingDate = elem.select("tr:nth-child(3) .hiduke font").text();

            goodsStatusMap.put("status", status);
            goodsStatusMap.put("inquiryNo", inquiryNo);
            goodsStatusMap.put("shippingDate", shippingDate);
            goodsStatusMap.put("company", "クロネコヤマト");
            break;
        }

        for(Map.Entry<String, String> e : goodsStatusMap.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }


        return goodsStatusMap;
    }
    
    
}