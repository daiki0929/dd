package slim3.controller.tools.userManage.reserve;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

/**
 * 予約完了のコントローラ
 * @author uedadaiki
 *
 */
public class ReserveDoneController extends Controller {

    @Override
    public Navigation run() throws Exception {
        return forward("index.jsp");
    }
}
