package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import slim3.exception.MyException;
/**
 * Date型をコンバートするユーティリティクラスです。
 * @author uedadaiki
 *
 */
public class DateConversionUtil {
    @SuppressWarnings("unused")
    private final static Logger log = Logger.getLogger(DateConversionUtil.class.getName());
    /**
     * String型からDate型に変換します。
     * TODO もっと多様な変換を出来るようにする。
     * @param str
     * @param pattern
     * @param locale
     * @return
     */
    public static Date toDate(String str){
        if (StringUtil.isEmpty(str)) {
            return null;
        }
//        SimpleDateFormat sdf = getDateFormat(str, pattern, locale);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
        try {
            return sdf.parse(str);
            
        } catch (ParseException ex) {
            throw new MyException(ex);
        }
    }
}