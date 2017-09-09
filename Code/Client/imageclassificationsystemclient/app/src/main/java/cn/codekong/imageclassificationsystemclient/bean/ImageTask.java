package cn.codekong.imageclassificationsystemclient.bean;

import java.util.List;

/**
 * Created by szh on 2017/5/26.
 * 图片任务bean
 */

public class ImageTask {
    //任务id
    private String task_id;
    private List<Images> images;

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }
}

