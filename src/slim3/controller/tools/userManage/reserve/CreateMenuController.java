package slim3.controller.tools.userManage.reserve;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;

public class CreateMenuController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        String msUserRefStr = asString("id");
        request.setAttribute("id", msUserRefStr);
        
        return forward("createMenu.jsp");
    }
}
