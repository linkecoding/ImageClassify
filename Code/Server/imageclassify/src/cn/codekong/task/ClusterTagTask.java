package cn.codekong.task;

import java.util.ArrayList;
import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled; 
import cn.codekong.bean.Category;
import cn.codekong.bean.Image;
import cn.codekong.bean.Image_Category;
import cn.codekong.config.Constant;
import cn.codekong.service.CategoryService; 
import cn.codekong.service.ImageService;
import cn.codekong.tag.util.HandleTagUtil;
import cn.codekong.util.ImagesUtil; 
 
//@Component 
public class ClusterTagTask {

	@Autowired
	ImageService imageService;
	@Autowired
	CategoryService categoryService;
 
	//标签聚类处理定时任务时间
	@Scheduled(cron = Constant.CLUSTER_TAG_CRON)
	public void clusterTag(){
		//得到所有类别  
		List<Category> categories = categoryService.getCategotyList();
		List<String> category_names = new ArrayList<String>();
		ImagesUtil imagesUtil = new ImagesUtil();
		System.out.println(categories.size());
		for (int i = 0; i < categories.size(); i++) {
			//遍历 categories列表，将Category_name存到category_names列表中
			category_names.add(categories.get(i).getCategory_name()); 
		}
		
		//得到所有满足条件的Image列表
		List<Image> images = imageService.getImageList(); 
		System.out.println("images:"+images.size());
		//遍历所有图片
		for (int i = 0; i < images.size(); i++) {
			Image image = new Image();
			image = images.get(i);
			
			//获取该image的机器初始后的标签img_machine_tag_label值
			String img_machine_tag_label = image.getImg_machine_tag_label();
			//调用imagesUtil的getLabelName方法将字符串类型的img_machine_tag_label转化为String列表
			List<String> list = imagesUtil.getLabelName(img_machine_tag_label);
			//调用HandleTagUtil的clusterTag方法进行聚类，得到聚类后的类别名
			String imgCategoryname = HandleTagUtil.clusterTag(category_names, list);
			//根据类别名查到该类别
			Category category = categoryService.findCatogoryByName(imgCategoryname);
			Image_Category image_Category = new Image_Category();
			//设置为image_Category对象的Category_id和Img_id值
			image_Category.setCategory_id(category.getCategory_id());
			image_Category.setImg_id(image.getImg_id());
			//持久化到数据库中
			boolean issaved = imageService.saveImageCategory(image_Category);
			image.setIs_clustered(1);
			int isupadte = imageService.updateImageOfCLus(image); 
		}
		
	}
}
