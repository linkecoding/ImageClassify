package cn.codekong.imageclassificationsystemclient.service;

import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/5/28.
 */

public interface CheckinService {
    @FormUrlEncoded
    @POST("user/getcheckinlist")
    Call<HttpResult<String>> getCheckInList(@Field(ApiConstant.OAUTH_TOKEN) String oauth_token,
                                            @Field(ApiConstant.YEAR) String year,
                                            @Field(ApiConstant.MONTH) String month);
    @FormUrlEncoded
    @POST("user/checkin")
    Call<HttpResult<String>> checkin(@Field(ApiConstant.OAUTH_TOKEN) String oauth_token);
}
