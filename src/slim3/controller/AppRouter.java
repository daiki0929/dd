    package slim3.controller;

import org.slim3.controller.router.RouterImpl;

/**
 * リクエストのルーティング設定
 * webサーバの再起動が必要です。
 * @author uedadaiki
 *
 */
public class AppRouter extends RouterImpl {

    public AppRouter() {
        //==============================================
        //予約ページ
        addRouting("/r/{userPath}/{pagePath}", "/tools/rese/reserve/customer/selectMenu?userPath={userPath}&pagePath={pagePath}");
        addRouting("/r/reserve/{userPath}/{pagePath}", "/tools/rese/reserve/customer/timeschedule?userPath={userPath}&pagePath={pagePath}");
        addRouting("/confirm", "/tools/rese/reserve/customer/confirmReserve");
        addRouting("/finish", "/tools/rese/reserve/customer/finish");
        addRouting("/reserve/cancel/{menuPageKey}/{reserveKey}", "tools/rese/reserve/customer/cancel?menuPageKey={menuPageKey}&reserveKey={reserveKey}");
        
        
        
    }

}
