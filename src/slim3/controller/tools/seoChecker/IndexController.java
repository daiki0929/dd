package slim3.controller.tools.seoChecker;

import org.slim3.controller.Navigation;

/**
 * SEOチェッカーの画面を表示します。
 * @author uedadaiki
 *
 */
public class IndexController extends AbstractSeoCheckerController {
    
    @Override
    public Navigation run() throws Exception {
        
        return forward("index.jsp");
        
    }
}
