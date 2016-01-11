package slim3.service.datastore.rese;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import com.google.appengine.api.datastore.Key;

import slim3.Const;
import slim3.dto.MsUserDto;
import slim3.meta.MsUserMeta;
import slim3.model.MsUser;
import slim3.service.datastore.AbstractDatastoreService;
import util.CookieUtil;
import util.StackTraceUtil;
/**
 * ユーザーを取得するサービスです。
 * @author uedadaiki
 *
 */
public class MsUserService extends AbstractDatastoreService{
    
    private final static MsUserMeta MS_USER_META = MsUserMeta.get();
    
    /**
     * 重複しているかをチェックします。
     * @param mailaddress 登録するメールアドレス
     * @param customer 
     * @return
     */
    public boolean duplicateMailAddress(String mailaddress, MsUserDto msUserDto){
        //登録済みチェック
        List<MsUser> MsUserList = Datastore
            .query(MS_USER_META)
            .filter(MS_USER_META.mailaddress.equal(mailaddress))
            .asList();
        for (MsUser t : MsUserList) {
            if (!t.getKey().equals(msUserDto.getMsUserId())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * データベースからkeyでデータを1つ取得。
     * @param id
     * @return
     */
    public MsUser get(Key id){
        return dsService.getSingle(MsUser.class, MsUserMeta.get(),id);
    }
    /**
     * データベースからemailでデータを1つ取得。
     * @param email
     * @return
     */
    public MsUser getSingleByEmail(String email){
        return Datastore.query(MS_USER_META).filter(MS_USER_META.mailaddress.equal(email)).asSingle();
    }
    
      //TODO クッキーでモデルを１つ取得するのが他でも使えそうなら抽象化して利用する。
    /**
     * データベースからクッキー情報(userId)でデータを1つ取得。
     * @param request
     * @return
     */
    public MsUser getSingleByCookie(HttpServletRequest request){
        String cookie = CookieUtil.getCookie(request, Const.MS_AUTH_COOKIE_NAME);
        if (StringUtil.isEmpty(cookie)) {
            log.info("クッキーがありませんでした。");
            return null;
        }

        try {
            MsUser msUser = Datastore
                    .query(MS_USER_META)
                    .filter(MS_USER_META.userId.equal(cookie))
                    .asSingle();
            if (msUser == null) {
                log.info("MsUserに存在しないuserIdです。");
                return null;
            }
            return msUser;
        } catch (Exception e) {
            StackTraceUtil.toString(e);
            return null;
        }
    }
    
    
    
    
}