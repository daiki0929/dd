package slim3.controller.tools.seoChecker;

import java.util.ArrayList;

import org.slim3.controller.Navigation;
import org.slim3.util.ArrayMap;

import main.java.org.jsoup.Connection;
import slim3.Const;

/**
 * MOZを実行します。
 * @author uedadaiki
 *
 */
public class MozController extends AbstractSeoCheckerController {
    
    @Override
    public Navigation run() throws Exception {
        
        Connection mozConnection = mozService.createConnection("http://pocketwifi-hikaku.jpn.org/");
        log.info(mozConnection.get().toString());
        ArrayList<String> mozDataList = seoChekiService.parseDocument(mozConnection.get());
        
        return null;
//        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, mozDataList));
        
    }
}
