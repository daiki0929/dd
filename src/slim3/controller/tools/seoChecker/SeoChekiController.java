package slim3.controller.tools.seoChecker;

import java.util.ArrayList;

import org.slim3.controller.Navigation;
import org.slim3.util.ArrayMap;

import main.java.org.jsoup.Connection;
import slim3.Const;

/**
 * SEOちぇきを実行します。jsでサイトURLを取得して実行します。
 * @author uedadaiki
 *
 */
public class SeoChekiController extends AbstractSeoCheckerController {
    
    @Override
    public Navigation run() throws Exception {
        
        Connection seoChekiConnection = seoChekiService.createConnection(asString("domain"));
        ArrayList<String> seoDataList = seoChekiService.parseDocument(seoChekiConnection.get());
        
        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, null, seoDataList));
        
    }
}
