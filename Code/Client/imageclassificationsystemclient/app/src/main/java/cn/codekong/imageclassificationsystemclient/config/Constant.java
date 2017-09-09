package cn.codekong.imageclassificationsystemclient.config;

/**
 * Created by szh on 2017/5/14.
 * 常量字符串
 */

public class Constant {
    /*********************请求相关********************************/
    //bean的格式
    public static final String  REQUEST_CODE = "code";
    public static final String  REQUEST_MSG = "msg";

    public static final String  REQUEST_DATA = "data";
    //不存在
    public static final String  SOMETHING_DONOT_EXIST = "-1";
    public static final String  SOMETHING_EXIST = "1";
    //请求成功和失败
    public static final String  REQUEST_SUCCESS = "200";
    public static final String  REQUEST_ERROR = "-1";
    //鉴权失败(账户可能被冻结)
    public static final String  OAUTH_FAILED = "400";
    /*********************请求相关********************************/

    /*********************文件命名相关********************************/
    public static final String  SHAREDPREFERENCES_DEFAULT_NAME = "img_classify_prefs";
    public static final String LOGIN_TOKEN = "token";
    /*********************文件命名相关********************************/
    /**********************Activity之间的通信************************/
    public static final String TASK_IMG_AMOUNT = "task_img_amount";
    public static final String TASK_ID = "task_id";

    /**********************ActivityForResult的请求码************************/
    public static final int CAMERA_CODE = 1;
    public static final int GALLERY_CODE = 2;
    public static final int CROP_CODE = 3;

    public static final String AVATAR_IMG_PATH = "imageclassify";

    /**********************任务是否提交标识************************/
    public static final String TASK_IS_COMMIT = "1";
    public static final String TASK_IS_NOT_COMMIT = "0";

    /**********************用户上传的头像的名称************************/
    public static final String USER_AVATAR_NAME = "avatar.jpg";

    /**********************用户选中贡献上传的图片保存目录************************/
    public static final String CONTRIBUTE_IMG_PATH = "contribute";
    public static final String CONTRIBUTE_IMG_FOLDER = "images";
    public static final String CONTRIBUTE_IMG_ZIP_PATH = "zip";
    public static final String CONTRIBUTE_IMG_ZIP_NAME = "contribute.zip";

    /**********************图片识别模型文件存放路径及名称************************/
    //模型文件存放的文件夹
    public static final String CLASSIFIER_IMG_MODEL_FILE_PATH = "imageclassifymodel";
    //模型文件压缩包文件名
//    public static final String CLASSIFIER_IMG_MODEL_ZIPL_FILE_NAME = "imagenet_comp_graph.zip";
    public static final String CLASSIFIER_IMG_MODEL_ZIPL_FILE_NAME = "assets.zip";
    //模型文件label文件名
    public static final String CLASSIFIER_IMG_MODEL_LABEL_FILE_NAME = "imagenet_comp_graph_label_strings.txt";
    //模型文件文件名
    public static final String CLASSIFIER_IMG_MODEL_FILE_NAME = "tensorflow_inception_graph.pb";
//    public static final String CLASSIFIER_IMG_MODEL_FILE_NAME = "tencentmobilemanager_20170731141056_7.2.0_android_build4115_102027.apk";
    //下载模型文件的地址
    public static final String DOWNLOAD_CLASSIFIER_IMG_MODEL_ZIP_FILE_BASE_URL = "http://123.206.194.41/";

    //志愿者一次贡献图片的最大数量
    public static final int MAX_NUM_OF_CONTRIBUTED_IMG_EACH_TIME = 20;
}
