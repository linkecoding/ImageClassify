package cn.codekong.imageclassificationsystemclient.view;

/**
 * Created by szh on 2017/5/23.
 * 重置密码接口
 */

public interface IResetPasswordView {
    //重置密码成功接口
    void resetSuccess(String msg);
    //重置密码失败接口
    void resetFailed(String msg);
    //合法性检验错误
    void validateError(String msg);
}
