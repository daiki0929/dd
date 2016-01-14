package slim3.controller.tools.rese.comeAndGo;

import org.slim3.controller.Navigation;

import slim3.controller.tools.rese.AbstractReseController;
/**
 * SNSによる会員登録画面を表示します。
 * @author uedadaiki
 *
 */
public class EntryBySNSController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        return forward("/tools/rese/comeAndGo/entryBySNS.jsp");
    }
}
