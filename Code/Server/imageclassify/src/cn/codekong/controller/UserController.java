package cn.codekong.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;

import cn.codekong.bean.Category;
import cn.codekong.bean.CheckIn;
import cn.codekong.bean.Interest;
import cn.codekong.bean.Oauth;
import cn.codekong.bean.User;
import cn.codekong.bean.label.CategoryHobby;
import cn.codekong.bean.label.Message;
import cn.codekong.bean.label.TaskOfAmount;
import cn.codekong.bean.label.UserTaskAmount;
import cn.codekong.config.Constant;
import cn.codekong.service.CategoryService;
import cn.codekong.service.CheckInService;
import cn.codekong.service.InterestService;
import cn.codekong.service.OauthService;
import cn.codekong.service.TaskService;
import cn.codekong.service.UserService;
import cn.codekong.util.DateUtils;
import cn.codekong.util.MD5;

@Controller
@SessionAttributes("oauth_token")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private OauthService oauthService;
	@Autowired
	private CheckInService checkInService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private InterestService interestService;
	@Autowired
	private TaskService taskService;

	/**
	 * 用户注册控制类
	 * 
	 * @param tel_num
	 * @param password
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public void register(@RequestParam("tel_num") String tel_num, @RequestParam("username") String username,
			@RequestParam("password") String password, HttpServletResponse response, HttpServletRequest request)
			throws IOException, ParseException {
		response.setCharacterEncoding("utf-8");// 设置编码格式为utf-8
		request.setCharacterEncoding("utf-8");

		PrintWriter out;
		String password2;
		out = response.getWriter();
		String json;
		Gson gson = new Gson();
		MD5 md5 = new MD5();
		// 创建用户并设置对应值
		User user = new User();
		User user2 = new User();
		user2 = userService.findUserByTelnum(tel_num);

		if (user2 != null) {
			Message<String> message = new Message<String>("-1", "手机号已存在", "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		} else {
			user.setTel_num(tel_num);
			user.setUsername(username);
			password2 = md5.generateMD5(password);
			user.setPassword(password2);

			if (userService.save(user)) {
				// 如果成功保存
				Message<String> message = new Message<String>("200", Constant.REGISTER_SUCCESS, ""); // 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			} else {
				Message<String> message = new Message<String>("-1", Constant.REGISTER_FAILED, ""); // 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			}
		}
	}

	/**
	 * 用户登录控制方法
	 * 
	 * @param phonenum
	 * @param password
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public void userLoginController(@RequestParam("tel_num") String tel_num, @RequestParam("password") String password,
			HttpServletResponse responce) throws IOException, ParseException {
		PrintWriter out;
		responce.setCharacterEncoding("utf-8");
		out = responce.getWriter();

		String json;
		User user = new User();
		Oauth oauth = new Oauth();
		Oauth oauthIsExist = new Oauth();
		MD5 md5 = new MD5();
		Gson gson = new Gson();
		DateUtils dtUtils = new DateUtils();

		password = md5.generateMD5(password);
		// 调用userService的findUserByPawTel方法查询数据库是否有该用户
		user = userService.findUserByPawTel(password, tel_num);

		if (user != null) { // 如果有此用户
			oauthIsExist = oauthService.findOauthByUser(user);
			if (oauthIsExist == null) {
				// 将指定类型的date类型时间设置为oauth的Oauth_token_expiration值
				Date date2 = dtUtils.getOauthTime();
				String dd = date2.toString();
				oauth.setOauth_token_expiration(date2);

				// 将当前推迟1周时间进行MD5加密后作为oauth的oauth_token值
				String oauth_token = dtUtils.DateTwoMD5(dd);
				oauth.setOauth_token(oauth_token);
				oauth.setUser(user);

				if (oauthService.saveOauth(oauth)) {
					Message<String> message = new Message<String>("200", Constant.LOGIN_SUCCESS,
							oauth.getOauth_token());// 初始化message对象
					json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
					out.print(json);
				} else {
					Message<String> message = new Message<String>("-1", Constant.LOGIN_FAILED, "");// 初始化message对象
					json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
					out.print(json);
				}
			} else {
				if (user.getIs_frozen() == 0) {

					Date oauth_token_expiration = dtUtils.getOauthTime();
					String dd = oauth_token_expiration.toString();

					// 将当前推迟1周时间进行MD5加密后作为oauth的oauth_token值
					String oauth_token = dtUtils.DateTwoMD5(dd);

					oauthIsExist.setOauth_token(oauth_token);
					oauthIsExist.setOauth_token_expiration(oauth_token_expiration);

					boolean isUpdate = oauthService.updateOauth(oauthIsExist);
					if (isUpdate) {
						Message<String> message = new Message<String>("200", Constant.LOGIN_SUCCESS,
								oauthIsExist.getOauth_token());// 初始化message对象
						json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
						out.print(json);
					} else {
						Message<String> message = new Message<String>("-1", Constant.LOGIN_FAILED, "");// 初始化message对象
						json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
						out.print(json);
					}
				} else {
					Message<String> message = new Message<String>("400", Constant.USER_LOGIN_IS_FROZEN, "");// 初始化message对象
					json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
					out.print(json);
				}
			}
		} else {
			Message<String> message = new Message<String>("-1", Constant.LOGIN_INFO_FAILED, "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	/**
	 * 修改密码
	 * 
	 * @param oldPwd
	 * @param newPwd
	 * @param oauth_token
	 * @param response
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/user/modifypwd", method = RequestMethod.POST)
	public void modifyPwd(@RequestParam("oldpwd") String oldpwd, @RequestParam("newpwd") String newpwd,
			@RequestParam("oauth_token") String oauth_token, HttpServletResponse response)
			throws IOException, ParseException {
		response.setCharacterEncoding("utf-8"); // 设置响应responce编码为utf-8
		PrintWriter out = response.getWriter();

		Oauth oauth = new Oauth();
		User user = new User();
		MD5 md5 = new MD5();
		Gson gson = new Gson();
		DateUtils dbUtils = new DateUtils();
		String json;

		String oldPwdMD5 = md5.generateMD5(oldpwd); // 对原密码进行MD5加密
		oauth = oauthService.findOauthByOauthToken(oauth_token); // 找到oauth_token对应的oauth对象

		user = oauth.getUser(); // 找到oauth_id对应的user对象
		if (user.getPassword().equals(oldPwdMD5)) { // 如果用户的输入原密码与数据库中密码一致
			String newPwdMD5 = md5.generateMD5(newpwd); // 对新密码进行md5加密
			user.setPassword(newPwdMD5);
			// 如果成功更新user对象
			if (userService.update(user)) { // 并将新密码持久化到数据库中
				// 获取当前date类型的时间戳推迟一周并设置为oauth的过期时间字段中
				Date timestamp = dbUtils.getOauthTime();
				String dd = timestamp.toString();
				oauth.setOauth_token_expiration(timestamp);
				// 将当前时间md5两次并设置为oauth对象的token值
				String newtoken = dbUtils.DateTwoMD5(dd);
				oauth.setOauth_token(newtoken);
				System.out.println(newtoken);
				// 更新oauth 并持久化到数据库中
				if (oauthService.updateOauth(oauth)) {
					Message<String> message = new Message<String>("200", Constant.MODIFY_SUCCESS, newtoken);// 初始化message对象
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
	 * 修改个人信息：头像、昵称、性别
	 * 
	 * @param avatar
	 * @param token
	 * @param sex
	 * @param username
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/user/modifyinfo", method = RequestMethod.POST)
	public void modifyInfo(@RequestParam("avatar") CommonsMultipartFile avatar,
			@RequestParam("oauth_token") String oauth_token, @RequestParam("sex") String sex,
			@RequestParam("username") String username, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		System.out.println(sex);
		System.out.println(username);

		String json;
		Gson gson = new Gson();
		Oauth oauth = new Oauth();
		User user = new User();
		response.setCharacterEncoding("utf-8"); // 设置编码为utf-8
		PrintWriter out = response.getWriter();

		oauth = oauthService.findOauthByOauthToken(oauth_token); // 找到token对应的oauth对象
		user = oauth.getUser();// 及oauth对应的用户

		String tel_num = user.getTel_num();
		String filefoder = tel_num;
		StringBuffer url = request.getRequestURL();
		String tempContextUrl = url.delete(url.length() - request.getServletPath().length(), url.length()).toString();
		String fileOri = avatar.getOriginalFilename();
		String[] array = fileOri.split("\\.");
		String type = array[array.length - 1];

		String newName = new MD5().generateMD5(filefoder);
		String filename = newName + "." + type; // 头像文件名
		// 文件保存相对路径
		try {
			//
			String avatarPath = request.getSession().getServletContext().getRealPath("/") + Constant.AVATAR_FILE_FOLDER
					+ File.separator + filefoder + File.separator;
			File avatarFile = new File(avatarPath, filename);
			if (!avatarFile.exists()) {
				avatarFile.mkdirs();
			}
			avatar.transferTo(avatarFile);

			String newAvatarPath = tempContextUrl + Constant.SIGNALZUOXIE + Constant.AVATAR_FILE_FOLDER
					+ Constant.SIGNALZUOXIE + filefoder + Constant.SIGNALZUOXIE + filename;

			user.setAvatar_url(newAvatarPath);
			user.setSex(sex);
			user.setUsername(username);

			boolean isupdate = userService.update(user);// 更新sex、username持久化到数据库中
			if (isupdate) {
				Message<String> message = new Message<String>("200", Constant.MODIFY_SUCCESS, oauth_token);// 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			} else {
				Message<String> message = new Message<String>("-1", "没有更新成功", "");// 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			}
		} catch (Exception e) {
			Message<String> message = new Message<String>("-1", "没有更新成功", "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
			e.printStackTrace();
		}
	}

	/**
	 * 查找指定的个人信息
	 * 
	 * @param oauth_token
	 * @param responce
	 * @throws IOException
	 */
	@RequestMapping(value = "/user/getinfo", method = RequestMethod.POST)
	public void getInfo(@RequestParam(Constant.OAUTH_TOKEN) String oauth_token, HttpServletResponse responce)
			throws IOException {

		System.out.println(oauth_token);
		String json;
		Gson gson = new Gson();
		PrintWriter out;
		responce.setCharacterEncoding("utf-8");
		out = responce.getWriter();
		User user = new User();
		Oauth oauth = new Oauth();

		oauth = oauthService.findOauthByOauthToken(oauth_token);// 找到token对应的oauth对象

		user = oauth.getUser();

		if (user != null) {
			UserTaskAmount ut = new UserTaskAmount();
			String accuracy = userService.getAvgAccuracy(user.getUser_id());
			String task_amount = taskService.getAmountTaskOfCommit(user.getUser_id());
			user.setPassword("");
			user.setAccuracy(accuracy);
			userService.updateAccuracy(user);
			ut.setUser(user);
			ut.setTask_amount(task_amount);
			Message<UserTaskAmount> message = new Message<UserTaskAmount>("200", "", ut);// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		} else {
			Message<String> message = new Message<String>("-1", Constant.GETLIST_FAILED, "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	/**
	 * 用户打卡签到
	 * 
	 * @param oauth_token
	 * @param response
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/user/checkin", method = RequestMethod.POST)
	public void checkIn(@RequestParam("oauth_token") String oauth_token, HttpServletResponse response)
			throws IOException, ParseException {

		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String json;
		Gson gson = new Gson();

		User user = new User();
		Oauth oauth = new Oauth();
		CheckIn checkIn = new CheckIn();
		DateUtils dbUtils = new DateUtils();
		oauth = oauthService.findOauthByOauthToken(oauth_token);// 找到token对应的oauth对象

		user = oauth.getUser();

		if (user != null) {
			user.setIntegral(user.getIntegral() + 1);
			// 获取当前date类型的时间戳并设置为签到打卡的时间
			Date checkin_time = dbUtils.getCheckInDate();
			checkIn.setCheckin_time(checkin_time); // 设置checkIn对象的时间
			checkIn.setUser_id(user.getUser_id()); // 设置checkIn对象的用户id

			boolean issava = checkInService.saveCheckIn(checkIn); // 将checkIn对象持久化到数据库中
			boolean integral = userService.update(user);
			if (issava && integral) { // 如果持久化成功
				Message<String> message = new Message<String>("200", Constant.CHECKIN_SUCCESS, "");// 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			}else{
				Message<String> message = new Message<String>("-1", Constant.CHECKIN_FAILED, "");// 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			}
		}else{
			Message<String> message = new Message<String>("-1", Constant.CHECKIN_FAILED, "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	/**
	 * 得到用户近一个月内的打卡记录
	 * 
	 * @param oauth_token
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/user/getcheckinlist", method = RequestMethod.POST)
	public void getCheckInList(@RequestParam("month") String month, @RequestParam("year") String year,
			@RequestParam("oauth_token") String oauth_token, HttpServletResponse response) throws IOException {

		System.out.println(month + year);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String json;
		Gson gson = new Gson();

		Oauth oauth = new Oauth();
		List<CheckIn> checkIns = new ArrayList<CheckIn>();
		oauth = oauthService.findOauthByOauthToken(oauth_token);// 找到token对应的oauth对象

		checkIns = checkInService.getCheckIns(oauth.getUser().getUser_id());// 得到user_id对应的checkIn对象列表

		if (checkIns != null) {
			StringBuilder data = new StringBuilder();
			int dayofMonth = 0;
			int year2 = 0;
			int month2 = 0;
			Calendar calendar = Calendar.getInstance();
			for (int i = 0; i < checkIns.size(); i++) { // 遍历列表对象
				calendar.setTime(checkIns.get(i).getCheckin_time());// 得到第i个对象的打标签时间
				month2 = calendar.get(Calendar.MONTH) + 1; // 获取时间的月份
				year2 = calendar.get(Calendar.YEAR);
				if (month2 == Integer.parseInt(month) && Integer.parseInt(year) == year2) { // 如果该月份值与传过来的月份值一致
					dayofMonth = calendar.get(Calendar.DAY_OF_MONTH); // 则获取该月日期值
					data.append(dayofMonth); // 将日期值以“|”分隔连接
					data.append("|");
				}
			}
			if (data.length() != 0) {
				data.deleteCharAt(data.length() - 1);
			}
			System.out.println(data);
			// 获取列表成功，并将获得的data值即当月打标签日期串传给客户端
			Message<StringBuilder> message = new Message<StringBuilder>("200", "", data);// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		} else {
			Message<String> message = new Message<String>("-1", Constant.GETLIST_FAILED, "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}

	}

	/**
	 * 检测手机号是否已被注册
	 * 
	 * @param tel_num
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/user/checkphoneisused", method = RequestMethod.POST)
	public void checkPhoneIsUsed(@RequestParam("tel_num") String tel_num, HttpServletResponse response)
			throws IOException {

		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String json;
		Gson gson = new Gson();

		User user = new User();
		user = userService.findUserByTelnum(tel_num);
		if (user != null) {
			Message<String> message = new Message<String>("200", "1", "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		} else {
			Message<String> message = new Message<String>("200", "-1", "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	@RequestMapping(value = "/user/getcategoryhobbylist", method = RequestMethod.POST)
	public void getCategoryList(@RequestParam(Constant.OAUTH_TOKEN) String oauth_token, HttpServletResponse response)
			throws IOException {

		System.out.println(oauth_token);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String json;
		Gson gson = new Gson();
		List<Interest> interests = new ArrayList<Interest>();

		Oauth oauth = oauthService.findOauthByOauthToken(oauth_token);
		User user = oauth.getUser();
		// 找到用户user_id对应的所有兴趣对象信息
		interests = interestService.getInterestById(user.getUser_id());
		List<Category> hobbies = new ArrayList<Category>();
		if (!interests.isEmpty()) {
			List<Integer> integers = new ArrayList<Integer>();
			for (int i = 0; i < interests.size(); i++) {
				integers.add(interests.get(i).getCategory_id());
			}
			hobbies = interestService.getInterestCategory(integers);
		}
		List<Category> categories = categoryService.getCategotyList();

		if (categories != null) {
			CategoryHobby categoryHobby = new CategoryHobby();
			categoryHobby.setCategories(categories);
			categoryHobby.setHobbies(hobbies);
			Message<CategoryHobby> message = new Message<CategoryHobby>("200", "", categoryHobby);// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		} else {
			Message<String> message = new Message<String>("-1", Constant.GETCHECKINLIST_FAILED, "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	/**
	 * 根据标签类别category_id，修改用户感兴趣表
	 * 
	 * @param oauth_token
	 * @param category_id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/user/modifyhobby", method = RequestMethod.POST)
	public void addHobby(HttpSession httpSession, @RequestParam(Constant.OAUTH_TOKEN) String oauth_token,
			@RequestParam("category_id") String category_ids, HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("utf-8");// 设置编码格式为utf-8
		PrintWriter out = response.getWriter();
		String json;
		Gson gson = new Gson();

		Oauth oauth = new Oauth();
		User user = new User();

		String[] category_ids2 = category_ids.split("\\|");
		oauth = oauthService.findOauthByOauthToken(oauth_token);

		user = oauth.getUser();
		boolean flag = true;
		if (user != null) {
			// 删除所有user_id对应的兴趣
			interestService.deleteInterests(user.getUser_id());
			StringBuilder inter = new StringBuilder();
			for (int i = 0; i < category_ids2.length; i++) {
				Interest interest = new Interest();
				inter.append(category_ids2[i]);
				inter.append("|");
				interest.setCategory_id(Integer.parseInt(category_ids2[i]));
				interest.setUser_id(user.getUser_id());
				boolean b = interestService.saveInterest(interest);
				flag = (flag && b);
			}
			if (inter.length() > 0) {
				inter.deleteCharAt(inter.length() - 1);
			}
			if (flag) {
				Message<StringBuilder> message = new Message<StringBuilder>("200", Constant.ADD_SUCCESS, inter);// 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			} else {
				Message<String> message = new Message<String>("-1", Constant.ADD_FAILED, "");// 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			}

		} else {
			Message<String> message = new Message<String>("-1", Constant.ADD_FAILED, "");// 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}
	  
	 /**
	  * 志愿者获取兴趣
	  * @param oauth_token 
	  * @param response
	  * @throws IOException
	  */
	 @RequestMapping(value = "/user/gethobbylist",method = RequestMethod.POST) 
	 public void deleteHobby(@RequestParam(Constant.OAUTH_TOKEN) String oauth_token,HttpServletResponse response) throws IOException{
		 
		 response.setCharacterEncoding("utf-8");//设置编码格式为utf-8
	     PrintWriter out = response.getWriter();
	     String json;
	     Gson gson = new Gson();  
	     Oauth oauth = new Oauth(); 
	     User user = new User(); 
	     List<Interest> interests = new ArrayList<Interest>();
	    
		 oauth=oauthService.findOauthByOauthToken(oauth_token); 
		 user=oauth.getUser();
		 
		 if (user!=null) { 
			  interests = interestService.getInterestById(user.getUser_id());
			  Message<List<Interest>> message = new Message<List<Interest>>("200",Constant.GETHOBBYLIST_SUCCESS,interests);//初始化message对象
			  json = gson.toJson(message, Message.class); //使用gson将Message对象转化为字符串
			  out.print(json);  
		 }else {
			 Message<String> message = new Message<String>("-1",Constant.GETHOBBYLIST_FAILED,"");//初始化message对象
			 json = gson.toJson(message, Message.class); //使用gson将Message对象转化为字符串
			 out.print(json); 
		} 
	 }
	 
}
