package cn.codekong.imageclassificationsystemclient.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 贡献图片Bean
 * Created by 尚振鸿 on 2017/8/21. 20:29
 * mail:szh@codekong.cn
 */

public class ContributeImg {
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    //贡献图片的时间
    private String upload_img_time;
    //贡献图片的审核状态
    private String upload_img_review_status;

    public String getUpload_img_time() {
        return mSimpleDateFormat.format(new Date(upload_img_time));
    }

    public void setUpload_img_time(String upload_img_time) {
        this.upload_img_time = upload_img_time;
    }

    public String getUpload_img_review_status() {
        return upload_img_review_status;
    }

    public void setUpload_img_review_status(String upload_img_review_status) {
        this.upload_img_review_status = upload_img_review_status;
    }
}
