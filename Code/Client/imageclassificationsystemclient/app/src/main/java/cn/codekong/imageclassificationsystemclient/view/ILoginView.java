package cn.codekong.imageclassificationsystemclient.view;

/**
 * Created by szh on 2017/5/8.
 * 注册接口
 */

public interface ILoginView {
    //注册成功接口
    void loginSuccess(String msg);
    //注册失败接口
    void loginFailed(String msg);
    //合法性检验错误
    void validateError(String msg);
    //跳转到选择兴趣界面
    void gotoChooseInterestedCategoryActivity(String msg);
}
