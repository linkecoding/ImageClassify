package cn.codekong.imageclassificationsystemclient.service;

import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/5/30.
 */

public interface ModifyPasswordService {

    @FormUrlEncoded
    @POST("user/modifypwd")
    Call<HttpResult<String>> modifyPwd(@Field(ApiConstant.OAUTH_TOKEN) String token,
                                       @Field(ApiConstant.OLD_PWD) String oldPwd,
                                       @Field(ApiConstant.NEW_PWD) String newPwd);
}
