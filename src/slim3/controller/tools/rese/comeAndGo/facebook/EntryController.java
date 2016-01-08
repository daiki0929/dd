package slim3.controller.tools.rese.comeAndGo.facebook;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;
/**
 * Facebookアカウントで会員登録します。
 * 名前とメールアドレスを記入するフォームを表示します。
 * @author uedadaiki
 *
 */
public class EntryController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        return forward("/tools/rese/comeAndGo/facebook/entry.jsp");
    }
}
