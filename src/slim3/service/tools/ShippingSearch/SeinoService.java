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
 * 西濃宅急便の配送状況を調べるサービスです。
 * @author uedadaiki
 *
 */
public class SeinoService implements SearchService {
    
    public final static Logger log = Logger.getLogger(SeinoService.class.getName());
    
    public static final String SEINO_URL = "http://track.seino.co.jp/kamotsu/GempyoNoShokai.do";
    public static final String SEINO_HOST_NAME = "track.seino.co.jp";
    Map<String, String> cookies = new HashMap<String, String>();
    
    /**
     * クッキーを作成します。
     */
    public void createCookie(){
            cookies = new HashMap<String, String>();
            //トップから検索した時のクッキー？
            cookies.put("JSESSIONID", "0000gIQzzGcDtjNAr9GgLyRYb2I");
            cookies.put("__utma", "150556340.575131525.1451995371.1451995371.1451995371.1");
            cookies.put("__utmb", "150556340.23.10.1451995371");
            cookies.put("__utmc", "150556340");
            cookies.put("__utmt", "1");
            cookies.put("__utmz", "150556340.1451995371.1.1");
            cookies.put("_ga", "GA1.3.575131525.1451995371");
            cookies.put("_gat", "1");
            cookies.put("seinoOPEN", "325626048.20480.0000");
            cookies.put("smartphoneFlagCommon", "0");
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
                .connect(SEINO_URL)
                .data("GNPNO1", inquiryNO)
                .header("Accept", "*/*")
                .header("Host", SEINO_HOST_NAME)
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
        Elements goodsTable = doc.getElementsByTag(".base tbody");
        for (Element elem : goodsTable) {
            String status = StringUtil.trim(elem.select("tr:nth-child(2) .col4").text());
            log.info(status);
            if (status.equals("入力されたお問合せ番号が見当りません")) {
                log.info("西濃に該当商品はありませんでした。");
                return null;
            }

            //TODO 「詳細表示」のセレクタは未設定
            String inquiryNo = elem.select("tr:nth-child(2) .col3").text();
            String shippingDate = elem.select("tr:nth-child(2) .col2").text();

            goodsStatusMap.put("inquiryNo", inquiryNo);
            goodsStatusMap.put("shippingDate", shippingDate);
            goodsStatusMap.put("company", "西濃運輸");
            break;
        }

        for(Map.Entry<String, String> e : goodsStatusMap.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }


        return goodsStatusMap;
    }


}