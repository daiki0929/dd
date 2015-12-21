package slim3.controller.api.twitter;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;
import slim3.service.sns.TwitterService;
/**
 * Twitterアカウントでログアウトします。
 * @author uedadaiki
 *
 */
public class CallBackController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        TwitterService twitterService = new TwitterService();
        twitterService.logOut(request, response);
        
        return null;
    }
}
