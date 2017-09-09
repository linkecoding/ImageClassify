package cn.codekong.imageclassificationsystemclient.service;

import java.util.List;

import cn.codekong.imageclassificationsystemclient.bean.ContributeImg;
import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 尚振鸿 on 2017/8/21. 20:36
 * mail:szh@codekong.cn
 */

public interface ContributeImgService {
    @FormUrlEncoded
    @POST("user/getcontributeimglist")
    Call<HttpResult<List<ContributeImg>>> getContributeImgList(@Field(ApiConstant.OAUTH_TOKEN) String oauth_token,
                                                               @Field(ApiConstant.START) String start,
                                                               @Field(ApiConstant.PAGE_NUM) String page_num);

    @POST("user/contributeimg")
    Call<HttpResult<String>> contributeImg(@Body RequestBody body);
}
