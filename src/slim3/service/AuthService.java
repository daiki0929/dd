package slim3.service;

import java.security.Principal;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.slim3.controller.validator.Errors;
import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import slim3.Const;
import slim3.dto.MsUserDto;
import slim3.meta.MsUserMeta;
import slim3.model.MsUser;
import slim3.service.datastore.rese.MsUserService;
import slim3.service.factory.ServiceFactory;
import util.CookieUtil;
import util.StackTraceUtil;
/**
 * ユーザーの認証機能です。
 * @author uedadaiki
 *
 */
public class AuthService {
    
    public final static Logger log = Logger.getLogger(AuthService.class.getName());
    protected static MsUserMeta MS_USER_META = MsUserMeta.get();
    private MsUserService msUserService = ServiceFactory.getService(MsUserService.class);
    
    /**
     * 管理者のメールアドレスか認証します。
     * @param email
     * @return
     */
    public boolean isAdmAcount(String email){
        for (String str : Const.ADM_ACOUNTS) {
            if (str == email) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * ADM用の認証機能です。
     * @param request
     * @param msUser
     * @param errors
     * @return
     */
    public boolean isAdm(HttpServletRequest request, MsUserDto msUserDto, Errors errors){
        UserService userService = UserServiceFactory.getUserService();
        request.setAttribute("loginURL", userService.createLoginURL("/tools/rese/comeAndGo/login"));
        request.setAttribute("logoutURL",userService.createLogoutURL("/tools/rese/comeAndGo/login"));
        //リクエストのユーザが認証されている場合は、java.security.Principalオブジェクトを返します。認証されていない場合はnullを返します。
        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            msUserDto = null;
            return false;
        } else {
            User user = userService.getCurrentUser();
            String email = user.getEmail();
            MsUser userEmail = msUserService.getSingleByEmail(email);
//            log.info("ログイン情報：" + user.getNickname());
            if (userEmail == null) {
                return false;
            }else if(isAdmAcount(email)){
                msUserDto.setRole(Const.Role.ALL_ADM);
                msUserDto.setName(user.getNickname());
            }else{
                return true;
            }
            return true;
        }
    }
    
    /**
     * Reseの認証機能
     * @param request
     * @param errors
     * @return
     * @throws InterruptedException 
     */
    public boolean isMsAuth(HttpServletRequest request, MsUserDto msUserDto, Errors errors) throws InterruptedException{
        
        //クッキー取得
        String cookie = CookieUtil.getCookie(request, Const.MS_AUTH_COOKIE_NAME);
        
        //クッキーが無かった場合
        if (StringUtil.isEmpty(cookie)) {
            log.info("クッキーがありませんでした。");
            Thread.sleep(1000);
            return false;
        }

        try {
            //データベースにあるか検索
            MsUser msUser = Datastore
                    .query(MS_USER_META)
                    .filter(MS_USER_META.userId.equal(cookie))
                    .asSingle();

            //MsUserに存在しない場合
            if (msUser == null) {
                log.info("MsUserに存在しません。");
                Thread.sleep(1000);
                    return false;
            }

            request.setAttribute("msUser", msUser);

        } catch (Exception e) {
            StackTraceUtil.toString(e);
            log.info("エラー");
            return false;
        }
        return true;
        
    }
    
    
}