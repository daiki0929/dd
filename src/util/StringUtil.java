package util;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 文字列を扱うユーティリティクラスです。
 * @author uedadaiki
 *
 */
public class StringUtil {
    @SuppressWarnings("unused")
    private final static Logger log = Logger.getLogger(StringUtil.class.getName());
    
    /**
     * 前後の全半角スペースをトリムします。
     * 
     * @param s
     * @return
     */
    public static String trim(String s) {

        if(s == null) {
            return s;
        }

        int len = s.length();
        int st = 0;
        char[] val = s.toCharArray();

        while(st < len && (val[st] <= ' ' || val[st] == '　')) {
            st++;
        }

        while(st < len && (val[len - 1] <= ' ' || val[len - 1] == '　')) {
            len--;
        }

        if(st > 0 || len < s.length()) {
            return s.substring(st, len);
        }
        return s;
    }
    
    public static String toString(Object o) {
        return o == null ? null : o.toString();
    }
    
    
    /**
     * 文字列が<code>null</code>または空文字列なら<code>true</code>を返します。
     *
     * @param text
     *            文字列
     * @return 文字列が<code>null</code>または空文字列なら<code>true</code>
     */
    public static final boolean isEmpty(final String text) {
        return text == null || text.length() == 0;
    }

    /**
     * 文字列が<code>null</code>でも空文字列でもなければ<code>true</code>を返します。
     *
     * @param text
     *            文字列
     * @return 文字列が<code>null</code>でも空文字列でもなければ<code>true</code>
     * @since 2.4.33
     */
    public static final boolean isNotEmpty(final String text) {
        return !isEmpty(text);
    }
    
    /**
     * 文字列を正規表現でパースします。
     * @param str
     * @param regex
     * @param replaceStr
     * @return
     */
    public static String parseRegex(String str, String regex, String replaceStr){
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        String parsedStr = m.replaceFirst(replaceStr);
        return parsedStr;
    }
    
    /**
     * 文字列を置き換えます。
     *
     * @param text
     *            テキスト
     * @param fromText
     *            置き換え対象のテキスト
     * @param toText
     *            置き換えるテキスト
     * @return 結果
     */
    public static final String replace(final String text, final String fromText, final String toText) {

        if(text == null || fromText == null || toText == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer(100);
        int pos = 0;
        int pos2 = 0;
        while(true) {
            pos = text.indexOf(fromText, pos2);
            if(pos == 0) {
                buf.append(toText);
                pos2 = fromText.length();
            } else if(pos > 0) {
                buf.append(text.substring(pos2, pos));
                buf.append(toText);
                pos2 = pos + fromText.length();
            } else {
                buf.append(text.substring(pos2));
                break;
            }
        }
        return buf.toString();
    }
    
    
}