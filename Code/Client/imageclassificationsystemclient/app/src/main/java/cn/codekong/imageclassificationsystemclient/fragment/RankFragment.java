package cn.codekong.imageclassificationsystemclient.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.activity.LoginActivity;
import cn.codekong.imageclassificationsystemclient.bean.Rank;
import cn.codekong.imageclassificationsystemclient.config.ApiConfig;
import cn.codekong.imageclassificationsystemclient.presenter.RankFragmentPresenter;
import cn.codekong.imageclassificationsystemclient.util.ActivityCollector;
import cn.codekong.imageclassificationsystemclient.util.ClearImageCacheUtil;
import cn.codekong.imageclassificationsystemclient.util.ShareUtil;
import cn.codekong.imageclassificationsystemclient.view.IRankFragmentView;

/**
 * 排行榜Fragment
 */
public class RankFragment extends LazyLoadFragment implements IRankFragmentView {
    //当前排行榜排序依据(默认根据积分排序)
    private static String CURRENT_RANK_IDENTIFICATION = RANK_IDENTIFICATION.integral.name();
    private Context mContext;

    private enum RANK_IDENTIFICATION {integral, accuracy, task}

    ;
    private RankFragmentPresenter mRankFragmentPresenter;
    @BindView(R.id.id_rank_listview)
    ListView mRankListview;
    @BindView(R.id.id_pull_to_refresh_view)
    PullToRefreshLayout mPullToRefreshView;
    //数据集
    private List<Rank> mRankList = new ArrayList<>();
    //适配器
    private CommonAdapter<Rank> mRankCommonAdapter;
    //默认的起始和终止为1-10
    private static int mDefaultStart = 1;
    private Handler mShareRankHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置Fragment也可以设置和处理菜单事件
        setHasOptionsMenu(true);
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_rank;
    }

    @Override
    protected void lazyLoad() {
        mContext = getActivity();
        mRankFragmentPresenter = new RankFragmentPresenter(mContext, this);
        //默认获得用户排行榜信息
        mRankFragmentPresenter.getRanking(CURRENT_RANK_IDENTIFICATION, mDefaultStart + "", ApiConfig.DEAULT_NUM_PER_PAGE + "");
        //添加下拉刷新上拉加载监听
        mPullToRefreshView.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                //获取用户排行榜数据(刷新只获得前10条数据)
                mRankFragmentPresenter.getRanking(CURRENT_RANK_IDENTIFICATION, mDefaultStart + "", ApiConfig.DEAULT_NUM_PER_PAGE + "");
            }

            @Override
            public void loadMore() {
                //获得更多排行榜信息
                mRankFragmentPresenter.getRanking(CURRENT_RANK_IDENTIFICATION, (mRankList.size() + 1) + "", ApiConfig.DEAULT_NUM_PER_PAGE + "");
            }
        });
    }

    @Override
    public void getRankingSuccess(List<Rank> rankList) {
        this.mRankList = rankList;
        //设置排行榜信息到ListView
        setDataToList(rankList);
        mRankCommonAdapter.notifyDataSetChanged();
        mPullToRefreshView.finishRefresh();
    }

    /**
     * 设置数据到ListView
     *
     * @param rankList
     */
    private void setDataToList(final List<Rank> rankList) {
        ClearImageCacheUtil.clearImageAllCache(mContext);
        mRankCommonAdapter = new CommonAdapter<Rank>(getActivity(), R.layout.rank_item, rankList) {
            @Override
            protected void convert(ViewHolder viewHolder, Rank item, int position) {
                ImageView rankAvatarImageView = viewHolder.getView(R.id.id_rank_avatar);
                Glide.with(getActivity())
                        .load(item.getAvatar_url())
                        .dontAnimate()
                        .placeholder(R.drawable.ic_user_default_avatar)
                        .centerCrop()
                        .into(rankAvatarImageView);
                viewHolder.setText(R.id.id_rank_name, item.getUsername());
                viewHolder.setText(R.id.id_rank_index, (1 + position) + "");
                //根据不同的排行榜标准替换为不同的值
                if (CURRENT_RANK_IDENTIFICATION.equals(RANK_IDENTIFICATION.accuracy.name())) {
                    viewHolder.setText(R.id.id_rank_identification_text, getString(R.string.str_accuracy));
                    String accuracy = "";
                    if (TextUtils.isEmpty(item.getAccuracy()) || "null".equals(item.getAccuracy())) {
                        accuracy = "0.0%";
                    } else {
                        accuracy = String.format("%.1f%%", 100 * Float.parseFloat(item.getAccuracy()));
                    }
                    viewHolder.setText(R.id.id_user_rank_identification, accuracy);
                } else if (CURRENT_RANK_IDENTIFICATION.equals(RANK_IDENTIFICATION.task.name())) {
                    viewHolder.setText(R.id.id_rank_identification_text, getString(R.string.str_task_num));
                    String taskAmount = "";
                    if (TextUtils.isEmpty(item.getAmount()) || "null".equals(item.getAmount())) {
                        taskAmount = "0";
                    } else {
                        taskAmount = item.getAmount();
                    }
                    viewHolder.setText(R.id.id_user_rank_identification, taskAmount);
                } else {
                    //默认为积分作为排行依据
                    viewHolder.setText(R.id.id_rank_identification_text, getString(R.string.str_integral));

                    String integral = "";
                    if (TextUtils.isEmpty(item.getIntegral()) || "null".equals(item.getIntegral())) {
                        integral = "0";
                    } else {
                        integral = item.getIntegral();
                    }
                    viewHolder.setText(R.id.id_user_rank_identification, integral);
                }
                //前三名显示王冠
                if (position < 3) {
                    viewHolder.getView(R.id.id_rank_king_icon).setVisibility(View.VISIBLE);
                } else {
                    viewHolder.getView(R.id.id_rank_king_icon).setVisibility(View.GONE);
                }
            }
        };
        mRankListview.setAdapter(mRankCommonAdapter);
    }

    @Override
    public void loadMoreRankingSuccess(List<Rank> rankList) {
        this.mRankList.addAll(rankList);
        setDataToList(mRankList);
        mRankCommonAdapter.notifyDataSetChanged();
        mPullToRefreshView.finishLoadMore();
        mRankListview.setSelection(mRankList.size());
    }

    @Override
    public void getRankingFailed(String msg) {
        //给出出错信息
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        mPullToRefreshView.finishRefresh();
        mPullToRefreshView.finishLoadMore();
    }

    @Override
    public void validateError(String msg) {
        //token失效
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        ActivityCollector.finishAll();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_rank_indetify, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_rank_by_integral_menu:
                CURRENT_RANK_IDENTIFICATION = RANK_IDENTIFICATION.integral.name();
                mRankFragmentPresenter.getRanking(CURRENT_RANK_IDENTIFICATION,
                        mDefaultStart + "", ApiConfig.DEAULT_NUM_PER_PAGE + "");
                break;
            case R.id.id_rank_by_accuracy_menu:
                CURRENT_RANK_IDENTIFICATION = RANK_IDENTIFICATION.accuracy.name();
                mRankFragmentPresenter.getRanking(CURRENT_RANK_IDENTIFICATION,
                        mDefaultStart + "", ApiConfig.DEAULT_NUM_PER_PAGE + "");
                break;
            case R.id.id_rank_by_taskamount_menu:
                CURRENT_RANK_IDENTIFICATION = RANK_IDENTIFICATION.task.name();
                mRankFragmentPresenter.getRanking(CURRENT_RANK_IDENTIFICATION,
                        mDefaultStart + "", ApiConfig.DEAULT_NUM_PER_PAGE + "");
                break;
            case R.id.id_share_rank_menu:
                //分享排行榜截图
                ShareUtil.shareRankShot(getContext(), mRankListview, mShareRankHandler);
                break;
        }
        return true;
    }
}
