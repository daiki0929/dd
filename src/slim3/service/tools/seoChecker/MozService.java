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
 * MOZのサービスクラスです。
 * @author uedadaiki
 *
 */
public class MozService {
    
    public final static Logger log = Logger.getLogger(MozService.class.getName());
    
    public static final String URL = "https://moz.com/researchtools/ose/links";
    public static final String HOST_NAME = "582120801.log.optimizely.com";
    
    
    Map<String, String> cookies = new HashMap<String, String>();
    /**
     * MOZのクッキーを作成します。
     */
    public void createCookie(){
            cookies = new HashMap<String, String>();
            cookies.put("ose_session", "slMDvRywYgyfyGUIKitUKg");
            cookies.put("ose_notification", "true");
            cookies.put("visid_incap_133232", "iSBQ5sYPT2m4wwEGcQjp1tlNtFYAAAAAQUIPAAAAAAB1CbktGNAeWLvX4CjpAjek");
            cookies.put("incap_ses_200_133232", "fiWyedMKpRg6NjsiD4zGAtpNtFYAAAAARaMT6xZM1N/27TTJahMp1A==");
            cookies.put("incap_ses_199_133232", "tPnPUX/WMjQ/FkhF4x7DAruEs1YAAAAAjgqM07Su+n++mlDB+ARbWw");
            cookies.put("optimizelyEndUserId", "oeu1454656986775r0.28730206144973636");
            cookies.put("optimizelySegments", "%7B%22571842640%22%3A%22gc%22%2C%22572040976%22%3A%22false%22%2C%22572831570%22%3A%22direct%22%7D");
            cookies.put("optimizelyBuckets", "%7B%7D");
            cookies.put("__utmt", "1");
            cookies.put("__utma", "181959355.1407154179.1454656987.1454656987.1454656987.1");
            cookies.put("__utmb", "181959355.1.10.1454656987");
            cookies.put("__utmc", "181959355");
            cookies.put("__utmz", "181959355.1454656987.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)");
            cookies.put("s_fid", "3EBB94C6F5466B40-065CEB0B393C3FE0");
            cookies.put("s_cc", "true");
            cookies.put("__qca", "P0-381616515-1454605501948");
            cookies.put("s_vi", "[CS]v1|2B5A26ED8548AB3E-40000104E0023ABE[CE]");
            cookies.put("__ar_v4", "");
            cookies.put("visid_incap_149736", "XJbD6ZzCTo2lNn/vFnxnLNpNtFYAAAAAQUIPAAAAAAA998yI00w/Hj3lqNppyOT/");
            cookies.put("incap_ses_407_149736", "sgsBCafuEFqQjS7hgfSlBdpNtFYAAAAA7oCb5ByH6tmxG/tuD1xWxw==");
            cookies.put("__ssid", "cf52ed5f-2781-49de-89af-e99149d4a743");
            cookies.put("optimizelyPendingLogEvents", "%5B%5D");
            
            
     }
    
    
    /**
     * MOZにHttp通信するConnectionを作成します。
     * @param domain
     * @return
     */
    public Connection createConnection(String domain){
//        //クッキーを作成します。
        createCookie();

        return
                Jsoup
                .connect(URL)
                .data("site", domain)
                .data("source", "external")
                .data("target", "page")
                .data("gorup", "0")
                .data("page", "1")
                .data("sort", "page_authority")
                .data("sort", "page_authority")
                .data("anchor_id", "")
                .data("anchor_type", "")
                .data("anchor_text", "")
                .data("from_site", "")
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
        ArrayList<String> seoDataList = new ArrayList<String>();
        
        //バックリンク情報
        Elements siteInfo = doc.select(".items tr");
        for (Element data : siteInfo) {
            String siteTitle = data.select(".title").select("strong span").last().text();
            String url = data.select(".title").select("strong span a").last().outerHtml();
            
//            data.select(".anchor").select();
//            data.select(".spam-score");
//            data.select(".pa");
//            data.select(".da");
//            String dataValue = data.select("td a").text();
            seoDataList.add(siteTitle);
            seoDataList.add(url);
        }
        
        
        return seoDataList;
    }
    
    
}