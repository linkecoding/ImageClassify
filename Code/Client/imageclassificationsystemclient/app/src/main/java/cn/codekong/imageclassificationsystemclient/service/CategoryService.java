package cn.codekong.imageclassificationsystemclient.service;

import cn.codekong.imageclassificationsystemclient.bean.CategoryHobby;
import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by szh on 2017/5/24.
 */

public interface CategoryService {
    //获得所有的分类以及用户曾经选择的标签
    @FormUrlEncoded
    @POST("user/getcategoryhobbylist")
    Call<HttpResult<CategoryHobby>> getCategoryHobbyList(@Field(ApiConstant.OAUTH_TOKEN) String token);

    @FormUrlEncoded
    @POST("user/modifyhobby")
    Call<HttpResult<String>>modifyHobby(@Field(ApiConstant.OAUTH_TOKEN) String token, @Field(ApiConstant.CATEGORY_ID) String categoryId);
}
