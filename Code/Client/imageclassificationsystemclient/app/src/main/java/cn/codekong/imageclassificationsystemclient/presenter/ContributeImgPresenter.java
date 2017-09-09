package cn.codekong.imageclassificationsystemclient.presenter;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.util.List;

import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.bean.ContributeImg;
import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.config.ApiConfig;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.net.RetrofitCallback;
import cn.codekong.imageclassificationsystemclient.net.RtHttp;
import cn.codekong.imageclassificationsystemclient.service.ContributeImgService;
import cn.codekong.imageclassificationsystemclient.util.SaveDataUtil;
import cn.codekong.imageclassificationsystemclient.view.IContributeImgView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 尚振鸿 on 2017/8/21. 19:36
 * mail:szh@codekong.cn
 */

public class ContributeImgPresenter implements IContributeImgPresenter {
    private Context mContext;
    private IContributeImgView iContributeImgView;
    private Call<HttpResult<String>> uploadZipCall;

    public ContributeImgPresenter(Context context, IContributeImgView iContributeImgView) {
        this.mContext = context;
        this.iContributeImgView = iContributeImgView;
    }

    @Override
    public void getContributeImgList(final String start, String pageNum) {
        String token = SaveDataUtil.getValueFromSharedPreferences(mContext, Constant.SHAREDPREFERENCES_DEFAULT_NAME,
                ApiConstant.OAUTH_TOKEN);
        if (!TextUtils.isEmpty(token)) {
            //发送网络请求
            ContributeImgService contributeImgService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, ContributeImgService.class);
            contributeImgService.getContributeImgList(token, start, pageNum).enqueue(new Callback<HttpResult<List<ContributeImg>>>() {
                @Override
                public void onResponse(Call<HttpResult<List<ContributeImg>>> call, Response<HttpResult<List<ContributeImg>>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        HttpResult<List<ContributeImg>> body = response.body();
                        if (body.getCode().equals(Constant.REQUEST_SUCCESS)) {
                            //获取数据成功
                            List<ContributeImg> contributeImgList = body.getData();
                            //=1为首次获取数据
                            if (start.equals("1")) {
                                iContributeImgView.getContributeImgListSuccess(contributeImgList);
                            } else {
                                iContributeImgView.loadMoreContributeImgSuccess(contributeImgList);
                            }
                        } else if (body.getCode().equals(Constant.OAUTH_FAILED)) {
                            //账户被冻结
                            iContributeImgView.validateError(body.getMsg());
                        } else {
                            iContributeImgView.getContributeImgListFailed(body.getMsg());
                        }
                    } else {
                        //服务器错误
                        iContributeImgView.validateError(mContext.getResources().getString(R.string.str_server_error));
                    }
                }

                @Override
                public void onFailure(Call<HttpResult<List<ContributeImg>>> call, Throwable t) {
                    iContributeImgView.getContributeImgListFailed(mContext.getString(R.string.str_network_error));
                }
            });
        } else {
            //token不存在,重新登录
            iContributeImgView.validateError(mContext.getString(R.string.str_login_status_error));
        }
    }

    @Override
    public void uploadContributeImg(String zipFilePath) {
        String token = SaveDataUtil.getValueFromSharedPreferences(mContext, Constant.SHAREDPREFERENCES_DEFAULT_NAME, ApiConstant.OAUTH_TOKEN);
        if (!TextUtils.isEmpty(token)) {
            ContributeImgService contributeImgService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, ContributeImgService.class);
            File zipFile = new File(zipFilePath);
            final RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(ApiConstant.OAUTH_TOKEN, token)
                    .addFormDataPart(ApiConstant.CONTRIBUTE_IMG_ZIP, zipFile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), zipFile))
                    .build();
            uploadZipCall = contributeImgService.contributeImg(requestBody);
            uploadZipCall.enqueue(new RetrofitCallback<HttpResult<String>>() {
                @Override
                public void onSuccess(Call<HttpResult<String>> call, Response<HttpResult<String>> response) {
                    if (response.body() != null) {
                        HttpResult<String> body = response.body();
                        if (body.getCode().equals(Constant.REQUEST_SUCCESS)) {
                            iContributeImgView.uploadContributeImgSuccess(body.getMsg());
                        } else if (response.body().getCode().equals(Constant.OAUTH_FAILED)) {
                            //账户被冻结
                            iContributeImgView.validateError(response.body().getMsg());
                        } else {
                            iContributeImgView.uploadContributeImgFailed(body.getMsg());
                        }
                    } else {
                        iContributeImgView.uploadContributeImgFailed(mContext.getResources().getString(R.string.str_server_error));
                    }
                }

                @Override
                public void onLoading(long total, long progress) {
                    iContributeImgView.updataUploadZipProgress(total, progress);
                }

                @Override
                public void onFailure(Call<HttpResult<String>> call, Throwable t) {
                    iContributeImgView.uploadContributeImgFailed(
                            mContext.getResources().getString(R.string.str_network_error));
                }
            });
        } else {
            iContributeImgView.validateError(mContext.getResources().getString(R.string.str_login_status_error));
        }
    }

    @Override
    public void cancelUploadContributeImg() {
        if (!uploadZipCall.isCanceled()){
            uploadZipCall.cancel();
        }
    }
}

interface IContributeImgPresenter {
    //获取贡献图片的列表
    void getContributeImgList(String start, String num);

    //上传贡献的图片压缩包
    void uploadContributeImg(String zipFilePath);

    //取消上传图片
    void cancelUploadContributeImg();
}

