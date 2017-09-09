package cn.codekong.imageclassificationsystemclient.bean;

import java.util.List;

/**
 * Created by szh on 2017/5/27.
 */
public class Images {
    //image的id
    private String img_id;
    private String img_path;
    //机器识别的标签
    private List<Label> machine_labels;
    //用户选择的标签
    private List<Label> option_labels;
    //用户输入的标签
    private List<Label> manual_labels;

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public List<Label> getMachine_labels() {
        return machine_labels;
    }

    public void setMachine_labels(List<Label> machine_labels) {
        this.machine_labels = machine_labels;
    }

    public List<Label> getOption_labels() {
        return option_labels;
    }

    public void setOption_labels(List<Label> option_labels) {
        this.option_labels = option_labels;
    }

    public List<Label> getManual_labels() {
        return manual_labels;
    }

    public void setManual_labels(List<Label> manual_labels) {
        this.manual_labels = manual_labels;
    }
}
