package slim3.controller.tools.ShippingSearch;

import org.slim3.controller.Navigation;

import main.java.org.jsoup.Connection;
import main.java.org.jsoup.nodes.Document;
import main.java.org.jsoup.nodes.Element;
import main.java.org.jsoup.select.Elements;
import slim3.controller.AbstractController;
import slim3.service.tools.ShippingSearch.SagawaService;
public class SearchSituationController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        
        String inquiryNO = asString("inquiryNO");
        
        //佐川に接続します。
        SagawaService sagawaService = new SagawaService();
        Connection connection = sagawaService.createConnection("4023-0989-2983");
        
        Document document = connection.post();
        Elements elements = document.select("#MainList");
        for (Element element : elements) {
            String searchNum = element.select(".nowrap strong").text();
            log.info("問い合わせNO：" + searchNum);
        }
        
        
        return null;
    }
}
