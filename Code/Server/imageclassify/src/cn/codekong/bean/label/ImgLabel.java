package cn.codekong.bean.label;

/** 
 * 图片标签bean
 */
public class ImgLabel{
    private String img_id;
    private String option_label;
    private String manual_label;

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public String getOption_label() {
        return option_label;
    }

    public void setOption_label(String option_label) {
        this.option_label = option_label;
    }

    public String getManual_label() {
        return manual_label;
    }

    public void setManual_label(String manual_label) {
        this.manual_label = manual_label;
    }
}
