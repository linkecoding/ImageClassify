package cn.codekong.imageclassificationsystemclient.service;

import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by szh on 2017/5/14.
 */

public interface LoginService {

    @FormUrlEncoded
    @POST("user/login")
    Call<HttpResult<String>> login(@Field(ApiConstant.TEL_NUM) String telNum,
                                   @Field(ApiConstant.PASSWORD) String password);
}
