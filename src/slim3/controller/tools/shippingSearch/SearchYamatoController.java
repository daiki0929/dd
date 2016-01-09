package slim3.controller.tools.shippingSearch;

import java.util.ArrayList;

import org.slim3.controller.Navigation;
import org.slim3.util.ArrayMap;

import main.java.org.jsoup.Connection;
import main.java.org.jsoup.nodes.Document;
import slim3.Const;
import slim3.exception.MyException;
/**
 * ヤマトをスクレイピングします。
 * @author uedadaiki
 *
 */
public class SearchYamatoController extends AbstractShippingSearchController {

    @Override
    public Navigation run() throws Exception {
        
        
        String inquiryNo = asString("inquiryNo");
        String[] inquiryNoList = inquiryNo.split("\n");
        int length = inquiryNoList.length;
        if (length > 10) {
            return returnResponse(createJsonDto(Const.JSON_STATUS_ERROR, "追跡番号は10個までです。", null)); 
        }
        
        log.info("問い合わせNo：" + inquiryNo);
        
        
        //ヤマトに接続します。
        Connection connectYamato = yamatoService.createConnection(inquiryNoList);
        try {
            Document doc = connectYamato.post();
            ArrayList<ArrayMap<String, String>> parseDocument = yamatoService.parseDocument(doc);
            return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, parseDocument));
        } catch (Exception e) {
            throw new MyException(e);
        }
    }
}
