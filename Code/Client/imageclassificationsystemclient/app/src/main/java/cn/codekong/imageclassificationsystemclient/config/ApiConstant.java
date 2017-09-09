package cn.codekong.imageclassificationsystemclient.config;

/**
 * Created by szh on 2017/5/26.
 * 与后台约定的请求发送参数常量
 */

public class ApiConstant {
    //登录注册
    //用户名
    public static final String USERNAME = "username";
    public static final String SEX = "sex";
    public static final String AVATAR = "avatar";
    //手机号
    public static final String TEL_NUM = "tel_num";
    //密码
    public static final String PASSWORD = "password";

    //短信验证码
    //手机号
    public static final String MOBILE = "mobile";
    //短信验证码模板id
    public static final String TEMPLATE_ID = "templateid";
    //短信验证码
    public static final String CODE = "code";
    //类别id
    public static final String CATEGORY_ID = "category_id";
    //签到相关
    //年
    public static final String YEAR = "year";
    //月
    public static final String MONTH = "month";
    //修改密码
    //旧密码
    public static final String OLD_PWD = "oldpwd";
    //新密码
    public static final String NEW_PWD = "newpwd";
    //获取任务
    //任务图片的的数量
    public static final String TASK_IMG_AMOUNT = "task_img_amount";
    //用户token
    public static final String OAUTH_TOKEN = "oauth_token";
    //任务
    public static final String TASK = "task";
    public static final String TASK_ID = "task_id";
    public static final String IS_COMMIT = "is_commit";

    //排行榜
    //起始数
    public static final String START = "start";
    //每次请求的数目
    public static final String PAGE_NUM = "page_num";
    //排行榜排名依据(integral、accuracy、task)
    public static final String RANK_IDENTIFICATION = "rankidentification";

    //志愿者贡献的图片
    public static final String CONTRIBUTE_IMG_ZIP = "contribute_img_zip";



}
