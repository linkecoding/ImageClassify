package cn.codekong.test;

import java.util.List;

import org.junit.Test;

import cn.codekong.bean.Constant;
import cn.codekong.bean.Contribute_Img;
import cn.codekong.bean.Zip;
import cn.codekong.bean.label.ContributeImgListOfAll;
import cn.codekong.bean.label.ImgUrl;
import cn.codekong.service.ConstantService;
import cn.codekong.service.ContributeService;
import cn.codekong.service.ImageService;
import cn.codekong.service.UserService;
import cn.codekong.service.ZipService;

public class AdminJunitTest {

	@Test
	public void getFrozenUserNum() {
		UserService userService = new UserService();
		int num = new Long(userService.getUsersNumOfFrozenOrUnfrozen(1)).intValue();
		System.out.println(num);
	}
	@Test
	public void name() {
		 ZipService zipService = new ZipService();
		 List<Zip> zips = zipService.showZip(1, 2);
		 System.out.println(zips.size());
	}
	@Test
	public void constantService() {
		ConstantService constantService = new ConstantService();
		List<Constant> constants = constantService.getConstantByKey();
		for (int i = 0; i < constants.size(); i++) {
			System.out.println(constants.get(i).getKey() + ":" + constants.get(i).getValue());
		}
	}
	
	@Test
	public void getList() {
		int category_id = 12;
		ImageService imageService = new ImageService();
		List<ImgUrl> imgUrls = imageService.getImageByCategory(category_id);
		for (int i = 0; i < imgUrls.size(); i++) {
			System.out.println(imgUrls.get(i).getImgUrl()+imgUrls.get(i).getImgName());
			System.out.println();
		}
	}
	
	@Test
	public void getAmount() {
		int category_id = 12;
		ImageService imageService = new ImageService();
		int amount = imageService.getAmountByCategoryId(category_id);
		System.out.println(amount);
	}
	
	@Test
	public void getZipList(){
		ContributeService contributeService = new ContributeService();
		List<Contribute_Img> contribute_Imgs = contributeService.getContributeBySelf(1, 1, 3);
		for (Contribute_Img cImg :contribute_Imgs){
			System.out.println(cImg.getStorage_path());
		}
	}
	
	@Test
	public void getZipListOfAll(){
		ContributeService contributeService = new ContributeService();
		List<ContributeImgListOfAll> contribute_Imgs = contributeService.getContributeOfAllByUser(1,3);
		for (ContributeImgListOfAll cImg :contribute_Imgs){
			System.out.println(cImg.getId());
		}
	}
	
	@Test
	public void getContributeImgById() {
		int id = 3;
		ContributeService contributeService = new ContributeService();
		Contribute_Img cImg = contributeService.getContributeImgById(id);
		System.out.println(cImg.getStorage_path());
	}
	
	@Test
	public void getAmountContributeOfAllByUser() {
		
		ContributeService contributeService = new ContributeService();
		int num = contributeService.getAmountContributeOfAllByUser();
		System.out.println(num);
	}
	
}
