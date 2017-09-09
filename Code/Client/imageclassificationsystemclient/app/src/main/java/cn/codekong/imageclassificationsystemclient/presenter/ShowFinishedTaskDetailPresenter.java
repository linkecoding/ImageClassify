package cn.codekong.imageclassificationsystemclient.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.bean.FinishedTaskImageDetail;
import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.config.ApiConfig;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.net.RtHttp;
import cn.codekong.imageclassificationsystemclient.service.TaskService;
import cn.codekong.imageclassificationsystemclient.util.SaveDataUtil;
import cn.codekong.imageclassificationsystemclient.view.IShowFinishedTaskDetailView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/6/16.
 */

public class ShowFinishedTaskDetailPresenter implements IShowFinishedTaskDetailPresenter {
    private IShowFinishedTaskDetailView iShowFinishedTaskDetailView;
    private Context mContext;

    public ShowFinishedTaskDetailPresenter(Context mContext, IShowFinishedTaskDetailView iShowFinishedTaskDetailView) {
        this.iShowFinishedTaskDetailView = iShowFinishedTaskDetailView;
        this.mContext = mContext;
    }

    @Override
    public void getFinishedTaskDetail(String taskId) {
        //获取token
        String token = SaveDataUtil.getValueFromSharedPreferences(mContext, Constant.SHAREDPREFERENCES_DEFAULT_NAME, ApiConstant.OAUTH_TOKEN);
        if (!TextUtils.isEmpty(token)) {
            Log.d("pyh", "getFinishedTaskDetail: ");
            TaskService taskService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, TaskService.class);
            taskService.getFinishedTaskDetail(token, taskId).enqueue(new Callback<HttpResult<List<FinishedTaskImageDetail>>>() {
                @Override
                public void onResponse(Call<HttpResult<List<FinishedTaskImageDetail>>> call, Response<HttpResult<List<FinishedTaskImageDetail>>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String code = response.body().getCode();
                        if (code.equals(Constant.REQUEST_SUCCESS)) {
                            iShowFinishedTaskDetailView.getFinishedTaskDetailSuccess(response.body().getData());
                        } else if (response.body().getCode().equals(Constant.OAUTH_FAILED)) {
                            //账户被冻结
                            iShowFinishedTaskDetailView.validateError(response.body().getMsg());
                        } else {
                            //获取失败
                            String msg = response.body().getMsg();
                            iShowFinishedTaskDetailView.getFinishedTaskDetailFailed(msg);
                        }
                    } else {
                        iShowFinishedTaskDetailView.getFinishedTaskDetailFailed(mContext.getResources().getString(R.string.str_server_error));
                    }
                }

                @Override
                public void onFailure(Call<HttpResult<List<FinishedTaskImageDetail>>> call, Throwable t) {
                    iShowFinishedTaskDetailView.getFinishedTaskDetailFailed(mContext.getResources().getString(R.string.str_network_error));
                }
            });
        } else {
            //iShowFinishedTaskDetailView.getFinishedTaskDetailSuccess(getData());
            iShowFinishedTaskDetailView.validateError(mContext.getResources().getString(R.string.str_login_status_error));
        }
    }

    @Override
    public void getLabels(String userLabels, String finalLabels) {
        String[] userLabelArray = userLabels.split(";");
        List<String> confirmLabels = new ArrayList<>();
        List<String> unconfirmLabels = new ArrayList<>();
        HashSet<String> finalLabelSet = new HashSet<>(Arrays.asList(finalLabels.split(";")));
        Log.d("pyh", "getLabels: set" + finalLabelSet);
        for (String label : userLabelArray) {
            if (finalLabelSet.contains(label)) {
                confirmLabels.add(label);
            } else if (!TextUtils.isEmpty(label)) {
                unconfirmLabels.add(label);
            }
        }
        iShowFinishedTaskDetailView.setLabels(confirmLabels, unconfirmLabels);
    }

    //TODO 模拟数据
    private List<FinishedTaskImageDetail> getData() {
        List<FinishedTaskImageDetail> taskDetailList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            FinishedTaskImageDetail finishedTaskImageDetail = new FinishedTaskImageDetail();
            finishedTaskImageDetail.setImg_path("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
            finishedTaskImageDetail.setUser_labels("西瓜;水果;苹果");
            finishedTaskImageDetail.setFinal_labels("南瓜;苹果");
            taskDetailList.add(finishedTaskImageDetail);
        }
        return taskDetailList;
    }
}

interface IShowFinishedTaskDetailPresenter {
    void getFinishedTaskDetail(String taskId);

    void getLabels(String userLabels, String finalLabels);
}
