package cn.codekong.imageclassificationsystemclient.view;

import java.util.List;

import cn.codekong.imageclassificationsystemclient.bean.Task;

/**
 * Created by 尚振鸿 on 2017/6/13. 11:09
 * mail:szh@codekong.cn
 */

public interface IFinishedTaskFragmentView {
    //获取已完成任务列表成功
    void getFinishedTaskSuccess(List<Task> taskList);
    //加载更多完成任务列表成功
    void loadMoreUnfinishedTaskSuccess(List<Task> taskList);
    //获取完成任务信息失败
    void getFinishedTaskFailed(String msg);
    //鉴权失败(token存在问题)
    void validateError(String msg);
}
