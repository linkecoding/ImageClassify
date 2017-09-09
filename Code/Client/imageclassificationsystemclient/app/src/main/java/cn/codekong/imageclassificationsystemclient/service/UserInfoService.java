package cn.codekong.imageclassificationsystemclient.service;

import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.bean.UserTaskAmount;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/6/1.
 */

public interface UserInfoService {

//    @FormUrlEncoded
    @POST("user/modifyinfo")
    Call<HttpResult<String>> modifyUserInfo(@Body RequestBody body);

    @FormUrlEncoded
    @POST("user/getinfo")
    Call<HttpResult<UserTaskAmount>> getUserInfo(@Field(ApiConstant.OAUTH_TOKEN) String token);
}
