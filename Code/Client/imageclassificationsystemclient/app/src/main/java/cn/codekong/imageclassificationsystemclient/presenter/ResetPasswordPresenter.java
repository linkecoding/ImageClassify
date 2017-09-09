package cn.codekong.imageclassificationsystemclient.presenter;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.bean.SMSVerifyResponse;
import cn.codekong.imageclassificationsystemclient.config.ApiConfig;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.net.RtHttp;
import cn.codekong.imageclassificationsystemclient.service.ResetPasswordService;
import cn.codekong.imageclassificationsystemclient.util.CheckSumBuilder;
import cn.codekong.imageclassificationsystemclient.view.IResetPasswordView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by szh on 2017/5/23.
 */

public class ResetPasswordPresenter implements IResetPasswordPresenter{
    private static final String TAG = "ResetPasswordPresenter";
    private IResetPasswordView iResetPasswordView;
    private Context mContext;

    public ResetPasswordPresenter(IResetPasswordView iResetPasswordView, Context context){
        this.iResetPasswordView = iResetPasswordView;
        this.mContext = context;
    }

    /**
     * 获取手机验证码
     * @param phoneNum
     * @param getCodeTv
     * @param timer
     */
    @Override
    public void getVerificationCode(final String phoneNum, final TextView getCodeTv, final CountDownTimer timer) {
        if (TextUtils.isEmpty(phoneNum)) {
            iResetPasswordView.validateError(mContext.getString(R.string.str_phone_num_cannot_empty));
        } else {
            //验证手机号是否存在,存在才可重置密码
            final ResetPasswordService checkPhoneService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, ResetPasswordService.class);
            checkPhoneService.checkPhoneIsUsed(phoneNum).enqueue(new Callback<HttpResult<String>>() {
                @Override
                public void onResponse(Call<HttpResult<String>> call, Response<HttpResult<String>> response) {
                    if (response.isSuccessful() && response.body() != null){
                        String code = response.body().getCode();
                        String msg = response.body().getMsg();
                        if (code.equals(Constant.REQUEST_SUCCESS)) {
                            if (msg.equals(Constant.SOMETHING_EXIST)) {
                                //创建网络请求验证码的实例
                                final ResetPasswordService verifySMSService = RtHttp.getHttpService(ApiConfig.SMS_VERIFY_BASE_URL, ResetPasswordService.class);
                                //手机号存在,开始发送验证码
                                verifySMSService.getVerifyCode(CheckSumBuilder.getRequestHeaders(), phoneNum, ApiConfig.SMS_VERIFY_TEMPLATE_ID).enqueue(new Callback<SMSVerifyResponse>() {
                                    @Override
                                    public void onResponse(Call<SMSVerifyResponse> call, Response<SMSVerifyResponse> response) {
                                        iResetPasswordView.validateError(mContext.getString(R.string.str_verify_code_send_success));
                                        getCodeTv.setClickable(false);
                                        timer.start();
                                    }

                                    @Override
                                    public void onFailure(Call<SMSVerifyResponse> call, Throwable t) {
                                        iResetPasswordView.validateError(mContext.getString(R.string.str_network_error));
                                    }
                                });

                            }else if (msg.equals(Constant.REQUEST_ERROR)){
                                //手机号不存在
                                iResetPasswordView.validateError(mContext.getString(R.string.str_phone_num_isnot_exist));
                            }else{
                                iResetPasswordView.validateError(mContext.getString(R.string.str_network_error));
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<HttpResult<String>> call, Throwable t) {
                    iResetPasswordView.validateError(mContext.getString(R.string.str_network_error));
                }
            });
        }
    }

    /**
     * 重置密码
     * @param phoneNum
     * @param password
     * @param code
     */
    @Override
    public void resetPassword(String phoneNum, String password, String code) {
        if (TextUtils.isEmpty(phoneNum)){
            iResetPasswordView.validateError(mContext.getString(R.string.str_phone_num_cannot_empty));
            return;
        }

        if (TextUtils.isEmpty(code)){
            iResetPasswordView.validateError(mContext.getString(R.string.str_verify_code_cannot_empty));
            return;
        }

        if (TextUtils.isEmpty(password)){
            iResetPasswordView.validateError(mContext.getString(R.string.str_password_cannot_empty));
            return;
        }
        //发送请求进行重置密码
        //首先检查验证码是否正确
        checkCodeAndResetPassword(phoneNum, code, password);

    }


    /**
     * 检查验证码是否正确,正确则重置
     * @param phoneNum
     * @param code
     */
    private void checkCodeAndResetPassword(final String phoneNum, String code, final String password){
        ResetPasswordService checkCodeService = RtHttp.getHttpService(ApiConfig.SMS_VERIFY_BASE_URL, ResetPasswordService.class);
        checkCodeService.checkVerifyCode(CheckSumBuilder.getRequestHeaders(), phoneNum, code)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String code = "";
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            code = jsonObject.get(Constant.REQUEST_CODE).toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (code.equals(Constant.REQUEST_SUCCESS)){
                            //验证码验证成功,开始提交注册信息
                            final ResetPasswordService resetService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, ResetPasswordService.class);
                            resetService.resetPassword(phoneNum, password)
                                    .enqueue(new Callback<HttpResult<String>>() {
                                        @Override
                                        public void onResponse(Call<HttpResult<String>> call, Response<HttpResult<String>> response) {
                                            String code = response.body().getCode();
                                            String msg = response.body().getMsg();
                                            if (code.equals(Constant.REQUEST_SUCCESS)){
                                                //重置成功
                                                iResetPasswordView.resetSuccess(msg);
                                            }else{
                                                //重置失败
                                                iResetPasswordView.resetFailed(msg);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<HttpResult<String>> call, Throwable t) {
                                            iResetPasswordView.validateError(mContext.getString(R.string.str_network_error));
                                        }
                                    });

                        }else{
                            Log.d(TAG, "onResponse: code" + code);
                            //验证失败
                            iResetPasswordView.validateError(mContext.getString(R.string.str_verify_is_wrong));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        iResetPasswordView.validateError(mContext.getString(R.string.str_network_error));
                    }
                });
    }
}

interface IResetPasswordPresenter{
    void getVerificationCode(final String phoneNum, final TextView getCodeTv, final CountDownTimer timer);
    void resetPassword(String phoneNum, String password, String code);
}