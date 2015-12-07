package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;
/**
 * Dateを扱うユーティリティクラスです。
 * @author uedadaiki
 *
 */
public class DateUtil {
    
    private final static Logger log = Logger.getLogger(CookieUtil.class.getName());
    
    /**
     * 現在時刻を返します。
     * @return
     */
    public static Date getDate(){
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Tokyo");
        Locale locale = Locale.JAPAN;
        Calendar calendar = Calendar.getInstance(timeZone, locale);
//        calendar.setTimeZone(timeZone);
        return calendar.getTime();
    }
    
    /**
     * 日付けをString型で返します。
     * @param fmt
     * @return
     */
    public static String getDateString(String fmt){
        return formatDate(getDate(), fmt);
    }
    
    /**
     * DateをPatternにフォーマットします。
     * @param date
     * @param fmt
     * @return
     */
    public static String formatDate(Date date, String fmt){
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(fmt);
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        return formatter.format(date);
    }
    
//    /**
//     * String型からDate型に変換します
//     * @param dateStr
//     * @param fmt
//     * @return
//     */
//    public static Date stringToDate(String dateStr, String fmt){
//        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
//        try {
//            return sdf.parse(dateStr);
//        } catch (ParseException e) {
//            log.info("date型に変換出来ませんでした");
//            e.printStackTrace();
//            return null;
//        }
//    }
    
    
    
}