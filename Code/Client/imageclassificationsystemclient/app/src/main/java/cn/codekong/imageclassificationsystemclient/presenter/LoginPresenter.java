package cn.codekong.imageclassificationsystemclient.presenter;

import android.content.Context;
import android.text.TextUtils;

import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.config.ApiConfig;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.net.RtHttp;
import cn.codekong.imageclassificationsystemclient.service.LoginService;
import cn.codekong.imageclassificationsystemclient.util.SaveDataUtil;
import cn.codekong.imageclassificationsystemclient.view.ILoginView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by szh on 2017/5/14.
 */

public class LoginPresenter implements ILoginPresenter{
    private ILoginView iLoginView;
    private Context mContext;

    public LoginPresenter(ILoginView loginView, Context context) {
        this.iLoginView = loginView;
        this.mContext = context;
    }

    /**
     * 注册功能
     * @param phoneNum
     * @param password
     */
    @Override
    public void login(String phoneNum, String password) {
        if (TextUtils.isEmpty(phoneNum)){
            iLoginView.validateError(mContext.getString(R.string.str_phone_num_cannot_empty));
            return;
        }
        if (TextUtils.isEmpty(password)){
            iLoginView.validateError(mContext.getString(R.string.str_password_cannot_empty));
            return;
        }

       //发送请求进行注册
        LoginService loginService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, LoginService.class);
        loginService.login(phoneNum, password)
                .enqueue(new Callback<HttpResult<String>>() {
                    @Override
                    public void onResponse(Call<HttpResult<String>> call, Response<HttpResult<String>> response) {
                        if (response.body() != null){
                            //请求成功
                            String code = response.body().getCode();
                            String msg = response.body().getMsg();
                            if (code.equals(Constant.REQUEST_SUCCESS)){
                                //登录成功,将token存储
                                String token = response.body().getData();
                                String oldToken = SaveDataUtil.getValueFromSharedPreferences(mContext,Constant.SHAREDPREFERENCES_DEFAULT_NAME,ApiConstant.OAUTH_TOKEN);
                                if (TextUtils.isEmpty(oldToken)){
                                    iLoginView.gotoChooseInterestedCategoryActivity(msg);
                                }else {
                                    iLoginView.loginSuccess(msg);
                                }
                                SaveDataUtil.saveToSharedPreferences(mContext, Constant.SHAREDPREFERENCES_DEFAULT_NAME,
                                        ApiConstant.OAUTH_TOKEN, token);
                            }else {
                                //登录失败
                                iLoginView.loginFailed(msg);
                            }
                        }else{
                            //服务器错误
                            iLoginView.loginFailed(mContext.getResources().getString(R.string.str_server_error));
                        }
                    }

                    @Override
                    public void onFailure(Call<HttpResult<String>> call, Throwable t) {
                        //网络错误
                        iLoginView.loginFailed(mContext.getResources().getString(R.string.str_network_error));
                    }
                });

    }
}

interface ILoginPresenter{
    void login(String phoneNum, String password);
}