package slim3.controller.tools;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;
public class TestController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        return forward("/tools/rese/test.jsp");
    }
}
