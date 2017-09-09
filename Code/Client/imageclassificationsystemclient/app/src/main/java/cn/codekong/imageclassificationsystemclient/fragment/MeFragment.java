package cn.codekong.imageclassificationsystemclient.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import be.webelite.ion.Icon;
import be.webelite.ion.IconView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.activity.CheckinActivity;
import cn.codekong.imageclassificationsystemclient.activity.ChooseInterestedCategoryActivity;
import cn.codekong.imageclassificationsystemclient.activity.ContributeImgActivity;
import cn.codekong.imageclassificationsystemclient.activity.ExperienceRoomActivity;
import cn.codekong.imageclassificationsystemclient.activity.LoginActivity;
import cn.codekong.imageclassificationsystemclient.activity.ModifyUserInfoActivity;
import cn.codekong.imageclassificationsystemclient.activity.SettingsActivity;
import cn.codekong.imageclassificationsystemclient.bean.UserTaskAmount;
import cn.codekong.imageclassificationsystemclient.presenter.MeFragmentPresenter;
import cn.codekong.imageclassificationsystemclient.util.ActivityCollector;
import cn.codekong.imageclassificationsystemclient.util.ClearImageCacheUtil;
import cn.codekong.imageclassificationsystemclient.view.IMeFragmentView;

/**
 * 我页面
 */
public class MeFragment extends LazyLoadFragment implements IMeFragmentView {
    private static final String TAG = "xxhh";

    @BindView(R.id.id_user_avatar)
    ImageView userAvatar;
    @BindView(R.id.id_username)
    TextView username;
    @BindView(R.id.id_user_sex_tv)
    IconView sex;
    @BindView(R.id.id_me_user_integral)
    TextView integral; //积分
    @BindView(R.id.id_user_task_num)
    TextView taskNum;
    @BindView(R.id.id_user_percent_of_accuracy)
    TextView accuracy;
    @BindView(R.id.id_experience_room)
    RelativeLayout mExperienceRoom;

    Unbinder unbinder;

    private Context mContext;
    private MeFragmentPresenter fragmentPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_me;
    }

    @Override
    protected void lazyLoad() {
        mContext = getActivity();
        fragmentPresenter = new MeFragmentPresenter(mContext, this);
        fragmentPresenter.getUserInfo();
    }

    @Override
    public void getUserInfoSuccess(UserTaskAmount userTaskAmount) {
        ClearImageCacheUtil.clearImageAllCache(mContext);
        Glide.with(mContext)
                .load(userTaskAmount.getUser().getAvatar_url())
                .dontAnimate()
                .placeholder(R.drawable.ic_user_default_avatar)
                .into(userAvatar);
        username.setText(userTaskAmount.getUser().getUsername());
        if (TextUtils.isEmpty(userTaskAmount.getUser().getSex())) {
            sex.setIcon(Icon.ion_male);
        } else if (userTaskAmount.getUser().getSex().equals("女")) {
            sex.setIcon(Icon.ion_female);
        } else {
            sex.setIcon(Icon.ion_male);
        }

        integral.setText(userTaskAmount.getUser().getIntegral() + "");

        if (TextUtils.isEmpty(userTaskAmount.getUser().getAccuracy()) || "null".equals(userTaskAmount.getUser().getAccuracy())) {
            accuracy.setText("0.0%");
        } else {
            String accuracyStr = String.format("%.1f", 100 * Float.parseFloat(userTaskAmount.getUser().getAccuracy()));
            accuracy.setText(accuracyStr + "%");
        }
        //设置任务量
        if (TextUtils.isEmpty(userTaskAmount.getTask_amount())) {
            taskNum.setText("0");
        } else {
            taskNum.setText(userTaskAmount.getTask_amount());
        }
    }

    @Override
    public void getUserInfoFailed(String msg) {
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

    @OnClick({R.id.id_user_info, R.id.id_user_interest, R.id.id_user_checkin_record, R.id.id_settings, R.id.id_experience_room, R.id.id_contribute_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_user_info:
                Intent userInfoIntent = new Intent(mContext, ModifyUserInfoActivity.class);
                startActivity(userInfoIntent);
                break;
            case R.id.id_user_interest:
                //跳转到选择兴趣界面
                Intent interestedIntent = new Intent(mContext, ChooseInterestedCategoryActivity.class);
                startActivity(interestedIntent);
                break;
            case R.id.id_user_checkin_record:
                Intent checkinIntent = new Intent(mContext, CheckinActivity.class);
                startActivity(checkinIntent);
                break;
            case R.id.id_settings:
                Intent settingsIntent = new Intent(mContext, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.id_experience_room:
                Intent experienceRoomIntent = new Intent(mContext, ExperienceRoomActivity.class);
                startActivity(experienceRoomIntent);
                break;
            case R.id.id_contribute_img:
                Intent contributeImgIntent = new Intent(mContext, ContributeImgActivity.class);
                startActivity(contributeImgIntent);
                break;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
