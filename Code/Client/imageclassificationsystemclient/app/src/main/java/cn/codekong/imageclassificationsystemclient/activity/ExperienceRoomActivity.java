package cn.codekong.imageclassificationsystemclient.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codekong.tflibrary.activity.CameraClassifierActivity;
import com.codekong.tflibrary.activity.ClassifierImgFromAlbumActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;
import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.util.ZipProgressUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 体验室(用户可以体验使用手机识别摄像头或者相册的图片的功能)
 */
public class ExperienceRoomActivity extends TopBarBaseActivity {
    private static final String TAG = "ExperienceRoomActivity";

    @BindView(R.id.id_identify_img_from_album_tv)
    TextView mIdentifyImgFromAlbumTv;
    @BindView(R.id.id_identify_from_camera_realtime_tv)
    TextView mIdentifyFromCameraRealtimeTv;

    //下载进度
    private ProgressBar mDownloadFileProgressBar;
    //下载zip文件请求
    private Call mDownloadModelFileCall;
    //下载文件对话框
    private AlertDialog mDownloadFileDialog;

    //下载进度百分比显示
    private TextView mDownloadModelfileProgressTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_experience_room;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle(getString(R.string.str_exoerience_room));
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        setTopRightButton(getString(R.string.str_update_model_file), new OnClickListener() {
            @Override
            public void onClick() {
                //更新模型文件
                updateModelFile();
            }
        });
    }


    /**
     * 更新模型文件
     */
    private void updateModelFile() {
        //显示下载对话框
        AlertDialog.Builder downloadFileDialogBuilder;
        downloadFileDialogBuilder = new AlertDialog.Builder(this);
        downloadFileDialogBuilder.setTitle(getString(R.string.str_download_model_file));
        downloadFileDialogBuilder.setMessage(R.string.str_downloading);
        downloadFileDialogBuilder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(this);
        View downloadFileView = inflater.inflate(R.layout.download_file_view, null);

        mDownloadFileProgressBar = (ProgressBar) downloadFileView.findViewById(R.id.id_download_modelfile_progress);
        mDownloadModelfileProgressTv = (TextView) downloadFileView.findViewById(R.id.id_download_modelfile_progress_tv);

        downloadFileDialogBuilder.setView(downloadFileView);
        downloadFileDialogBuilder.setNegativeButton(getString(R.string.str_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mDownloadModelFileCall != null && !mDownloadModelFileCall.isCanceled()){
                    mDownloadModelFileCall.cancel();
                }
            }
        });

        mDownloadFileDialog = downloadFileDialogBuilder.show();
        //下载文件
        downloadModelFileAndLabelFile();
    }

    @OnClick({R.id.id_identify_img_from_album_tv, R.id.id_identify_from_camera_realtime_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_identify_img_from_album_tv:
                if (!checkModelFileExist()) {
                    showAskDownloadDialog();
                } else {
                    //从相册获取图片并识别
                    Intent fromAlbumActivityIntent = new Intent(ExperienceRoomActivity.this, ClassifierImgFromAlbumActivity.class);
                    startActivity(fromAlbumActivityIntent);
                }
                break;
            case R.id.id_identify_from_camera_realtime_tv:
                if (!checkModelFileExist()) {
                    showAskDownloadDialog();
                } else {
                    //摄像头实时识别图片
                    Intent fromCameraActivityIntent = new Intent(ExperienceRoomActivity.this, CameraClassifierActivity.class);
                    startActivity(fromCameraActivityIntent);
                }
                break;
        }
    }

    /**
     * 检查图像识别的模型文件是否存在
     */
    private boolean checkModelFileExist() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return false;
        } else {
            String modelFilePathName = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + Constant.CLASSIFIER_IMG_MODEL_FILE_PATH;
            String modelLabelFileName = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + Constant.CLASSIFIER_IMG_MODEL_FILE_PATH + File.separator
                    + Constant.CLASSIFIER_IMG_MODEL_LABEL_FILE_NAME;
            String modelFileName = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + Constant.CLASSIFIER_IMG_MODEL_FILE_PATH + File.separator
                    + Constant.CLASSIFIER_IMG_MODEL_FILE_NAME;

            File modelFilePath = new File(modelFilePathName);
            File modelLabelFile = new File(modelLabelFileName);
            File modelFile = new File(modelFileName);
            if (modelFile.exists() && modelLabelFile.exists()) {
                //模型文件和标签文件都存在
                return true;
            }
        }
        return false;
    }

    /**
     * 询问用户是否需要下载模型
     */
    private void showAskDownloadDialog() {
        final AlertDialog.Builder askDialog = new AlertDialog.Builder(this);
        askDialog.setTitle(R.string.str_download_model_file);
        askDialog.setMessage(R.string.str_model_file_not_exist);
        askDialog.setPositiveButton(getString(R.string.str_download), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //开启子线程下载模型文件和标签文件
                updateModelFile();
            }
        });

        askDialog.setNegativeButton(getString(R.string.str_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        askDialog.setCancelable(false);
        askDialog.show();
    }

    /**
     * 开启子线程下载模型文件和标签文件
     */
    private void downloadModelFileAndLabelFile() {
        String modelZipFileUrl = Constant.DOWNLOAD_CLASSIFIER_IMG_MODEL_ZIP_FILE_BASE_URL + Constant.CLASSIFIER_IMG_MODEL_ZIPL_FILE_NAME;

        OkHttpClient client = new OkHttpClient();
        final Request downloadModelRequest = new Request.Builder()
                .get()
                .url(modelZipFileUrl)
                .build();
        mDownloadModelFileCall = client.newCall(downloadModelRequest);
        mDownloadModelFileCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ExperienceRoomActivity.this, R.string.str_download_file_failed, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //检查存储卡
                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    return;
                }
                //模型存储位置
                final String modelFilePathName = Environment.getExternalStorageDirectory().getPath() + File.separator
                        + Constant.CLASSIFIER_IMG_MODEL_FILE_PATH + File.separator;
                final File modelFilePath = new File(modelFilePathName);
                if (!modelFilePath.exists()){
                    //创建存储目录
                    modelFilePath.mkdirs();
                }
                final File modelFile = new File(modelFilePath, Constant.CLASSIFIER_IMG_MODEL_ZIPL_FILE_NAME);
                //拿到字节流
                InputStream is = response.body().byteStream();

                //文件总大小
                final long modelFileTotalLength = response.body().contentLength();
                //显示当前文件大小
                long currentModelFileLength = 0;

                int modelFileLen = 0;
                FileOutputStream fos = new FileOutputStream(modelFile);
                byte[] buf = new byte[128];

                while ((modelFileLen = is.read(buf)) != -1) {
                    fos.write(buf, 0, modelFileLen);
                    currentModelFileLength += modelFileLen;
                    final int progress = (int) ((currentModelFileLength * 1.0 / modelFileTotalLength) * 100);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mDownloadFileProgressBar.setProgress(progress);
                            mDownloadModelfileProgressTv.setText(progress + "%");
                            //下载完成
                            if (progress == 100){
                                Toast.makeText(ExperienceRoomActivity.this, getString(R.string.str_download_over), Toast.LENGTH_SHORT).show();

                                String zipFilePath = modelFilePathName + Constant.CLASSIFIER_IMG_MODEL_ZIPL_FILE_NAME;
                                unzipModelFile(zipFilePath, modelFilePathName);
                            }
                        }
                    });
                }

                fos.flush();
                //关闭流
                fos.close();
                is.close();
            }
        });
    }

    /**
     * 解压模型文件
     * @param zipFilePath
     * @param modelFilePathName
     */
    private void unzipModelFile(String zipFilePath, String modelFilePathName) {
        ZipProgressUtil.UnZipFile(zipFilePath, modelFilePathName, new ZipProgressUtil.ZipListener() {
            @Override
            public void zipStart() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDownloadFileProgressBar.setProgress(0);
                        mDownloadModelfileProgressTv.setText("0%");
                    }
                });

            }

            @Override
            public void zipSuccess() {
                showToast(getString(R.string.str_unzip_success));
            }

            @Override
            public void zipProgress(final int progress) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDownloadFileProgressBar.setProgress(progress);
                        mDownloadModelfileProgressTv.setText(progress + "%");
                    }
                });

            }

            @Override
            public void zipFail() {
                showToast(getString(R.string.str_unzip_failed));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDownloadModelFileCall != null && !mDownloadModelFileCall.isCanceled()){
            mDownloadModelFileCall.cancel();
        }
    }

    public void showToast(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ExperienceRoomActivity.this, msg, Toast.LENGTH_SHORT).show();
                mDownloadFileDialog.dismiss();
            }
        });
    }
}

