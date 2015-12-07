package slim3.service.datastore;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.meta.MsShopMeta;
import slim3.model.MsShop;
/**
 * 店舗情報を取得するサービスです。
 * @author uedadaiki
 *
 */
public class MsShopService extends AbstractDatastoreService{
    
    private final static MsShopMeta MS_SHOP_META = MsShopMeta.get();
    
    /**
     * お店の情報をkeyで1つ取得する。
     * @param id
     * @return
     */
    public MsShop get(Key id){
        return dsService.getSingle(MsShop.class, MsShopMeta.get(),id);
    }
    
    /**
     * MsUserのKeyで、ユーザーが所持しているお店の情報を取り出します。
     * @param msUserKey
     * @return
     */
    public MsShop getByMsUserKey(Key msUserKey){
        return Datastore.query(MS_SHOP_META).filter(MS_SHOP_META.msUserRef.equal(msUserKey)).asSingle();
    }
    
//    以下保留
//    /**
//     * クッキーにあるuserIdでお店の情報を取り出します。
//     * @param request
//     * @param msUserService
//     * @param MS_USER_META
//     * @param userId
//     * @return
//     */
//    public MsShop getByUserId(HttpServletRequest request, MsUserService msUserService, MsUserMeta MS_USER_META, String userId){
//        MsUser msUser = msUserService.getSingleByCookie(request, Const.MS_AUTH_COOKIE_NAME, MS_USER_META);
//        if (msUser == null) {
//            return null;
//        }
//        Key msUserKey = msUser.getKey();
//        return Datastore
//        .query(MS_SHOP_META)
//        .filter(MS_SHOP_META.msUserRef.equal(msUserKey))
//        .asSingle();
//    }
    
}