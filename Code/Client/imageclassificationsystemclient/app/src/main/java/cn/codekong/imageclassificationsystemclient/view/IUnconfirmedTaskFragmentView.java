package cn.codekong.imageclassificationsystemclient.view;

import java.util.List;

import cn.codekong.imageclassificationsystemclient.bean.Task;

/**
 * Created by 尚振鸿 on 2017/6/13. 11:05
 * mail:szh@codekong.cn
 */

public interface IUnconfirmedTaskFragmentView {
    //获取未完成任务列表成功
    void getUncomfirmedTaskListSuccess(List<Task> taskList);
    //加载更多未完成任务列表成功
    void loadMoreUncomfirmedTaskSuccess(List<Task> taskList);
    //获取未完成任务信息失败
    void getUncomfirmedTaskFailed(String msg);
    //鉴权失败(token存在问题)
    void validateError(String msg);
}
