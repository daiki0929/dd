package slim3.service.sns;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import slim3.Const.TWAPI;
import slim3.controller.AbstractController;
import slim3.exception.MyException;
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
    
    public final static Logger log = Logger.getLogger(AbstractController.class.getName());
    Twitter twitter = new TwitterFactory().getInstance();
    
    /**
     * Twitter連携の認証画面にリダイレクトします。
     * @param request
     * @param response
     * @param callbackURL
     * @param serviceName
     * @throws ServletException
     * @throws IOException
     * @throws InterruptedException
     */
    public void signIn(HttpServletRequest request, HttpServletResponse response, String callbackURL, TWAPI serviceName) throws ServletException, IOException, InterruptedException {
        String twId = serviceName.getTwId();
        String twPass = serviceName.getTwPass();
        twitter.setOAuthConsumer(twId, twPass);
        
        try{
            RequestToken reqToken = twitter.getOAuthRequestToken(callbackURL);
 
            HttpSession session = request.getSession();
            session.setAttribute("RequestToken", reqToken);
            session.setAttribute("Twitter", twitter);
            log.info("RequestToken：" + session.getAttribute("RequestToken").toString());
            log.info("Twitter：" + session.getAttribute("Twitter").toString());
            
            //認証画面に飛ばします。
            String strUrl = reqToken.getAuthorizationURL();
            response.sendRedirect(strUrl);
        }catch (TwitterException e){
            throw new MyException(e);
        }
    }
    
    /**
     * 認証完了後のコールバックです。
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws TwitterException 
     */
    public void callback(HttpServletRequest request, HttpServletResponse response, RequestToken reqToken, TWAPI serviceName) throws ServletException, IOException, TwitterException {
        HttpSession session = request.getSession();
        String twId = serviceName.getTwId();
        String twPass = serviceName.getTwPass();
        twitter.setOAuthConsumer(twId, twPass);
        String verifier = request.getParameter("oauth_verifier");
        AccessToken accessToken = null;
        
        log.info("reqToken：" + reqToken.toString());
 
        try {
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
            
            log.info("ログイン完了。");
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


}