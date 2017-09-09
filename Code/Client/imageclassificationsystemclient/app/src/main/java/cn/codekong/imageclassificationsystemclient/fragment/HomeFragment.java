package cn.codekong.imageclassificationsystemclient.fragment;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.activity.CheckinActivity;
import cn.codekong.imageclassificationsystemclient.activity.LoginActivity;
import cn.codekong.imageclassificationsystemclient.activity.TagImageActivity;
import cn.codekong.imageclassificationsystemclient.bean.HomeTaskStatusDetail;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.presenter.HomeFragmentPresenter;
import cn.codekong.imageclassificationsystemclient.util.ActivityCollector;
import cn.codekong.imageclassificationsystemclient.view.IHomeFragmentView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends LazyLoadFragment implements IHomeFragmentView {
    private static final String TAG = "xxhh";

    @BindView(R.id.id_checkin_date_tv)
    TextView mCheckinDateTv;
    @BindView(R.id.id_yesterday_task_num_tv)
    TextView mYesterdayTaskNumTv;
    @BindView(R.id.id_today_task_num_tv)
    TextView mTodayTaskNumTv;
    @BindView(R.id.id_unfinished_task_num_tv)
    TextView mUnfinishedTaskNumTv;
    @BindView(R.id.id_times_10_tv)
    TextView mTimes10Tv;
    @BindView(R.id.id_times_20_tv)
    TextView mTimes20Tv;
    @BindView(R.id.id_times_30_tv)
    TextView mTimes30Tv;
    @BindView(R.id.id_start_task_btn)
    Button mStartTaskBtn;
    @BindView(R.id.id_look_checkin_record_tv)
    TextView mLookCheckinRecordTv;

    //当前选择的任务图片数量
    private int mTaskImgAmount = 10;
    private HomeFragmentPresenter mHomeFragmentPresenter;
    private Context mContext;

    @Override
    protected int setContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void lazyLoad() {
        init();
    }

    private void init() {
        mContext = getActivity();
        //将图片数量10选中
        mTimes10Tv.setSelected(true);
        mTimes10Tv.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
        mHomeFragmentPresenter = new HomeFragmentPresenter(this, mContext);
        mHomeFragmentPresenter.getTaskStatusInfo();
        mHomeFragmentPresenter.getCheckinDate();
    }

    /**
     * 为打卡TextView设置日期
     *
     * @param date 当天日期
     */
    public void setCheckinDateTv(String date) {
        mCheckinDateTv.setText(date);
    }

    @Override
    public void checkinSuccess(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkinFailed(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void validateError(String msg) {
        //token失效
        Toast.makeText(this.getActivity(), msg, Toast.LENGTH_SHORT).show();
        ActivityCollector.finishAll();
        Intent intent = new Intent(this.getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void getHomeTaskStatusDetailSuccess(HomeTaskStatusDetail homeTaskStatusDetail) {
        mYesterdayTaskNumTv.setText(String.valueOf(homeTaskStatusDetail.getAmount_of_task_yesterday()));
        mTodayTaskNumTv.setText(String.valueOf(homeTaskStatusDetail.getAmount_of_task_today()));
        mUnfinishedTaskNumTv.setText(String.valueOf(homeTaskStatusDetail.getAmount_of_task_unfinished()));
    }

    @Override
    public void getHomeTaskStatusDetailFailed(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.id_checkin_date_tv, R.id.id_times_10_tv, R.id.id_times_20_tv, R.id.id_times_30_tv, R.id.id_start_task_btn, R.id.id_look_checkin_record_tv,R.id.id_checkin_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_checkin_date_tv:
                mHomeFragmentPresenter.checkin();
                break;
            case R.id.id_times_10_tv:
                mTaskImgAmount = 10;
                mHomeFragmentPresenter.selectTaskImgAmount(mTimes10Tv, mTimes20Tv, mTimes30Tv, mTaskImgAmount);
                break;
            case R.id.id_times_20_tv:
                mTaskImgAmount = 20;
                mHomeFragmentPresenter.selectTaskImgAmount(mTimes10Tv, mTimes20Tv, mTimes30Tv, mTaskImgAmount);
                break;
            case R.id.id_times_30_tv:
                mTaskImgAmount = 30;
                mHomeFragmentPresenter.selectTaskImgAmount(mTimes10Tv, mTimes20Tv, mTimes30Tv, mTaskImgAmount);
                break;
            case R.id.id_start_task_btn:
                Intent taskIntent = new Intent(mContext, TagImageActivity.class);
                taskIntent.putExtra(Constant.TASK_IMG_AMOUNT, mTaskImgAmount);
                startActivity(taskIntent);
                break;
            case R.id.id_look_checkin_record_tv:
                Intent checkinIntent = new Intent(mContext, CheckinActivity.class);
                startActivity(checkinIntent);
                break;
            case R.id.id_checkin_layout:
                mHomeFragmentPresenter.checkin();
                break;
        }
    }
}
