package slim3.service.factory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceFactory {
    private static final Logger logger = Logger.getLogger(ServiceFactory.class.getName());
    @SuppressWarnings("rawtypes")
    private static ConcurrentMap<Class, Object> map = new ConcurrentHashMap<Class, Object>();

    @SuppressWarnings("unchecked")
    public static <T> T getService(Class<T> c) {
        // ConcurrentHashMapはキーにnullが入るとNullPointerExceptionを吐く（らしい）ので先に対応
        // if (c == null) {
        // return null;
        // }

        try {
            if(!map.containsKey(c)) {
                //クラスのインスタンスを作成
                T instance = c.newInstance();
                //インスタンスをmapのObjectに詰める
                map.put(c, instance);
            }
        } catch (IllegalAccessException e) {
            logger.log(Level.WARNING, "サービスクラスの取得に失敗しました。");
            e.printStackTrace();
        } catch (InstantiationException e) {
            logger.log(Level.WARNING, "サービスクラスの取得に失敗しました。");
            e.printStackTrace();
        }
        //インスタンスしたクラスを返す。
        return (T) map.get(c);
    }
}
