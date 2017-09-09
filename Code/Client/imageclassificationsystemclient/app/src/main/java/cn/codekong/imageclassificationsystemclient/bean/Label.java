package cn.codekong.imageclassificationsystemclient.bean;

/**
 * Created by szh on 2017/5/27.
 */
public class Label{
    //标签名
    private String label_name;

    public String getLabel_name() {
        return label_name;
    }

    public void setLabel_name(String label_name) {
        this.label_name = label_name;
    }

    //重写equals方法(便于在打标签页面判断备选标签是否被选中)
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Label) {
            Label label = (Label) obj;
            return this.label_name.equals(label.getLabel_name());
        }
        return super.equals(obj);
    }
}
