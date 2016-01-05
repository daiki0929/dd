package slim3.service.sns;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import slim3.controller.AbstractController;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


/**
 * TwitterAPIのサービスです。
 * @author uedadaiki
 *
 */
public class TwitterService {
    
    final String reseConsumerKey = "uVV6FY1v67LhMaTS4N3xRwbNT";
    final String reseConsumerSecret = "j5SUMbIt4VvnEMuJRd0HY5XJNIq7k88pVxG7Gz657gqE7TTJvK";
    
    public final static Logger log = Logger.getLogger(AbstractController.class.getName());
    
    /**
     * ツイートします。
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void tweet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String text = request.getParameter("text");
        Twitter twitter = (Twitter)request.getSession().getAttribute("twitter");
        try {
            String screenName = twitter.getScreenName();
            log.info("名前：" + screenName);
            twitter.updateStatus(text);
        } catch (TwitterException e) {
            throw new ServletException(e);
        }
        response.sendRedirect(request.getContextPath()+ "/");
    }


    
    /**
     * Twitterアカウントにサインインします。
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    Twitter twitter = new TwitterFactory().getInstance();
    public void signIn(HttpServletRequest request, HttpServletResponse response, String callbackURL) throws ServletException, IOException, InterruptedException {
        // Titterオブジェクトの生成
        twitter.setOAuthConsumer(reseConsumerKey, reseConsumerSecret);
        try{
            // リクエストトークンの生成
            RequestToken reqToken = twitter.getOAuthRequestToken(callbackURL);
 
            // RequestTokenとTwitterオブジェクトをセッションに保存
            HttpSession session = request.getSession();
            session.setAttribute("RequestToken", reqToken);
            session.setAttribute("Twitter", twitter);
            log.info("RequestToken：" + session.getAttribute("RequestToken").toString());
            log.info("Twitter：" + session.getAttribute("Twitter").toString());
 
            // 認証画面にリダイレクトするためのURLを生成
            String strUrl = reqToken.getAuthorizationURL();
            response.sendRedirect(strUrl);
        }catch (TwitterException e){
            log.info(e.getMessage());
//            if (e.getMessage().contains("Operation timed out")) {
//                log.info("タイムアウトしたので、リトライします。");
//                //TODO この処理は大丈夫？
//                signIn(request, response, callbackURL);
//            }
        }
    }
    
//    /**
//     * Twitterアカウントにアクセストークンを使ってサインインします。(既に会員登録済みの人)
//     * @param request
//     * @param response
//     * @throws ServletException
//     * @throws IOException
//     */
//    public void signInByAccessToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InterruptedException {
//        // Titterオブジェクトの生成
//        try{
//            Twitter twitter = TwitterFactory.getSingleton();
//            twitter.setOAuthConsumer(reseConsumerKey, reseConsumerSecret);
//             
//            AccessToken accessToken = new AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
//                   
//            twitter.setOAuthAccessToken(accessToken);
//            Status status = twitter.updateStatus(
//                "test from twitter4J " + System.currentTimeMillis());
//                           
//            System.out.println(
//                "status updated to [" + status.getText() + "].");
//        }catch (TwitterException e){
//            log.info(e.getMessage());
////            }
//        }
//    }
    
    /**
     * 認証完了後のコールバックです。
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws TwitterException 
     */
    public void callback(HttpServletRequest request, HttpServletResponse response, RequestToken reqToken) throws ServletException, IOException, TwitterException {
        HttpSession session = request.getSession();
        twitter.setOAuthConsumer(reseConsumerKey, reseConsumerSecret);
        String verifier = request.getParameter("oauth_verifier");
        AccessToken accessToken = null;
        
        log.info("reqToken：" + reqToken.toString());
 
        try {
            // RequestTokenからAccessTokenを取得
            accessToken = twitter.getOAuthAccessToken(reqToken, verifier);
        } catch (TwitterException e) {
            log.info("アクセストークンを取得出来ませんでした。");
            e.printStackTrace();
        }
 
        if(accessToken != null){
            // AccessTokenをセッションに保持
            session.setAttribute("AccessToken", accessToken.getToken());
            session.setAttribute("AccessTokenSecret", accessToken.getTokenSecret());
            log.info("アクセストークン：" + accessToken.getToken());
            log.info("シークレットトークン：" + accessToken.getTokenSecret());
            
            //アクセストークンとシークレットトークンをDBに保存
            //次回からは、セッションに残ってるアクセストークン・シークレットトークンをDBと照らし合わす
            //セッションが切れてたら、もう一度Twitterにログインしてもらい、DBと照らし合わす。
            
            log.info("ログイン完了。");
//            response.sendRedirect("/api/twitter/index");
        }else{
            response.setContentType("text/plain");
            response.getWriter().println("AccessTokenがNullです");
        }
    }
    
    /**
     * ログアウトします。
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ログアウトします");
        request.getSession(true).invalidate();
//        response.sendRedirect(request.getContextPath()+ "/");
    }


}