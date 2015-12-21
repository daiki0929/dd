package slim3.controller.api.twitter;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;
import slim3.service.sns.TwitterService;
/**
 * Twitterアカウントでログインします。
 * @author uedadaiki
 *
 */
public class SignInController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        TwitterService twitterService = new TwitterService();
        twitterService.signIn(request, response);
        
        return null;
    }
}
