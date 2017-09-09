package cn.codekong.imageclassificationsystemclient.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.bean.HomeTaskStatusDetail;
import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.config.ApiConfig;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.net.RtHttp;
import cn.codekong.imageclassificationsystemclient.service.CheckinService;
import cn.codekong.imageclassificationsystemclient.service.TaskService;
import cn.codekong.imageclassificationsystemclient.util.SaveDataUtil;
import cn.codekong.imageclassificationsystemclient.view.IHomeFragmentView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/5/26.
 */

public class HomeFragmentPresenter implements IHomeFragmentPresenter {

    private static final String TAG = "HomeFragmentPresenter";
    private IHomeFragmentView iHomeFragmentView;
    private Context mContext;

    public HomeFragmentPresenter(IHomeFragmentView iHomeFragmentView, Context context) {
        this.iHomeFragmentView = iHomeFragmentView;
        this.mContext = context;
    }

    /**
     * 获取当天日期
     */
    @Override
    public void getCheckinDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd EE", Locale.CHINA);
        iHomeFragmentView.setCheckinDateTv(sdf.format(date));
    }

    /**
     * 打卡
     */
    @Override
    public void checkin() {
        String token = SaveDataUtil.getValueFromSharedPreferences(mContext, Constant.SHAREDPREFERENCES_DEFAULT_NAME,
                ApiConstant.OAUTH_TOKEN);
        if (!TextUtils.isEmpty(token)) {
            //发送网络请求
            CheckinService checkinService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL,CheckinService.class);
            checkinService.checkin(token).enqueue(new Callback<HttpResult<String>>() {
                @Override
                public void onResponse(Call<HttpResult<String>> call, Response<HttpResult<String>> response) {
                    if (response.isSuccessful() && response.body() != null){
                        HttpResult<String> body = response.body();
                        if (body.getCode().equals(Constant.REQUEST_SUCCESS)) {
                            iHomeFragmentView.checkinSuccess(response.body().getMsg());
                        }else if (response.body().getCode().equals(Constant.OAUTH_FAILED)) {
                            //账户被冻结
                            iHomeFragmentView.validateError(response.body().getMsg());
                        } else {
                            iHomeFragmentView.checkinFailed(response.body().getMsg());
                        }
                    }else{
                        //服务器错误
                        iHomeFragmentView.checkinFailed(mContext.getResources().getString(R.string.str_server_error));
                    }
                }

                @Override
                public void onFailure(Call<HttpResult<String>> call, Throwable t) {
                    iHomeFragmentView.checkinFailed(mContext.getString(R.string.str_network_error));
                }
            });
        } else{
            //token存在问题
            iHomeFragmentView.validateError(mContext.getString(R.string.str_login_status_error));
        }
    }

    /**
     * 获取昨日任务、今日任务、未完成任务数量
     */
    @Override
    public void getTaskStatusInfo() {
        String token = SaveDataUtil.getValueFromSharedPreferences(mContext, Constant.SHAREDPREFERENCES_DEFAULT_NAME,
                ApiConstant.OAUTH_TOKEN);
        if (!TextUtils.isEmpty(token)) {
            //发送网络请求
            TaskService taskService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL,TaskService.class);
            taskService.getHomeTaskStatusDetail(token).enqueue(new Callback<HttpResult<HomeTaskStatusDetail>>() {
                @Override
                public void onResponse(Call<HttpResult<HomeTaskStatusDetail>> call, Response<HttpResult<HomeTaskStatusDetail>> response) {
                    if (response.isSuccessful() && response.body() != null){
                        HttpResult<HomeTaskStatusDetail> body = response.body();
                        if (body.getCode().equals(Constant.REQUEST_SUCCESS)) {
                            iHomeFragmentView.getHomeTaskStatusDetailSuccess(body.getData());
                        }else  if (body.getCode().equals(Constant.OAUTH_FAILED)) {
                            //账户被冻结
                            iHomeFragmentView.validateError(response.body().getMsg());
                        } else {
                            iHomeFragmentView.getHomeTaskStatusDetailFailed(response.body().getMsg());
                        }
                    }else{
                        //服务器错误
                        iHomeFragmentView.getHomeTaskStatusDetailFailed(mContext.getResources().getString(R.string.str_server_error));
                    }
                }

                @Override
                public void onFailure(Call<HttpResult<HomeTaskStatusDetail>> call, Throwable t) {
                    iHomeFragmentView.getHomeTaskStatusDetailFailed(mContext.getString(R.string.str_network_error));
                }
            });
        } else{
            //token存在问题
            iHomeFragmentView.validateError(mContext.getString(R.string.str_login_status_error));
        }
    }

    /**
     * 选择任务图片数量，并设置相应的TextView背景
     */
    @Override
    public void selectTaskImgAmount(TextView times_10,TextView times_20,TextView times_30,int task_img_amount) {
        initTaskButtonBg(times_10,times_20,times_30);
        switch (task_img_amount){
            case 10:
                times_10.setSelected(true);
                times_10.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                break;
            case 20:
                times_20.setSelected(true);
                times_20.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                break;
            case 30:
                times_30.setSelected(true);
                times_30.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                break;
        }
    }

    /**
     * 将所有图片次数TextView初始化为为选中状态
     * @param times_10
     * @param times_20
     * @param times_30
     */
    private void initTaskButtonBg(TextView times_10,TextView times_20,TextView times_30) {
        times_10.setSelected(false);
        times_10.setTextColor(mContext.getResources().getColor(R.color.colorTheme));
        times_20.setSelected(false);
        times_20.setTextColor(mContext.getResources().getColor(R.color.colorTheme));
        times_30.setSelected(false);
        times_30.setTextColor(mContext.getResources().getColor(R.color.colorTheme));
    }
}

interface IHomeFragmentPresenter{
    //获取当天日期
    void getCheckinDate();
    //打卡
    void checkin();
    //获取昨日任务、今日任务、未完成任务数量
    void getTaskStatusInfo();
    //选择任务图片数量
    void selectTaskImgAmount(TextView times_10,TextView times_20,TextView times_30, int task_img_amount);
}
