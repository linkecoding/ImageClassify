package cn.codekong.dao;
 
import java.util.List;

import cn.codekong.bean.Image_Composition;
import cn.codekong.bean.Image_Mark;
import cn.codekong.bean.Mark; 

public interface MarkDao {

	boolean saveMark(Mark mark);
	boolean updateMark(Mark mark);
	boolean updateMarkAll(Mark mark);
	int deleteMark(int user_id,int img_id);
	List<Image_Mark> getComposition(int amount,int user_id);
	List<Image_Composition> getCompositionOfAll(int amount,int user_id,String identify_frequency_marks,String identify_time);
	List<Image_Mark> getInterestComposition(int amount,int user_id,List<Integer> imgIds);
	List<Integer> getImgIdAble(int thresholdValue);
	List<Image_Mark> getImageMark(int img_id); 
	List<Mark> getMarkList(int img_id);
	List<Mark> getMarkListByUserAndImage(int user_id,int img_id);
	List<Mark> getMarkListByUserAndTask(int user_id,int img_id);
	List<String> getMarkListByUserId(int user_id);
}