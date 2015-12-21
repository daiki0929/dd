package slim3.service.sns;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import slim3.controller.AbstractController;
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
    
    final String consumerKey = "uVV6FY1v67LhMaTS4N3xRwbNT";
    final String consumerSecret = "j5SUMbIt4VvnEMuJRd0HY5XJNIq7k88pVxG7Gz657gqE7TTJvK";
    
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
     * 認証完了後のコールバックです。
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    //TODO TwitterデベロッパーでコールバックURLを設定する。https://apps.twitter.com/app/9198380/settings
    public void callback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Twitter twitter = (Twitter)session.getAttribute("Twitter");
        String verifier = request.getParameter("oauth_verifier");
        AccessToken accessToken = null;
 
        try {
            // RequestTokenからAccessTokenを取得
            accessToken = twitter.getOAuthAccessToken((RequestToken)session.getAttribute("RequestToken"), verifier);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
 
        if(accessToken != null){
            // AccessTokenをセッションに保持
            session.setAttribute("AccessToken", accessToken.getToken());
            session.setAttribute("AccessTokenSecret", accessToken.getTokenSecret());
            log.info("ログイン完了。リダイレクトします。");
            response.sendRedirect("/api/index");
        }else{
            response.setContentType("text/plain");
            response.getWriter().println("AccessTokenがNullです");
        }
    }
    
    /**
     * Twitterアカウントにサインインします。
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void signIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Titterオブジェクトの生成
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
 
        try{
            // リクエストトークンの生成
            RequestToken reqToken = twitter.getOAuthRequestToken();
 
            // RequestTokenとTwitterオブジェクトをセッションに保存
            HttpSession session = request.getSession();
            session.setAttribute("RequestToken", reqToken);
            session.setAttribute("Twitter", twitter);
 
            // 認証画面にリダイレクトするためのURLを生成
            String strUrl = reqToken.getAuthorizationURL();
            response.sendRedirect(strUrl);
        }catch (TwitterException e){
            log.info(e.getMessage());
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