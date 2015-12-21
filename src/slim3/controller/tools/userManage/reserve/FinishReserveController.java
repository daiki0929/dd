package slim3.controller.tools.userManage.reserve;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;
/**
 * 予約を完了し、ユーザー・カスタマーにメールを送信します。
 * @author uedadaiki
 *
 */
public class FinishReserveController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        //TODO メール送信のサービスクラスを作成する。
        return forward("finishReserve.jsp");
    }
}
