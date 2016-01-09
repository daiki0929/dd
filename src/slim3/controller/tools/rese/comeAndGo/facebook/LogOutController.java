package slim3.controller.tools.rese.comeAndGo.facebook;

import org.slim3.controller.Navigation;

import slim3.controller.tools.rese.AbstractReseController;
import slim3.service.sns.TwitterService;
/**
 * Facebookアカウントでログアウトします。
 * @author uedadaiki
 *
 */
public class LogOutController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        TwitterService twitterService = new TwitterService();
        twitterService.logOut(request, response);
        
        return forward("/api/Twitter/index");
    }
}
