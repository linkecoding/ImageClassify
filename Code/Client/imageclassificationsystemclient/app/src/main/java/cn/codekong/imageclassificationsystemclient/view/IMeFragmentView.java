package cn.codekong.imageclassificationsystemclient.view;

import cn.codekong.imageclassificationsystemclient.bean.UserTaskAmount;

/**
 * Created by Administrator on 2017/5/28.
 */

public interface IMeFragmentView {
    //获取用户信息成功
    void getUserInfoSuccess(UserTaskAmount user);
    //获取用户信息失败
    void getUserInfoFailed(String msg);
    //鉴权失败(token存在问题)
    void validateError(String msg);
}
