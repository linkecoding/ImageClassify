package cn.codekong.imageclassificationsystemclient.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.File;

import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.activity.ModifyUserInfoActivity;
import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.bean.UserTaskAmount;
import cn.codekong.imageclassificationsystemclient.config.ApiConfig;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.net.RtHttp;
import cn.codekong.imageclassificationsystemclient.service.UserInfoService;
import cn.codekong.imageclassificationsystemclient.util.FileConvertUtil;
import cn.codekong.imageclassificationsystemclient.util.SaveDataUtil;
import cn.codekong.imageclassificationsystemclient.view.IModifyUserInfoView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/5/30.
 */

public class ModifyUserInfoPresenter implements IModifyUserInfoPresenter {

    private Context mContext;
    private IModifyUserInfoView iModifyUserInfoView;

    public ModifyUserInfoPresenter(Context mContext, IModifyUserInfoView modifyUserInfoView) {
        this.mContext = mContext;
        this.iModifyUserInfoView = modifyUserInfoView;
    }

    /**
     * 网络请求获取用户信息
     */
    @Override
    public void getUserInfo() {
        String token = SaveDataUtil.getValueFromSharedPreferences(mContext, Constant.SHAREDPREFERENCES_DEFAULT_NAME, ApiConstant.OAUTH_TOKEN);
        if (!TextUtils.isEmpty(token)) {
            UserInfoService userInfoService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, UserInfoService.class);
            userInfoService.getUserInfo(token).enqueue(new Callback<HttpResult<UserTaskAmount>>() {
                @Override
                public void onResponse(Call<HttpResult<UserTaskAmount>> call, Response<HttpResult<UserTaskAmount>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        HttpResult<UserTaskAmount> body = response.body();
                        if (body.getCode().equals(Constant.REQUEST_SUCCESS)) {
                            iModifyUserInfoView.getUserInfoSuccess(body.getData());
                        } else if (response.body().getCode().equals(Constant.OAUTH_FAILED)) {
                            //账户被冻结
                            iModifyUserInfoView.validateError(response.body().getMsg());
                        } else {
                            iModifyUserInfoView.getUserInfoFailed(body.getMsg());
                        }
                    } else {
                        //服务器错误
                        iModifyUserInfoView.getUserInfoFailed(mContext.getResources().getString(R.string.str_server_error));
                    }
                }

                @Override
                public void onFailure(Call<HttpResult<UserTaskAmount>> call, Throwable t) {
                    iModifyUserInfoView.getUserInfoFailed(mContext.getResources().getString(R.string.str_network_error));
                }
            });
        } else {
            //token存在问题
            iModifyUserInfoView.validateError(mContext.getResources().getString(R.string.str_login_status_error));
        }
    }

    /**
     * 弹出选择用户头像的对话框
     */
    @Override
    public void openSelectAvatarDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_select_avatar, null);
        final PopupWindow popupWindow = getPopupWindow(view);
        //设置点击事件
        TextView cameraTextView = (TextView) view.findViewById(R.id.id_take_camera);
        TextView selectAvatar = (TextView) view.findViewById(R.id.id_select_from_photo_album);
        cameraTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeCamera();
                popupWindow.dismiss();
            }
        });
        selectAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                popupWindow.dismiss();
            }
        });
        View parent = LayoutInflater.from(mContext).inflate(R.layout.activity_modify_user_info, null);
        //显示PopupWindow
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 打开相册
     */
    private void openGallery() {
        //构建隐式Intent
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //调用系统相机
        ModifyUserInfoActivity modifyUserInfoActivity = (ModifyUserInfoActivity) mContext;
        modifyUserInfoActivity.startActivityForResult(intent, Constant.GALLERY_CODE);
    }

    /**
     * 调用系统相机
     */
    private void takeCamera() {
        //构建隐式Intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //调用系统相机
        ModifyUserInfoActivity modifyUserInfoActivity = (ModifyUserInfoActivity) mContext;
        modifyUserInfoActivity.startActivityForResult(intent, Constant.CAMERA_CODE);
    }

    /**
     * 通过Uri传递图像信息以供裁剪
     *
     * @param uri
     */
    private void startImageZoom(Uri uri) {
        //构建隐式Intent来启动裁剪程序
        Intent intent = new Intent("com.android.camera.action.CROP");
        //设置数据uri和类型为图片类型
        intent.setDataAndType(uri, "image/*");
        //显示View为可裁剪的
        intent.putExtra("crop", true);
        //裁剪的宽高的比例为1:1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //输出图片的宽高均为150
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        //裁剪之后的数据是通过Intent返回
        intent.putExtra("return-data", true);
        //调用系统相机
        ModifyUserInfoActivity modifyUserInfoActivity = (ModifyUserInfoActivity) mContext;
        modifyUserInfoActivity.startActivityForResult(intent, Constant.CROP_CODE);
    }


    /**
     * 网络请求并修改用户信息
     *
     * @param avatarBitmap
     * @param username
     * @param sex
     */
    @Override
    public void modifyUserInfo(Bitmap avatarBitmap, String username, String sex) {
        if (TextUtils.isEmpty(username)) {
            iModifyUserInfoView.modifyUserInfoFailed("用户名不能为空！");
            return;
        }
        String token = SaveDataUtil.getValueFromSharedPreferences(mContext, Constant.SHAREDPREFERENCES_DEFAULT_NAME, ApiConstant.OAUTH_TOKEN);
        if (!TextUtils.isEmpty(token)) {
            UserInfoService userInfoService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, UserInfoService.class);
            File avatarFile = new File(Environment.getExternalStorageDirectory() + "/" + Constant.AVATAR_IMG_PATH + "/avatar.jpg");
            final RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(ApiConstant.OAUTH_TOKEN, token)
                    .addFormDataPart(ApiConstant.USERNAME, username)
                    .addFormDataPart(ApiConstant.SEX, sex)
                    .addFormDataPart(ApiConstant.AVATAR, avatarFile.getName(), RequestBody.create(MediaType.parse("image/*"), avatarFile))
                    .build();
            userInfoService.modifyUserInfo(requestBody).enqueue(new Callback<HttpResult<String>>() {
                @Override
                public void onResponse(Call<HttpResult<String>> call, Response<HttpResult<String>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        HttpResult<String> body = response.body();
                        if (body.getCode().equals(Constant.REQUEST_SUCCESS)) {
                            iModifyUserInfoView.modifyUserInfoSuccess(body.getMsg());
                        } else if (response.body().getCode().equals(Constant.OAUTH_FAILED)) {
                            //账户被冻结
                            iModifyUserInfoView.validateError(response.body().getMsg());
                        } else {
                            iModifyUserInfoView.modifyUserInfoFailed(body.getMsg());
                        }
                    } else {
                        iModifyUserInfoView.modifyUserInfoFailed(mContext.getResources().getString(R.string.str_server_error));
                    }
                }

                @Override
                public void onFailure(Call<HttpResult<String>> call, Throwable t) {
                    iModifyUserInfoView.modifyUserInfoFailed(mContext.getResources().getString(R.string.str_network_error));
                }
            });
        } else {
            iModifyUserInfoView.validateError(mContext.getResources().getString(R.string.str_login_status_error));
        }
    }

    /**
     * 设置用户性别
     *
     * @param sexView
     */
    @Override
    public void setUserSex(final TextView sexView) {
        //加载对话框布局文件
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_select_sex, null);
        final PopupWindow popupWindow = getPopupWindow(view);
        //设置点击事件
        final TextView femaleTextView = (TextView) view.findViewById(R.id.id_user_sex_female);
        TextView maleTextView = (TextView) view.findViewById(R.id.id_user_sex_male);
        femaleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v;
                sexView.setText(textView.getText().toString());
                popupWindow.dismiss();
            }
        });
        maleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v;
                sexView.setText(textView.getText().toString());
                popupWindow.dismiss();
            }
        });
        View parent = LayoutInflater.from(mContext).inflate(R.layout.activity_modify_user_info, null);
        //显示PopupWindow
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 将拍照结果进行处理，并进行剪裁
     *
     * @param bitmap
     */
    @Override
    public void setUserAvatarFromCamera(Bitmap bitmap) {
        //将Bitmap转化为uri
        Uri uri = FileConvertUtil.saveBitmap(bitmap, Constant.AVATAR_IMG_PATH, Constant.USER_AVATAR_NAME);
        //启动图像裁剪
        startImageZoom(uri);
    }

    /**
     * 将图库中选择的图片进行剪裁
     *
     * @param uri
     */
    @Override
    public void setUserAvatarFromGallery(Uri uri) {
        //返回的Uri为content类型的Uri,不能进行复制等操作,需要转换为文件Uri
        uri = FileConvertUtil.ContentUriToFileUri(mContext, uri, Constant.AVATAR_IMG_PATH, Constant.USER_AVATAR_NAME);
        //启动图像裁剪
        startImageZoom(uri);
    }

    /**
     * 生成并设置PopupWindow对话框
     *
     * @param view
     * @return
     */
    private PopupWindow getPopupWindow(View view) {
        PopupWindow popupWindow = new PopupWindow(mContext);
        popupWindow.setContentView(view);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        return popupWindow;
    }
}


interface IModifyUserInfoPresenter {
    void getUserInfo();

    void openSelectAvatarDialog();

    void modifyUserInfo(Bitmap avatarBitmap, String username, String sex);

    void setUserSex(TextView sexView);

    void setUserAvatarFromCamera(Bitmap bitmap);

    void setUserAvatarFromGallery(Uri uri);
}
