package cn.codekong.dao;
 
import java.util.List;

import cn.codekong.bean.Composition;
import cn.codekong.bean.Image; 
import cn.codekong.bean.label.UserRank; 

public interface CompositionDao {

	boolean saveComp(Composition composition);
	int update(Composition composition);
	List<Integer> getComposition(int task_id);
	Composition getComposition(int task_id,int img_id);
	List<Image> getTaskOfImages(int task_id);
	String getUnconfirmedOfImages(int task_id);
	List<Image> getConfirmedOfImages(int task_id);
	List<UserRank> getRankListOfAll(int start,int num,String rankidentification);
	List<UserRank> getRankListOfAmountTask(int start,int num);
}
