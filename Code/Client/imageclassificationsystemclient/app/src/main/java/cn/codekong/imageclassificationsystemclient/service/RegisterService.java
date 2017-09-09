package cn.codekong.imageclassificationsystemclient.service;

import java.util.Map;

import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.bean.SMSVerifyResponse;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * Created by szh on 2017/5/13.
 */

public interface RegisterService {
    @FormUrlEncoded
    @POST("user/checkphoneisused")
    Call<HttpResult<String>> checkPhoneIsUsed(@Field(ApiConstant.TEL_NUM) String telNum);

    //TODO 短信验证码
    @FormUrlEncoded
    @POST("sendcode.action")
    Call<SMSVerifyResponse> getVerifyCode(@HeaderMap Map<String, String> headers,
                                          @Field(ApiConstant.MOBILE) String mobile,
                                          @Field(ApiConstant.TEMPLATE_ID) String templateId);

    @FormUrlEncoded
    @POST("verifycode.action")
    Call<ResponseBody> checkVerifyCode(@HeaderMap Map<String, String> headers,
                                       @Field(ApiConstant.MOBILE) String mobile,
                                       @Field(ApiConstant.CODE) String code);

    @FormUrlEncoded
    @POST("user/register")
    Call<HttpResult<String>> register(@Field(ApiConstant.USERNAME) String username,
                                      @Field(ApiConstant.TEL_NUM) String telNum,
                                      @Field(ApiConstant.PASSWORD) String password);
}
