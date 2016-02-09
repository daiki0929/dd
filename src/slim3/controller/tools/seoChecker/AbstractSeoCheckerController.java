package slim3.controller.tools.seoChecker;

import slim3.controller.AbstractController;
import slim3.service.tools.seoChecker.MozService;
import slim3.service.tools.seoChecker.SeoChekiService;


public abstract class AbstractSeoCheckerController extends AbstractController {
    
    //サービスクラス
    protected SeoChekiService seoChekiService = new SeoChekiService();
    protected MozService mozService = new MozService();
    
}




