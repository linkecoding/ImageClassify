package cn.codekong.imageclassificationsystemclient.presenter;


import android.content.Context;
import android.text.TextUtils;

import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.config.ApiConfig;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.net.RtHttp;
import cn.codekong.imageclassificationsystemclient.service.ModifyPasswordService;
import cn.codekong.imageclassificationsystemclient.util.SaveDataUtil;
import cn.codekong.imageclassificationsystemclient.view.IModifyPasswordView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/5/29.
 */

public class ModifyPasswordPresenter implements IModifyPasswordPresenter {

    private Context mContext;
    private IModifyPasswordView iModifyPasswordView;

    public ModifyPasswordPresenter(Context mContext, IModifyPasswordView modifyPasswordView) {
        this.mContext = mContext;
        this.iModifyPasswordView = modifyPasswordView;
    }

    /**
     * 检查新密码与确认密码是否一致
     *
     * @param newPassword     新密码
     * @param confirmPassword 确认密码
     * @return
     */
    @Override
    public boolean checkConfirmPassword(String newPassword, String confirmPassword) {
        if (newPassword.equals(confirmPassword))
            return true;
        else {
            iModifyPasswordView.confirmPasswordInconsistent("新密码与确认密码不一致");
            return false;
        }
    }

    /**
     * 网络请求修改密码
     *
     * @param oldPassword
     * @param newPassword
     */
    @Override
    public void modifyPassword(String oldPassword, String newPassword) {
        //获取token
        String token = SaveDataUtil.getValueFromSharedPreferences(mContext,
                Constant.SHAREDPREFERENCES_DEFAULT_NAME, ApiConstant.OAUTH_TOKEN);
        if (!TextUtils.isEmpty(token)) {
            ModifyPasswordService modifyPasswordService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, ModifyPasswordService.class);
            modifyPasswordService.modifyPwd(token, oldPassword, newPassword).enqueue(
                    new Callback<HttpResult<String>>() {
                        @Override
                        public void onResponse(Call<HttpResult<String>> call, Response<HttpResult<String>> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                HttpResult<String> result = response.body();
                                if (result.getCode().equals(Constant.REQUEST_CODE)) {
                                    iModifyPasswordView.modifyPasswordSuccess(result.getMsg());
                                } else if (response.body().getCode().equals(Constant.OAUTH_FAILED)) {
                                    //账户被冻结
                                    iModifyPasswordView.validateError(response.body().getMsg());
                                } else {
                                    iModifyPasswordView.modifyPasswordFailed(result.getMsg());
                                }
                            } else {
                                iModifyPasswordView.validateError(mContext.getResources().getString(R.string.str_server_error));
                            }

                        }

                        @Override
                        public void onFailure(Call<HttpResult<String>> call, Throwable t) {
                            //网络错误
                            iModifyPasswordView.modifyPasswordFailed(mContext.getString(R.string.str_network_error));
                        }
                    });
        } else {
            //token不存在,重新登录
            iModifyPasswordView.validateError(mContext.getResources().getString(R.string.str_login_status_error));
        }
    }
}

interface IModifyPasswordPresenter {
    boolean checkConfirmPassword(String newPassword, String confirmPassword);

    void modifyPassword(String oldPassword, String newPassword);
}