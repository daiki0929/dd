package slim3.controller.tools.rese.page;

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
        return forward("/tools/rese/test.jsp");
    }
}
