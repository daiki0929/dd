package slim3.controller.tools.shippingSearch;

import java.util.ArrayList;

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
        String[] inquiryNoList = inquiryNo.split("\n");
        int length = inquiryNoList.length;
        if (length > 10) {
            return returnResponse(createJsonDto(Const.JSON_STATUS_ERROR, "追跡番号は10個までです。", null)); 
        }
        
        log.info("問い合わせNo：" + inquiryNoList);
        
        
        ArrayList<ArrayMap<String, String>> goodsInfoList = new ArrayList<ArrayMap<String, String>>();
        //佐川に接続します。
        //TODO 佐川のサイトでは複数の追跡番号を送るページが解析出来ないので、１個づつ送る通信を繰り返します。
        for (String oneInquiryNo : inquiryNoList) {
            Thread.sleep(1000);
            Connection connectSagawa = sagawaService.createConnection(oneInquiryNo);
            Document doc = connectSagawa.post();
            ArrayMap<String, String> goodsInfoMap = sagawaService.parseDocument(doc);
            goodsInfoList.add(goodsInfoMap);
        }
        
        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, goodsInfoList));

    }
}
