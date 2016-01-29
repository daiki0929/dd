package slim3;


public class Const {    
    
    // ================================
    // 環境系
    public final static String DEFAULT_CONTENT_TYPE = "UTF-8";
    public final static String JSON_CONTENT_TYPE = "text/html; charset=UTF-8;";
    
    // ================================
    // クッキー
    public final static String MS_AUTH_COOKIE_NAME = "oiniaesfs";
//    public final static String MEGAPON_AUTH_COOKIE_NAME = "oiniaesfs";
//    public final static String COKKIE_CIPHER = "s8j34kd7";
    public final static String MS_AUTH_COOKIE_DISP_TIME = "1sjr74mv";
//    public final static String MEGAPON_AUTH_COOKIE_DISP_TIME = "sg39d9nl";
    public final static String MS_REQUEST_URL = "euhxdgtp";
    
    // ================================
    // 管理者
    public final static String[] ADM_ACOUNTS = {
                "test@example.com"
                };
    // ================================
    // GoogleCloudStorage
    public static final String BUCKETNAME = "/user-manage_img";
    public static final String MENU_PAGE_IMG = "/menuPageImg";
    
    // ================================
    // Ajax系
    public final static String JSON_STATUS_SUCSESS = "success";
    public final static String JSON_STATUS_WARN = "warn";
    public final static String JSON_STATUS_ERROR = "error";
    public final static String JSON_ERROR_FORMAT = "エラーが発生しました。[%s]";
    
    // ================================
    // 店舗の情報
    //営業日 or 非営業日
    public static final String OPEN = "open";
    public static final String NOT_OPEN = "notOpen";
    //曜日
    public static final String SUNDAY = "sunday";
    public static final String MONDAY = "monday";
    public static final String TUESDAY = "tuesday";
    public static final String WEDNESDAY = "wednesday";
    public static final String THURSDAY = "thursday";
    public static final String FRIDAY = "friday";
    public static final String SATURDAY = "saturday";
    public static final String[] DAYS_OF_THE_WEEK = {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};
    
    
    
    // ================================
    // 入力チェック正規表現
    /**
     * 正規表現-日付 ○20130101 ☓201311
     * */
    public final static String REGEX_YYYYMMDD_SIMPLE = "^(19|20)[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$";
    /**
     * 正規表現-日付 ○2011/01/32 ○2011/01/1 ○2013/1/1
     * */
    public final static String REGEX_YYYYMMDD_SLASH = "^[0-9]{4}/[01]?[0-9]/[0123]?[0-9]$";
    public final static String[] REGEX_DATE = { REGEX_YYYYMMDD_SIMPLE
        + "|"
        + REGEX_YYYYMMDD_SLASH };
    /** ひらがな・カタカナ・数値・記号（全ての文字がOK） */
    public final static String REGEX_KANA_HIRA = "^[\\u30A0-\\u30FF]+[ 　]*[\\u30A0-\\u30FF]+$|^[\\u3040-\\u309F]+[ 　]*[\\u3040-\\u309F]+$|";
    /** 整数(プラスのみ) */
    public final static String REGEX_NUMBER = "^[0-9]+$";
    /** 整数＋少数どちらも可 */
    public final static String REGEX_NUMBER_DECIMAL_ALL = "^(([0-9]+)|0)(\\.([0-9]+))?$";
    /** 整数3ケタ＋少数4ケタの数値用 */
    public final static String REGEX_NUMBER_DECIMAL = "^([1-9]\\d{0,3}|0)(\\.\\d{0,4})?$";
    /** (プラス、マイナス可、少数不可) */
    public final static String REGEX_NUMBER_ALL = "^[-]?[0-9]+(¥.[0-9]+)?$";

    public final static String REGEX_YEAR_DATE = "([0-9]{4})/([0-9]{1,2})/([0-9]{1,2})";
    public final static String REGEX_YEAR = "^(19|20)[\\d]{2}+$";
    public final static String REGEX_MONTH = "^(0[1-9]|1[0-2])|[1-9]";
    public final static String REGEX_DAY = "^(0[1-3]|1[0-9]|2[0-9]|3[0-1])|[1-9]";
    public final static String REGEX_MOMENTS = "^([01]?[0-9]|2[0-3]):([0-5][0-9])$";
//    public final static String REGEX_PHONE = "^[0-9]+$|^[0-9]+-+[0-9]+-+[0-9]+$";
    public final static String REGEX_PHONE = "^[0-9]+-[0-9]+-[0-9]+$";
    public final static String REGEX_MAIL_ADDRESS = "[\\w\\.\\-]+@(?:[\\w\\-]+\\.)+[\\w\\-]+";
    public final static String REGEX_PASSWORD = "^[a-zA-Z0-9]+$";
    public final static String REGEX_AZ = "^[A-Z]+$";
    public final static String REGEX_AZ09 = "^[A-Z0-9]+$";
    public final static String REGEX_FUJIN_ID = "^id[\\d]{6}+$";
    public final static String REGEX_1BYTE_CHAR = "[^\\x01-\\x7E]+";

    /**
     * 正規表現管理Enum
     * @author kitazawatakuya
     *
     */
    public static enum RegexType {
        DATE(REGEX_DATE), KANA_HIRA(new String[] { REGEX_KANA_HIRA }),
        NUMBER_DECIMAL_ALL(new String[] { REGEX_NUMBER_DECIMAL_ALL }),
        NUMBER_DECIMAL(new String[] { REGEX_NUMBER_DECIMAL }),
        NUMBER(new String[] { REGEX_NUMBER }),
        NUMBER_ALL(new String[] { REGEX_NUMBER_ALL }),
        YEAR_DATE(new String[] { REGEX_YEAR_DATE }),
        YEAR(new String[] { REGEX_YEAR }),
        MONTH(new String[] { REGEX_MONTH }),
        DAY(new String[] { REGEX_DAY }),
        MOMENTS(new String[] { REGEX_MOMENTS }),
        PHONE(new String[] { REGEX_PHONE }),
        MAIL_ADDRESS(new String[] { REGEX_MAIL_ADDRESS }),
        PASSWORD(new String[] { REGEX_PASSWORD }),
        SEX(new String[] { "man|woman" }),
        FUJIN_ID(new String[] { REGEX_FUJIN_ID }),
        BOOL(new String[] { "true|false" }),
        AZ(new String[] { REGEX_AZ }),
        AZ09(new String[] { REGEX_AZ09 }),
        SKU(new String[] { "^([A-Z0-9]+-?)+$"}),
        ASIN(new String[] { "^[A-Z0-9]{10}$"}),
        /** ひらがな */
        HIRAGANA(new String[]{"^[\\u3040-\\u309F]+$"}),
        /** 半角英数 */
        HALF_WIDTH_ALPHANUMERIC(new String[] { "^[a-zA-Z0-9]+$" });

        private RegexType(String[] s) {
            this.values = s;
        }

        private String[] values;

        public String[] getValues() {
            return values;
        }

        public void setValues(String[] values) {
            this.values = values;
        }

    }
    
    
    // ================================
    // FacebookAPI
    /**
     * FacebookAPIのID・PW
     * @author uedadaiki
     *
     */
    public static enum FbAPI{
        Rese("439419456260791","0754033e8212cb8c18cf0bd1e9230e2d");
        
        private final String id;
        private final String pass;
        
        private FbAPI(String id, String pass){
            this.id = id;
            this.pass = pass;
        }
        
        public String getFbId() {
            return id;
        }
        public String getFbPass() {
            return pass;
        }
    }
    
    /**
     * FacebookAPIのコールバックURL
     * @author uedadaiki
     *
     */
    public static enum FbCallbackEnum{
        Rese_TEST("http://rese.space/tools/rese/comeAndGo/facebook/callBack"),
        Rese("http://rese.space/tools/rese/comeAndGo/facebook/callBack");
        
        private final String callbackURL;
        
        private FbCallbackEnum(final String callbackURL){
            this.callbackURL = callbackURL;
        }
        public String getCallbackURL() {
            return callbackURL;
        }
    }
    
    
    // ================================
    // TwitterAPI
    /**
     * TwitterAPIのID・PW
     * ID：ConsumerKey / PW：ConsumerSecretKey
     * @author uedadaiki
     *
     */
    public static enum TWAPI{
        Rese("uVV6FY1v67LhMaTS4N3xRwbNT","j5SUMbIt4VvnEMuJRd0HY5XJNIq7k88pVxG7Gz657gqE7TTJvK");
        
        private final String id;
        private final String pass;
        
        private TWAPI(String id, String pass){
            this.id = id;
            this.pass = pass;
        }
        
        public String getTwId() {
            return id;
        }
        public String getTwPass() {
            return pass;
        }
    }
    
    /**
     * TwitterAPIのコールバックURL
     * @author uedadaiki
     *
     */
    public static enum TwCallbackEnum{
        Rese_TEST("http://127.0.0.1:8888/tools/rese/comeAndGo/twitter/CallBack"),
        Rese("http://dd01-1142.appspot.com/tools/rese/comeAndGo/twitter/CallBack");
        
        private final String callbackURL;
        private TwCallbackEnum(final String callbackURL){
            this.callbackURL = callbackURL;
        }
        public String getCallbackURL() {
            return callbackURL;
        }
    }
    
    // ================================
    // GoogleAPI
    /**
     * GoogleAPIのアクセストークン・リフレッシュトークン
     * @author uedadaiki
     *
     */
    public static enum GGAPI{
        Rese("ya29.ZwJehmAzILvZesGoa--zynsy3h7YOooHE08K-7F-ForYM2iCwfMqrFZXMsXQ9QdEm6Tu","1/9w6RsBl9VfSrQ5gnhMJbP2GzxMD3-HjknNQOeR6ckAE");
        
        private final String accessToken;
        private final String refreshToken;
        
        private GGAPI(String accessToken, String refreshToken){
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
        
        public String getGGAccessToken() {
            return accessToken;
        }
        public String getGGRefreshToken() {
            return refreshToken;
        }
    }
    
    
    // ================================
    // 権限種別
    public static enum Role{
        ALL_ADM("全体管理者", true, 2),
        PREMIUM_USER("有料会員", false, 1),
        BASIC_USER("無料会員", false, 0);
        
        private String name;
        private boolean adm;
        private int role;
        
        private Role(String name, boolean adm, int role){
            this.setName(name);
            this.setAdm(adm);
            this.setRole(role);
        }
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public boolean isAdm() {
            return adm;
        }
        public void setAdm(boolean adm) {
            this.adm = adm;
        }
        public int getRole() {
            return role;
        }
        public void setRole(int role) {
            this.role = role;
        }
    }
    
    /**
     * ユーザーエージェント
     * @author ueda.daiki
     *
     */
    public static enum UA{
        A20("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)"),
        A21("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; Sleipnir/2.9.8)"),
        A22("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.0; Trident/5.0)"),
        A23("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)"),
        A24("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Win64; x64; Trident/6.0)"),
        A25("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)"),
        A26("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; WOW64; Trident/6.0)"),
        A27("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Win64; x64; Trident/6.0)"),
        A28("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; ARM; Trident/6.0)"),
        A29("Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; Touch; rv:11.0) like Gecko"),
        A30("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.52 Safari/537.36"),
        A31("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.63 Safari/537.36"),
        A32("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_3; ja-jp) AppleWebKit/533.16 (KHTML, like Gecko) Version/5.0 Safari/533.16"),
        A33("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.52.7 (KHTML, like Gecko) Version/5.1.2 Safari/534.52.7"),
        A34("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2"),
        A35("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8) AppleWebKit/536.25 (KHTML, like Gecko) Version/6.0 Safari/536.25"),
        ;

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private UA(String name){
            this.name = name;
        }

        //ランダムで取得します。
        public static UA getRandom(){
            UA[] uas = UA.values();
            int size = uas.length;
            int ran =  (int)(Math.random() * (size));

            return uas[ran];
        }
    }

    
    
}