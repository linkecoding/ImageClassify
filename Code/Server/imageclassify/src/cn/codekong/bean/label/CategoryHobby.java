package cn.codekong.bean.label;

import java.util.List;

import cn.codekong.bean.Category;

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
