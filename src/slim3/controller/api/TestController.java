package slim3.controller.api;

import javax.servlet.http.HttpSession;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;
import twitter4j.auth.RequestToken;
/**
 * 
 * @author uedadaiki
 *
 */
public class TestController extends AbstractController {

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
