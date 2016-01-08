package slim3.service.sns;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import slim3.Const.FbAPI;
import slim3.controller.AbstractController;
import slim3.exception.MyException;

/**
 * FacebookAPIのサービスです。
 * Facebook4J：http://facebook4j.org/ja/code-examples.html
 * @author uedadaiki
 *
 */
public class FacebookService{
    public final static Logger log = Logger.getLogger(AbstractController.class.getName());
    
    Facebook facebook = new FacebookFactory().getInstance();
    
    /**
     * Facebook連携の認証画面にリダイレクトします。
     * 
     * @param request
     * @param response
     * @param callbackURL
     * @param serviceName
     * @throws ServletException
     * @throws IOException
     * @throws InterruptedException
     */
    public void signIn(HttpServletRequest request, HttpServletResponse response, String callbackURL, FbAPI serviceName) throws ServletException, IOException, InterruptedException {
        String fbId = serviceName.getFbId();
        String fbPass = serviceName.getFbPass();
        
        Facebook facebook = new FacebookFactory().getInstance();
        request.getSession().setAttribute("facebook", facebook);
        facebook.setOAuthAppId(fbId, fbPass);
        
        response.sendRedirect(facebook.getOAuthAuthorizationURL(callbackURL.toString()));
    }
    
    
    /**
     * FacebookAPIのコールバック
     * @param request
     * @param response
     * @throws ServletException
     */
    public void callback(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        try {
            Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
            String oauthCode = request.getParameter("code");
            try {
                facebook.getOAuthAccessToken(oauthCode);
            } catch (FacebookException e) {
                throw new ServletException(e);
            }
            response.sendRedirect(request.getContextPath() + "/");
            
        } catch (Exception e) {
            throw new MyException(e);
        }
        
    }
    
}