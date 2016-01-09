package slim3.controller.tools.shippingSearch;

import java.util.ArrayList;

import org.slim3.controller.Navigation;
import org.slim3.util.ArrayMap;

import main.java.org.jsoup.Connection;
import main.java.org.jsoup.nodes.Document;
import slim3.Const;
import slim3.exception.MyException;
/**
 * 日本郵政をスクレイピングします。
 * @author uedadaiki
 *
 */
public class SearchYuseiController extends AbstractShippingSearchController {

    @Override
    public Navigation run() throws Exception {
        
        
        String inquiryNo = asString("inquiryNo");
        String[] inquiryNoList = inquiryNo.split("\n");
        int length = inquiryNoList.length;
        if (length > 10) {
            return returnResponse(createJsonDto(Const.JSON_STATUS_ERROR, "追跡番号は10個までです。", null)); 
        }
        
        log.info("問い合わせNo：" + inquiryNo);
        
        
        //に接続します。
        Connection connectYusei = yuseiService.createConnection(inquiryNoList);
        try {
            Document doc = connectYusei.post();
//            log.info(doc.toString());
            ArrayList<ArrayMap<String, String>> parseDocument = yuseiService.parseDocument(doc);
            return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, parseDocument));
        } catch (Exception e) {
            throw new MyException(e);
        }
    }
}
