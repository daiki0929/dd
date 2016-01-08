package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
/**
 * Dateを扱うユーティリティクラスです。
 * @author uedadaiki
 *
 */
public class DateUtil {
    
//    private final static Logger log = Logger.getLogger(CookieUtil.class.getName());
    
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
    
    
    
}