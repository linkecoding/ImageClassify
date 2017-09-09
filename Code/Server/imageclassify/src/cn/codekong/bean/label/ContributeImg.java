package cn.codekong.bean.label;

/**
 * 志愿者上传贡献图片
 * @author 于晴
 *	
 */

public class ContributeImg {
    //贡献图片的时间
    private String upload_img_time;
    //贡献图片的审核状态
    private String upload_img_review_status;

    public String getUpload_img_time() {
        return upload_img_time;
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
