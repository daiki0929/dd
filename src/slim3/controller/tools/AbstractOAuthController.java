package slim3.controller.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelQuery;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.Preconditions;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListThreadsResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;
import com.google.appengine.api.datastore.KeyFactory;

import slim3.Const;
import slim3.controller.AbstractController;
import slim3.exception.MyException;
import slim3.meta.MsUserMeta;
import slim3.model.MsUser;
import util.StringUtil;

/**
 * GoogleのOAUTHを利用するControllerの親クラスです。
 *
 * @author uedadaiki
 *
 */
public abstract class AbstractOAuthController extends AbstractController {

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final String APP_NAME = "CAN";

    // スコープリスト
    // http://hayageek.com/google-oauth-scope-list/
    private static final List<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.email", // ユーザーのEmail
//    "https://spreadsheets.google.com/feeds", // スプレッドシート
//    "https://www.googleapis.com/auth/drive.file", // Google Drive Files
//    "https://www.googleapis.com/auth/drive", // Google Drive
    GmailScopes.GMAIL_MODIFY, // Gmail
    GmailScopes.MAIL_GOOGLE_COM, // Gmail
    GmailScopes.GMAIL_COMPOSE // Gmail
    );

    /**
     * リダイレクトURLを返却します。
     * 
     * @param req
     * @return
     */
    protected String getRedirectUri(HttpServletRequest req) {
        GenericUrl genericUrl = new GenericUrl(req.getRequestURL().toString());
        genericUrl.setRawPath("/bcc/thanksmail/callback");
        String url = genericUrl.build();
        return url;
    }

    /**
     * 認可コード取得用のGoogleAuthorizationCodeFlowを生成します。
     * 
     * @return
     * @throws IOException
     */
    public GoogleAuthorizationCodeFlow newFlow() throws IOException {

        return new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), getClientCredential(), SCOPE).setDataStoreFactory(AppEngineDataStoreFactory.getDefaultInstance()).setAccessType("offline").build();
    }

    /**
     * コールバック用のGoogleAuthorizationCodeFlowを生成します。
     * 
     * @return
     * @throws IOException
     */
    protected GoogleAuthorizationCodeFlow callbackFlow() throws IOException {
        try {
            return new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), getClientCredential(), SCOPE).setDataStoreFactory(AppEngineDataStoreFactory.getDefaultInstance()).setAccessType("offline").setJsonFactory(JacksonFactory.getDefaultInstance()).build();

        } catch (Exception e) {
            throw new MyException(e);
        }
    }

    /**
     * Secreat.jsonを元にGoogleClientSecretsを生成します。
     * 
     * @return
     * @throws IOException
     */
    protected GoogleClientSecrets getClientCredential() throws IOException {

        GoogleClientSecrets clientSecrets = null;
        if(clientSecrets == null) {
            clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(this.getClass().getResourceAsStream("/google_oauth_config.json")));

            Preconditions.checkArgument(!clientSecrets.getDetails().getClientId().startsWith("Enter ") && !clientSecrets.getDetails().getClientSecret().startsWith("Enter "), "Download client_secrets.json file from https://code.google.com/apis/console/" + "?api=calendar into calendar-appengine-sample/src/main/resources/client_secrets.json");
        }
        return clientSecrets;
    }

    /**
     * 受信メールを取得します。
     * 
     * @param credential
     * @param MsUser
     * @return
     * @throws IOException
     */
    protected List<com.google.api.services.gmail.model.Thread> getMessageList(GoogleCredential credential, MsUser MsUser) throws IOException {
        Gmail service = getMailService(credential);
        ListThreadsResponse threadsResponse = service.users().threads().list(MsUser.getGmailAddress()).execute();
        return threadsResponse.getThreads();
    }

    /**
     * ユーザー情報を取得します。
     * 
     * @param credentials
     * @return
     */
    static Userinfoplus getUserInfo(GoogleCredential credentials) {
        Oauth2 userInfoService = new Oauth2(HTTP_TRANSPORT, JSON_FACTORY, credentials);

        Userinfoplus userinfoplus = null;
        try {
            userinfoplus = userInfoService.userinfo().get().execute();
            log.info(userinfoplus.getEmail());
        } catch (IOException e) {
            throw new MyException(e);
        }
        if(userinfoplus != null && userinfoplus.getId() != null) {
            return userinfoplus;
        } else {
            throw new MyException("失敗");
        }
    }

    /**
     * メッセージを送信します。
     * 
     * @param MsUser
     * @param to
     * @param subject
     * @param content
     * @throws GeneralSecurityException
     * @throws IOException
     */
    protected void sendMessage(MsUser msUser, String to, String bcc, String subject, String content) throws GeneralSecurityException, IOException {

//        // テスト環境ではメール送信しない。
//        if(!isCommerce(request)) {
//            log.info("テスト環境のためメール送信はスキップします。");
//            log.info(subject);
//            log.info(content);
//            return;
//        }

        // アクセストークンを元に取得
        GoogleCredential credential = new GoogleCredential().setAccessToken(msUser.getGmailAccessToken());
        if(credential == null) {
            throw new MyException("アクセストークンから認証情報を取得できませんでした。");
        }

        try {
            sendMessage(credential, msUser, to, bcc, subject, content);
            log.info("mail send success.");
        } catch (Exception e) {
            log.info("access token deadline limit.");
            if(e instanceof GoogleJsonResponseException) {
                if(e.getMessage().indexOf("authError") < 0) {
                    throw new MyException(e);
                }
            }

            // リフレッシュトークンを元に取得
            GoogleCredential refreshCredential;
            log.info("失敗したためリフレッシュトークンからアクセストークンを再取得します。" + msUser.getGmailRefreshToken());
            try {
                refreshCredential = getAccessTokenByRefreshToken(msUser);
            } catch (TokenResponseException e2) {
                log.info("リフレッシュトークンで失敗しました。");

                // 失敗した場合は同じGmail設定を行っているアカウントを全て初期化する。
                if(e2.getMessage().indexOf("invalid_grant") > 0) {
                    List<MsUser> list = getSameMsUserList(msUser.getGmailAddress());
                    for(MsUser t : list) {
                        log.info("refresh token is no use. entity reset. " + KeyFactory.keyToString(t.getKey()));
                        t.setGmailAddress(null);
                        t.setGmailAccessToken(null);
                        // t.setGmailRefleshToken(null);
                        dsService.put(t);
                    }
                }
                throw new MyException(e2);
            }

            // アクセストークンの更新
            updateAccessToken(refreshCredential, msUser);
            sendMessage(refreshCredential, msUser, to, bcc, subject, content);

            log.info("mail send success by refresh token.");
        }
    }

    /**
     * 受信メールを取得するAPIを実行し結果を返却します。
     * 
     * @param credential
     * @param rmUserProperty
     * @return
     * @throws IOException
     */
    private boolean sendMessage(GoogleCredential credential, MsUser from, String to, String bcc, String subject, String content) throws IOException {
        try {
            Gmail gmailService = getMailService(credential);

            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage email = new MimeMessage(session);
            // Set From: header field of the header.
//            email.setFrom(new InternetAddress(from.getGmailAddress()));
            //TODO とりあえずメアドを入れてます。
            email.setFrom(new InternetAddress("0929dddd@gmail.com"));

            // Set To: header field of the header.
            email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));

            if(StringUtil.isNotEmpty(bcc)) {
                email.addRecipient(javax.mail.Message.RecipientType.BCC, new InternetAddress(bcc));
                log.fine("BCC=" + bcc);
            }

            // Set Subject: header field
            email.setSubject(subject, Const.DEFAULT_CONTENT_TYPE);

            // Send the actual HTML message, as big as you like
            email.setText(content, Const.DEFAULT_CONTENT_TYPE);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            email.writeTo(baos);
            String encodedEmail = Base64.encodeBase64URLSafeString(baos.toByteArray());
            Message message = new Message();
            message.setRaw(encodedEmail);

            message = gmailService.users().messages().send(from.getGmailAddress(), message).execute();
            if(message.getId() != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(e);
        }
    }

    /**
     * 同一のメールアドレスで登録されているアカウントがあれば返却します。
     * 
     * @param mailAddress
     * @return
     */
    protected List<MsUser> getSameMsUserList(String mailAddress) {
        MsUserMeta msUserMeta = MsUserMeta.get();
        ModelQuery<MsUser> query = Datastore.query(msUserMeta);
        query = query.filter(msUserMeta.gmailAddress.equal(mailAddress));
        List<MsUser> list = query.asList();
        return list;
    }

    /**
     * 同一のメールアドレスで登録されているアカウントがあれば返却します。
     * 
     * @param mailAddress
     * @return
     */
    protected MsUser getSameMsUser(String mailAddress) {
        List<MsUser> list = getSameMsUserList(mailAddress);
        for(MsUser t : list) {
            if(StringUtil.isNotEmpty(t.getGmailRefreshToken())) {
                return t;
            }
        }
        return null;
    }

    /**
     * Gmailのサービスインスタンスを生成します。
     * 
     * @param credential
     * @return
     */
    protected Gmail getMailService(GoogleCredential credential) {
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APP_NAME).build();
    }

    /**
     * リフレッシュトークンを元にアクセストークンを再取得します。
     * 
     * @param rmUserProperty
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    protected GoogleCredential getAccessTokenByRefreshToken(MsUser MsUser) throws GeneralSecurityException, IOException {
        GoogleCredential refreshCredential;
        refreshCredential = new GoogleCredential.Builder().setTransport(GoogleNetHttpTransport.newTrustedTransport()).setJsonFactory(JacksonFactory.getDefaultInstance()).setClientSecrets(getClientCredential()).build().setFromTokenResponse(new TokenResponse().setRefreshToken(MsUser.getGmailRefreshToken()));
        refreshCredential.setRefreshToken(MsUser.getGmailRefreshToken());
        refreshCredential.refreshToken();

        log.fine("アクセストークン before " + MsUser.getGmailAccessToken());
        log.info("新アクセストークン after  " + refreshCredential.getAccessToken());

        return refreshCredential;
    }

    /**
     * アクセストークンを更新します。
     * 
     * @param credential
     * @param rmUserProperty
     */
    protected void updateAccessToken(GoogleCredential credential, MsUser msUser) {
        if(StringUtil.isEmpty(credential.getAccessToken())) {
            throw new MyException("new accessToken is null.");
        }

        List<MsUser> sameMsUserList = getSameMsUserList(msUser.getGmailAddress());
        for(MsUser t : sameMsUserList) {
            log.info(String.format("アクセストークンを更新します。[%s][%s]→[%s]", t.getName(), t.getGmailAccessToken(), credential.getAccessToken()));
            t.setGmailAccessToken(credential.getAccessToken());
            dsService.put(t);

        }

    }

}
