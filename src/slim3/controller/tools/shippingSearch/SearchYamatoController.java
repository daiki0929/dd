package slim3.controller.tools.shippingSearch;

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
        log.info("問い合わせNo：" + inquiryNo);
        
        //ヤマトに接続します。
        Connection connectYamato = yamatoService.createConnection(inquiryNo);
        try {
            Document doc = connectYamato.post();
            ArrayMap<String, String> goodsInfoMap = yamatoService.parseDocument(doc);
            return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, goodsInfoMap));
        } catch (Exception e) {
            throw new MyException(e);
        }
    }
}
