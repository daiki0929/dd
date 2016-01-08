package slim3.service.tools.ShippingSearch;



import org.slim3.util.ArrayMap;

import main.java.org.jsoup.Connection;
import main.java.org.jsoup.nodes.Document;

public interface SearchService {
    
    /**
     * 接続するためのクッキーを作成します。
     */
    void createCookie();
    
    /**
     * Http通信するConnectionを作成します。
     * @param url
     * @param cookies
     * @param hostName
     * @return
     */
    Connection createConnection(String inquiryNO);
    
    /**
     * 必要なデータをパースします。
     * @param document
     * @return
     */
    ArrayMap<String, String> parseDocument(Document document);
}