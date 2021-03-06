package util;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;

import slim3.exception.MyException;
/**
 * クッキーのユーティリティクラスです。
 * @author uedadaiki
 *
 */
public class CookieUtil {
    @SuppressWarnings("unused")
    private final static Logger log = Logger.getLogger(CookieUtil.class.getName());
   /**
    * クッキーを取得する。無ければ作成する。
    * @param request
    * @param name
    * @return
    */
    public static String getCookie(HttpServletRequest request, String name){
        Cookie cookie[] = request.getCookies();
        if(cookie != null) {
            for(int i = 0; i < cookie.length; i++) {
                // マッチしたらTrueを返す。
                if(name.equals(cookie[i].getName())) {
                    return (cookie[i].getValue().toString());
                }
            }
        }
        return null;
    }
    
    /**
     * クッキーをセットします。
     * @param res
     * @param name
     * @param value
     * @param maxAge
     */
    public static void setCookie(HttpServletResponse res, String name, String value, int maxAge){
        Cookie cookie = new Cookie(name, value);
//        log.info("クッキー情報："+cookie.toString());
        if (maxAge != 0) {
            //0の場合セットしない。
            cookie.setMaxAge(maxAge);
        }
        cookie.setPath("/");
        res.addCookie(cookie);
    }
    
    public static void setCookie(HttpServletResponse res, String name, String value) {
        setCookie(res, name, value, 60 * 5);
    }
    
    /**
     * クッキーを削除します。
     * @param res
     * @param name
     */
    public static void deleteCookie(HttpServletResponse res, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/");
        res.addCookie(cookie);
    }
    
    /**
     * クッキーを作成します。
     * @param key
     * @return
     */
    public static String createCookieStr(Key key){
        String encrypt;
        try {
            encrypt = Base64Util.encodeWebSafe(String.format("%s_%s", key, DateUtil.getDateString("yyyyMMddHHmm")).getBytes("UTF-8"), false);
//            log.info("暗号化しました。 " + encrypt);
        } catch (UnsupportedEncodingException e) {
            throw new MyException(e);
        }
        return encrypt;
    }
    
    
}