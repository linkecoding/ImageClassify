package cn.codekong.imageclassificationsystemclient.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.adapter.TaskFragmentPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends LazyLoadFragment {

    @BindView(R.id.id_tab_task_title)
    TabLayout mTaskTileTabLayout;
    @BindView(R.id.id_task_view_pager)
    ViewPager mTaskViewPager;
    Unbinder unbinder;
    private TaskFragmentPagerAdapter taskFragmentPagerAdapter;
    private List<Fragment> listFragment;
    private List<String> listTitle;
    private FinishedTaskFragment finishedTaskFragment;
    private UnConfirmedTaskFragment unConfirmedTaskFragment;
    private UnfinishedTaskFragment unfinishedTaskFragment;

    @Override
    protected int setContentView() {
        return R.layout.fragment_task;
    }

    @Override
    protected void lazyLoad() {
        initView();
    }

    private void initView() {
        //初始化各fragment
        unfinishedTaskFragment = new UnfinishedTaskFragment();
        unConfirmedTaskFragment = new UnConfirmedTaskFragment();
        finishedTaskFragment = new FinishedTaskFragment();
        //将fragment装进列表中
        listFragment = new ArrayList<>();
        listFragment.add(unfinishedTaskFragment);
        listFragment.add(unConfirmedTaskFragment);
        listFragment.add(finishedTaskFragment);
        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        listTitle = new ArrayList<>();
        listTitle.add(getString(R.string.str_task_unfinisheda));
        listTitle.add(getString(R.string.str_task_wait_to_cnfirm));
        listTitle.add(getString(R.string.str_task_finished));
        //设置TabLayout的模式
        mTaskTileTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        mTaskTileTabLayout.addTab(mTaskTileTabLayout.newTab().setText(listTitle.get(0)));
        mTaskTileTabLayout.addTab(mTaskTileTabLayout.newTab().setText(listTitle.get(1)));
        mTaskTileTabLayout.addTab(mTaskTileTabLayout.newTab().setText(listTitle.get(2)));
        taskFragmentPagerAdapter = new TaskFragmentPagerAdapter(getActivity().getSupportFragmentManager(), listFragment, listTitle);

        //viewpager加载adapter
        mTaskViewPager.setAdapter(taskFragmentPagerAdapter);
        //TabLayout加载viewpager
        mTaskTileTabLayout.setupWithViewPager(mTaskViewPager);
    }
}
