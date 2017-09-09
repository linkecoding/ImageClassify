package cn.codekong.config;

public class Constant {
	//成功响应码
	public static final String QUERY_SUCCESS_RESPONSE_CODE = "200";
	public static final String QUERY_FAILED_RESPONSE_CODE = "-1";
	
    public static final String LOGIN_INFO_FAILED = "您的登录信息错误";
    public static final String LOGIN_FAILED = "登录失败"; 
    public static final String USER_LOGIN_IS_FROZEN = "对不起，因为您准确率较低，已被冻结"; 
    public static final String LOGIN_SUCCESS = "登录成功";
    public static final String MODIFY_SUCCESS = "修改成功";
    public static final String ADMIN_MODIFY_USERINFO_SUCCESS = "修改用户信息成功";
    public static final String ADMIN_MODIFY_USERINFO_FAIL = "修改用户信息失败";
    public static final String MODIFY_FAILED = "修改失败";
    public static final String ADD_SUCCESS = "添加成功";
    public static final String ADD_FAILED = "添加失败";
    public static final String ADD_CATEGORY_EXSIST = "此类别已存在";
    public static final String REGISTER_FAILED = "注册失败"; 
    public static final String REGISTER_SUCCESS = "注册成功"; 
    public static final String GETLIST_FAILED = "用户列表获取失败";
    public static final String GET_CONTRIBUTE_LIST_FAILED = "用户列表获取失败";
    public static final String GET_CATEGORY_LIST_FAILED = "用户列表获取失败";
    public static final String CHECKIN_FAILED = "打卡失败";
    public static final String CHECKIN_SUCCESS = "打卡成功";
    public static final String ISEXISTED = "已存在";
    public static final String GETCHECKINLIST_FAILED = "获取打卡记录失败";
    public static final String GETHOBBYLIST_FAILED = "获取兴趣列表失败";
    public static final String GETHOBBYLIST_SUCCESS = "获取兴趣列表成功";
    public static final String FILE_FORMATE_ZIP = "zip"; 
    public static final String FILE_FORMATE_IMAGES = "images"; 
    public static final String ZIP_FILE_FOLDER= "zip"; 
    public static final String ZIP_SUFFIX= ".zip"; 
    public static final String ZIP_BY_CATEGORY_FILE_FOLDER= "backup";
    public static final String FOLDER_TEMP= "temp";
    public static final String AVATAR_FILE_FOLDER= "avatar";
    public static final String DATE_FORMAT= "yyyy-MM-dd HH:mm:ss"; 
    public static final String DATE_FORMAT_TO_NAME= "yyyyMMddHHmmss";
    public static final String ZIP_DATE_FORMAT= "yyyy-MM-dd HH:mm";
    public static final String COMMIT_SUCCESS= "提交任务成功"; 
    public static final String COMMIT_FAIL= "提交任务失败"; 
    public static final String GET_TASK_FAIL= "获取任务失败"; 
    public static final String GET_TASK_SUCCESS= "获取任务成功"; 
    public static final String HAVA_NO_IMAGE= "没有足够图片";
    public static final String TASK_IS_NOT_SAVED= "task没有成功保存";
    //图片处理定时任务时间
    public static final String HANDLE_IMG_CRON= "0 0 2 * * ?"; 
    //标签聚类处理定时任务时间
    public static final String CLUSTER_TAG_CRON= "0 0 1 * * ?"; 
    public static final String TAG_IMG_FINISHED= "1"; 
    public static final String TAG_SEPARATOR= ";"; 
    public static final String SIGNALZUOXIE= "/";  
    public static final String PATH_SINGAL= "\\\\";
    public static final String OAUTH_TOKEN = "oauth_token";
    public static final String GET_TASK_CONMMIT_FULL = "获取未完成列表完成";
    public static final String GET_TASK_CONMMIT_FAIl = "获取未完成列表失败";

    public static final String USERNAME = "username"; 
    public static final String TOKEN = "token";
    public static final String UNFROZEN_USER_IS_NULL = "未冻结用户为空";
    public static final String FROZEN_USER_IS_NULL = "已冻结用户为空";
    public static final String MODIFY_USER_FAIl = "无法修改用户信息";
    public static final String UNZIP_SUCCESS = "解压成功";
    public static final String UNZIP_FAIL = "解压失败";
    public static final String CLASSIFY_SUCCESS = "识别成功";
    public static final String CLASSIFY_FAIL = "识别失败";
    public static final String UPLOAD_SUCCESS = "上传成功"; 
    public static final String UPLOAD_FAIL = "上传失败";
    public static final String LOGIN_EXPIRATION = "登录过期";
    public static final String AUTHENTICATION_FAIL = "鉴权失败";
    public static final String NO_EXPORT_RESULT = "所有图片已导出过,可从历史记录再次导出";
    public static final String EXPORT_FAIL = "导出错误";
    public static final String EXPORT_SUCCESS = "导出成功";
    public static final String EXPORT_FAILED = "导出历史标签结果失败";
    public static final String GET_TASK_IMAGES_OF_SQL = "SELECT image.img_path,image.img_name,image.img_id,image.img_machine_tag_label,image_mark.manual_mark_name,image_mark.option_mark_name "
    + "FROM (composition LEFT JOIN image_mark ON composition.img_id = image_mark.img_id AND image_mark.user_id = ? ) LEFT JOIN image ON composition.img_id = image.img_id WHERE composition.task_id = ?";
    public static final String HAVE_NO_CONSTANT = "没有查询到此常量";
    public static final int DEVI_NUM = 8;
    public static final String THREHOLD = "threshold"; 
    public static final String IDENTITY_TIME= "identify_time";
    public static final String IDENTIFY_FREQUENCY_MARKS= "identify_frequency_marks";
    public static final String HOUR = "hour";
    public static final String MINIT = "minit";  
    public static final String RANKIDENTIFY = "rankidentification";  
    public static final String INTEGRAL = "integral";  
    public static final String ACCURACY = "accuracy"; 
    public static final String GET_SPRO_CONFIG_INFO = "获取后台配置信息失败 "; 
    public static final String HAVE_NO_ZIP_OF_CATEGORY = "对不起，此类别没有已完成的图片可供导出";  
    public static final String IDENTITY = "identity"; 
    public static final String ZIP_FAILED = "指定所有文件未压缩成功";  
    
    //失败提示
    public static final String UPLOAD_IMG_BY_USER = "对不起，您上传失败";
    public static final String NONE_UPLOAD_IMG_BY_USER = "对不起，您没有上传记录";
    public static final String NONE_ZIP_UPLOAD_BY_USER = "预览压缩包图片失败";
    public static final String REVIEW_FAIL = "审核失败";
    public static final String OPRATE_FAIL = "操作失败";

    
    //成功提示
    public static final String REVIEW_SUCCESS = "审核成功";
    public static final String OPRATE_SUCCESS = "操作成功";

    
    
    //文件夹名
    public static final String ZIP_FOLDER_BY_USER = "contribute";
    public static final String ZIP_FOLDER_BY_USER_TEMP = "contributetemp"; 
    
}
