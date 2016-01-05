package slim3.service.tools.ShippingSearch;

import java.util.HashMap;
import java.util.Map;

import main.java.org.jsoup.Connection;
import main.java.org.jsoup.Jsoup;
import main.java.org.jsoup.Connection.Method;
import slim3.Const;

/**
 * 佐川宅急便の配送状況を調べるサービスです。
 * @author uedadaiki
 *
 */
public class SagawaService implements SearchService {
    
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
    
    
}