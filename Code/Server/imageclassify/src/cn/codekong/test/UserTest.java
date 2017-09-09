package cn.codekong.test;

import java.text.ParseException; 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List; 
import org.junit.Test; 
import cn.codekong.bean.Admin;
import cn.codekong.bean.Category;
import cn.codekong.bean.CheckIn;
import cn.codekong.bean.Composition;
import cn.codekong.bean.Image;
import cn.codekong.bean.Image_Composition;
import cn.codekong.bean.Image_Mark;
import cn.codekong.bean.Interest;
import cn.codekong.bean.Mark;
import cn.codekong.bean.Oauth;
import cn.codekong.bean.Task;
import cn.codekong.bean.User;
import cn.codekong.bean.Zip; 
import cn.codekong.bean.label.Label;
import cn.codekong.bean.label.UserRank;
import cn.codekong.service.AdminService;
import cn.codekong.service.CategoryService;
import cn.codekong.service.CheckInService;
import cn.codekong.service.CompositionService;
import cn.codekong.service.ImageService;
import cn.codekong.service.InterestService;
import cn.codekong.service.MarkService;
import cn.codekong.service.OauthService;
import cn.codekong.service.TaskService;
import cn.codekong.service.UserService;
import cn.codekong.service.ZipService;
import cn.codekong.tag.util.HandleTagUtil;
import cn.codekong.util.DateUtils;
import cn.codekong.util.MD5;
import cn.codekong.util.ImagesUtil; 

public class UserTest {  
	@Test
	public void userTest(){
		User user=new User();
		user.setUsername("yuqing");
		user.setPassword("123456");
		user.setTel_num("15161132971");
		
		Oauth oauth=new Oauth();
		oauth.setOauth_token("1234567897785"); 
		
		UserService userService=new UserService();
		OauthService oauthService=new OauthService();
		
		boolean bb=oauthService.saveOauth(oauth);
		boolean aa=userService.save(user);
		System.out.println(aa+""+bb); 
	} 
	@Test
	public void testFindByTelnum(){
		
		User user=new User();
		String tel_num="15161132971";
		UserService userService=new UserService();
		user =  userService.findUserByTelnum(tel_num);
		System.out.println(user.getPassword());  
	}
	@Test
	public void findImageById(){
		
		ImageService imageService = new ImageService();
		Image image = imageService.findImageById(1510);
		image.setIs_clustered(1);
		imageService.updateImage(image);
	}
	
	@Test
	public void testFindByToken(){
		Oauth oauth=new Oauth();
		String token1="6ff3b02ef597226f7cb66764bb55cf2d";
		OauthService oauthService=new OauthService();
		oauth = oauthService.findOauthByOauthToken(token1);
		System.out.println(oauth.getOauth_id()); 
		
	} 
	@Test
	public void testInsertOauth(){
  	    UserService userService=new UserService();
//		int temp=userService.saveOauthId(5, 31); 
//		OauthService oauthService=new OauthService();
//		int aa=oauthService.updateOauth("1234567897785", "156798anjk");
//		System.out.println(temp+aa);
	 
		User user=new User();
		user=userService.findUserById(31);
    	System.out.println(user.toString());
    	//cn.codekong.bean.User@bccb269 
//		user.setOauth_id(5);
//		boolean u=userService.update(user);
//		System.out.println(u);
	}
	
	@Test
	public void testOldPwd(){
		  UserService userService=new UserService();
		  OauthService oauthService=new OauthService();
		  
		Oauth oauth=new Oauth();
		User user=new User();
		String oldpwd="1578";
		MD5 md5=new MD5();
		String pwdMD5=md5.generateMD5(oldpwd);
		 System.out.println(pwdMD5+"old");
		 
		 oauth=oauthService.findOauthByOauthToken("fa1027ddd41a2db4e5e72db8c0fda52d");
		 user=oauth.getUser();
		 System.out.println(user.getUser_id());
		 System.out.println(user.getPassword());
		
		System.out.println(user.getPassword().equals(pwdMD5));
		  
	}
	 
	@Test
	public void tesPwd(){
		String password="846503";
		MD5 md5=new MD5();
		String md5pass=md5.generateMD5(password);
		System.out.println(md5pass.equals("9f40f45e573c5db33196619f6a8e87a4"));
	}
	@Test
	public void findtel(){
		String telnum="15161132974";
		UserService userService=new UserService();
		User user=userService.findUserByTelnum(telnum);
		System.out.println(user.getUser_id());
		MD5 md5=new MD5();
		String ss = md5.generateMD5("123456");
		System.out.println(ss);
	}
	@Test
	public void findAdmin(){
		String admin_name="yuqing";
		MD5 md5=new MD5();
		String ss = md5.generateMD5("123456");
		AdminService adminService=new AdminService();
		Admin admin = adminService.findAdminByNameAndPaw(admin_name, ss);
		System.out.println(admin.getAdmin_id());
		
	}
	
	 @Test
	public void getCategoryList(){
		 
		CategoryService categoryService=new CategoryService();
		List<Category> categories=categoryService.getCategotyList();
		System.out.println(categories.size());
		 
//		UserService userService = new UserService();
//		List<User> users = userService.getAllUser();
//		for (int i = 0; i < users.size(); i++) {
//			System.out.println(users.get(i).getTel_num());
//		} 
	}
	 
	 @Test
	 public void getCheckInList() throws ParseException{
		 CheckInService checkInService=new CheckInService();
		 List<CheckIn> checkIns =checkInService.getCheckIns(38);
		 Calendar calendar = Calendar.getInstance();
		  String month="5";
		  String data = "";
	      int dayofMonth = 0;
	      int month2 = 0;
		 if (checkIns!=null) {
			 for (int i = 0; i < checkIns.size(); i++) { 
				     Date date = checkIns.get(i).getCheckin_time();
				     
//				     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//				     String ss=sdf.format(date); 
//				     System.out.println(ss);
//				     
//				     Date time=sdf.parse(ss);
//				      System.out.println(time);
//				       
					 calendar.setTime(date); //得到第i个对象的打标签时间
			    	 month2 = calendar.get(Calendar.MONTH);      //获取时间的月份
			    	 System.out.println(month2+1);
			    	 
                     if ((month2+1) == Integer.parseInt(month)) {       //如果该月份值与传过来的月份值一致
    				     dayofMonth = calendar.get(Calendar.DAY_OF_MONTH);   //则获取该月日期值
    				     data += dayofMonth+",";   //将日期值以“，”分隔连接
    				     System.out.println(data+"wowow"); 
				}
			 }
		 }
		}  
	 @Test
	 public void addCategoty(){
//		 UserService userService=new UserService();
//		 User user = userService.findUserById(1);
 		 CategoryService categoryService=new CategoryService();
//		 Category category = categoryService.findCategoryById(2); 
//		  boolean isaved = userService.addCategory(user, category);
//		  System.out.println(isaved); 
		  
		Category category = categoryService.findCategoryById(1);
		System.out.println(category.getCategory_name());
	 }
	 @Test
	 public void addInterest(){ 
		 InterestService interestService = new InterestService(); 
		 boolean flag = true;
		 String arr = "2|3";
		 String []array = arr.split("\\|");
	   for (int i = 0; i < array.length; i++) { 
		   Interest interest = new Interest();
		   interest.setCategory_id(Integer.parseInt(array[i]));
		   interest.setUser_id(1);
		   boolean temp = interestService.saveInterest(interest);
		   flag=(flag&&temp); 
		 }
		 System.out.println(flag);
	 } 
	  
	 @Test
	 public void findInterest(){
		 List<Interest> interests = new ArrayList<Interest>();
		 InterestService interestService = new InterestService(); 
		 interests = interestService.getInterestById(1);
		 System.out.println(interests.size());
	 } 
	 
	 @Test
	 public void delInterest(){
//		 Category category=new Category();
//		 CategoryService categoryService=new CategoryService();
//		 category = categoryService.findCategoryById(8);
//		 System.out.println(category.getCategory_id());
		 InterestService interestService = new InterestService();
		 //boolean cs=userService.delCategory(14, category);
		 int cs = interestService.deleteInterests(1) ;
		 System.out.println(cs);
	 } 
	 
	 @Test
	 public void getUnzipList(){
		 ZipService zipService = new ZipService();
		 Zip zip = zipService.findZipById(1);
		 System.out.println(zip.getOld_zip_name());
//		 List<Zip> zips = zipService.showZip();
//		 System.out.println(zips.size());
 }
	 
	 @Test
	 public void getUnClassifyList(){
		 ZipService zipService = new ZipService();
		 List<Zip> zips = zipService.showZipAndUnClassifyFolder();
		 System.out.println(zips.size());
	 }
	 
	 @Test
	 public void getUnclassifyImages(){
		 ImageService imageService = new ImageService();
		 ZipService zipService = new ZipService();
		 List<Image> images = new ArrayList<Image>();
		 Zip zip = zipService.findZipById(3);
		 images = imageService.findImageByZip(zip);
		 for (int i = 0; i < images.size(); i++) {
			 System.out.println(images.get(i).getImg_name());	
		 } 
	 }
	 @Test
	 public void testFindOauthByUser(){
		 OauthService oauthService = new OauthService();
		 UserService userService = new UserService();
		 User user = userService.findUserById(1);
		 System.out.println(user==null);
		 Oauth oauth = oauthService.findOauthByUser(user);
		 System.out.println(oauth.getOauth_token_expiration());
	 }
	 
	 @Test
	 public void testGetTask() throws ParseException{
		  
		 MarkService markService = new MarkService();
		 List<Image_Mark> marks = markService.getComposition(2, 4); 
		 for (int i = 0; i < marks.size(); i++) {
			Image_Mark image_Mark = marks.get(i);
			System.out.println(image_Mark.getImg_id());
		} 
	 }
	 
	 @Test
	 public void getComposition(){
		  
		CompositionService compositionService = new CompositionService();
		List<Integer> integers = compositionService.getComposition(131);
		for (int i = 0; i < integers.size(); i++) {
			System.out.println(integers.get(i));
		}
		 
	 }
	 @Test
	 public void deleteMark(){
		  
		 MarkService markService = new MarkService();
		 int aa = markService.deleteMark(1, 45);
		 System.out.println(aa);
	 }
	 
	 @Test
	 public void updateComposition(){
			CompositionService compositionService = new CompositionService();
			Composition composition = compositionService.getComposition(68, 45);
			composition.setIs_masked(0);
			int aa = compositionService.update(composition);
			System.out.println(aa);
	 }
	 @Test
	 public void getMarkListByUserAndImage(){
		 MarkService markService = new MarkService();
		 List<Mark> marks = markService.getMarkListByUserAndImage(2, 1);
		 Mark mark = marks.get(0);
		 mark.setMark_accuracy("0.2");
		 Date dd = new Date();
		 System.out.println(dd);
		 mark.setMark_time(dd);
		boolean aa = markService.updateMarkAll(mark);
		System.out.println(aa);
		 List<Mark> marks2 = markService.getMarkListByUserAndImage(2, 1);
		 System.out.println(marks2.get(0).getMark_time());
	 }
	 
	 
	 @Test
	 public void testFindTask(){
//		 TaskService taskService = new TaskService();
//		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//		 Date date;
//		 Date date2 = new Date();
//		 Date temp = date2;
//		try {
//			date = sdf.parse("2017-06-06 15:14:39");
//			 Task task = taskService.findTaskIdByTask(2, date, 1);
//			 System.out.println(task.getTask_id());
//			 System.out.println(temp);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		 ImagesUtil imagesUtil = new ImagesUtil();
		 String aa = "43|文本;29|截图;14|卡通;";
		 List<Label> labels = imagesUtil.getLabels(aa);
		 for (int i = 0; i < labels.size(); i++) {
			 System.out.println(labels.get(i).getLabel_name());
		} 
	 }
	 
	 @Test
	 public void testFilter() {
		 String[] tagsArray = {"文本","卡通"};
		 	List<String> list = HandleTagUtil.filterTag(tagsArray); 
		 	System.out.println(list);
	}
	 
	 @Test
	 public void testgetImageMark(){
		 MarkService markService = new MarkService();
		 List<Image_Mark> image_Marks = markService.getImageMark(145);
		 System.out.println(image_Marks.size());
		 for (int i = 0; i < image_Marks.size(); i++) {
			Image_Mark image_Mark = new Image_Mark();
			image_Mark = image_Marks.get(i);
			System.out.println(image_Mark.getOption_mark_name());
		}
	 }
	 
	 @Test
	 public void testCategoryList(){
		 ImageService imageService = new ImageService();
		 CategoryService categoryService = new CategoryService();
		  List<Image> list =  imageService.getImageList();
		  for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getImg_id());
		}
	 }
	 
	 @Test
	 public void testGetCategory(){
		 CategoryService categoryService = new CategoryService();
		 List<Category> categories = categoryService.getCategotyList();
		 for (int j = 0; j < categories.size(); j++) {
			 System.out.println(categories.get(j).getCategory_name());
		} 
	 }
	 @Test
	 public void getMachineName(){
		 ImagesUtil imagesUtil = new ImagesUtil();
		List<String> list = imagesUtil.getLabelName("43|小狗;29|小猫;14|牛;");
		 for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	 }
	 
	 @Test
	 public void getImageList(){
		 ImageService imageService = new ImageService();
		 List<Image> images = imageService.getImageList();
		 for (int i = 0; i < images.size(); i++) {
			System.out.println(images.get(i).getImg_id());
		}
	 }
	 
	 
	 @Test
	 public void testGetTaskImgIds(){
		 MarkService markService = new MarkService();
		 List<Integer> imgIds =new ArrayList<Integer>();
		 imgIds.add(145);
		 imgIds.add(2);
		 List<Image_Mark> image_Marks =  markService.getInterestComposition(2, 4, imgIds);
		 System.out.println(image_Marks.size());
		 for (int i = 0; i < image_Marks.size(); i++) {
			System.out.println(image_Marks.get(i).getImg_machine_tag_label());
		}
	 }
	 
	 @Test
	 public void testGetComposition(){
		  
		 MarkService markService = new MarkService();
		 List<Image_Composition> image_Compositions = markService.getCompositionOfAll(10, 1,"asc","asc");
		System.out.println(image_Compositions.size());
		 for (int i = 0; i < image_Compositions.size(); i++) {
			System.out.println(image_Compositions.get(i).getImg_id());
		 }
	 }
	 @Test
	 public void testGetImage(){
		ImageService imageService = new ImageService();
		List<Integer> imgIds = new ArrayList<Integer>();
		imgIds.add(1497);
		imgIds.add(1543);
		imgIds.add(1520);
		imgIds.add(1546);
		imgIds.add(1545);
		//1497,1543,1546,1520,1545,1504,1519,1534,1552,1544,1493
		imgIds.add(1504);
		imgIds.add(1519);
		imgIds.add(1534);
		imgIds.add(1552);
		imgIds.add(1544);
		imgIds.add(1493);
		List<Image_Composition> image_Compositions = imageService.getPriorityImages(imgIds, 10,"asc","asc");
		for (int i = 0; i < image_Compositions.size(); i++) {
			System.out.println(image_Compositions.get(i).getImg_id());
		}
	 }
	 @Test
	 public void testGetNeededImagesAmount(){
		 ImageService imageService = new ImageService();
		 List<Integer> imgIds = new ArrayList<Integer>(); 
		 List<Integer> categories = new ArrayList<Integer>();  
		 categories.add(12);
		 List<Image_Composition> image_Compositions = imageService.getNeededAmountImages(1, imgIds, 10, categories,"asc","asc");
		  
		 for (int i = 0; i < image_Compositions.size(); i++) {
			System.out.println(image_Compositions.get(i).getImg_id());
		}
	 }
	 @Test
	 public void testGetImagesList(){
		 ImageService imageService = new ImageService();
		 List<Image> images = imageService.getFinishedImages();
		 System.out.println(images.size());
		 MarkService markService = new MarkService();
		 int img_id = 145;
		 List<Mark> marks = markService.getMarkList(img_id);
		 System.out.println(marks.size());
		 for (int i = 0; i < marks.size(); i++) {
			System.out.println(marks.get(i).getUser_id());
		}
	 }
	 @Test
	 public void tes5(){
		 Mark mark = new Mark();
		 mark.setMark_accuracy("12.0");
		 mark.setImg_id(1);
		 mark.setUser_id(1);
		 MarkService markService = new MarkService();
		 markService.updateMark(mark); 
	 }
	 @Test
	 public void  getMarkListByUserId() {
		MarkService markService = new MarkService();
		List<String> accuracy = markService.getMarkListByUserId(1);
		System.out.println(accuracy.size());
		 
	}
	 @Test
	 public void  seleteListOfCategory() {
		ImageService imageService = new ImageService();
		List<Integer> list = imageService.seleteListOfCategory(12, 1,"asc","asc");
		 for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	 @Test
	 public void testNum(){
		  UserService userService = new UserService();
		 String ac = userService.getAvgAccuracy(1);
		 System.out.println(ac);
	 }
	 @Test
	 public void name() {
		
		 DateUtils dateUtils = new DateUtils();
		 Date date = new Date();
		 String ss = dateUtils.getFormatDateOfNoneSs(date);
		 System.out.println(ss);
	}
	 @Test
	 public void TestGetUnfinishedTask(){
		 TaskService taskService = new TaskService();
		 List<Task> tasks = taskService.geTasksOfUnconfirmed(1,1,5);
		 System.out.println(tasks.size());
		 for (int i = 0; i < tasks.size(); i++) {
			System.out.println(tasks.get(i).getTask_id());  
		}
	 }
	 
	 @Test
	 public void getImagesOfTask() {
		CompositionService compositionService = new CompositionService();
		List<Image> images = compositionService.getTaskOfImages(127); 
		for (int i = 0; i < images.size(); i++) {
			System.out.println(images.get(i).getImg_id());
			Image image = images.get(i);
			System.out.println(image.getImg_path()+image.getImg_name());
		}
	}
	 @Test
	 public void  getNumOfUnconfirmed() {
		 CompositionService compositionService = new CompositionService();
		 Object object = compositionService.getUnconfirmedOfImages(2); 
		String aa=  object.toString();
		 System.out.println(aa);
	}
	 @Test
	 public void getFinishedImages(){
		 CompositionService compositionService = new CompositionService();
		 List<Image> images = compositionService.getConfirmedOfImages(2);
		 for (int i = 0; i < images.size(); i++) {
			System.out.println(images.get(i).getImg_id());
		}
	 }
	 @Test
	 public void getFinishedMarks(){
		 MarkService markService = new MarkService();
		 List<Mark> marks = markService.getMarkListByUserAndTask(1, 1);
		 System.err.println(marks.get(0).getMark_accuracy());
	 }
	 @Test
	 public void getImageMarkOfTask() {
		TaskService taskService = new TaskService();
		List<Image_Mark> image_Marks = taskService.getImageMarkOfTask(1,168);
		System.out.println(image_Marks.size());
		for (int i = 0; i < image_Marks.size(); i++) {
			Image_Mark image_Mark = image_Marks.get(i);
			System.out.println(image_Mark.getImg_id());
			System.out.println(image_Mark.getImg_machine_tag_label());
			System.out.println(image_Mark.getImg_name());
			System.out.println(image_Mark.getImg_path());
			System.out.println(image_Mark.getManual_mark_name());
			System.out.println(image_Mark.getOption_mark_name()); 
		}
	}
	 
	 @Test
	 public void getInterestCategory(){
		 InterestService interestService = new InterestService();
		 List<Integer> interestIds = new ArrayList<Integer>();
		 interestIds.add(2);
		 interestIds.add(3);
		 List<Category> categories = interestService.getInterestCategory(interestIds);
		 System.out.println(categories.size());
	 } 
	 @Test
	 public void getRankList(){
	
//		 CompositionService compositionService = new CompositionService();
//		 List<AmountOfTask> userRanks = compositionService.getRankListOfAmountTask(1, 8);
//		 for (int i = 0; i < userRanks.size(); i++) {
//			 System.out.println(userRanks.get(i).getUsername());
//		} 
	 } 
	 @Test
	 public void testGson(){
		 MarkService markService = new MarkService();
		// List<Integer> integers = markService.getImgIdAble();
//		 for(int i= 0;i<integers.size();i++){
//			 System.out.println(integers.get(i));
//		 } 
	 }
	 
	 @Test
	 public void testAmount(){
		 TaskService taskService = new TaskService();
		 String num = taskService.getAmountTaskOfCommit(31);
		 System.out.println(num);
//		 } 
	 }
}
