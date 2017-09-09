package cn.codekong.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;
import cn.codekong.bean.Admin;
import cn.codekong.bean.Category;
import cn.codekong.bean.ExportRecord;
import cn.codekong.bean.ExportTag;
import cn.codekong.bean.User;
import cn.codekong.bean.label.AmountOfCategory;
import cn.codekong.bean.label.Assign;
import cn.codekong.bean.label.AssignImageLabel;
import cn.codekong.bean.label.ExportHistoryLabel;
import cn.codekong.bean.label.ExportLabel;
import cn.codekong.bean.label.Message;
import cn.codekong.bean.label.SplitObject;
import cn.codekong.bean.label.UserSex;
import cn.codekong.config.Constant;
import cn.codekong.service.AdminService;
import cn.codekong.service.CategoryService;
import cn.codekong.service.ConstantService;
import cn.codekong.service.ContributeService;
import cn.codekong.service.ExportService;
import cn.codekong.service.ImageService;
import cn.codekong.service.OauthService;
import cn.codekong.service.UserService;
import cn.codekong.util.DataExportUtil;
import cn.codekong.util.DateUtils;

@Controller
public class AdminController {
	@Autowired
	AdminService adminService;
	@Autowired
	OauthService oauthService;
	@Autowired
	UserService userService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ExportService exportService;
	@Autowired
	ImageService imageService;
	@Autowired
	ConstantService constantService;

	/**
	 * 用于管理员登录验证，及持久化登录信息表
	 * 
	 * @param admin_name
	 * @param admin_password
	 * @param responce
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam("username") String admin_name,
			@RequestParam("password") String admin_password, HttpServletResponse response, HttpServletRequest request,
			ModelAndView model) throws IOException {

		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		DateUtils dateUtils = new DateUtils();
		String pwd = dateUtils.pwdOneMD5(admin_password);// 将passwordmd5加密
		Admin admin = new Admin();
		PrintWriter out;
		out = response.getWriter();
		admin = adminService.findAdminByNameAndPaw(admin_name, pwd);

		if (admin != null) { // 若有此管理员

			Date date2 = dateUtils.getOauthTime();
			String dd = date2.toString(); // 获取当前时间的字符串形式
			String admin_token = dateUtils.DateTwoMD5(dd);// 获取当前时间加密的token
			admin.setAdmin_token(admin_token);

			boolean oisupdate = adminService.update(admin);// 更新oauth登录信息表中的token和过期时间
			if (oisupdate) {// 若更新成功，则登陆成功
				session.setAttribute(Constant.USERNAME, admin_name);
				session.setAttribute(Constant.TOKEN, admin_token);
				model.setViewName("index");
			} else { // 负责提示oauth登录信息表未更新
				// response.getWriter().print("");
				// model.setViewName("login");
				out.print("<script>alert('登录失败');</script>");
				// out.print("<script>location='http://www.baidu.com';</script>");
				out.flush();// 有了这个，下面的return就不会执行了
				model.setViewName("login");
			}
		} else {
			out.print("<script>alert('用户名或密码错误');</script>");
			// out.print("<script>location='http://www.baidu.com';</script>");
			out.flush();// 有了这个，下面的return就不会执行了
			model.setViewName("login");
			// response.getWriter().print("<script>alert('用户名或密码错误');</script>");
			// model.setViewName("login");
		}
		return model;
	}

	/**
	 * 获取指定志愿者信息
	 * 
	 * @param user_id
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/getuserinfo", method = RequestMethod.POST)
	public void getUserInfo(@RequestParam("user_id") String user_id, HttpServletResponse response,
			HttpServletRequest request) throws IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		String json;

		User user = userService.findUserById(Integer.parseInt(user_id));
		if (user != null) {
			user.setPassword("");
			Message<User> message = new Message<User>("200", "", user);// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		} else {
			Message<String> message = new Message<String>("-1", Constant.MODIFY_USER_FAIl, "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	/**
	 * 修改志愿者信息
	 * 
	 * @param username
	 * @param sex
	 * @param tel_num
	 * @param integral
	 * @param accuracy
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/modifyuserinfo", method = RequestMethod.POST)
	public void modifyUserInfo(@RequestParam("user_id") String user_id, @RequestParam("username") String username,
			@RequestParam("sex") String sex, @RequestParam("integral") String integral,
			@RequestParam("accuracy") String accuracy, HttpServletResponse response, HttpServletRequest request)
			throws IOException {

		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		String json;
		// 非空操作，非负
		User user = new User();
		user.setUser_id(Integer.parseInt(user_id));
		user.setUsername(username);
		user.setSex(sex);
		user.setIntegral(Integer.parseInt(integral));
		user.setAccuracy(accuracy);

		int isupdate = adminService.updateUserByAdmin(user);
		if (isupdate >= 1) {
			Message<String> message = new Message<String>("200", Constant.ADMIN_MODIFY_USERINFO_SUCCESS, "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		} else {
			Message<String> message = new Message<String>("-1", Constant.ADMIN_MODIFY_USERINFO_FAIL, "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}

	}

	/**
	 * 管理员修改个人密码
	 * 
	 * @param oldpwd
	 * @param newpwd
	 * @param oauth_token
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/modifyadminpassword", method = RequestMethod.POST)
	public void modifyPwd(@RequestParam("old_password") String oldpwd, @RequestParam("new_password") String newpwd,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		DateUtils dateUtils = new DateUtils();
		HttpSession session = request.getSession();
		Admin admin = new Admin();
		Gson gson = new Gson();
		String json;

		String admin_token = session.getAttribute(Constant.TOKEN).toString();
		String oldPwdMD5 = dateUtils.pwdOneMD5(oldpwd); // 对原密码进行MD5加密
		String newPwdMD5 = dateUtils.pwdOneMD5(newpwd);

		admin = adminService.findAdminByToken(admin_token); // 找到oauth_token对应的oauth对象
		if (admin != null) {
			if (admin.getAdmin_password().equals(oldPwdMD5)) {
				admin.setAdmin_password(newPwdMD5);
				boolean isupdate = adminService.update(admin);
				if (isupdate) {
					Message<String> message = new Message<String>("200", Constant.MODIFY_SUCCESS,
							admin.getAdmin_token());// 初始化message对象
					json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
					out.print(json);
				} else {
					Message<String> message = new Message<String>("-1", Constant.MODIFY_FAILED, "");// 初始化message对象
					json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
					out.print(json);
				}
			} else {
				Message<String> message = new Message<String>("-1", Constant.MODIFY_FAILED, "");// 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			}
		} else {
			Message<String> message = new Message<String>("-1", Constant.MODIFY_FAILED, "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}

	}

	/**
	 * 得到所有用户信息
	 * 
	 * @param start
	 * @param num
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/allusers", method = RequestMethod.POST)
	public void getInfo(@RequestParam("start") String start, @RequestParam("page_num") String num,
			HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		String json;
		int pages_num = 0;
		int numOfPages = 0;
		SplitObject<User> uSplitObject = new SplitObject<User>();

		List<User> users = adminService.getAllUsers(Integer.parseInt(start), Integer.parseInt(num));
		pages_num = adminService.getAllUsers();

		if (users != null) {
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getAccuracy() == null || users.get(i).getAccuracy().equals("null")) {
					users.get(i).setAccuracy("0.0%");
				}else{
					users.get(i).setAccuracy(String.format("%.2f", Float.parseFloat(users.get(i).getAccuracy())) + "%");
				}
			}
		}

		uSplitObject.setList(users);
		if (pages_num % Constant.DEVI_NUM == 0) {
			numOfPages = pages_num / Constant.DEVI_NUM;
		} else {
			numOfPages = (pages_num / Constant.DEVI_NUM + 1);
		}
		uSplitObject.setPages_num(numOfPages);

		Message<SplitObject<User>> message = new Message<SplitObject<User>>("200", "", uSplitObject);// 初始化message对象
		json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
		out.print(json);
	}

	/**
	 * 冻结用户
	 * 
	 * @param user_id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/frozenuser", method = RequestMethod.POST)
	public void frozenUser(@RequestParam("user_id") String user_id, HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		String json;

		int update = userService.updateUserFrozen(Integer.parseInt(user_id), 1);
		if (update >= 1) {
			Message<String> message = new Message<String>("200", "", "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		} else {
			Message<String> message = new Message<String>("-1", "", "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	/**
	 * 解冻用户
	 * 
	 * @param user_id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/unfrozenuser", method = RequestMethod.POST)
	public void unfrozenUser(@RequestParam("user_id") String user_id, HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		String json;

		int update = userService.updateUserFrozen(Integer.parseInt(user_id), 0);
		if (update >= 1) {
			Message<String> message = new Message<String>("200", "", "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		} else {
			Message<String> message = new Message<String>("-1", "", "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	/**
	 * 未冻结用户列表
	 * 
	 * @param start
	 * @param num
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/unfrozenuserlist", method = RequestMethod.POST)
	public void unfrozenUsers(@RequestParam("start") String start, @RequestParam("page_num") String num,
			HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		SplitObject<User> splitObject = new SplitObject<User>();
		String json;
		// 获取未冻结用户的user列表
		List<User> users = userService.getUsersOfFrozenOrUnfrozen(0, Integer.parseInt(start), Integer.parseInt(num));
		// 获取未冻结用户数量
		int unfrozenNum = new Long(userService.getUsersNumOfFrozenOrUnfrozen(0)).intValue();
		// 设置准确率格式
	
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getAccuracy() == null || users.get(i).getAccuracy().equals("null")) {
				users.get(i).setAccuracy("0.0%");
			}else{
				users.get(i).setAccuracy(String.format("%.2f", Float.parseFloat(users.get(i).getAccuracy())) + "%");
			}
		}
		if (unfrozenNum % Constant.DEVI_NUM == 0) {
			splitObject.setPages_num((unfrozenNum / Constant.DEVI_NUM));
		} else {
			splitObject.setPages_num((unfrozenNum / Constant.DEVI_NUM + 1));
		}
		// 设置对象属性
		splitObject.setList(users);

		Message<SplitObject<User>> message = new Message<SplitObject<User>>("200", "", splitObject);// 初始化message对象
		json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
		out.print(json);
	
	}

	/**
	 * 冻结用户列表
	 * 
	 * @param start
	 * @param num
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/frozenuserlist", method = RequestMethod.POST)
	public void frozenUsers(@RequestParam("start") String start, @RequestParam("page_num") String num,
			HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		SplitObject<User> uSplitObject = new SplitObject<User>();
		Gson gson = new Gson();
		String json;

		// 获取被冻结用户列表
		List<User> users = userService.getUsersOfFrozenOrUnfrozen(1, Integer.parseInt(start), Integer.parseInt(num));
		// 获取冻结用户数量
		int frozenUserNum = new Long(userService.getUsersNumOfFrozenOrUnfrozen(1)).intValue();

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getAccuracy() == null || users.get(i).getAccuracy().equals("null")) {
				users.get(i).setAccuracy("0.0%");
			}else{
				users.get(i).setAccuracy(String.format("%.2f", Float.parseFloat(users.get(i).getAccuracy())) + "%");
			}
		}
		if (frozenUserNum % Constant.DEVI_NUM == 0) {
			uSplitObject.setPages_num((frozenUserNum / Constant.DEVI_NUM));
		} else {
			uSplitObject.setPages_num((frozenUserNum / Constant.DEVI_NUM + 1));
		}
		// 设置对象属性
		uSplitObject.setList(users);

		Message<SplitObject<User>> message = new Message<SplitObject<User>>("200", "", uSplitObject);// 初始化message对象
		json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
		out.print(json);
	}

	/**
	 * @param category_name
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/getallcategory", method = RequestMethod.POST)
	public void getAllCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		String json;
		List<AmountOfCategory> amountOfCategories = new ArrayList<AmountOfCategory>();
		List<Category> categories = categoryService.getCategotyList();
		for (int i = 0; i < categories.size(); i++) {
			AmountOfCategory amountOfCategory = new AmountOfCategory();
			Category category = categories.get(i);
			amountOfCategory.setCategory(category);
			amountOfCategory.setImg_amount_of_category(
					String.valueOf(imageService.getAmountByCategoryId(category.getCategory_id())));

			amountOfCategories.add(amountOfCategory);
		}
		Message<List<AmountOfCategory>> message = new Message<List<AmountOfCategory>>("200", "", amountOfCategories);// 初始化message对象
		json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
		out.print(json);
	}

	/**
	 * 管理员添加类别
	 * 
	 * @param oauth_token
	 * @param category_name
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/addcategory", method = RequestMethod.POST)
	public void addCategory(@RequestParam("category") String category_name, HttpServletResponse response)
			throws IOException {

		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();

		Category category = new Category();
		Category category2 = new Category();
		Gson gson = new Gson();
		String json;
		boolean issaved = false;
		category2 = categoryService.findCatogoryByName(category_name);// 判断category表中是否有此类别名
		if (category2 == null) { // 若没有此类别名 ，则可进行添加
			category.setCategory_name(category_name);
			issaved = categoryService.saveCatogory(category); // 持久化category对象
			if (issaved) {
				Message<String> message = new Message<String>("200", Constant.ADD_SUCCESS, "");// 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			} else {
				Message<String> message = new Message<String>("-1", Constant.ADD_FAILED, "");// 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			}
		} else {
			Message<String> message = new Message<String>("-1", Constant.ADD_CATEGORY_EXSIST, "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	/**
	 * 预览标签结果
	 * 
	 * @param start
	 * @param num
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/exportresultlist", method = RequestMethod.POST)
	public void getDownloadImagesHistory(HttpServletResponse response, HttpServletRequest request) throws IOException {

		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		String json;
		Gson gson = new Gson();
		List<ExportLabel> images = adminService.getExportImage();
		String data = DataExportUtil.jsonFormatter(images);
		Message<String> message = new Message<String>("200", "", data);// 初始化message对象
		json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
		out.print(json);
	}

	/**
	 * 被系统判定的标签化结果的数量
	 * 
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/exportresultlistamount", method = RequestMethod.POST)
	public void exportResultListAmount(HttpServletResponse response, HttpServletRequest request) throws IOException {

		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		String json;
		Gson gson = new Gson();
		List<ExportLabel> images = adminService.getExportImage();

		Message<String> message = new Message<String>("200", "", images.size() + "");// 初始化message对象
		json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
		out.print(json);
	}

	/**
	 * 导出历史标签结果或预览列表
	 * 
	 * @param export_id
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/exportorpreviewhistorylistresult", method = RequestMethod.POST)
	public void exportOrPreviewHistoryListResult(@RequestParam(Constant.IDENTITY) String identity,
			@RequestParam("export_id") String export_id, HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		System.out.println(identity + "," + export_id);
		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		String json;
		Gson gson = new Gson();
		List<ExportLabel> images = adminService.getExportHistoryFile(Integer.parseInt(export_id));
		if (identity.equals("preview")) {
			String data = DataExportUtil.jsonFormatter(images);
			Message<String> message = new Message<String>("200", "", data);// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		} else {
			// 导出标签化结果
			DataExportUtil.downloadJsonResult(images, response);
			Message<String> message = new Message<String>("200", Constant.EXPORT_SUCCESS, "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	/**
	 * 导出已被判定的图片
	 * 
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/exportresult", method = RequestMethod.POST)
	public void exportResult(HttpServletResponse response, HttpServletRequest request) throws IOException {

		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		String json;
		Gson gson = new Gson();
		boolean issaved = true;
		// 得到导出图片列表
		List<ExportLabel> images = adminService.getExportImage();
		if (!images.isEmpty()) {
			// 如果导出图片列表不为空
			ExportRecord exportRecord = new ExportRecord();
			Date export_time = new Date();// 获取当前时间
			exportRecord.setExport_time(export_time);
			// 将exportRecord对象持久化到数据库中
			int export_id = exportService.saveExportRecord(exportRecord);
			if (export_id != 0) {
				// export_id不为0即持久化成功
				for (int i = 0; i < images.size(); i++) {// 遍历ExportLabel列表
					ExportLabel exportLabel = images.get(i);
					int img_id = exportLabel.getImg_id();// 获取对象的id
					ExportTag exportTag = new ExportTag();
					// 设置exportTag对象的属性
					exportTag.setExport_id(export_id);
					exportTag.setImg_id(img_id);
					// 持久化到数据库
					issaved = (issaved && exportService.save(exportTag));
				}
				if (issaved) {
					int isUpdate = 0;
					for (int i = 0; i < images.size(); i++) {
						ExportLabel exportLabel = images.get(i);
						int img_id = exportLabel.getImg_id();// 获取对象的id
						isUpdate += exportService.updateExport(img_id);
					}
					if (isUpdate == images.size()) {
						// 将导出记录id返回
						Message<String> message = new Message<String>("200", Constant.EXPORT_SUCCESS, export_id + "");// 初始化message对象
						json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
						out.print(json);
					} else {
						Message<String> message = new Message<String>("-1", Constant.EXPORT_FAIL, "");// 初始化message对象
						json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
						out.print(json);
					}

				} else {
					Message<String> message = new Message<String>("-1", Constant.EXPORT_FAIL, "");// 初始化message对象
					json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
					out.print(json);
				}
			} else {
				Message<String> message = new Message<String>("-1", Constant.EXPORT_FAIL, "");// 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			}
		} else {
			Message<String> message = new Message<String>("-1", Constant.NO_EXPORT_RESULT, "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	/**
	 * 历史导出列表
	 * 
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/getdownloadimageshistory", method = RequestMethod.POST)
	public void getDownloadImagesHistory(HttpServletResponse response, @RequestParam("start") String start,
			@RequestParam("page_num") String page_size, HttpServletRequest request) throws IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		SplitObject<ExportHistoryLabel> splitObject = new SplitObject<ExportHistoryLabel>();
		String json;
		Gson gson = new Gson();
		// 获取所有历史列表
		List<ExportHistoryLabel> exportHistoryLabels = exportService.gExportHistoryLabels(Integer.parseInt(start),
				Integer.parseInt(page_size));

		// 获取历史列表数量
		int num = new Long(exportService.exportHistoryLabelsNum()).intValue();

		splitObject.setList(exportHistoryLabels);
		if (num % Constant.DEVI_NUM == 0) {
			splitObject.setPages_num(num / Constant.DEVI_NUM);
		} else {
			splitObject.setPages_num((num / Constant.DEVI_NUM + 1));
		}
		Message<SplitObject<ExportHistoryLabel>> message = new Message<SplitObject<ExportHistoryLabel>>("200", "",
				splitObject);// 初始化message对象
		json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
		out.print(json);
	}

	/**
	 * 
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/getimgamountdata", method = RequestMethod.POST)
	public void getImgAmounData(HttpServletResponse response, HttpServletRequest request) throws IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		String json;
		Gson gson = new Gson();
		Assign assign = new Assign();

		long amount10 = imageService.getAmountOfDefinedTask(10);
		long amount20 = imageService.getAmountOfDefinedTask(20);
		long amount30 = imageService.getAmountOfDefinedTask(30);
		long all_task_amount = amount10 + amount20 + amount30;
		assign.setAmount10(amount10 + "");
		assign.setAmount20(amount20 + "");
		assign.setAmount30(amount30 + "");
		assign.setAll_task_amount(all_task_amount + "");

		Message<Assign> message = new Message<Assign>("200", "", assign);// 初始化message对象
		json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
		out.print(json);
	}

	@RequestMapping(value = "/admin/logout", method = RequestMethod.GET)
	public void logout(HttpServletResponse response, HttpServletRequest request) throws IOException {

		HttpSession session = request.getSession();
		session.setAttribute(Constant.USERNAME, "");
		session.setAttribute(Constant.TOKEN, "");
		PrintWriter out = response.getWriter();
		out.print("<script>window.location.href='/ImageClassify/admin/login.jsp'</script>");
	}

	/**
	 * 导出历史标签结果
	 * 
	 * @param export_id
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/exporthistorylistresult", method = RequestMethod.GET)
	public void exportHistoryListResult(@RequestParam("export_id") String export_id, HttpServletResponse response,
			HttpServletRequest request) throws IOException {

		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		String json;
		Gson gson = new Gson();
		List<ExportLabel> images = adminService.getExportHistoryFile(Integer.parseInt(export_id));

		if (!images.isEmpty()) {
			// 导出标签化结果
			DataExportUtil.downloadJsonResult(images, response);
		} else {
			Message<String> message = new Message<String>("-1", Constant.EXPORT_FAILED, "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	/**
	 * 得到完成未完成的图片任务
	 * 
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/getalltaskfinished", method = RequestMethod.POST)
	public void getAllTaskFinished(HttpServletResponse response, HttpServletRequest request) throws IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		String json;
		Gson gson = new Gson();
		AssignImageLabel assign = new AssignImageLabel();

		long unfinishedAmount = imageService.getAmountOfDefinedImage(0);
		long finishedAmount = imageService.getAmountOfDefinedImage(1);

		assign.setAll_img_amount((unfinishedAmount + finishedAmount) + "");
		assign.setFinished_img(finishedAmount + "");
		assign.setUnfinished_img(unfinishedAmount + "");
		Message<AssignImageLabel> message = new Message<AssignImageLabel>("200", "", assign);// 初始化message对象
		json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
		out.print(json);
	}

	/**
	 * 志愿者性别数量
	 * 
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/getallusersex", method = RequestMethod.POST)
	public void getAllUserSex(HttpServletResponse response, HttpServletRequest request) throws IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		String json;
		Gson gson = new Gson();

		long usermount = imageService.getAmountOfUser();
		long userOfMan = imageService.getAmountOfUserSexAmount("男");
		long userOfWoman = imageService.getAmountOfUserSexAmount("女");
		long unKnown = usermount - userOfMan - userOfWoman;
		UserSex userSex = new UserSex();
		userSex.setAll_user_amount(usermount + "");
		userSex.setMan_user(userOfMan + "");
		userSex.setUnknow_user(unKnown + "");
		userSex.setWoman_user(userOfWoman + "");

		Message<UserSex> message = new Message<UserSex>("200", "", userSex);// 初始化message对象
		json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
		out.print(json);
	}

	/**
	 * 修改推送策略的标识
	 * 
	 * @param identify_time
	 * @param identify_frequency_marks
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/modifyconfiginfos", method = RequestMethod.POST)
	public void modifyConfigInfos(@RequestParam(Constant.THREHOLD) String threhold,
			@RequestParam(Constant.IDENTITY_TIME) String identify_time,
			@RequestParam(Constant.IDENTIFY_FREQUENCY_MARKS) String identify_frequency_marks,
			HttpServletResponse response, HttpServletRequest request) throws IOException {

		System.out.println(threhold + "," + identify_frequency_marks + "," + identify_time);
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();
		String json;
		Gson gson = new Gson();
		// 分别获取constant对象
		cn.codekong.bean.Constant constant1 = constantService.getConstantByKey(Constant.THREHOLD);
		cn.codekong.bean.Constant constant2 = constantService.getConstantByKey(Constant.IDENTITY_TIME);
		cn.codekong.bean.Constant constant3 = constantService.getConstantByKey(Constant.IDENTIFY_FREQUENCY_MARKS);

		if (constant1 != null && constant2 != null && constant3 != null) {
			// 设置接收值
			constant1.setValue(threhold);
			constant2.setValue(identify_time);
			constant3.setValue(identify_frequency_marks);
			// 更新数据库
			boolean isUpdate = (constantService.updateValueOfKey(constant1)
					&& constantService.updateValueOfKey(constant2) && constantService.updateValueOfKey(constant3));
			if (isUpdate) {
				// 返回json格式的list列表
				Message<String> message = new Message<String>("200", Constant.MODIFY_SUCCESS, "");// 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			} else {
				Message<String> message = new Message<String>("-1", Constant.MODIFY_FAILED, "");// 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			}
		} else {
			Message<String> message = new Message<String>("-1", "", Constant.HAVE_NO_CONSTANT);// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	/**
	 * 获取后台配置的信息
	 * 
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/getsproconfig", method = RequestMethod.POST)
	public void getSPROConfig(HttpServletResponse response, HttpServletRequest request) throws IOException {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		String json;
		Gson gson = new Gson();

		List<cn.codekong.bean.Constant> constants = constantService.getConstantConfig();
		if (constants.size() == 3) {
			Message<List<cn.codekong.bean.Constant>> message = new Message<List<cn.codekong.bean.Constant>>("200", "",
					constants);// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		} else {
			Message<String> message = new Message<String>("-1", Constant.GET_SPRO_CONFIG_INFO, "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

}
