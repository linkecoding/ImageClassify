package cn.codekong.imageclassificationsystemclient.view;

import cn.codekong.imageclassificationsystemclient.bean.ImageTask;

/**
 * Created by szh on 2017/5/26.
 */

public interface ITagImageView {
    //获取图片任务成功
    void getImageTaskByAmountSuccess(ImageTask imageTask);
    //获取图片任务失败
    void getImageTaskByAmountFailed(String msg);
    //获取图片任务成功
    void getImageTaskByIdSuccess(ImageTask imageTask);
    //获取图片任务失败
    void getImageTaskByIdFailed(String msg);
    //设置图片任务成功
    void setImageTaskSuccess();
    //设置图片任务失败
    void setImageTaskFailed(String msg);
    //返回修改结果
    void backToModifyRes();
    //提交任务成功
    void submitImageTaskSuccess(String msg);
    //提交任务失败
    void submitImageTaskFailed(String msg);
    //鉴权失败(token存在问题)
    void validateError(String msg);
}
