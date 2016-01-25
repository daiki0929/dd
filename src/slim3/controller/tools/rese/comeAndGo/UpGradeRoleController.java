package slim3.controller.tools.rese.comeAndGo;

import org.slim3.controller.Navigation;

import slim3.controller.tools.rese.AbstractReseController;
/**
 * 有料会員にアップグレードする画面を表示します。
 * @author uedadaiki
 *
 */
public class UpGradeRoleController extends AbstractReseController {

    @Override
    public Navigation run() throws Exception {
        
        return forward("/tools/rese/dashboard/comeAndGo/upGrade.jsp");
    }
}
