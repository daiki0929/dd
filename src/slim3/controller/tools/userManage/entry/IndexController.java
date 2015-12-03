package slim3.controller.tools.userManage.entry;

import java.util.Locale;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import util.DateConversionUtil;

public class IndexController extends Controller {

    @Override
    public Navigation run() throws Exception {
        
        return forward("/dd01/war/tools/userManage/index.jsp");
    }
}
