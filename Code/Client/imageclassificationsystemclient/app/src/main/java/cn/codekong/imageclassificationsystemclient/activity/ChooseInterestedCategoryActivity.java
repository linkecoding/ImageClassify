package cn.codekong.imageclassificationsystemclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.presenter.ChooseInterestedCategoryPresenter;
import cn.codekong.imageclassificationsystemclient.util.ActivityCollector;
import cn.codekong.imageclassificationsystemclient.view.IChooseInterestedCategoryView;

/**
 * Created by szh on 2017/5/23.
 */

public class ChooseInterestedCategoryActivity extends TopBarBaseActivity implements IChooseInterestedCategoryView {
    @BindView(R.id.id_category_gridview)
    GridView mCategoryGridview;
    @BindView(R.id.id_submit_interested_tv)
    TextView mSubmitInterestedTv;
    private ChooseInterestedCategoryPresenter mChooseInterestedCategoryPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_choose_interested_category;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle(getString(R.string.str_iterested_category));
        setTopRightButton(getString(R.string.str_skip), new OnClickListener() {
            @Override
            public void onClick() {
                //进入主页
                startActivity(new Intent(ChooseInterestedCategoryActivity.this, MainActivity.class));
            }
        });
        mChooseInterestedCategoryPresenter = new ChooseInterestedCategoryPresenter(this, this);
        //展示兴趣列表
        mChooseInterestedCategoryPresenter.getAndShowCategoryHobbyList(mCategoryGridview);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 请求数据列表成功
     */
    @Override
    public void getCategoryHobbyListSuccess() {

    }

    @Override
    public void getCategoryHobbyListFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void submitChosenIntterestedCategorySuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        //进入下一个页面
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void submitChosenIntterestedCategoryFailed(String msg) {
        //给出提示信息
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

    @OnClick(R.id.id_submit_interested_tv)
    public void onViewClicked() {
        //先获取用户的选择结果,再提交网络请求,最后跳转
        mChooseInterestedCategoryPresenter.submitChosenInterestedCategory();
    }
}
