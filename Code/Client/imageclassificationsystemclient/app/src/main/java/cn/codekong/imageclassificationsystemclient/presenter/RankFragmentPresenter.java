package cn.codekong.imageclassificationsystemclient.presenter;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.bean.Rank;
import cn.codekong.imageclassificationsystemclient.config.ApiConfig;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.net.RtHttp;
import cn.codekong.imageclassificationsystemclient.service.RankService;
import cn.codekong.imageclassificationsystemclient.util.SaveDataUtil;
import cn.codekong.imageclassificationsystemclient.view.IRankFragmentView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 尚振鸿 on 2017/6/12. 19:22
 * mail:szh@codekong.cn
 */

public class RankFragmentPresenter implements IRankFragmentPresenter {
    private Context mContext;
    private IRankFragmentView iRankFragmentView;

    public RankFragmentPresenter(Context context, IRankFragmentView iRankFragmentView) {
        this.mContext = context;
        this.iRankFragmentView = iRankFragmentView;

    }

    @Override
    public void getRanking(String identification, final String start, String pageNum) {
        //获取token
        String token = SaveDataUtil.getValueFromSharedPreferences(mContext, Constant.SHAREDPREFERENCES_DEFAULT_NAME, ApiConstant.OAUTH_TOKEN);
        if (!TextUtils.isEmpty(token)) {
            RankService rankService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, RankService.class);
            rankService.getRanking(token, identification, start, pageNum).enqueue(new Callback<HttpResult<List<Rank>>>() {
                @Override
                public void onResponse(Call<HttpResult<List<Rank>>> call, Response<HttpResult<List<Rank>>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String code = response.body().getCode();
                        if (code.equals(Constant.REQUEST_SUCCESS)) {
                            List<Rank> rankList = response.body().getData();
                            //=1为首次获取数据
                            if (start.equals("1")) {
                                iRankFragmentView.getRankingSuccess(rankList);
                            } else {
                                iRankFragmentView.loadMoreRankingSuccess(rankList);
                            }
                        } else if (response.body().getCode().equals(Constant.OAUTH_FAILED)) {
                            //账户被冻结
                            iRankFragmentView.validateError(response.body().getMsg());
                        } else {
                            //获取失败
                            String msg = response.body().getMsg();
                            iRankFragmentView.getRankingFailed(msg);
                        }
                    } else {
                        iRankFragmentView.getRankingFailed(mContext.getResources().getString(R.string.str_server_error));
                    }
                }

                @Override
                public void onFailure(Call<HttpResult<List<Rank>>> call, Throwable t) {
                    iRankFragmentView.getRankingFailed(mContext.getResources().getString(R.string.str_network_error));
                    //moniData(start);
                }
            });
        } else {
            iRankFragmentView.validateError(mContext.getResources().getString(R.string.str_login_status_error));
        }
    }


    //TODO 模拟数据
    //模拟数据
    private void moniData(String start) {
        List<Rank> rankList = new ArrayList<>();
        Rank rank = new Rank();
        rank.setAvatar_url("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        rank.setUsername("小红");
        rank.setIntegral("1200");
        for (int i = 0; i < 10; i++) {
            rankList.add(rank);
        }
        if (start.equals("1")) {
            iRankFragmentView.getRankingSuccess(rankList);
        } else {
            iRankFragmentView.loadMoreRankingSuccess(rankList);
        }
    }
}

interface IRankFragmentPresenter {
    //获得排行榜信息
    void getRanking(String identification, String start, String num);
}