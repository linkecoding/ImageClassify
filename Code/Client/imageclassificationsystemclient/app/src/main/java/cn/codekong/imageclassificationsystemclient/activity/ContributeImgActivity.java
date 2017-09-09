package cn.codekong.imageclassificationsystemclient.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.bean.ContributeImg;
import cn.codekong.imageclassificationsystemclient.config.ApiConfig;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.presenter.ContributeImgPresenter;
import cn.codekong.imageclassificationsystemclient.util.ActivityCollector;
import cn.codekong.imageclassificationsystemclient.util.FileConvertUtil;
import cn.codekong.imageclassificationsystemclient.util.ZipProgressUtil;
import cn.codekong.imageclassificationsystemclient.view.IContributeImgView;

/**
 * 贡献图片Activity及贡献图片的列表
 */
public class ContributeImgActivity extends TopBarBaseActivity implements IContributeImgView {
    //选择图片请求码
    private static final int REQUEST_CODE_CHOOSE_IMG = 0x8888;

    //上传图片审核通过
    private static final String CONTRIBUTE_IMG_REVIEW_PASS = "1";
    //上传图片未审核
    private static final String CONTRIBUTE_IMG_REVIEW_PASSING = "0";
    //上传图片审核不通过
    private static final String CONTRIBUTE_IMG_REVIEW_NOT_PASS = "-1";

    @BindView(R.id.id_contribute_img_listview)
    ListView mContributeImgListview;
    @BindView(R.id.id_contribute_img_pulltorefresh_layout)
    PullToRefreshLayout mContributeImgPulltorefreshLayout;
    @BindView(R.id.id_loading_progressbar)
    ProgressBar mLoadingProgressbar;
    @BindView(R.id.id_progress_framelayout)
    FrameLayout mProgressFramelayout;

    private ContributeImgPresenter mContributeImgPresenter;
    //所有被选择的图片的uri
    private List<Uri> mSelectedImgUri;

    //当前所加载的数据的列表
    private List<ContributeImg> mContributeImgList = new ArrayList<>();
    //默认加载最前面10条数据
    private int mDefaultStart = 1;
    private CommonAdapter<ContributeImg> mContributeImgCommonAdapter;

    private Context mContext;
    //处理贡献的图片对话框
    private AlertDialog mDealWithImgDialog;
    //进度条
    private ProgressBar mDealWithImgProgressBar;
    //显示正在处理的进度
    private TextView mDealWithImgProgressTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_contribute_img;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle(getString(R.string.str_contribute_img_list));
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        setTopRightButton(getString(R.string.str_contribute_img), new OnClickListener() {
            @Override
            public void onClick() {
                //打开图片选择器
                Matisse.from(ContributeImgActivity.this)
                        .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF))
                        .countable(true)
                        .theme(R.style.Matisse_Dracula)
                        .maxSelectable(Constant.MAX_NUM_OF_CONTRIBUTED_IMG_EACH_TIME)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE_IMG);
                ;
            }
        });

        //上拉刷新和下拉加载监听
        mContributeImgPulltorefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                mContributeImgPresenter.getContributeImgList(mDefaultStart + "", ApiConfig.DEAULT_NUM_PER_PAGE + "");
            }

            @Override
            public void loadMore() {
                mContributeImgPresenter.getContributeImgList((mContributeImgList.size() + 1) + "", ApiConfig.DEAULT_NUM_PER_PAGE + "");
            }
        });
        mContributeImgPresenter = new ContributeImgPresenter(this, this);
        //加载当前贡献图片的列表
        mContributeImgPresenter.getContributeImgList(mDefaultStart + "", ApiConfig.DEAULT_NUM_PER_PAGE + "");
    }

    @Override
    public void getContributeImgListSuccess(List<ContributeImg> contributeImgList) {
        this.mContributeImgList = contributeImgList;
        //设置排行榜信息到ListView
        setDataToList(contributeImgList);
        mContributeImgCommonAdapter.notifyDataSetChanged();
        mContributeImgPulltorefreshLayout.finishRefresh();
    }

    @Override
    public void getContributeImgListFailed(String msg) {
        //给出出错信息
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        mContributeImgPulltorefreshLayout.finishRefresh();
        mContributeImgPulltorefreshLayout.finishLoadMore();
    }

    @Override
    public void loadMoreContributeImgSuccess(List<ContributeImg> contributeImgList) {
        this.mContributeImgList.addAll(contributeImgList);
        setDataToList(mContributeImgList);
        mContributeImgCommonAdapter.notifyDataSetChanged();
        mContributeImgPulltorefreshLayout.finishLoadMore();
        mContributeImgListview.setSelection(mContributeImgList.size());
    }

    @Override
    public void uploadContributeImgSuccess(String msg) {
        Toast.makeText(this, R.string.str_upload_success, Toast.LENGTH_SHORT).show();
        mDealWithImgDialog.dismiss();
        mContributeImgPresenter.getContributeImgList(mDefaultStart + "", ApiConfig.DEAULT_NUM_PER_PAGE + "");
    }

    @Override
    public void uploadContributeImgFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        mDealWithImgDialog.dismiss();
    }

    @Override
    public void updataUploadZipProgress(long total, long progress) {
        mLoadingProgressbar.setProgress((int) (progress / total * 100));
        mDealWithImgProgressTv.setText((progress / total * 100) + "%");
    }

    @Override
    public void validateError(String msg) {
        //token失效
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        ActivityCollector.finishAll();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE_IMG && resultCode == RESULT_OK) {
            mSelectedImgUri = Matisse.obtainResult(data);
            if (mSelectedImgUri.size() > 0) {
                //将contentUri转为FileUri
                //先压缩图片
                dealWithImg();
            }
        }
    }

    /**
     * 处理图片(转化、压缩、上传)
     */
    private void dealWithImg() {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return;
        }

        final String sourcePath = Environment.getExternalStorageDirectory()
                + File.separator + Constant.AVATAR_IMG_PATH
                + File.separator + Constant.CONTRIBUTE_IMG_PATH
                + File.separator + Constant.CONTRIBUTE_IMG_FOLDER;
        final String outPath = Environment.getExternalStorageDirectory()
                + File.separator + Constant.AVATAR_IMG_PATH
                + File.separator + Constant.CONTRIBUTE_IMG_PATH
                + File.separator + Constant.CONTRIBUTE_IMG_ZIP_PATH;
        final String imgZipName = Constant.CONTRIBUTE_IMG_ZIP_NAME;
        ZipProgressUtil.ZipFile(sourcePath, outPath,
                Constant.CONTRIBUTE_IMG_ZIP_NAME, new ZipProgressUtil.ZipListener() {
                    @Override
                    public void zipStart() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mLoadingProgressbar.setVisibility(View.VISIBLE);
                                mProgressFramelayout.setVisibility(View.VISIBLE);
                            }
                        });
                        //进行contentUri到FileUri的转化
                        for (int i = 0; i < mSelectedImgUri.size(); i++) {
                            FileConvertUtil.ContentUriToFileUri(mContext, mSelectedImgUri.get(i), Constant.AVATAR_IMG_PATH
                                    + File.separator + Constant.CONTRIBUTE_IMG_PATH
                                    + File.separator + Constant.CONTRIBUTE_IMG_FOLDER, i + ".jpg");
                        }
                    }

                    @Override
                    public void zipSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //删除图片文件夹
                                FileConvertUtil.deleteDir(new File(sourcePath));
                                Toast.makeText(mContext, "压缩完成", Toast.LENGTH_SHORT).show();
                                mLoadingProgressbar.setVisibility(View.GONE);
                                mProgressFramelayout.setVisibility(View.GONE);
                                showDealWithDialog(outPath + File.separator + imgZipName);
                            }
                        });
                    }

                    @Override
                    public void zipProgress(final int progress) {
                    }

                    @Override
                    public void zipFail() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "压缩失败", Toast.LENGTH_SHORT).show();
                                mLoadingProgressbar.setVisibility(View.GONE);
                                mProgressFramelayout.setVisibility(View.GONE);
                            }
                        });
                    }
                });
    }

    /**
     * 给ListView设置值
     *
     * @param dataToList
     */
    public void setDataToList(List<ContributeImg> dataToList) {
        mContributeImgCommonAdapter = new CommonAdapter<ContributeImg>(this, R.layout.contribute_img_item, mContributeImgList) {
            @Override
            protected void convert(ViewHolder viewHolder, ContributeImg item, int position) {
                //图片上传时间
                viewHolder.setText(R.id.id_image_upload_time_tv, item.getUpload_img_time());
                String reviewStatus = item.getUpload_img_review_status();
                if (!TextUtils.isEmpty(reviewStatus)) {
                    if (reviewStatus.equals(CONTRIBUTE_IMG_REVIEW_PASS)) {
                        //审核通过
                        viewHolder.setText(R.id.id_image_upload_review_status_tv, getString(R.string.str_review_pass));
                        viewHolder.setTextColor(R.id.id_image_upload_review_status_tv, R.color.colorTheme);
                    } else if (reviewStatus.equals(CONTRIBUTE_IMG_REVIEW_PASSING)) {
                        //真正审核
                        viewHolder.setText(R.id.id_image_upload_review_status_tv, getString(R.string.str_waiting_to_review));
                        viewHolder.setTextColor(R.id.id_image_upload_review_status_tv, R.color.colorBlack);
                    } else {
                        //审核不通过
                        viewHolder.setText(R.id.id_image_upload_review_status_tv, getString(R.string.str_review_not_pass));
                        viewHolder.setTextColor(R.id.id_image_upload_review_status_tv, Color.RED);
                    }
                }
            }
        };
        mContributeImgListview.setAdapter(mContributeImgCommonAdapter);
    }

    /**
     * 展示处理对话框
     */
    private void showDealWithDialog(String zipFilePath) {
        //显示下载对话框
        AlertDialog.Builder dealwithImgBuilder = new AlertDialog.Builder(this);
        dealwithImgBuilder.setTitle(R.string.str_dealwith_upload_img);
        dealwithImgBuilder.setMessage(R.string.str_uploading);
        dealwithImgBuilder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dealwithView = inflater.inflate(R.layout.download_file_view, null);

        mDealWithImgProgressBar = (ProgressBar) dealwithView.findViewById(R.id.id_download_modelfile_progress);
        mDealWithImgProgressTv = (TextView) dealwithView.findViewById(R.id.id_download_modelfile_progress_tv);

        dealwithImgBuilder.setView(dealwithView);
        dealwithImgBuilder.setNegativeButton(getString(R.string.str_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mContributeImgPresenter.cancelUploadContributeImg();
            }
        });

        mDealWithImgDialog = dealwithImgBuilder.show();
        //开始图片上传任务
        mContributeImgPresenter.uploadContributeImg(zipFilePath);
    }
}
