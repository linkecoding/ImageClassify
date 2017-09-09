package cn.codekong.imageclassificationsystemclient.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */

public class Task {
    private String task_id;
    private String task_start_time;
    private String task_img_amount;
    private List<String> image_path_five;
    private String img_amount;

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_start_time() {
        return task_start_time;
    }

    public void setTask_start_time(String task_start_time) {
        this.task_start_time = task_start_time;
    }

    public String getTask_img_amount() {
        return task_img_amount;
    }

    public void setTask_img_amount(String task_img_amount) {
        this.task_img_amount = task_img_amount;
    }

    public List<String> getImage_path_five() {
        return image_path_five;
    }

    public void setImage_path_five(List<String> image_path_five) {
        this.image_path_five = image_path_five;
    }

    public String getImg_amount() {
        return img_amount;
    }

    public void setImg_amount(String img_amount) {
        this.img_amount = img_amount;
    }
}
