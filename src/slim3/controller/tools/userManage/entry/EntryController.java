package slim3.controller.tools.userManage.entry;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class EntryController extends Controller {

    @Override
    public Navigation run() throws Exception {
        
        return forward("/tools/userManage/entry.jsp");
    }
}
