package cn.codekong.imageclassificationsystemclient.view;

/**
 * Created by szh on 2017/5/8.
 * 注册接口
 */

public interface IRegisterView {
    //注册成功接口
    void registerSuccess(String msg);
    //注册失败接口
    void registerFailed(String msg);
    //合法性检验错误
    void validateError(String msg);
}
