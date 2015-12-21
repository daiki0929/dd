package slim3.controller.api;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;
/**
 * 
 * @author uedadaiki
 *
 */
public class TestController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
         
        
        return forward("/sns/index.jsp");
    }
}
