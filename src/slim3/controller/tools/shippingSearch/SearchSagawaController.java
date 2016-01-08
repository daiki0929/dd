package slim3.controller.tools.shippingSearch;

import org.slim3.controller.Navigation;
import org.slim3.util.ArrayMap;

import main.java.org.jsoup.Connection;
import main.java.org.jsoup.nodes.Document;
import slim3.Const;
/**
 * 佐川をスクレイピングします。
 * @author uedadaiki
 *
 */
public class SearchSagawaController extends AbstractShippingSearchController {

    @Override
    public Navigation run() throws Exception {
        
        
        String inquiryNo = asString("inquiryNo");
        log.info("問い合わせNo：" + inquiryNo);
        
        //佐川に接続します。
        Connection connectSagawa = sagawaService.createConnection(inquiryNo);
        Document doc = connectSagawa.post();
        ArrayMap<String, String> goodsInfoMap = sagawaService.parseDocument(doc);
        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, goodsInfoMap));

    }
}
