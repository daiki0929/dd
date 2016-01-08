package slim3.controller.tools.shippingSearch;

import org.slim3.controller.Navigation;
import org.slim3.util.ArrayMap;

import main.java.org.jsoup.Connection;
import main.java.org.jsoup.nodes.Document;
import slim3.Const;
import slim3.exception.MyException;
/**
 * 西濃をスクレイピングします。
 * @author uedadaiki
 *
 */
public class SearchSeinoController extends AbstractShippingSearchController {

    @Override
    public Navigation run() throws Exception {
        
        String inquiryNo = asString("inquiryNo");
        log.info("問い合わせNo：" + inquiryNo);
        //10桁じゃない時に構造が変わるので、10桁以外はすぐにnullを返します。
        int length = inquiryNo.length();
        log.info(Integer.toString(length));
        if (length != 10) {
            return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, null));
        }
        
        //西濃に接続します。
        Connection connectSeino = seinoService.createConnection(inquiryNo);
        try {
            Document doc = connectSeino.post();
            ArrayMap<String, String> goodsInfoMap = seinoService.parseDocument(doc);
            return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, goodsInfoMap));
            
        } catch (Exception e) {
            throw new MyException(e);
        }
    }
}
