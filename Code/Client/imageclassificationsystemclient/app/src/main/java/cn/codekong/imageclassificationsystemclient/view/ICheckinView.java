package cn.codekong.imageclassificationsystemclient.view;

import java.util.List;

/**
 * Created by Administrator on 2017/5/27.
 */

public interface ICheckinView {
    //获取打卡信息成功
    void getCheckinDataSuccess(List<String> days);
    //获取打卡信息失败
    void getCheckinDataFailed(String msg);
    //鉴权失败(token存在问题)
    void validateError(String msg);
}
