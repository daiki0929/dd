package slim3.service.tools.splitText;

import java.util.ArrayList;
import java.util.List;

/**
 * ヤフーのキーフレーズ抽出APIを使用するサービスクラスです。
 * @author uedadaiki
 *
 */
public class YahooKeyphraseService {
    
    private final static String APP_ID = "dj0zaiZpPXVlZ2JEZTFaYTBSTSZzPWNvbnN1bWVyc2VjcmV0Jng9MTU-";
    /**
     * キーフレーズを取得するURLをリストで返却します。
     * @param sentenceList
     * @return
     */
    public List<String> getKeyphrase(String[] sentenceList){
        ArrayList<String> requestUrlList = new ArrayList<String>();
        for (String sentence : sentenceList) {
            //アプリケーションID、解析対象テキスト(コールバック無しにしています。)
            String url = String.format("http://jlp.yahooapis.jp/KeyphraseService/V1/extract?appid=%s&sentence=%s", APP_ID, sentence);
            String replace = url.replace("|", "").replace(" ", "").replace("　", "");
            requestUrlList.add(replace);
        }
        
        return requestUrlList;
        
    }
}