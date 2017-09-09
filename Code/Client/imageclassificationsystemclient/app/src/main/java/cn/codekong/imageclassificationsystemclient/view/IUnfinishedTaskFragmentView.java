package cn.codekong.imageclassificationsystemclient.view;


import java.util.List;

import cn.codekong.imageclassificationsystemclient.bean.Task;


public interface IUnfinishedTaskFragmentView {
    //获取未完成任务列表成功
    void getUnfinishedTaskListSuccess(List<Task> taskList);
    //加载更多未完成任务列表成功
    void loadMoreUnfinishedTaskSuccess(List<Task> taskList);
    //获取未完成任务信息失败
    void getUnfinishedTaskFailed(String msg);
    //鉴权失败(token存在问题)
    void validateError(String msg);
}
