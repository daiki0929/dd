package slim3.controller.tools.userManage.reserve;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.appengine.api.datastore.Key;
/**
 * カスタマーに予約できる日時を表示します。
 * @author uedadaiki
 *
 */
public class TimescheduleController extends Controller {

    @Override
    public Navigation run() throws Exception {
        
        //メニューのkey
        Key key = asKey("id");
        
        //TODO メニューの時間、最初に設定した営業時間を計算。予約可能な日時をスケジュールで表示する。
        
        
        
        //TODO 予約完了後、カスタマーとユーザーにメールで通知。
        
        
        
        return forward("index.jsp");
    }
}
