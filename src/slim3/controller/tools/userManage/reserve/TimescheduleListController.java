package slim3.controller.tools.userManage.reserve;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class TimescheduleListController extends Controller {

    @Override
    public Navigation run() throws Exception {
        return forward("index.jsp");
    }
}