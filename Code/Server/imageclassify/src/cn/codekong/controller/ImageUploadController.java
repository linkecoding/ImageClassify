package cn.codekong.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;

import cn.codekong.bean.Contribute_Img;
import cn.codekong.bean.Image;
import cn.codekong.bean.Oauth;
import cn.codekong.bean.User;
import cn.codekong.bean.Zip;
import cn.codekong.bean.label.ContributeImgListOfAll;
import cn.codekong.bean.label.Message;
import cn.codekong.bean.label.SplitObject;
import cn.codekong.config.Constant;
import cn.codekong.img.util.ImgUtil;
import cn.codekong.service.ContributeService;
import cn.codekong.service.ImageService;
import cn.codekong.service.OauthService;
import cn.codekong.service.ZipService;
import cn.codekong.util.DateUtils;

@Controller
public class ImageUploadController {

	@Autowired
	ImageService imageService;
	@Autowired
	ZipService zipService;
	@Autowired
	OauthService oauthService;
	@Autowired
	ContributeService contributeService;

	/**
	 * 志愿者贡献上传图片
	 * 
	 * @param file
	 * @param oauth_token
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/contributeimg", method = RequestMethod.POST)
	public void uploadZipByUser(@RequestParam("contribute_img_zip") MultipartFile file,
			@RequestParam(Constant.OAUTH_TOKEN) String oauth_token, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		String json;
		Oauth oauth = new Oauth();
		User user = new User();
		Contribute_Img cImg = new Contribute_Img();

		oauth = oauthService.findOauthByOauthToken(oauth_token);
		user = oauth.getUser();
		// 项目路径
		StringBuffer url = request.getRequestURL();
		String tempContextUrl = url.delete(url.length() - request.getServletPath().length(), url.length()).toString();
		String sqlUrl = tempContextUrl + Constant.SIGNALZUOXIE + Constant.ZIP_FOLDER_BY_USER + Constant.SIGNALZUOXIE;
		// System.out.println(sqlUrl);

		String contributeZipName = DateUtils.DateToMD5() + ".zip";
		// 志愿者上传图片保存的文件夹路径
		String fileUrl = request.getSession().getServletContext().getRealPath("/") + Constant.ZIP_FOLDER_BY_USER
				+ File.separator;
		// System.out.println(fileUrl);
		File fileOfUser = new File(fileUrl, contributeZipName);
		if (!fileOfUser.exists()) {
			fileOfUser.mkdirs();
		}
		try {
			// 将志愿者上传的图片转存到指定路径下
			file.transferTo(fileOfUser);
			// 此时可以把图片压缩包进行解压并黄暴过滤处理

			// 过滤处理完成后，将上传图片的信息存到contribute_img表中
			Date upload_img_time = new Date();
			String storage_path = sqlUrl + contributeZipName;
			int user_id = user.getUser_id();
			cImg.setUpload_img_time(upload_img_time);
			cImg.setUpload_img_review_status("0");
			cImg.setStorage_path(storage_path);
			cImg.setUser_id(user_id);

			boolean isSaved = contributeService.saveContribute(cImg);
			if (isSaved) {
				Message<String> message = new Message<String>("200", "", "");
				json = gson.toJson(message, Message.class);
				out.print(json);
			} else {
				Message<String> message = new Message<String>("-1", Constant.UPLOAD_IMG_BY_USER, "");
				json = gson.toJson(message, Message.class);
				out.print(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取志愿者上传贡献图片列表
	 * 
	 * @param oauth_token
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/user/getcontributeimglist", method = RequestMethod.POST)
	public void uploadZipByUser(@RequestParam(Constant.OAUTH_TOKEN) String oauth_token,
			@RequestParam("start") String start, @RequestParam("page_num") String page_num, HttpServletRequest request,
			HttpServletResponse response) {
		String json = "";
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			Oauth oauth = oauthService.findOauthByOauthToken(oauth_token);
			User user = oauth.getUser();
			// 根据指定的用户查询列表
			List<Contribute_Img> contribute_Imgs = contributeService.getContributeBySelf(user.getUser_id(),
					new Integer(start).intValue(), new Integer(page_num).intValue());
			for (Contribute_Img cImg : contribute_Imgs) {
				cImg.setId(0);
				cImg.setUser_id(0);
			}

			Message<List<Contribute_Img>> message = new Message<List<Contribute_Img>>("200", "", contribute_Imgs);
			json = gson.toJson(message, Message.class);
			out.print(json);
		} catch (Exception e) {
			Message<String> message = new Message<String>("-1", Constant.NONE_UPLOAD_IMG_BY_USER, "");
			json = gson.toJson(message, Message.class);
			out.print(json);
		}

	}

	/**
	 * 管理员获取志愿者上传的贡献压缩包
	 * 
	 * @param start
	 * @param page_num
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/getcontributeuploadziplist", method = RequestMethod.POST)
	public void getUploadZipListByUserList(@RequestParam("start") String start,
			@RequestParam("page_num") String page_num, HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		// 设置编码格式
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		String json = "";
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		SplitObject<ContributeImgListOfAll> splitObject = new SplitObject<ContributeImgListOfAll>();
		List<ContributeImgListOfAll> cImgs = new ArrayList<ContributeImgListOfAll>();
		// 分页查询志愿者上传贡献的压缩包列表
		cImgs = contributeService.getContributeOfAllByUser(new Integer(start).intValue(),
				new Integer(page_num).intValue());
		splitObject.setList(cImgs);
		// 计算数据的页数
		int num = contributeService.getAmountContributeOfAllByUser();
		if (num % Constant.DEVI_NUM == 0) {
			splitObject.setPages_num(num / Constant.DEVI_NUM);
		} else {
			splitObject.setPages_num(num / Constant.DEVI_NUM + 1);
		}

		// 查询有记录
		Message<SplitObject<ContributeImgListOfAll>> message = new Message<SplitObject<ContributeImgListOfAll>>("200",
				"", splitObject);
		json = gson.toJson(message);
		out.print(json);
	}

	/**
	 * 根据指定id预览志愿者上传的压缩包图片
	 * 
	 * @param id
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/previewzipuploadbyuser", method = RequestMethod.POST)
	public void previewZipUploadedByUser(@RequestParam("id") String id, HttpServletResponse response,
			HttpServletRequest request) throws Exception {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		String json;

		Contribute_Img cImg = contributeService.getContributeImgById(Integer.parseInt(id));
		if (cImg != null) {

			// 项目路径
			StringBuffer url = request.getRequestURL();
			String tempContextUrl = url.delete(url.length() - request.getServletPath().length(), url.length())
					.toString();
			String fileURL = request.getSession().getServletContext().getRealPath("/") + Constant.ZIP_FOLDER_BY_USER
					+ File.separator; // 压缩文件路径

			String[] zipNames = cImg.getStorage_path().split("/");
			// 志愿者上传图片压缩包的文件名
			String zipName = zipNames[zipNames.length - 1];
			String zipFilePath = fileURL + zipName;
			// 志愿者上传图片压缩包的网络请求路径
			String unzipFilePath = fileURL + Constant.ZIP_FOLDER_BY_USER_TEMP; // 解压后文件的路径

			File fileFolder = new File(fileURL, Constant.ZIP_FOLDER_BY_USER_TEMP);// 在fileURL路径下unzipFilePath的文件
			if (!fileFolder.exists()) {
				// 如果临时文件夹不存在，则新建文件夹
				fileFolder.mkdirs();
			} else {
				// 如果文件夹里有任何文件，则进行遍历删除
				File file = new File(unzipFilePath);
				if (file.isDirectory()) {
					File[] files = file.listFiles();
					for (File file2 : files)
						if (file2.exists())
							file2.delete();
				}
			}
			// 对压缩包文件进行解压处理
			ImgUtil.unzip(zipFilePath, unzipFilePath);
			// 进行重命名操作
			List<String> urList = ImgUtil.renameFiles(unzipFilePath, unzipFilePath);
			String pString = tempContextUrl + Constant.SIGNALZUOXIE + Constant.ZIP_FOLDER_BY_USER
					+ Constant.SIGNALZUOXIE + Constant.ZIP_FOLDER_BY_USER_TEMP + Constant.SIGNALZUOXIE;
			List<String> urlPath = new ArrayList<String>();
			for (String path : urList) {
				urlPath.add(pString + path);
				System.out.println(pString + path);
			}
			Message<List<String>> message = new Message<List<String>>("200", "", urlPath);
			json = gson.toJson(message);
			out.print(json);
		} else {
			Message<String> message = new Message<String>("-1", Constant.NONE_ZIP_UPLOAD_BY_USER, "");
			json = gson.toJson(message);
			out.print(json);
		}

	}

	/**
	 * 管理员审核通过接口
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/reviewsuccess", method = RequestMethod.POST)
	public void reviewSuccess(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 设置编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		String json;

		// 更新志愿者贡献的压缩包审核状态字段
		boolean isUpdate = contributeService.updateZipUploadByUser(Integer.parseInt(id), 1);

		if (isUpdate) {
			Contribute_Img cImg = contributeService.getContributeImgById(Integer.parseInt(id));
			// 如果更新成功，则转存到工程项目的zip目录下
			String[] urls = cImg.getStorage_path().split("/");
			String fileURL = request.getSession().getServletContext().getRealPath("/");// 压缩文件路径
			String fileName = urls[urls.length - 1];
			File file = new File(fileURL + Constant.ZIP_FOLDER_BY_USER + File.separator + fileName);
			File dest = new File(fileURL + Constant.ZIP_FILE_FOLDER);
			if (file.exists()) {
				// 如果压缩包文件存在，则将压缩包文件转存到zip文件夹下
				file.renameTo(dest);
				// 再进行添加到zip表中，作为解压识别的样本
				Zip zip = new Zip();
				zip.setIsClassify(0);
				zip.setIsZip(0);
				Date upload_time = new Date();
				zip.setUpload_time(upload_time);
				zip.setOld_zip_name(fileName);
				zip.setZip_name(fileName);
				zip.setZip_url("." + File.separator + Constant.ZIP_FILE_FOLDER);
				boolean isSaved = zipService.saveZip(zip);
				if (isSaved) {
					// 如果成功保存到zip表中
					Message<String> message = new Message<String>("200", Constant.REVIEW_SUCCESS, "");
					json = gson.toJson(message);
					out.print(json);
				}
			} else {
				Message<String> message = new Message<String>("-1", Constant.REVIEW_FAIL, "");
				json = gson.toJson(message);
				out.print(json);
			}
		} else {
			Message<String> message = new Message<String>("-1", Constant.REVIEW_FAIL, "");
			json = gson.toJson(message);
			out.print(json);
		}
	}

	/**
	 * 管理员审核失败
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/reviewfail", method = RequestMethod.POST)
	public void reviewFail(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 设置编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		String json;

		// 更新志愿者贡献的压缩包审核状态字段为-1，即不通过审核
		boolean isUpdate = contributeService.updateZipUploadByUser(Integer.parseInt(id), -1);
		if (isUpdate) {
			Message<String> message = new Message<String>("200", Constant.OPRATE_SUCCESS, "");
			json = gson.toJson(message);
			out.print(json);
		} else {
			Message<String> message = new Message<String>("-1", Constant.OPRATE_FAIL, "");
			json = gson.toJson(message);
			out.print(json);
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void toUploadPage(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		String json;

		// 图片路径
		Zip zip = new Zip();
		String old_zip_name = file.getOriginalFilename();// 上传文件的原始压缩文件名
		String dateToMD5 = DateUtils.DateToMD5();// 调用dateUtils的DateTwoMD5函数获取2次md5加密后的字符串
		String filename = dateToMD5 + ".zip"; // 加密后的压缩包名
		// 文件保存相对路径
		String fileURL = request.getSession().getServletContext().getRealPath("/") + Constant.ZIP_FILE_FOLDER
				+ File.separator;
		System.err.println("file Url " + fileURL);
		File file2 = new File(fileURL, filename);// 在fileURL路径下filename的文件
		// 如果文件不存在，创建文件
		if (!file2.exists()) {
			file2.mkdirs();
		}
		try {
			file.transferTo(file2); // 转存到文件路径下
			Date upload_time = new Date(); // 当前时间
			zip.setZip_url("." + File.separator + Constant.ZIP_FILE_FOLDER); // 压缩文件路径
			zip.setUpload_time(upload_time); // 当前时间设置为上传时间
			zip.setZip_name(filename); // 文件名+后缀
			zip.setOld_zip_name(old_zip_name); // 原始压缩文件名

			boolean issaved = zipService.saveZip(zip); // 持久化到数据库
			if (issaved) {
				Message<String> message = new Message<String>("200", Constant.UPLOAD_SUCCESS, "");// 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			} else {
				Message<String> message = new Message<String>("-1", Constant.UPLOAD_FAIL, "");// 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取上传的压缩包列表
	 * 
	 * @param start
	 * @param num
	 * @param request
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/getuploadziplist", method = RequestMethod.POST)
	public void getUploadZipList(@RequestParam("start") String start, @RequestParam("page_num") String num,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(start + "," + num);

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		SplitObject<Zip> splitObject = new SplitObject<Zip>();
		String json;
		// 获取zip列表
		List<Zip> zips = zipService.showZip(Integer.parseInt(start), Integer.parseInt(num));
		// System.out.println("zips.size" + zips.size());
		// 获取zip列表数量
		int pages_num = new Long(zipService.showZip()).intValue();
		// System.out.println("pages_num" + pages_num);
		SimpleDateFormat sdf = new SimpleDateFormat(Constant.ZIP_DATE_FORMAT);
		for (int i = 0; i < zips.size(); i++) {
			zips.get(i).setUpload_time(sdf.parse(sdf.format(zips.get(i).getUpload_time())));
		}
		if (!zips.isEmpty()) {

			splitObject.setList(zips);
			if (pages_num % Constant.DEVI_NUM == 0) {
				splitObject.setPages_num(pages_num / Constant.DEVI_NUM);
			} else {
				System.out.println("false");
				splitObject.setPages_num(pages_num / Constant.DEVI_NUM + 1);
			}
			Message<SplitObject<Zip>> message = new Message<SplitObject<Zip>>("200", "", splitObject);// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		} else {
			Message<String> message = new Message<String>("-1", "", "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}

	}

	/**
	 * 解压文件到指定文件夹并持久化到数据库
	 * 
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/unzip", method = RequestMethod.POST)
	public void toUnZip(@RequestParam("zip_id") String zip_id2, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		System.out.println(zip_id2);
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		String json;

		// 项目路径
		StringBuffer url = request.getRequestURL();
		String tempContextUrl = url.delete(url.length() - request.getServletPath().length(), url.length()).toString();
		boolean isSaved = true;
		int zip_id = Integer.parseInt(zip_id2); // 获取需要解压的压缩包文件
		Zip zip = zipService.findZipById(zip_id); // 调用zipService查找zip_id对应的zip对象
		String fileURL = request.getSession().getServletContext().getRealPath("/") + Constant.ZIP_FILE_FOLDER
				+ File.separator; // 压缩文件路径

		String zip_name = zip.getZip_name(); // 压缩文件名
		String[] filename = zip_name.split("[.]"); // 去除.zip,保留压缩包名
		String folder = filename[0];
		String zipFilePath = fileURL + zip.getZip_name(); // 压缩包完整路径
		String unzipFilePath = fileURL + folder; // 解压后文件的路径

		File file = new File(fileURL, folder);// 在fileURL路径下unzipFilePath的文件
		if (!file.exists()) {
			file.mkdirs();
		}
		// zipFilePath: 压缩包的路径+压缩包名字.zip
		// unzipFilePath: 压缩包的路径 + 压缩后文件夹的name
		ImgUtil.unzip(zipFilePath, unzipFilePath); // 进行解压操作
		List<String> urList = ImgUtil.renameFiles(unzipFilePath, unzipFilePath);// 解压到unzipFilePath路径下

		// 将所有图片的路径持久化到数据库
		Image image = new Image();
		// 图片路径
		String sqlUrl = tempContextUrl + Constant.SIGNALZUOXIE + Constant.ZIP_FILE_FOLDER + Constant.SIGNALZUOXIE
				+ folder + Constant.SIGNALZUOXIE;
		for (int i = 0; i < urList.size(); i++) {
			image.setImg_name(urList.get(i)); // 解压并加密的文件名
			image.setImg_path(sqlUrl); // 路径
			image.setImg_is_finish("0"); // 设置文件是否完成
			image.setZip(zip);
			isSaved = (isSaved && imageService.savaImage(image)); // 所有图片持久化到数据库
		}
		if (isSaved) {
			Date unzip_time = new Date(); // 解压时间
			zip.setIsZip(1); // 是否解压标识设置为1表示已解压
			zip.setUnzip_time(unzip_time);
			boolean isUpdate = zipService.updateZip(zip); // 更新zip对象
			if (isUpdate) {
				Message<String> message = new Message<String>("200", Constant.UNZIP_SUCCESS, "");// 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			} else {
				Message<String> message = new Message<String>("-1", Constant.UNZIP_FAIL, "");// 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			}
		} else {
			Message<String> message = new Message<String>("-1", Constant.UNZIP_FAIL, "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	/**
	 * 接口识别解压后文件标签
	 * 
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/classify", method = RequestMethod.POST)
	public void toClassifyImg(@RequestParam("zip_id") String zip_id2, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		String json;
		// 项目路径
		// StringBuffer url = request.getRequestURL();
		// String tempContextUrl = url.delete(url.length() -
		// request.getServletPath().length(), url.length()).toString();

		request.setCharacterEncoding("utf-8");
		Map<String, String> imgs = new HashMap<String, String>();// map类型变量
		List<Image> images = new ArrayList<Image>(); // Image类型列表
		Image image = new Image();
		int zip_id = Integer.parseInt(zip_id2);
		Zip zip = zipService.findZipById(zip_id); // 通过zip_id找到Zip对象
		// String unzipFilePath = tempContextUrl+ "/" + Constant.ZIP_FILE_FOLDER
		// + "/";

		if (zip != null) {
			images = imageService.findImageByZip(zip); // 通过zip对象查找解压后的所有图片
			String zipFolder = zip.getZip_name(); // 得到zip对象的压缩包名
			String[] zipfolders = zipFolder.split("[.]"); // 根据.分割成数组
			String imgsFolder = zipfolders[0]; // 得到压缩后文件夹名

			for (int i = 0; i < images.size(); i++) {
				// map存储img_id及其对应的图片完整路径
				imgs.put(images.get(i).getImg_id() + "",
						request.getSession().getServletContext().getRealPath("/") + Constant.ZIP_FILE_FOLDER
								+ File.separator + imgsFolder + File.separator + images.get(i).getImg_name());
			}

			boolean isupdate = true;
			Map<String, String> aMap = ImgUtil.ImgClassifyTag(imgs, true);// 调用接口识别图片，得到Map集合
			for (Entry<String, String> entry : aMap.entrySet()) { // 遍历集合
				System.out.println(entry.getKey() + " " + entry.getValue());
				image = imageService.findImageById(Integer.parseInt(entry.getKey()));// 通过entry.getKey()即集合的img_id对应的Image对象
				image.setImg_machine_tag_label(entry.getValue());// 将map集合的value设置为图片对象Img_machine_tag_label字段
				isupdate = (isupdate && imageService.updateImage(image)); // 表示图片更新成功
			}
			if (isupdate) {
				zip.setIsClassify(1);// 设置zip字段IsClassify为1表示已经初次识别
				boolean iszipUpdate = zipService.updateZip(zip); // 更新持久化数据库

				if (iszipUpdate) {
					Message<String> message = new Message<String>("200", Constant.CLASSIFY_SUCCESS, "");// 初始化message对象
					json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
					out.print(json);
				} else {
					Message<String> message = new Message<String>("-1", Constant.CLASSIFY_FAIL, "");// 初始化message对象
					json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
					out.print(json);
				}
			} else {
				Message<String> message = new Message<String>("-1", Constant.CLASSIFY_FAIL, "");// 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			}
		} else {
			Message<String> message = new Message<String>("-1", Constant.CLASSIFY_FAIL, "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}
}
