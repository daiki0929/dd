package slim3.controller.tools.userManage.reserve;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;

public class CreateMenuPageController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        
        
        return forward("createMenuPage.jsp");
    }
}
