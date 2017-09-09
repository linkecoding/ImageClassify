package cn.codekong.dao;

import java.util.List;

import cn.codekong.bean.Admin;
import cn.codekong.bean.User;
import cn.codekong.bean.label.ExportLabel;

public interface AdminDao {
        Admin findAdminByNameAndPaw(String admin_name,String admin_password);
        boolean update(Admin admin); 
        Admin findAdminByToken(String admin_token);
        List<User> getAllUsers(int start,int num);
        int getAllUsers();
        int updateUserByAdmin(User user);
        List<ExportLabel> getExportImage();
        List<ExportLabel> getExportHistoryFile(int export_id);
}
