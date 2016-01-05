package slim3.controller.api.twitter;

import javax.servlet.http.HttpSession;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;
import twitter4j.auth.RequestToken;
/**
 * TwitterAPIのコールバックコントローラ
 * @author uedadaiki
 *
 */
public class CallBackController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        log.info("コールバックを呼ぶコントローラ");
        HttpSession session = request.getSession();
        RequestToken reqToken = (RequestToken) session.getAttribute("RequestToken");
        log.info("reqToken：" + reqToken.toString());
        
        twitterService.callback(request, response, reqToken);
        return forward("/sns/index.jsp");
    }
}
