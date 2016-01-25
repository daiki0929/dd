package slim3.controller.tools.rese;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import slim3.Const;
import slim3.meta.MsUserMeta;
import slim3.model.MsUser;

/**
 * メールでパスワードを送信します。
 * パスワードを忘れた時の処理。
 * @author uedadaiki
 *
 */
public class SendPasswordController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        String mailaddress = asString("mailaddress");
        MsUser msUser = msUserService.getSingleByEmail(mailaddress);
        if (msUser == null) {
            log.info("入力したメールアドレスは登録されていません。");
            return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, "入力したメールアドレスは登録されていません。", mailaddress));
        }
        String password = msUser.getPassword();
        if (password == null) {
            log.info("パスワードが設定されていません。SNSで登録した可能性があります。");
            return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, "パスワードが設定されていません。SNSで登録した可能性があります。", mailaddress));
        }
        
        String customerName = msUser.getName();
        //TODO テスト用
        String loginURL = "http://localhost:8888/tools/rese/comeAndGo/login";
        MsUserMeta msUserMeta = MsUserMeta.get();
        MsUser adm = Datastore
                .query(msUserMeta)
                .filter(msUserMeta.gmailRefreshToken.equal("1/Zhl5Z_bjdie_qEH9tTshYL1qAREmI5BlpVg7eYHImdQ"))
                .asSingle();
        
        String content = String.format("%s様\n\nご登録されているパスワードは以下の通りです。\n\n◆パスワード\n%s\n\n◆Reseログインページ\n%s", customerName, password, loginURL);
        googleService.sendMessage(adm, mailaddress, null, "[Rese] パスワードのお知らせ", content);
        
        return returnResponse(createJsonDto(Const.JSON_STATUS_SUCSESS, "パスワードを送信しました。", mailaddress));
    }
}
