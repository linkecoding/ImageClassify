package cn.codekong.imageclassificationsystemclient.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import butterknife.BindView;
import butterknife.OnClick;
import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.bean.ImageTask;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.presenter.TagImagePresenter;
import cn.codekong.imageclassificationsystemclient.util.ActivityCollector;
import cn.codekong.imageclassificationsystemclient.view.ITagImageView;

public class TagImageActivity extends TopBarBaseActivity implements ITagImageView {
    private static final String TAG = "xxhh";
    @BindView(R.id.id_prev_next_btn_layout)
    LinearLayout mPrevNextBtnLayout;
    //任务中的图片数目
    private int mTaskImageAmount;
    @BindView(R.id.id_task_viewflipper)
    ViewFlipper mTaskViewFlipper;
    @BindView(R.id.id_prev_img_btn)
    TextView mPrevImgBtn;
    @BindView(R.id.id_next_img_btn)
    TextView mNextImgBtn;
    @BindView(R.id.id_submit_tag_result_textview)
    TextView mSubmitTagResultTv;
    private TagImagePresenter mTagImagePresenter;
    //滑动的开始
    private float startX = 0;
    //判断手势滑动的阈值
    private static final int scrollDistance = 50;

    @Override
    protected int getContentView() {
        return R.layout.activity_tag_image;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle(getString(R.string.str_tag_image));
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                backDialog(TagImageActivity.this);
//                Intent intent = new Intent(TagImageActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });
        mTagImagePresenter = new TagImagePresenter(this, this);
        Intent intent = getIntent();
        int amount = intent.getIntExtra(Constant.TASK_IMG_AMOUNT, 0);
        String taskId = intent.getStringExtra(Constant.TASK_ID);
        if (amount != 0) {
            //获取任务打标签
            //获得任务图片的数量
            mTaskImageAmount = amount;
            mTagImagePresenter.getImageTaskByAmount(amount + "");
            /*//TODO 模拟数据
            mTaskImageAmount = 3;
            mTagImagePresenter.getImageTaskByAmount("3");*/
        }/*else if (taskId  == null){
            mTagImagePresenter.getImageTaskByAmount("3");*/
        else if (taskId != null){
            //恢复未完成的任务或者修改没有被系统接受的标签
            //TODO 恢复任务,从网络请求数据
            mTagImagePresenter.getImageTaskById(taskId);
        }else{
            finish();
        }
        
        mTaskViewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
            Log.d(TAG, "onTouch: xx");
            return false;
            }
        });
    }

    @Override
    public void getImageTaskByAmountSuccess(ImageTask imageTask) {
        //转去设置图片任务视图
        mTagImagePresenter.setImageTask(imageTask, mTaskViewFlipper);
    }

    @Override
    public void getImageTaskByAmountFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getImageTaskByIdSuccess(ImageTask imageTask) {
        //转去设置图片任务视图
        mTagImagePresenter.setImageTask(imageTask, mTaskViewFlipper);
    }

    @Override
    public void getImageTaskByIdFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setImageTaskSuccess() {

    }

    @Override
    public void setImageTaskFailed(String msg) {

    }

    @Override
    public void backToModifyRes() {
        mPrevNextBtnLayout.setVisibility(View.VISIBLE);
        mSubmitTagResultTv.setVisibility(View.GONE);
        if (mTaskViewFlipper.getDisplayedChild() == 0){
            //第一个需要特殊处理
            mPrevImgBtn.setText(getString(R.string.str_no));
        }
    }

    @Override
    public void submitImageTaskSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        //跳转到首页
        startActivity(new Intent(TagImageActivity.this, MainActivity.class));
    }

    @Override
    public void submitImageTaskFailed(String msg) {
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

    @OnClick({R.id.id_prev_img_btn, R.id.id_next_img_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_prev_img_btn:
                mTagImagePresenter.prevView(mTaskViewFlipper, mPrevImgBtn, mNextImgBtn);
                break;
            case R.id.id_next_img_btn:
                mTagImagePresenter.nextView(mTaskViewFlipper, mPrevImgBtn, mNextImgBtn);
                if (mTaskViewFlipper.getDisplayedChild() == mTaskViewFlipper.getChildCount() - 1){
                    mPrevNextBtnLayout.setVisibility(View.GONE);
                    mSubmitTagResultTv.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @OnClick(R.id.id_submit_tag_result_textview)
    public void onViewClicked() {
        //提交结果
        mTagImagePresenter.submitImageTask(Constant.TASK_IS_COMMIT);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                startX = event.getX();
                break;
            }
            case MotionEvent.ACTION_UP: {
                //滑动响应
                if (event.getX() - startX > scrollDistance) {
                    //前一页
                    mTagImagePresenter.prevView(mTaskViewFlipper, mPrevImgBtn, mNextImgBtn);
                }
                //滑动响应
                if (startX - event.getX() > scrollDistance) {
                    //后一页
                    mTagImagePresenter.nextView(mTaskViewFlipper, mPrevImgBtn, mNextImgBtn);
                    if (mTaskViewFlipper.getDisplayedChild() == mTaskViewFlipper.getChildCount() - 1){
                        mPrevNextBtnLayout.setVisibility(View.GONE);
                        mSubmitTagResultTv.setVisibility(View.VISIBLE);
                    }
                }
                break;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        //用户按返回按钮,需要将用户的当前任务状态存入SQLite,并提交到服务端
        backDialog(this);
    }

    /**
     * 打标签中途退出
     * @param context
     */
    private void backDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.str_tasking_exit_prompt);
        builder.setTitle(R.string.str_prompt);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton(R.string.str_sure,
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    //提交任务的当前状态
                    mTagImagePresenter.submitImageTask(Constant.TASK_IS_NOT_COMMIT);
                }
            });

        builder.setNegativeButton(R.string.str_cancel,
            new android.content.DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

        builder.create().show();
    }
}
