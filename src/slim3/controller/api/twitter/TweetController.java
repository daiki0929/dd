package slim3.controller.api.twitter;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;
import slim3.service.sns.TwitterService;
/**
 * ツイートします。
 * @author uedadaiki
 *
 */
public class TweetController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        TwitterService twitterService = new TwitterService();
        twitterService.tweet(request, response);
        
        return null;
    }
}
