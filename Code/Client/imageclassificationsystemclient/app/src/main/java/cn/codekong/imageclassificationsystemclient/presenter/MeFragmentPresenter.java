package cn.codekong.imageclassificationsystemclient.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.bean.User;
import cn.codekong.imageclassificationsystemclient.bean.UserTaskAmount;
import cn.codekong.imageclassificationsystemclient.config.ApiConfig;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.net.RtHttp;
import cn.codekong.imageclassificationsystemclient.service.UserInfoService;
import cn.codekong.imageclassificationsystemclient.util.SaveDataUtil;
import cn.codekong.imageclassificationsystemclient.view.IMeFragmentView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/5/28.
 */

public class MeFragmentPresenter implements IMeFragmentPresenter {

    private Context mContext;
    private IMeFragmentView iMeFragmentView;

    public MeFragmentPresenter(Context mContext, IMeFragmentView meFragmentView) {
        this.mContext = mContext;
        this.iMeFragmentView = meFragmentView;
    }

    /**
     * 网络请求获取用户信息
     */
    @Override
    public void getUserInfo() {
        String token = SaveDataUtil.getValueFromSharedPreferences(mContext, Constant.SHAREDPREFERENCES_DEFAULT_NAME, ApiConstant.OAUTH_TOKEN);
        if(!TextUtils.isEmpty(token)){
            UserInfoService userInfoService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL,UserInfoService.class);
            userInfoService.getUserInfo(token).enqueue(new Callback<HttpResult<UserTaskAmount>>() {
                @Override
                public void onResponse(Call<HttpResult<UserTaskAmount>> call, Response<HttpResult<UserTaskAmount>> response) {
                    if (response.isSuccessful() && response.body() != null){
                        HttpResult<UserTaskAmount> body = response.body();
                        if (body.getCode().equals(Constant.REQUEST_SUCCESS)) {
                            UserTaskAmount userTaskAmount = body.getData();
                            iMeFragmentView.getUserInfoSuccess(userTaskAmount);
                        }else if (response.body().getCode().equals(Constant.OAUTH_FAILED)) {
                            //账户被冻结
                            iMeFragmentView.validateError(response.body().getMsg());
                        } else {
                            iMeFragmentView.getUserInfoFailed(body.getMsg());
                        }
                    }else{
                        //服务器错误
                        iMeFragmentView.getUserInfoFailed(mContext.getResources().getString(R.string.str_server_error));
                    }

                }

                @Override
                public void onFailure(Call<HttpResult<UserTaskAmount>> call, Throwable t) {
                    Log.d("Content", "onFailure: " + t.toString());
                    iMeFragmentView.getUserInfoFailed(mContext.getResources().getString(R.string.str_network_error));
                }
            });
        }else{
            //token失效
            iMeFragmentView.validateError(mContext.getResources().getString(R.string.str_login_status_error));
        }
    }
}

interface IMeFragmentPresenter{
    //获取用户信息
    void getUserInfo();
}