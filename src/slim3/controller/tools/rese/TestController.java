package slim3.controller.tools.rese;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.slim3.controller.Navigation;

import main.java.org.jsoup.Jsoup;
import main.java.org.jsoup.nodes.Document;
import slim3.controller.AbstractController;
import slim3.controller.tools.rese.AbstractReseController;
import slim3.exception.MyException;
import slim3.model.MsUser;
public class TestController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
//        String contents = null;
//        try {
//            File file = new File("/tools/rese/reserve/customer/messageFile.txt");
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String str;
//            while((str = br.readLine()) != null){
//                contents += str;
//            }
//            br.close();
//        } catch (Exception e) {
//            throw new MyException(e);
//        }
        
//        String customerContent = String.format("%s", contents);
        Document document = Jsoup.parse(new File("/tools/rese/reserve/messageFile.html"), "UTF-8");

        System.out.println(document.html());
        
//        System.out.println(contents);
        //admアカウントから送信します。
//        MsUser adm = msUserService.getSingleByEmail("reseinfomail@gmail.com");
        //カスタマーへのメール
//        googleService.sendMessage(adm, "0929dddd@gmail.com", null, "予約が確定しました", contents);
        
        return null;
//        return forward("/tools/rese/test.jsp");
    }
}
