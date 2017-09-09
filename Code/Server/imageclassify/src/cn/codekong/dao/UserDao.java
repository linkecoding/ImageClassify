package cn.codekong.dao;

import java.util.List; 
import cn.codekong.bean.User;

public interface UserDao{
	User findUserByTelnum(String tel_num);
	boolean save(User user); 
    User findUserByPawTel(String password,String tel_num); 
    User findUserById(int user_id); 
    boolean delete(User user);
    boolean update(User user);
    boolean updateAccuracy(User user);
    String getAvgAccuracy(int user_id); 
    List<User> getAllUser(); 
    String findAvatarUrl(int user_id);
    int updateUserFrozen(int user_id,int is_frozen);
    List<User> getUsersOfFrozenOrUnfrozen(int is_frozen,int start,int num); 
    long getUsersNumOfFrozenOrUnfrozen(int is_frozen);
}
