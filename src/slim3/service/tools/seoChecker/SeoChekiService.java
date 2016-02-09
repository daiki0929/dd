package slim3.service.tools.seoChecker;

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
 * SEOちぇきのサービスクラスです。
 * @author uedadaiki
 *
 */
public class SeoChekiService {
    
    public final static Logger log = Logger.getLogger(SeoChekiService.class.getName());
    
    public static final String URL = "http://seocheki.net/site-check.php";
    public static final String HOST_NAME = "seocheki.net";
    
    
    Map<String, String> cookies = new HashMap<String, String>();
    //TODO IPアドレスは使用者のが入る？
    /**
     * SEOちぇきのクッキーを作成します。
     */
    public void createCookie(){
            cookies = new HashMap<String, String>();
            cookies.put("PHPSESSID", "b8bmi0g87h0a3nfenuid2mili6");
            cookies.put("__utmt", "1");
            cookies.put("__utma", "192971567.710492134.1454603647.1454603647.1454603647.1");
            cookies.put("__utmb", "192971567.1.10.1454603647");
            cookies.put("__utmc", "192971567");
            cookies.put("__utmz", "192971567.1454603647.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)");
     }
    
    
    /**
     * SEOちぇきにHttp通信するConnectionを作成します。
     * @param domain
     * @return
     */
    public Connection createConnection(String domain){
//        //クッキーを作成します。
        createCookie();

        return
                Jsoup
                .connect(URL)
                .data("u", domain)
                .header("Accept", "*/*")
                .header("Host", HOST_NAME)
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
    public ArrayList<String> parseDocument(Document doc){
//        ArrayMap<String, String> seoDataMap = new ArrayMap<String, String>();
        ArrayList<String> seoDataList = new ArrayList<String>();
        
        //「ページ情報」
        Elements siteInfo = doc.select(".pelem");
        for (Element data : siteInfo) {
//            String dataTitle = data.text();
            String dataValue = data.select("td a").text();
            seoDataList.add(dataValue);
//            seoDataMap.put(dataTitle, dataValue);
        }
        
        //Google PageRank
        String pageRank = doc.select("#td-pr a").text();
        seoDataList.add(pageRank);
//        seoDataMap.put("Google PageRank", pageRank);
        //インデックス数
        String index = doc.select("#td-gi a").text();
        seoDataList.add(index);
//        seoDataMap.put("インデックス数", index);
        //被リンク数
        String backLink = doc.select("#td-gl a").text();
        seoDataList.add(backLink);
//        seoDataMap.put("被リンク数", backLink);
        
        return seoDataList;
    }
    
    
}