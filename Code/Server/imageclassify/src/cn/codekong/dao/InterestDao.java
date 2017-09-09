package cn.codekong.dao;
 
import java.util.List;

import cn.codekong.bean.Category;
import cn.codekong.bean.Interest;

public interface InterestDao {
	boolean saveInterest(Interest interest); 
	int deleteInterests(int user_id);
	int deleteInterest(int user_id,int category_id);
	List<Interest> getInterestById(int user_id);
	List<Category> getInterestCategory(List<Integer> interestIds);
}
