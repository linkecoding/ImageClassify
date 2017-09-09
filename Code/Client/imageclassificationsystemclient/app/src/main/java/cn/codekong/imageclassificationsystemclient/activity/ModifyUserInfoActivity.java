package cn.codekong.imageclassificationsystemclient.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.bean.UserTaskAmount;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.presenter.ModifyUserInfoPresenter;
import cn.codekong.imageclassificationsystemclient.util.ActivityCollector;
import cn.codekong.imageclassificationsystemclient.util.ClearImageCacheUtil;
import cn.codekong.imageclassificationsystemclient.view.IModifyUserInfoView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ModifyUserInfoActivity extends TopBarBaseActivity implements IModifyUserInfoView {

    @BindView(R.id.id_modify_user_avatar_circleview)
    CircleImageView mUserAvatarImgCircleView;
    @BindView(R.id.id_user_username_edit)
    EditText mUsernameEdit;
    @BindView(R.id.id_user_sex_tv)
    TextView mUserSexTv;

    private ModifyUserInfoPresenter mModifyUserInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_modify_user_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("修改用户信息");
        mModifyUserInfoPresenter = new ModifyUserInfoPresenter(this, this);
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        setTopRightButton("保存", new OnClickListener() {
            @Override
            public void onClick() {
                mUserAvatarImgCircleView.setDrawingCacheEnabled(true);
                Bitmap userAvatarBitmap = mUserAvatarImgCircleView.getDrawingCache();
                mUserAvatarImgCircleView.setDrawingCacheEnabled(false);
                mModifyUserInfoPresenter.modifyUserInfo(userAvatarBitmap,
                        mUsernameEdit.getText().toString(), mUserSexTv.getText().toString());
            }
        });
        mModifyUserInfoPresenter.getUserInfo();
    }

    @OnClick({R.id.id_user_avatar_modify, R.id.id_user_sex_modify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_user_avatar_modify:
                mModifyUserInfoPresenter.openSelectAvatarDialog();
                break;
            case R.id.id_user_sex_modify:
                mModifyUserInfoPresenter.setUserSex(mUserSexTv);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.CAMERA_CODE:
                //用户点击了取消
                if (data == null) {
                    return;
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        //获得拍的照片
                        Bitmap bitmap = extras.getParcelable("data");
                        mModifyUserInfoPresenter.setUserAvatarFromCamera(bitmap);
                    }
                }
                break;
            case Constant.CROP_CODE:
                if (data == null) {
                    return;
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        //获取到裁剪后的图像
                        Bitmap bm = extras.getParcelable("data");
                        if (bm != null){
                            mUserAvatarImgCircleView.setImageBitmap(bm);
                        }
                    }
                }
                break;
            case Constant.GALLERY_CODE:
                if (data == null) {
                    return;
                } else {
                    //获取到用户所选图片的Uri
                    Uri uri = data.getData();
                    mModifyUserInfoPresenter.setUserAvatarFromGallery(uri);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void modifyUserInfoSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void modifyUserInfoFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getUserInfoSuccess(UserTaskAmount userTaskAmount) {
        ClearImageCacheUtil.clearImageAllCache(this);
        Glide.with(this)
                .load(userTaskAmount.getUser().getAvatar_url())
                .dontAnimate()
                .placeholder(R.drawable.ic_user_default_avatar)
                .into(mUserAvatarImgCircleView);
        mUsernameEdit.setText(userTaskAmount.getUser().getUsername());
        if(TextUtils.isEmpty(userTaskAmount.getUser().getSex())){
            mUserSexTv.setText("");
        }else {
            mUserSexTv.setText(userTaskAmount.getUser().getSex());
        }
    }

    @Override
    public void getUserInfoFailed(String msg) {
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
}
