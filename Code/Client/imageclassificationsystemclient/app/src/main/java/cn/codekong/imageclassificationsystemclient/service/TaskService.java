package cn.codekong.imageclassificationsystemclient.service;

import java.util.List;

import cn.codekong.imageclassificationsystemclient.bean.FinishedTaskImageDetail;
import cn.codekong.imageclassificationsystemclient.bean.HomeTaskStatusDetail;
import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.bean.Task;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 尚振鸿 on 2017/6/13. 11:28
 * mail:szh@codekong.cn
 */

public interface TaskService {

    //TODO 网络请求任务
    //获得已经被系统接受的任务(只可以查看不可以修改)
    @FormUrlEncoded
    @POST("task/getfinishedtask")
    Call<HttpResult<List<Task>>> getFinishedTask(@Field(ApiConstant.OAUTH_TOKEN) String token,
                                                 @Field(ApiConstant.START) String start,
                                                 @Field(ApiConstant.PAGE_NUM) String pageNum);

    //获得没有提交的任务(从数据库读取)
    @FormUrlEncoded
    @POST("task/getunfinishedtask")
    Call<HttpResult<List<Task>>> getUnfinishedTask(@Field(ApiConstant.OAUTH_TOKEN) String token,
                                                   @Field(ApiConstant.START) String start,
                                                   @Field(ApiConstant.PAGE_NUM) String pageNum);

    //获得没有被系统接受的任务
    @FormUrlEncoded
    @POST("task/getunconfirmedtask")
    Call<HttpResult<List<Task>>> getUnconfirmedTask(@Field(ApiConstant.OAUTH_TOKEN) String token,
                                                    @Field(ApiConstant.START) String start,
                                                    @Field(ApiConstant.PAGE_NUM) String pageNum);

    //获取已经被系统接收的任务详情
    @FormUrlEncoded
    @POST("task/getfinishedtaskdetail")
    Call<HttpResult<List<FinishedTaskImageDetail>>> getFinishedTaskDetail(@Field(ApiConstant.OAUTH_TOKEN) String token,
                                                                          @Field(ApiConstant.TASK_ID) String taskId);

    //获取首页显示的任务状态详情
    @FormUrlEncoded
    @POST("task/gethometaskstatusdetail")
    Call<HttpResult<HomeTaskStatusDetail>> getHomeTaskStatusDetail(@Field(ApiConstant.OAUTH_TOKEN) String token);
}
