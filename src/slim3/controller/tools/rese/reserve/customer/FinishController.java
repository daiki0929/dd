package slim3.controller.tools.rese.reserve.customer;

import org.slim3.controller.Navigation;

import slim3.controller.tools.rese.AbstractReseController;

/**
 * 予約完了後のコントローラです。
 * @author uedadaiki
 *
 */
public class FinishController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        return forward("/tools/rese/reserve/customer/finishReserve.jsp");
    }
}
