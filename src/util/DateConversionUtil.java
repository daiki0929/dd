package util;

import java.util.logging.Logger;
/**
 * Date型をコンバートするユーティリティクラスです。
 * TODO JodaTime使ってるから必要無しっぽい。
 * @author uedadaiki
 *
 */
public class DateConversionUtil {
    @SuppressWarnings("unused")
    private final static Logger log = Logger.getLogger(DateConversionUtil.class.getName());
    /**
     * String型からDate型に変換します。
     * @param str
     * @param pattern
     * @param locale
     * @return
     */
//    public static Date toDate(String str){
//        if (StringUtil.isEmpty(str)) {
//            return null;
//        }
////        SimpleDateFormat sdf = getDateFormat(str, pattern, locale);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
//        try {
//            return sdf.parse(str);
//            
//        } catch (ParseException ex) {
//            throw new MyException(ex);
//        }
//    }
}