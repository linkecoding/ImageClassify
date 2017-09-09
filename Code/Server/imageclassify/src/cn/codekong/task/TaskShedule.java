package cn.codekong.task;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.codekong.bean.Image;
import cn.codekong.bean.Image_Mark;
import cn.codekong.bean.Mark;
import cn.codekong.bean.User;
import cn.codekong.config.Constant;
import cn.codekong.service.ConstantService;
import cn.codekong.service.ImageService;
import cn.codekong.service.MarkService;
import cn.codekong.service.UserService;
import cn.codekong.tag.util.CalcAccuracyRateUtil;
import cn.codekong.tag.util.HandleTagUtil;
import cn.codekong.util.DateUtils;
import cn.codekong.util.ImagesUtil; 
 
@Lazy(false)
@Component
public class TaskShedule {

	@Autowired
	MarkService markService;
	@Autowired
	ImageService imageService;
	@Autowired
	UserService userService;
	@Autowired
	ConstantService constantService;
	
	/**
	 * 在凌晨0时启动定时任务，完成图片标签的处理
	 */
	@Scheduled(cron = Constant.HANDLE_IMG_CRON) 
	public void handleTag(){ 
		cn.codekong.bean.Constant constant = constantService.getConstantByKey("threshold"); 
		List<Integer> imgids = markService.getImgIdAble(Integer.parseInt(constant.getValue())); //得到满足标签处理条件的所有图片id
		ImagesUtil imagesUtil = new ImagesUtil(); 
		DateUtils dateUtil = new DateUtils(); 
		System.out.println(imgids.size());
		if (!imgids.isEmpty()) {   //如果有满足条件的图片
			String all_option_mark_name = "";
			String manual_mark_name = "";
			String mark_name = "";
			for (int i = 0; i < imgids.size(); i++) {  //遍历
				int img_id = imgids.get(i);		
				System.out.println(img_id);
				//根据该图片id找到所有打标签的标签记录
				List<Image_Mark> image_Marks = markService.getImageMark(img_id);
				//遍历所有标签记录
				for (int j = 0; j < image_Marks.size(); j++) {
					Image_Mark image_Mark = new Image_Mark();
					image_Mark = image_Marks.get(j);
					//得到该图片所有的选择标签结果
					all_option_mark_name += image_Mark.getOption_mark_name();
					//得到该图片所有的输入标签结果
					manual_mark_name +=image_Mark.getManual_mark_name(); 
				}
				mark_name = all_option_mark_name + manual_mark_name; //合并该图片的选择标签结果和输入标签结果 
				String [] marknames = mark_name.split(Constant.TAG_SEPARATOR); //“;”号分割该字符串
				//调用filterTag接口得到处理之后的该图片标签结果
				List<String> list = HandleTagUtil.filterTag(marknames);
				//调用imagesUtil的getString方法将list类型的结果转化为String类型
				String label_name = imagesUtil.getString(list); 
			    Image image = new Image();
			    image = imageService.findImageById(img_id);	//根据图片id找到该图片
			    image.setImg_label_name(label_name);	//设置图片的标签结果字段
			    image.setImg_finish_time(dateUtil.getNowDate());	//设置图片完成的当前时间
			    image.setImg_is_finish(Constant.TAG_IMG_FINISHED);	//设置该图片的是否完成标识为“1”
			    imageService.updateImage(image); //持久化到数据库image对象
			    
			    List<Mark> marks = markService.getMarkList(img_id);
			    System.out.println(marks.size());
			    for (int k = 0; k < marks.size(); k++) {
					 
			    	Mark markEach = marks.get(k); 
					String markName = markEach.getManual_mark_name()+markEach.getOption_mark_name(); 
					double accuracy = CalcAccuracyRateUtil.calcEachTaskAccuracyRate(markName,label_name);
				 
					Mark mark = new Mark();
					mark.setMark_accuracy(accuracy+""); 
					mark.setImg_id(markEach.getImg_id());
					mark.setUser_id(markEach.getUser_id());
					markService.updateMark(mark);
					
					User user = userService.findUserById(markEach.getUser_id()); 
					if (accuracy == 0 ) {
						user.setIntegral(user.getIntegral()+1);
					}else if(accuracy > 0 && accuracy <= 0.2){
						user.setIntegral(user.getIntegral()+2);
					}else if (accuracy > 0.2 && accuracy <= 0.4) {
						user.setIntegral(user.getIntegral()+3);
					}else if (accuracy > 0.4 && accuracy <= 0.6) {
						user.setIntegral(user.getIntegral()+4);
					}else if (accuracy > 0.6 && accuracy <= 0.8) {
						user.setIntegral(user.getIntegral()+5);
					}else {
						user.setIntegral(user.getIntegral()+6);
					} 
					userService.update(user);
				}
			}
		}  
	}
}
