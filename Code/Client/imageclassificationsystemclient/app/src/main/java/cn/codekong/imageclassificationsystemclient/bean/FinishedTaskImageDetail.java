package cn.codekong.imageclassificationsystemclient.bean;

/**
 * Created by Administrator on 2017/6/16.
 */

public class FinishedTaskImageDetail {
    private String user_labels;
    private String final_labels;
    private String img_path;
    private String accuracy;

    public String getUser_labels() {
        return user_labels;
    }

    public void setUser_labels(String user_labels) {
        this.user_labels = user_labels;
    }

    public String getFinal_labels() {
        return final_labels;
    }

    public void setFinal_labels(String final_labels) {
        this.final_labels = final_labels;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }
}
