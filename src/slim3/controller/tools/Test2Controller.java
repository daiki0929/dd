package slim3.controller.tools;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;

/**
 * @author uedadaiki
 *
 */
public class Test2Controller extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        
        return forward("/tools/rese/test2.jsp");
    }
}
