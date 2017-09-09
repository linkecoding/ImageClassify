package cn.codekong.bean.label;
import java.util.List;

/** 
 * 打标签结果bean
 */ 
public class TagResult {
    private String task_id;
    private List<ImgLabel> img_label;

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public List<ImgLabel> getImg_label() {
        return img_label;
    }

    public void setImg_label(List<ImgLabel> img_label) {
        this.img_label = img_label;
    }
}
