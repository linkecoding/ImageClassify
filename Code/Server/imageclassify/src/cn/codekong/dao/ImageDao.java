package cn.codekong.dao;

import java.util.List; 
import cn.codekong.bean.Image;
import cn.codekong.bean.Image_Category;
import cn.codekong.bean.Image_Composition; 
import cn.codekong.bean.Zip;
import cn.codekong.bean.label.ImgUrl;

public interface ImageDao {
       
	boolean savaImage(Image image); 
	Image findImageByUrl(String img_path);
	List<Image> findImageByZip(Zip zip);
	boolean updateImage(Image image);
	int updateImageOfCLus(Image image);
	Image findImageById(int img_id);
	List<Image> getImageList();
	boolean saveImageCategory(Image_Category image_Category);
	List<Integer> seleteListOfCategory(int category_id,int user_id,String orderOfTime,String orderOfAmount);
	List<Image_Composition> getNeededAmountImages(int user_id,List<Integer> imgIds,int task_img_amount,List<Integer> categories,String orderOfTime,String orderOfAmount);
	List<Image_Composition> getPriorityImages(List<Integer> imgIds,int task_img_amount,String orderOfTime,String orderOfAmount);
	List<Image> getFinishedImages();
	long getImageAmount();
	long getTaskAmount();
	long getAmountOfDefinedTask(int amount);
	long getAmountOfDefinedImage(int is_finished);
	long getAmountOfUserSexAmount(String sex);
	long getAmountOfUser();
	List<ImgUrl> getImageByCategory(int category_id);
	int getAmountByCategoryId(int category_id);
}
