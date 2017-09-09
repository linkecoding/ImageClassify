package cn.codekong.imageclassificationsystemclient.config;

/**
 * Created by szh on 2017/5/6.
 * 网络请求配置文件
 */

public class ApiConfig {
    //短信验证码请求地址
    public static final String SMS_VERIFY_BASE_URL = "https://api.netease.im/sms/";
    //短信验证码APP_KEY
    public static final String SMS_VERIFY_APP_KEY = "bf0fe989cc8972f35da16e5941da5719";
    //短信验证码APP_SECRET
    public static final String SMS_VERIFY_APP_SECRET = "d38bfd2aadbf";
    //短信模板id
    public static final String SMS_VERIFY_TEMPLATE_ID = "3049453";
    public static final String SMS_VERIFY_CONTENT_TYPE = "application/x-www-form-urlencoded";
    /********************服务端API**************************/
    public static final String SERVER_BASE_URL = "http://122.112.232.97/ImageClassify/";
//    public static final String SERVER_BASE_URL = "http://192.168.23.1:8080/ImageClassify/";

    /********************服务端API**************************/

    //请求分页数据默认每页10条数据
    public static final int DEAULT_NUM_PER_PAGE = 10;
}
