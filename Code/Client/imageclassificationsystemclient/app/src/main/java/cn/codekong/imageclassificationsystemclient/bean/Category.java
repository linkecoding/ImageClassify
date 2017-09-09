package cn.codekong.imageclassificationsystemclient.bean;

/**
 * 分类bean
 * Created by 尚振鸿 on 2017/6/15. 21:40
 * mail:szh@codekong.cn
 */

public class Category {
    private String category_id;
    private String category_name;

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Category){
            Category category = (Category) obj;
            return this.category_name.equals(category.getCategory_name());
        }
        return super.equals(obj);
    }
}
