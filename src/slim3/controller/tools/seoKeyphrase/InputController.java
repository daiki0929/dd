package slim3.controller.tools.seoKeyphrase;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;

/**
 * タイトル入力画面を表示します。
 * @author uedadaiki
 *
 */
public class InputController extends AbstractController {
    

    @Override
    public Navigation run() throws Exception {
        
        return forward("/tools/seoKeyphrase/input.jsp");
    }
}
