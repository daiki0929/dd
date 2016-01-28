package slim3.controller.tools.rese;

import org.slim3.controller.Navigation;
public class IndexController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {    
        return redirect("/tools/rese/lp/index.html");
    }
}
