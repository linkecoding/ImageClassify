package cn.codekong.imageclassificationsystemclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.webelite.ion.IconView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.customizeview.NoScrollViewPager;
import cn.codekong.imageclassificationsystemclient.fragment.HomeFragment;
import cn.codekong.imageclassificationsystemclient.fragment.MeFragment;
import cn.codekong.imageclassificationsystemclient.fragment.RankFragment;
import cn.codekong.imageclassificationsystemclient.fragment.TaskFragment;
import cn.codekong.imageclassificationsystemclient.util.SaveDataUtil;


public class MainActivity extends TopBarBaseActivity implements View.OnClickListener {

    @BindView(R.id.id_view_pager)
    NoScrollViewPager viewPager;
    @BindView(R.id.id_tab_home_img)
    IconView tabHomeImg;
    @BindView(R.id.id_tab_home_text)
    TextView tabHomeText;
    @BindView(R.id.id_tab_task_img)
    IconView tabTaskImg;
    @BindView(R.id.id_tab_task_text)
    TextView tabTaskText;
    @BindView(R.id.id_tab_rank_img)
    IconView tabRankImg;
    @BindView(R.id.id_tab_rank_text)
    TextView tabRankText;
    @BindView(R.id.id_tab_me_img)
    IconView tabMeImg;
    @BindView(R.id.id_tab_me_text)
    TextView tabMeText;

    private FragmentPagerAdapter adapter;
    private List<Fragment> listFragment;

    private HomeFragment homeFragment;
    private TaskFragment taskFragment;
    private RankFragment rankFragment;
    private MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        String token = SaveDataUtil.getValueFromSharedPreferences(this, Constant.SHAREDPREFERENCES_DEFAULT_NAME, ApiConstant.OAUTH_TOKEN);
        if (TextUtils.isEmpty(token)) {
            finish();
            Intent intent = new Intent(this, LoginRegisterActivity.class);
            startActivity(intent);
        } else {
            initView();
            initEvent();
            setSelect(0);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle(getString(R.string.str_tab_home));
    }

    private void initEvent() {
        //禁止viewPager左右滑动
        viewPager.setNoScroll(true);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initView() {
        listFragment = new ArrayList<>();
        homeFragment = new HomeFragment();
        taskFragment = new TaskFragment();
        rankFragment = new RankFragment();
        meFragment = new MeFragment();
        listFragment.add(homeFragment);
        listFragment.add(taskFragment);
        listFragment.add(rankFragment);
        listFragment.add(meFragment);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return listFragment.get(position);
            }

            @Override
            public int getCount() {
                return listFragment.size();
            }
        };
        viewPager.setAdapter(adapter);
    }

    @OnClick({R.id.id_tab_home, R.id.id_tab_task, R.id.id_tab_rank, R.id.id_tab_me})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tab_home:
                setTitle(getString(R.string.str_tab_home));
                setSelect(0);
                break;
            case R.id.id_tab_task:
                setTitle(getString(R.string.str_tab_task));
                setSelect(1);
                break;
            case R.id.id_tab_rank:
                setTitle(getString(R.string.str_tab_rank));
                setSelect(2);
                break;
            case R.id.id_tab_me:
                setTitle(getString(R.string.str_tab_me));
                setSelect(3);
                break;
        }
    }

    private void setSelect(int i) {
        setTab(i);
        viewPager.setCurrentItem(i);
    }

    private void setTab(int i) {
        resetImg();
        switch (i) {
            case 0:
                tabHomeImg.setTextColor(getResources().getColor(R.color.colorTheme2));
                tabHomeText.setTextColor(getResources().getColor(R.color.colorTheme2));
                break;
            case 1:
                tabTaskImg.setTextColor(getResources().getColor(R.color.colorTheme2));
                tabTaskText.setTextColor(getResources().getColor(R.color.colorTheme2));
                break;
            case 2:
                tabRankImg.setTextColor(getResources().getColor(R.color.colorTheme2));
                tabRankText.setTextColor(getResources().getColor(R.color.colorTheme2));
                break;
            case 3:
                tabMeImg.setTextColor(getResources().getColor(R.color.colorTheme2));
                tabMeText.setTextColor(getResources().getColor(R.color.colorTheme2));
                break;
        }
    }

    /**
     * 将所有图片切换成暗色
     */
    private void resetImg() {
        tabHomeImg.setTextColor(getResources().getColor(R.color.colorTextHint));
        tabHomeText.setTextColor(getResources().getColor(R.color.colorTextHint));
        tabTaskImg.setTextColor(getResources().getColor(R.color.colorTextHint));
        tabTaskText.setTextColor(getResources().getColor(R.color.colorTextHint));
        tabRankImg.setTextColor(getResources().getColor(R.color.colorTextHint));
        tabRankText.setTextColor(getResources().getColor(R.color.colorTextHint));
        tabMeImg.setTextColor(getResources().getColor(R.color.colorTextHint));
        tabMeText.setTextColor(getResources().getColor(R.color.colorTextHint));
    }


    private boolean mIsExit;

    //双击返回键退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                this.finish();

            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
