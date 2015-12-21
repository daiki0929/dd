package slim3.controller.api.twitter;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;
/**
 * 
 * @author uedadaiki
 *
 */
public class IndexController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
         
        
        return forward("/sns/index.jsp");
    }
}
