package slim3.service.datastore;

import java.util.List;

import org.slim3.datastore.Datastore;

import slim3.controller.dto.ManageUserDto;
import slim3.meta.reserve.ManageUserMeta;
import slim3.model.reserve.ManageUser;

public class ManageUserService {
    
    private final static ManageUserMeta MANAGE_USER_META = ManageUserMeta.get();
    
    /**
     * 重複しているかをチェックします。
     * @param mailaddress 登録するメールアドレス
     * @param manageUser 
     * @return
     */
    public boolean duplicateMailAddress(String mailaddress, ManageUserDto manageUserDto){
        //登録済みチェック
        List<ManageUser> dupulicateUserList = Datastore
            .query(MANAGE_USER_META)
            .filter(MANAGE_USER_META.mailaddress.equal(mailaddress))
            .asList();
        for (ManageUser t : dupulicateUserList) {
            if (!t.getKey().equals(manageUserDto.getManageUserId())) {
                return true;
            }
        }
        return false;
    }
}