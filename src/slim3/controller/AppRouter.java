    package slim3.controller;

import org.slim3.controller.router.RouterImpl;

/**
 * リクエストのルーティング設定
 * webサーバの再起動が必要です。
 * @author kitazawa.takuya
 *
 */
public class AppRouter extends RouterImpl {

    public AppRouter() {
        
        //Rese
        addRouting("/", "/tools/rese");
        //予約ページ
        addRouting("/{userPath}/{pagePath}", "/tools/rese/reserve/customer/selectMenu?userPath={userPath}&pagePath={pagePath}");
    }

}
