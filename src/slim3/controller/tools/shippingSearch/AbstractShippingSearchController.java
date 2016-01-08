package slim3.controller.tools.shippingSearch;

import slim3.controller.AbstractController;
import slim3.service.tools.ShippingSearch.SagawaService;
import slim3.service.tools.ShippingSearch.SeinoService;
import slim3.service.tools.ShippingSearch.YamatoService;
/**
 * ShippingSearchの親コントローラ
 * @author uedadaiki
 *
 */
public abstract class AbstractShippingSearchController extends AbstractController {
    
    //=================================================================
    //サービスクラス
    
    SagawaService sagawaService = new SagawaService();
    YamatoService yamatoService = new YamatoService();
    SeinoService seinoService = new SeinoService();

}
