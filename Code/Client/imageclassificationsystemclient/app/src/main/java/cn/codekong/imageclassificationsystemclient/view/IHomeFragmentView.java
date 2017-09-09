package cn.codekong.imageclassificationsystemclient.view;

import cn.codekong.imageclassificationsystemclient.bean.HomeTaskStatusDetail;

/**
 * Created by Administrator on 2017/5/26.
 */

public interface IHomeFragmentView {
    //设置打卡日期
    void setCheckinDateTv(String date);
    //打卡成功接口
    void checkinSuccess(String msg);
    //打卡失败接口
    void checkinFailed(String msg);
    //鉴权失败(token存在问题)
    void validateError(String msg);

    //获取首页任务状态详情成功
    void getHomeTaskStatusDetailSuccess(HomeTaskStatusDetail homeTaskStatusDetail);
    //获取首页任务状态详情失败
    void getHomeTaskStatusDetailFailed(String msg);
}
