package slim3.controller.tools.shippingSearch;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;
/**
 * 問い合わせ(追跡番号)の入力を行います。
 * @author uedadaiki
 *
 */
public class InputController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        return forward("input.jsp");
    }
}
