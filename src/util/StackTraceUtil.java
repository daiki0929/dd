package util;

import java.io.PrintWriter;
import java.io.StringWriter;
/**
 * {@link java.lang.Throwable} のスタックトレースに関するユーティリティメソッドを提供します。
 *
 */
public class StackTraceUtil {

    /**
     * ユーティリティクラスのため、コンストラクタを公開しません。
     */
    private StackTraceUtil() {
    }

    /**
     * 指定された例外のスタックトレースを文字列に変換します。
     */
    public static String toString(Throwable t) {
        if(null == t) {
            return "null";
        }
        //1000文字のバッファーサイズでエラー内容を文字にする。
        StringWriter sw = new StringWriter(1000);
        //スタックトレースを出力する。
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

}