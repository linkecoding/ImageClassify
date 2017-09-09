package cn.codekong.test;
 
import java.util.List; 
import org.junit.Test;

import cn.codekong.bean.Constant;
import cn.codekong.bean.ExportTag; 
import cn.codekong.bean.User;
import cn.codekong.bean.Zip; 
import cn.codekong.bean.label.ExportLabel; 
import cn.codekong.service.AdminService;
import cn.codekong.service.ConstantService;
import cn.codekong.service.ExportService;
import cn.codekong.service.ImageService;
import cn.codekong.service.UserService;
import cn.codekong.service.ZipService; 

public class AdminTest {
	
	@Test
	public void name() {
		AdminService adminService = new AdminService();
		List<User> users = adminService.getAllUsers(1,15);
		System.out.println(users.size());
		for (int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i).getUser_id());
		}
	}
	@Test
	public void frozen() { 
		 UserService userService = new UserService();
		 List<User> users = userService.getUsersOfFrozenOrUnfrozen(0, 1, 15);
		 for (int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i).getUser_id());
		} 
	}
	@Test
	public void updateUserByAdmin(){
	User user = new User();
	user.setUser_id(1);
	user.setUsername("huahua");
	user.setSex("nv");
	user.setIntegral(12);
	user.setAccuracy("20%");
	
	AdminService adminService = new AdminService();
	int aa  = adminService.updateUserByAdmin(user);
	System.out.println(aa); 
	}
	@Test
	public void showZip(){
	 ZipService zipService = new ZipService();
	 List<Zip> zips = zipService.showZip(1,2);
	 for (int i = 0; i < zips.size(); i++) {
		System.out.println(zips.get(i).getIsZip());
		System.out.println(zips.get(i).getIsClassify());
	}
	}
	@Test
	public void getExportImage(){
		AdminService adminService = new AdminService();
		List<ExportLabel> images = adminService.getExportImage();
		System.out.println(images.size());
		for (int i = 0; i < images.size(); i++) {
			System.out.println(images.get(i).getPicture_name());
		}
	} 
	
	@Test
	public void saved(){
		ExportService exportService = new ExportService();
		ExportTag exportTag = new ExportTag();
		exportTag.setExport_id(1);
		exportTag.setImg_id(1497);
		boolean issaved = exportService.save(exportTag);
		System.out.println(issaved);
	} 
	
	 
	@Test
	public void getExportHistoryFile() {
		
		AdminService adminService = new AdminService();
		List<ExportLabel> exportHistoryLabels = adminService.getExportHistoryFile(7);
		for (int i = 0; i < exportHistoryLabels.size() ; i++) {
			System.out.println(exportHistoryLabels.get(i).getImg_id());
		} 
	}
	@Test
	public void asefc() {
//		ImageService imageService = new ImageService();
//		long aa = imageService.getImageAmount();
//		System.out.println(aa); 
		ConstantService constantService = new ConstantService();
		List<Constant> constant = constantService.getConstantConfig();
		for(Constant constant2 :constant){
			System.out.println(constant2.getValue());
		}
		
	}
	
}
