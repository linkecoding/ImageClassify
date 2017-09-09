package com.codekong.tflibrary.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codekong.tflibrary.R;
import com.codekong.tflibrary.config.Constant;
import com.codekong.tflibrary.utils.BitmapUtil;
import com.codekong.tflibrary.utils.Classifier;
import com.codekong.tflibrary.utils.FileUtil;
import com.codekong.tflibrary.utils.TensorFlowImageClassifier;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 识别来自相册的图片
 */
public class ClassifierImgFromAlbumActivity extends TopBarBaseActivity {
    private static final int CHOOSE_IMG_FROM_ALBUM_REQUEST_CODE = 0x1234;

    //TensorFlow配置
    private static final int INPUT_SIZE = 224;
    private static final int IMAGE_MEAN = 117;
    private static final float IMAGE_STD = 1;
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "output";

    private static String MODEL_FILE;
    private static String LABEL_FILE;

    //显示从相册选择的图片
    private ImageView mShowImgImageView;
    //显示图片识别结果
    private TextView mImageRecognitionTv;

    //显示结果
    private Handler mShowResultHandler = new Handler();

    public ClassifierImgFromAlbumActivity() {
        //构造函数给模型文件路径常量赋值
        String modelFilePath = FileUtil.getExternalStoragePath(this) + File.separator
                + Constant.CLASSIFIER_IMG_MODEL_FILE_PATH;
        MODEL_FILE = modelFilePath + File.separator + Constant.CLASSIFIER_IMG_MODEL_FILE_NAME;
        LABEL_FILE = modelFilePath + File.separator + Constant.CLASSIFIER_IMG_MODEL_LABEL_FILE_NAME;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_classifier_img_from_album;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle(getString(R.string.str_image_recognition));
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mImageRecognitionTv = (TextView) findViewById(R.id.id_image_recognition_tv);

        //显示图片
        mShowImgImageView = (ImageView) findViewById(R.id.id_show_img_imageview);
        mShowImgImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImgFromAlbum();
            }
        });
    }

    /**
     * 从相册选择图片
     */
    private void chooseImgFromAlbum() {
        Intent chooseImgIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooseImgIntent.setType("image/*");
        startActivityForResult(chooseImgIntent, CHOOSE_IMG_FROM_ALBUM_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_IMG_FROM_ALBUM_REQUEST_CODE:
                //接收从相册选择的图片
                if (data == null) {
                    return;
                }
                Uri imgUri = data.getData();
                if (imgUri != null) {
                    //显示图片
                    mShowImgImageView.setImageURI(imgUri);

                    ClassifyImg(imgUri);

                }
                break;
        }
    }

    /**
     * 识别图片
     */
    private void ClassifyImg(final Uri imgUri) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = getContentResolver().openInputStream(imgUri);
                    //原始Bitmap
                    final Bitmap orgImgBitmap = BitmapFactory.decodeStream(is);
                    if (is != null) {
                        is.close();
                    }
                    int finalSize = 224;
                    final Bitmap resizedBitmap = BitmapUtil.resizeBitmap(orgImgBitmap, finalSize, finalSize);
                    //识别图片
                    Classifier classifier =
                            TensorFlowImageClassifier.create(
                                    getAssets(),
                                    MODEL_FILE,
                                    LABEL_FILE,
                                    INPUT_SIZE,
                                    IMAGE_MEAN,
                                    IMAGE_STD,
                                    INPUT_NAME,
                                    OUTPUT_NAME);

                    final List<Classifier.Recognition> result = classifier.recognizeImage(resizedBitmap);
                    mShowResultHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            //设置识别结果
                            StringBuilder stringBuilder = new StringBuilder();
                            for (Classifier.Recognition res : result) {
                                String confidence = String.format("%.1f%%", res.getConfidence() * 100.0f);
                                stringBuilder.append(res.getTitle())
                                        .append(" : ")
                                        .append(confidence)
                                .append("\r\n");
                            }
                            mImageRecognitionTv.setText(stringBuilder);

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
