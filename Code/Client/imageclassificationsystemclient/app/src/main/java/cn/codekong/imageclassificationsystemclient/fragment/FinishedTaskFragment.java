package cn.codekong.imageclassificationsystemclient.fragment;


import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.activity.LoginActivity;
import cn.codekong.imageclassificationsystemclient.activity.ShowFinishedTaskDetailActivity;
import cn.codekong.imageclassificationsystemclient.bean.Task;
import cn.codekong.imageclassificationsystemclient.config.ApiConfig;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.presenter.FinishedTaskFragmentPresenter;
import cn.codekong.imageclassificationsystemclient.util.ActivityCollector;
import cn.codekong.imageclassificationsystemclient.util.CompositionAvatarUtil;
import cn.codekong.imageclassificationsystemclient.view.IFinishedTaskFragmentView;
import cn.yiiguxing.compositionavatar.CompositionAvatarView;

/**
 *
 */
public class FinishedTaskFragment extends LazyLoadFragment implements IFinishedTaskFragmentView {

    @BindView(R.id.id_finished_task_list_view)
    ListView finishedTaskListView;
    @BindView(R.id.id_pull_to_refresh_view)
    PullToRefreshLayout pullToRefreshView;

    private FinishedTaskFragmentPresenter finishedTaskFragmentPresenter;
    //默认的起始和终止为1-10
    private static int mDefaultStart = 1;
    private List<Task> mTaskList = new ArrayList<>();
    private CommonAdapter<Task> mTaskCommonAdapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_finished_task;
    }

    @Override
    protected void lazyLoad() {
        finishedTaskFragmentPresenter = new FinishedTaskFragmentPresenter(getActivity(),this);
        finishedTaskFragmentPresenter.getFinishedTaskList(mDefaultStart+"", ApiConfig.DEAULT_NUM_PER_PAGE+"");
        pullToRefreshView.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                //加一个延时
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //获取未完成任务数据(刷新只获得前10条数据)
                        finishedTaskFragmentPresenter.getFinishedTaskList(mDefaultStart + "", ApiConfig.DEAULT_NUM_PER_PAGE + "");
                    }
                }, 0);
            }

            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //获得更多未完成任务信息
                        finishedTaskFragmentPresenter.getFinishedTaskList((mTaskList.size() + 1) + "", ApiConfig.DEAULT_NUM_PER_PAGE + "");
                    }
                }, 0);
            }
        });
    }

    /**
     * 设置数据到ListView
     *
     * @param taskList
     */
    private void setDataToList(final List<Task> taskList) {
        mTaskCommonAdapter = new CommonAdapter<Task>(getActivity(), R.layout.task_item, taskList) {
            @Override
            protected void convert(ViewHolder viewHolder, final Task item, int position) {
                CompositionAvatarView compositionAvatarView =  viewHolder.getView(R.id.id_task_images_icon);
                CompositionAvatarUtil.asyncLoadDrawable(compositionAvatarView, item.getImage_path_five());
                viewHolder.setOnClickListener(R.id.id_task_item_layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),ShowFinishedTaskDetailActivity.class);
                        intent.putExtra(Constant.TASK_ID,item.getTask_id());
                        startActivity(intent);
                    }
                });
                viewHolder.setText(R.id.id_task_time, item.getTask_start_time());
                viewHolder.setText(R.id.id_task_num, item.getImg_amount()+"/"+item.getTask_img_amount());
            }
        };
        finishedTaskListView.setAdapter(mTaskCommonAdapter);
    }

    @Override
    public void getFinishedTaskSuccess(List<Task> taskList) {
        this.mTaskList = taskList;
        setDataToList(taskList);
        mTaskCommonAdapter.notifyDataSetChanged();
        pullToRefreshView.finishRefresh();
    }

    @Override
    public void loadMoreUnfinishedTaskSuccess(List<Task> taskList) {
        this.mTaskList.addAll(taskList);
        setDataToList(mTaskList);
        mTaskCommonAdapter.notifyDataSetChanged();
        pullToRefreshView.finishLoadMore();
        finishedTaskListView.setSelection(mTaskList.size());
    }

    @Override
    public void getFinishedTaskFailed(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        pullToRefreshView.finishRefresh();
        pullToRefreshView.finishLoadMore();
    }

    @Override
    public void validateError(String msg) {
        //token失效
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        ActivityCollector.finishAll();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
