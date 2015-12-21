package slim3.controller;

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
    public static final String[] DAYS_OF_THE_WEEK = {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY};
    
    // ================================
    // お店の情報
    public static final String MENU_PUBLIC = "public";
    public static final String MENU_CLOSED = "closed";
    
    
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
    // メニューページ作成
//    //承認制 or 先着順
//    public final static String RECOGNITION_SYSTEM = "recognition";
//    public final static String FIRST_ARRIVAL_SYSTEM = "firstArrival";
//    
//    //公開 or 非公開
//    public final static String OPEN = "open";
//    public final static String NOT_OPEN = "notOpen";
    
    
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
    
    
}