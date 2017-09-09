package cn.codekong.imageclassificationsystemclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.bean.FinishedTaskImageDetail;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.presenter.ShowFinishedTaskDetailPresenter;
import cn.codekong.imageclassificationsystemclient.util.ActivityCollector;
import cn.codekong.imageclassificationsystemclient.view.IShowFinishedTaskDetailView;

/**
 * 展示完成任务详情Activity
 * Created by 尚振鸿 on 2017/6/16. 13:55
 * mail:szh@codekong.cn
 */

public class ShowFinishedTaskDetailActivity extends TopBarBaseActivity implements IShowFinishedTaskDetailView {
    private static final String TAG = "xxhh";

    @BindView(R.id.id_finished_task_grid_view)
    GridView finishedTaskGridView;

    private ShowFinishedTaskDetailPresenter mShowFinishedTaskDetailPresenter;
    private List<FinishedTaskImageDetail> taskList;
    private List<String> confirmLabels;
    private List<String> unconfirmLabels;

    @Override
    protected int getContentView() {
        return R.layout.activity_show_finishedtask_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle(getString(R.string.str_task_detail));
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        String taskId = getIntent().getStringExtra(Constant.TASK_ID);
        mShowFinishedTaskDetailPresenter = new ShowFinishedTaskDetailPresenter(this, this);
        mShowFinishedTaskDetailPresenter.getFinishedTaskDetail(taskId);
    }

    private void setTaskDetail() {
        CommonAdapter<FinishedTaskImageDetail> taskItemDetailAdapter = new CommonAdapter<FinishedTaskImageDetail>(this, R.layout.finished_task_detail_item, taskList) {
            @Override
            protected void convert(ViewHolder viewHolder, FinishedTaskImageDetail item, int position) {
                ImageView taskImageView = viewHolder.getView(R.id.id_finished_task_image_view);
                Glide.with(ShowFinishedTaskDetailActivity.this)
                        .load(item.getImg_path())
                        .placeholder(R.drawable.ic_user_default_avatar)
                        .centerCrop()
                        .dontAnimate()
                        .into(taskImageView);
                mShowFinishedTaskDetailPresenter.getLabels(item.getUser_labels(), item.getFinal_labels());
                if (confirmLabels.size() != 0) {
                    GridView confirmLabelGridView = viewHolder.getView(R.id.id_confirm_task_label_grid_view);
                    confirmLabelGridView.setAdapter(new ArrayAdapter<>(ShowFinishedTaskDetailActivity.this,
                            R.layout.confirm_task_label, confirmLabels));
                }
                if (unconfirmLabels.size() != 0) {
                    GridView unconfirmLabelGridView = viewHolder.getView(R.id.id_unconfirm_task_label_grid_view);
                    unconfirmLabelGridView.setAdapter(new ArrayAdapter<>(ShowFinishedTaskDetailActivity.this,
                            R.layout.unconfirm_task_label, unconfirmLabels));
                }
                TextView accuracyTextView =  viewHolder.getView(R.id.id_image_accuracy);
                if(TextUtils.isEmpty(item.getAccuracy()) || item.getAccuracy().equals("null")){
                    accuracyTextView.setText("准确度 0.0%");
                }else {
                    String accuracyStr = String.format("%.1f",100*Float.parseFloat(item.getAccuracy()));
                    accuracyTextView.setText("准确度 "+ accuracyStr+"%");
                }
            }
        };
        finishedTaskGridView.setAdapter(taskItemDetailAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void getFinishedTaskDetailSuccess(List<FinishedTaskImageDetail> taskList) {
        this.taskList = taskList;
        setTaskDetail();
    }

    @Override
    public void getFinishedTaskDetailFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void validateError(String msg) {
        //token失效
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        ActivityCollector.finishAll();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void setLabels(List<String> confirmLabels, List<String> unconfirmLabels) {
        if (this.confirmLabels!=null){
            this.confirmLabels.clear();
        }
        if (this.unconfirmLabels!=null){
            this.unconfirmLabels.clear();
        }
        this.confirmLabels = confirmLabels;
        this.unconfirmLabels = unconfirmLabels;
    }
}
