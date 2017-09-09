package cn.codekong.imageclassificationsystemclient.service;

import java.util.List;

import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.bean.Rank;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 获取排行榜
 * Created by 尚振鸿 on 2017/6/12. 20:32
 * mail:szh@codekong.cn
 */

public interface RankService {

    @FormUrlEncoded
    @POST("rank/getranking")
    Call<HttpResult<List<Rank>>> getRanking(@Field(ApiConstant.OAUTH_TOKEN) String token,
                                            @Field(ApiConstant.RANK_IDENTIFICATION) String rankIdentification,
                                            @Field(ApiConstant.START) String start,
                                            @Field(ApiConstant.PAGE_NUM) String num);
}
