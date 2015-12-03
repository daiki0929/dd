package slim3.controller.tools.userManage;

import java.util.Locale;

import org.mortbay.log.Log;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import slim3.controller.AbstractController;
import util.DateConversionUtil;

public class TestController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
//        UserService us = UserServiceFactory.getUserService();
//        User user = us.getCurrentUser();
// 
//        if( us.isUserLoggedIn() == false ){
//            log.info("ログインしていません。");
//            String loginUrl = us.createLoginURL("/tools/userManage/customerList.jsp");
//            String logoutUrl = us.createLogoutURL("/tools/userManage/test.jsp");
//            request.setAttribute("loginUrl", loginUrl);
//            request.setAttribute("logoutUrl", logoutUrl);
//        }else{
//            log.info("ログイン情報：" + user.getNickname());
//        }
        
        return forward("test.jsp");
    }
}
