package cn.codekong.imageclassificationsystemclient.presenter;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;

import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.bean.Task;
import cn.codekong.imageclassificationsystemclient.config.ApiConfig;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.net.RtHttp;
import cn.codekong.imageclassificationsystemclient.service.TaskService;
import cn.codekong.imageclassificationsystemclient.util.SaveDataUtil;
import cn.codekong.imageclassificationsystemclient.view.IUnfinishedTaskFragmentView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UnfinishedTaskFragmentPresenter implements IUnfinishedTaskFragmentPresenter {
    private Context mContext;
    private IUnfinishedTaskFragmentView iUnfinishedTaskFragmentView;

    public UnfinishedTaskFragmentPresenter(Context context, IUnfinishedTaskFragmentView iUnfinishedTaskFragmentView) {
        this.mContext = context;
        this.iUnfinishedTaskFragmentView = iUnfinishedTaskFragmentView;
    }

    @Override
    public void getUnfinishedTaskList(final String start, String pageNum) {
        //获取token
        String token = SaveDataUtil.getValueFromSharedPreferences(mContext, Constant.SHAREDPREFERENCES_DEFAULT_NAME, ApiConstant.OAUTH_TOKEN);
        if (!TextUtils.isEmpty(token)) {
            TaskService taskService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, TaskService.class);
            taskService.getUnfinishedTask(token, start, pageNum).enqueue(new Callback<HttpResult<List<Task>>>() {
                @Override
                public void onResponse(Call<HttpResult<List<Task>>> call, Response<HttpResult<List<Task>>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String code = response.body().getCode();
                        if (code.equals(Constant.REQUEST_SUCCESS)) {
                            List<Task> taskList = response.body().getData();
                            //=1为首次获取数据
                            if (start.equals("1")) {
                                iUnfinishedTaskFragmentView.getUnfinishedTaskListSuccess(taskList);
                            } else {
                                iUnfinishedTaskFragmentView.loadMoreUnfinishedTaskSuccess(taskList);
                            }
                        } else if (response.body().getCode().equals(Constant.OAUTH_FAILED)) {
                            //账户被冻结
                            iUnfinishedTaskFragmentView.validateError(response.body().getMsg());
                        } else {
                            //获取失败
                            String msg = response.body().getMsg();
                            iUnfinishedTaskFragmentView.getUnfinishedTaskFailed(msg);
                        }
                    } else {
                        iUnfinishedTaskFragmentView.getUnfinishedTaskFailed(mContext.getResources().getString(R.string.str_server_error));
                    }
                }

                @Override
                public void onFailure(Call<HttpResult<List<Task>>> call, Throwable t) {
                    //TODO 模拟数据
                    //moniData(start);
                    iUnfinishedTaskFragmentView.getUnfinishedTaskFailed(mContext.getResources().getString(R.string.str_network_error));
                }
            });
        } else {
            iUnfinishedTaskFragmentView.validateError(mContext.getResources().getString(R.string.str_login_status_error));
        }
    }

    /*//TODO 模拟数据
    //模拟数据
    private void moniData(String start) {
        List<Task> taskList = new ArrayList<>();
        Task task = new Task();
        task.setTask_img_amount("10");
        List<String>imgList = new ArrayList<>();
        imgList.add("http://bbs.iiyi.com/data/attachment/image/001/20/03/11_100_100.jpg");
        imgList.add("http://bbs.iiyi.com/data/attachment/image/001/20/03/11_100_100.jpg");
        imgList.add("http://bbs.iiyi.com/data/attachment/image/001/20/03/11_100_100.jpg");
        imgList.add("http://bbs.iiyi.com/data/attachment/image/001/20/03/11_100_100.jpg");
        imgList.add("http://bbs.iiyi.com/data/attachment/image/001/20/03/11_100_100.jpg");
        task.setImage_path_five(imgList);
        task.setTask_start_time("2017-05-15 12:20");
        for (int i = 0;i < 10;i++){
            taskList.add(task);
        }
        if (start.equals("1")){
            iUnfinishedTaskFragmentView.getUnfinishedTaskListSuccess(taskList);
        }else{
            iUnfinishedTaskFragmentView.loadMoreUnfinishedTaskSuccess(taskList);
        }
    }*/
}

interface IUnfinishedTaskFragmentPresenter {
    void getUnfinishedTaskList(String start, String pageNum);
}
