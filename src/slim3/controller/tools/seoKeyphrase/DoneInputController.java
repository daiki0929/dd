package slim3.controller.tools.seoKeyphrase;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;

import com.google.api.client.util.ArrayMap;

import main.java.org.jsoup.Connection;
import main.java.org.jsoup.Jsoup;
import main.java.org.jsoup.nodes.Document;
import main.java.org.jsoup.nodes.Element;
import main.java.org.jsoup.select.Elements;
import slim3.controller.AbstractController;
import slim3.exception.MyException;
import slim3.service.tools.splitText.YahooKeyphraseService;

public class DoneInputController extends AbstractController {
    
    protected static final String DEFAULT_ENCODING = "UTF-8";

    @Override
    public Navigation run() throws Exception {
        log.info("move");
        String sentence = asString("sentence");
        String[] sentenceList = sentence.split("\n");
        
        //キーフレーズとスコアのMapです。
//        ArrayMap<String, String> keyphraseMap = new ArrayMap<String, String>();
        ArrayList<String[]> resultList = new ArrayList<String[]>();
        
        YahooKeyphraseService yahooKeyphraseService = new YahooKeyphraseService();
        //リクエストURLを生成します。
        List<String> requestUrlList = yahooKeyphraseService.getKeyphrase(sentenceList);
        try {
            for (String requestUrl : requestUrlList) {
                Connection connect = Jsoup.connect(requestUrl);
                Document document = connect.get();
                
                Elements resultElem = document.select("result");
                for (Element elem : resultElem) {
                    String keyphrase = elem.select("keyphrase").text();
                    String score = elem.select("score").text();
                    
                    String[] result = new String[2];
                    result[0] = keyphrase;
                    result[1] = score;
                    
                    resultList.add(result);
                }
                
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            throw new MyException(e);
        }
        log.info(resultList.toString());
        request.setAttribute("resultList", resultList);
        
        
        return forward("/tools/seoKeyphrase/result.jsp");
    }
}
