package cn.codekong.imageclassificationsystemclient.service;

import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.bean.ImageTask;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by szh on 2017/5/26.
 * 图片任务service
 */

public interface ImageTaskService {
    //通过图片数量获取任务
    @FormUrlEncoded
    @POST("task/gettask")
    Call<HttpResult<ImageTask>> getImageTaskByAmount(@Field(ApiConstant.OAUTH_TOKEN) String oauth_token,
                                                     @Field(ApiConstant.TASK_IMG_AMOUNT) String taskImgAmount);
    //通过任务id恢复任务
    @FormUrlEncoded
    @POST("task/gettaskbyid")
    Call<HttpResult<ImageTask>> getImageTaskById(@Field(ApiConstant.OAUTH_TOKEN) String oauth_token,
                                                 @Field(ApiConstant.TASK_ID) String taskId);
    //提交任务
    @FormUrlEncoded
    @POST("task/committask")
    Call<HttpResult<String>> commitTask(@Field(ApiConstant.OAUTH_TOKEN) String oauth_token,
                                        @Field(ApiConstant.TASK) String task,
                                        @Field(ApiConstant.IS_COMMIT) String isCommit);
}
