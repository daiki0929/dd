package slim3.service.tools.ShippingSearch;


import main.java.org.jsoup.Connection;

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
}