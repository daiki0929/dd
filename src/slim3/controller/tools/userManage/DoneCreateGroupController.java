package slim3.controller.tools.userManage;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import slim3.controller.AbstractController;
import slim3.model.reserve.GroupManageUser;

/**
 * グループ作成完了のコントローラ
 * @author uedadaiki
 *
 */
public class DoneCreateGroupController extends AbstractController {
    
    @Override
    public Navigation run() throws Exception {
        
        Validators v = new Validators(request);
        validate(v, "groupName", 1, 50, true, null, null);
        
        if (errors.size() != 0) {
            log.info("エラー");
            return forward("customerList.jsp");
        }

        
        String groupName = asString("gourpName");
        
        //TODO 同一のメールアドレスが無いかチェック(今はメソッドをstaticにしている)
//        boolean duplicate = ManageUserService.duplicateMailAddress(mailaddress, manageUserDto);
//        if (duplicate) {
//            errors.put("duplicate", "グループ名が重複しています。");
//            return forward("customerList.jsp");
//        }
        
        GroupManageUser groupManageUser = new GroupManageUser(); 
        groupManageUser.setGroupName(groupName);
        //保存
        //TODO キャッシュに残ってたら、そこから保存するようにする。
        ///web17/src/slim3/jackpot/controller/adm/delivery/check/DoneController.java　L232
        Datastore.put(groupManageUser);
        log.info("保存しました。");
        
        return redirect("/tools/userManage/CustomerList");
    }
}
