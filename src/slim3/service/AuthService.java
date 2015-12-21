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

import slim3.controller.Const;
import slim3.dto.MsUserDto;
import slim3.meta.MsUserMeta;
import slim3.model.MsUser;
import slim3.service.datastore.MsUserService;
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
        request.setAttribute("loginURL", userService.createLoginURL("/tools/userManage/login"));
        request.setAttribute("logoutURL",userService.createLogoutURL("/tools/userManage/login"));
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
                msUserDto.setRole(slim3.controller.Const.Role.ALL_ADM);
                msUserDto.setName(user.getNickname());
            }else{
                return true;
            }
            return true;
        }
    }
    
    /**
     * userManageの認証機能
     * @param request
     * @param errors
     * @return
     */
    public boolean isMsAuth(HttpServletRequest request, MsUserDto msUserDto, Errors errors){
        
        //クッキー取得
        String cookie = CookieUtil.getCookie(request, Const.MS_AUTH_COOKIE_NAME);
//        log.info("クッキーを取り出しました：" + cookie);
        //TODO デコードが上手くいかない。webのプロジェクトとライブラリが違う？
//        String decodeCookie = CypherUtil.decodeBrowfish(cookie);
//        log.info("クッキーをデコードしました：" + decodeCookie);
        
        //クッキーが無かった場合
        if (StringUtil.isEmpty(cookie)) {
            log.info("クッキーがありませんでした。");
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
                return false;
            }
            
//            log.info("ログイン情報：" + msUser.getName());
            request.setAttribute("msUser", msUser);
            
        } catch (Exception e) {
            StackTraceUtil.toString(e);
            return false;
        }
        return true;
        
    }
    
    
}