package cn.codekong.imageclassificationsystemclient.view;

/**
 * Created by Administrator on 2017/5/29.
 */

public interface IModifyPasswordView {
    //旧密码输入错误
    void oldPasswordInconsistent(String msg);
    //新密码与确认密码不一致
    void confirmPasswordInconsistent(String msg);
    //修改密码失败
    void modifyPasswordFailed(String msg);
    //修改密码成功
    void modifyPasswordSuccess(String msg);
    //鉴权失败(token存在问题)
    void validateError(String msg);
}
