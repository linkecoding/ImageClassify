package cn.codekong.imageclassificationsystemclient.bean;

import java.util.List;

/**
 * 所有的分类和用户选择的兴趣
 * Created by szh on 2017/5/24.
 */

public class CategoryHobby {
    private List<Category> categories;
    private List<Category> hobbies;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Category> hobbies) {
        this.hobbies = hobbies;
    }
}
