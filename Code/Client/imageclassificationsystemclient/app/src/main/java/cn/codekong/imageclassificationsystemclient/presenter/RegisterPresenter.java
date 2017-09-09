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
import cn.codekong.imageclassificationsystemclient.service.RegisterService;
import cn.codekong.imageclassificationsystemclient.util.CheckSumBuilder;
import cn.codekong.imageclassificationsystemclient.view.IRegisterView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by szh on 2017/5/8.
 */

public class RegisterPresenter implements IRegisterPresenter{
    private static final String TAG = "xxhh";
    private IRegisterView iRegisterView;
    private Context mContext;

    public RegisterPresenter(IRegisterView iRegisterView, Context context){
        this.iRegisterView = iRegisterView;
        this.mContext = context;
    }

    /**
     * 获取验证码(先验证手机号是否已经被注册)
     * @param phoneNum   手机号
     * @param getCodeTv  获得验证码的按钮
     * @param timer      验证码计时器
     * @return
     */
    @Override
    public void getVerificationCode(final String phoneNum, final TextView getCodeTv, final CountDownTimer timer) {
        if (TextUtils.isEmpty(phoneNum)) {
            iRegisterView.validateError(mContext.getString(R.string.str_phone_num_cannot_empty));
        } else {
            //验证手机号是否被注册
            final RegisterService checkPhoneService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, RegisterService.class);
            checkPhoneService.checkPhoneIsUsed(phoneNum).enqueue(new Callback<HttpResult<String>>() {
                @Override
                public void onResponse(Call<HttpResult<String>> call, Response<HttpResult<String>> response) {
                    if (response.isSuccessful() && response.body() != null){
                        String code = response.body().getCode();
                        String msg = response.body().getMsg();
                        if (code.equals(Constant.REQUEST_SUCCESS)) {
                            if (msg.equals(Constant.SOMETHING_DONOT_EXIST)) {
                                //创建网络请求验证码的实例
                                final RegisterService verifySMSService = RtHttp.getHttpService(ApiConfig.SMS_VERIFY_BASE_URL, RegisterService.class);
                                //手机号不存在,开始发送验证码
                                verifySMSService.getVerifyCode(CheckSumBuilder.getRequestHeaders(), phoneNum, ApiConfig.SMS_VERIFY_TEMPLATE_ID)
                                        .enqueue(new Callback<SMSVerifyResponse>() {
                                    @Override
                                    public void onResponse(Call<SMSVerifyResponse> call, Response<SMSVerifyResponse> response) {
                                        if (response.isSuccessful()){
                                            iRegisterView.validateError(mContext.getString(R.string.str_verify_code_send_success));
                                            getCodeTv.setClickable(false);
                                            timer.start();
                                        }else{
                                            //服务器错误
                                            iRegisterView.validateError(mContext.getResources().getString(R.string.str_server_error));
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<SMSVerifyResponse> call, Throwable t) {
                                        Log.d(TAG, "onFailure2: " + t.toString());
                                        iRegisterView.validateError(mContext.getString(R.string.str_network_error));
                                    }
                                });
                            } else{
                                //手机号存在
                                iRegisterView.validateError(mContext.getString(R.string.str_phone_num_isused));
                            }
                        }
                    }else{
                        //服务器错误
                        iRegisterView.validateError(mContext.getString(R.string.str_server_error));
                    }
                }

                @Override
                public void onFailure(Call<HttpResult<String>> call, Throwable t) {
                    iRegisterView.validateError(mContext.getString(R.string.str_network_error));
                }
            });
        }
    }

    /**
     * 注册功能
     * @param username
     * @param phoneNum
     * @param password
     * @param code  验证码
     */
    @Override
    public void register(String username, String phoneNum, String password, String code){
        if (TextUtils.isEmpty(username)){
            iRegisterView.validateError(mContext.getString(R.string.str_username_cannot_empty));
            return;
        }
        if (TextUtils.isEmpty(phoneNum)){
            iRegisterView.validateError(mContext.getString(R.string.str_phone_num_cannot_empty));
            return;
        }

        if (TextUtils.isEmpty(code)){
            iRegisterView.validateError(mContext.getString(R.string.str_verify_code_cannot_empty));
            return;
        }

        if (TextUtils.isEmpty(password)){
            iRegisterView.validateError(mContext.getString(R.string.str_password_cannot_empty));
            return;
        }
        //发送请求进行注册
        //首先检查验证码是否正确
        checkCodeAndRegister(phoneNum, code, username, password);
    }

    /**
     * 检查验证码是否正确,正确则注册
     * @param phoneNum
     * @param code
     */
    private void checkCodeAndRegister(final String phoneNum, String code, final String username, final String password){
        RegisterService checkCodeService = RtHttp.getHttpService(ApiConfig.SMS_VERIFY_BASE_URL, RegisterService.class);
        checkCodeService.checkVerifyCode(CheckSumBuilder.getRequestHeaders(), phoneNum, code)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful() && response.body() != null){
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
                                final RegisterService register = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, RegisterService.class);
                                register.register(username, phoneNum, password)
                                        .enqueue(new Callback<HttpResult<String>>() {
                                            @Override
                                            public void onResponse(Call<HttpResult<String>> call, Response<HttpResult<String>> response) {
                                                if (response.isSuccessful() && response.body() != null){
                                                    String code = response.body().getCode();
                                                    String msg = response.body().getMsg();
                                                    if (code.equals(Constant.REQUEST_SUCCESS)){
                                                        //注册成功
                                                        iRegisterView.registerSuccess(msg);
                                                    }else{
                                                        //注册失败
                                                        iRegisterView.registerFailed(msg);
                                                    }
                                                }else{
                                                    //服务器内部错误
                                                    iRegisterView.validateError(mContext.getResources().getString(R.string.str_server_error));
                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<HttpResult<String>> call, Throwable t) {
                                                iRegisterView.validateError(mContext.getString(R.string.str_network_error));
                                            }
                                        });
                            }else{
                                Log.d(TAG, "onResponse: code" + code);
                                //验证失败
                                iRegisterView.validateError(mContext.getString(R.string.str_verify_is_wrong));
                            }
                        }else{
                            //服务器内部错误
                            iRegisterView.validateError(mContext.getResources().getString(R.string.str_server_error));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        iRegisterView.validateError(mContext.getString(R.string.str_network_error));
                    }
                });
    }
}

interface IRegisterPresenter{
    void getVerificationCode(final String phoneNum, final TextView getCodeTv, final CountDownTimer timer);
    void register(String username, String phoneNum, String password, String code);
}