package cn.codekong.dao;

import java.util.List;

import cn.codekong.bean.Category;

public interface CategoryDao {
         boolean saveCatogory(Category category);
         Category findCatogoryByName(String category_name);
         Category findCategoryById(int category_id);
         List<Category> getCategotyList();
}
