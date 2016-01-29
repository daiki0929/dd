package slim3.controller.tools.rese;

import org.slim3.controller.Navigation;
/**
 * エラーページを表示します。
 * @author uedadaiki
 *
 */
public class ErrorPageController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {    
        return forward("/tools/rese/common/errorPage.jsp");
    }
}
