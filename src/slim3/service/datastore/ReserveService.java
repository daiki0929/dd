package slim3.service.datastore;

import java.util.List;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import slim3.meta.reserve.ReserveMeta;
import slim3.model.reserve.Reserve;
/**
 * 予約情報を取得するサービスです。
 * @author uedadaiki
 *
 */
public class ReserveService extends AbstractDatastoreService{
    
    private final static ReserveMeta RESERVE_META = ReserveMeta.get();
    
    /**
     * 予約情報をkeyで1つ取得する。
     * @param id
     * @return
     */
    public Reserve get(Key id){
        return dsService.getSingle(Reserve.class, ReserveMeta.get(),id);
    }
    
    /**
     * MsUserのKeyで、予約情報を全て取り出します。
     * @param msUserKey
     * @return
     */
    public List<Reserve> getListByMsUserKey(Key msUserKey){
        return Datastore.query(RESERVE_META).filter(RESERVE_META.msUserRef.equal(msUserKey)).asList();
    }
    
}