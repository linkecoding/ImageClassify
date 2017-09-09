package cn.codekong.imageclassificationsystemclient.presenter;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;

import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.config.ApiConfig;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.net.RtHttp;
import cn.codekong.imageclassificationsystemclient.service.CheckinService;
import cn.codekong.imageclassificationsystemclient.util.SaveDataUtil;
import cn.codekong.imageclassificationsystemclient.view.ICheckinView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/5/27.
 */

public class CheckinPresenter implements ICheckinPresenter {

    private ICheckinView iCheckinView;
    private Context mContext;

    public CheckinPresenter(Context context, ICheckinView checkinView) {
        this.iCheckinView = checkinView;
        this.mContext = context;
    }

    /**
     * 网络请求获取指定月份的打卡记录
     *
     * @param year
     * @param month
     * @return
     */
    @Override
    public void getCheckinData(String year, String month) {
        String token = SaveDataUtil.getValueFromSharedPreferences(mContext, Constant.SHAREDPREFERENCES_DEFAULT_NAME,
                ApiConstant.OAUTH_TOKEN);
        if (!TextUtils.isEmpty(token)) {
            //发送网络请求
            CheckinService checkinService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, CheckinService.class);
            checkinService.getCheckInList(token, year, month).enqueue(new Callback<HttpResult<String>>() {
                @Override
                public void onResponse(Call<HttpResult<String>> call, Response<HttpResult<String>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        HttpResult<String> body = response.body();
                        if (body.getCode().equals(Constant.REQUEST_SUCCESS)) {
                            String days = body.getData();
                            if (TextUtils.isEmpty(days)) {
                                iCheckinView.getCheckinDataSuccess(new ArrayList<String>());
                            } else {
                                iCheckinView.getCheckinDataSuccess(Arrays.asList(days.split("\\|")));
                            }
                        } else if (response.body().getCode().equals(Constant.OAUTH_FAILED)) {
                            //账户被冻结
                            iCheckinView.validateError(response.body().getMsg());
                        } else {
                            iCheckinView.getCheckinDataFailed(body.getMsg());
                        }
                    } else {
                        //服务器错误
                        iCheckinView.getCheckinDataFailed(mContext.getResources().getString(R.string.str_server_error));
                    }
                }

                @Override
                public void onFailure(Call<HttpResult<String>> call, Throwable t) {
                    iCheckinView.getCheckinDataFailed(mContext.getString(R.string.str_network_error));
                }
            });
        } else {
            //token不存在,重新登录
            iCheckinView.validateError(mContext.getString(R.string.str_login_status_error));
            //TODO 模拟数据
            //simulatedData();
        }
    }

    /**
     * 模拟签到数据
     */
    private void simulatedData() {
        /*checkinView.getCheckinDataFailed("获取信息失败");*/
        iCheckinView.getCheckinDataSuccess(Arrays.asList("1", "3", "4", "7", "20"));
    }
}

interface ICheckinPresenter {
    void getCheckinData(String year, String month);
}
