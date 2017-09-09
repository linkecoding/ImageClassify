package cn.codekong.imageclassificationsystemclient.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.codekong.imageclassificationsystemclient.fragment.HomeFragment;
import cn.codekong.imageclassificationsystemclient.fragment.MeFragment;
import cn.codekong.imageclassificationsystemclient.fragment.RankFragment;
import cn.codekong.imageclassificationsystemclient.fragment.TaskFragment;

/**
 * Created by szh on 2017/5/6.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles;

    public MainFragmentPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1){
            return new TaskFragment();
        }else if (position == 2){
            return new RankFragment();
        }else if (position == 3){
            return new MeFragment();
        }
        return new HomeFragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
