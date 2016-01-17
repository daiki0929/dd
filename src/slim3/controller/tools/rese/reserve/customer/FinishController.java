package slim3.controller.tools.rese.reserve.customer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.google.appengine.api.datastore.Key;

import slim3.Const.RegexType;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.model.reserve.Menu;
import util.StringUtil;

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
