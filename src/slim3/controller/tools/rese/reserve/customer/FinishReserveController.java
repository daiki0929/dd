package slim3.controller.tools.rese.reserve.customer;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;
import slim3.controller.tools.rese.AbstractReseController;
/**
 * 予約を完了し、ユーザー・カスタマーにメールを送信します。
 * @author uedadaiki
 *
 */
public class FinishReserveController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        //TODO メール送信のサービスクラスを作成する。
//        googleService.sendMessage(msUser, to, bcc, subject, content);
        return forward("finishReserve.jsp");
    }
}
