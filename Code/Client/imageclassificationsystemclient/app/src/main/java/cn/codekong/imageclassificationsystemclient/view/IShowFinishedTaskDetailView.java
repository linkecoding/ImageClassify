package cn.codekong.imageclassificationsystemclient.view;

import java.util.List;

import cn.codekong.imageclassificationsystemclient.bean.FinishedTaskImageDetail;

/**
 * Created by Administrator on 2017/6/16.
 */

public interface IShowFinishedTaskDetailView {
    //获取已完成任务成功
    void getFinishedTaskDetailSuccess(List<FinishedTaskImageDetail> taskList);
    //获取已完成任务失败
    void getFinishedTaskDetailFailed(String msg);
    //鉴权失败(token存在问题)
    void validateError(String msg);
    //设置被系统接受的标签和未被系统接收的标签
    void setLabels(List<String> confirmLabels,List<String> unconfirmLabels);
}
