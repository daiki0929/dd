
package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Provides utility methods and decorators for {@link Collection} instances.
 *
 * @since Commons Collections 1.0
 * @version $Revision: 646777 $ $Date: 2008-04-10 13:33:15 +0100 (Thu, 10 Apr 2008) $
 *
 * @author Rodney Waldhoff
 * @author Paul Jack
 * @author Stephen Colebourne
 * @author Steve Downey
 * @author Herve Quiroz
 * @author Peter KoBek
 * @author Matthew Hawthorne
 * @author Janek Bogucki
 * @author Phil Steitz
 * @author Steven Melzer
 * @author Jon Schewe
 * @author Neil O'Toole
 * @author Stephen Smith
 */
public class CollectionUtils {

    // -----------------------------------------------------------------------
    /**
     * Null-safe check if the specified collection is empty.
     * <p>
     * Null returns true.
     *
     * @param coll
     *            the collection to check, may be null
     * @return true if empty or null
     * @since Commons Collections 3.2
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Collection coll) {
        return (coll == null || coll.isEmpty());
    }

    /**
     * Null-safe check if the specified collection is not empty.
     * <p>
     * Null returns false.
     *
     * @param coll
     *            the collection to check, may be null
     * @return true if non-null and non-empty
     * @since Commons Collections 3.2
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(Collection coll) {
        return !CollectionUtils.isEmpty(coll);
    }
//
//    /**
//     * 配列をリストに変換します。 未検証
//     * 
//     * @param arrays
//     * @return
//     */
//    public static <T> List<T> array4List(T[] arrays) {
//        return Arrays.asList(arrays);
//    }

//    /**
//     * 文字列を区切り文字で分割してリストで返却します。
//     * 
//     * @param arrayStr
//     * @param divideOption
//     * @return
//     */
//    public static List<String> string4List(String arrayStr, DivideOption divideOption) {
//        List<String> retList = new ArrayList<String>();
//
//        arrayStr = arrayStr.replaceAll("\r\n", "\n");
//
//        String[] array = arrayStr.split(divideOption.separator);
//        for(String str : array) {
//            str = StringUtil.trim(str);
//            if(StringUtil.isNotEmpty(str)) {
//                retList.add(str);
//            }
//        }
//        return retList;
//    }

    /**
     * Listを指定したサイズ毎に分割します。
     *
     * @param origin
     *            分割元のList
     * @param size
     *            Listの分割単位
     * @return サイズ毎に分割されたList。但し、Listがnullまたは空の場合、もしくはsizeが0以下の場合は空のListを返す。
     */
    public static <T> List<List<T>> divide(List<T> origin, int size) {
        if(origin == null || origin.isEmpty() || size <= 0) {
            return Collections.emptyList();
        }

        int block = origin.size() / size + (origin.size() % size > 0 ? 1 : 0);

        List<List<T>> devidedList = new ArrayList<List<T>>(block);
        for(int i = 0; i < block; i++) {
            int start = i * size;
            int end = Math.min(start + size, origin.size());
            devidedList.add(new ArrayList<T>(origin.subList(start, end)));
        }
        return devidedList;
    }


}
