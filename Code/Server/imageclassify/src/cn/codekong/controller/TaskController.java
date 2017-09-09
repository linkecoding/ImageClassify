package cn.codekong.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.google.gson.Gson;
import cn.codekong.bean.Composition;
import cn.codekong.bean.Image;
import cn.codekong.bean.Image_Composition;
import cn.codekong.bean.Image_Mark;
import cn.codekong.bean.Interest;
import cn.codekong.bean.Mark;
import cn.codekong.bean.Oauth;
import cn.codekong.bean.Task;
import cn.codekong.bean.User;
import cn.codekong.bean.label.ConfirmedImageDetail;
import cn.codekong.bean.label.ImageTask;
import cn.codekong.bean.label.Images;
import cn.codekong.bean.label.ImgLabel;
import cn.codekong.bean.label.Label;
import cn.codekong.bean.label.Message;
import cn.codekong.bean.label.TagResult;
import cn.codekong.bean.label.TaskOfAmount;
import cn.codekong.bean.label.UnconfirmedLabel;
import cn.codekong.bean.label.UnfinishedTaskLabel;
import cn.codekong.config.Constant;
import cn.codekong.service.CompositionService;
import cn.codekong.service.ConstantService;
import cn.codekong.service.ImageService;
import cn.codekong.service.InterestService;
import cn.codekong.service.MarkService;
import cn.codekong.service.OauthService;
import cn.codekong.service.TaskService;
import cn.codekong.service.UserService;
import cn.codekong.util.DateUtils;
import cn.codekong.util.ImagesUtil;

@Controller
public class TaskController {

	@Autowired
	UserService userService;
	@Autowired
	OauthService oauthService;
	@Autowired
	MarkService markService;
	@Autowired
	TaskService taskService;
	@Autowired
	CompositionService compositionService;
	@Autowired
	InterestService interestService;
	@Autowired
	ImageService imageService;
	@Autowired
	ConstantService constantService;

	/**
	 * 分配任务
	 * 
	 * @param oauth_token
	 * @param identify_time
	 * @param identify_frequency_marks
	 * @param task_img_amount
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/task/gettask", method = RequestMethod.POST)
	public void getTask(@RequestParam("oauth_token") String oauth_token,
			@RequestParam("task_img_amount") String task_img_amount, HttpServletResponse response) throws IOException {

		User user = new User();
		Task task = new Task();
		Gson gson = new Gson();
		String json = null;
		PrintWriter out = response.getWriter();
		ImagesUtil imagesUtil = new ImagesUtil();
		// 找到此oauth_token对对应的用户user
		user = oauthService.findOauthByOauthToken(oauth_token).getUser();
		// 定义初始化两个列表
		List<Label> optionLabels = new ArrayList<Label>();
		List<Label> manualLabels = new ArrayList<Label>();
		List<Images> imageslist = new ArrayList<Images>();
		List<Image_Composition> marks = new ArrayList<Image_Composition>();
		List<Integer> imgIds = new ArrayList<Integer>();
		String identify_time = "";
		String identify_frequency_marks = "";
		// 得到constant的属性值列表
		List<cn.codekong.bean.Constant> constants = constantService.getConstantByKey();

		// 查询该用户所有兴趣
		List<Interest> interests = interestService.getInterestById(user.getUser_id());
		System.out.println(interests.size());

		if ((!constants.isEmpty()) && (constants.size() == 2)) {
			// 如果constants不为空
			identify_time = constants.get(0).getValue();
			identify_frequency_marks = constants.get(1).getValue();
			String orderOfTime = "desc";// later
			String orderOfAmount = "desc";// more
			if (identify_time.equals("early")) {
				orderOfTime = "asc";
			}
			if (identify_frequency_marks.equals("less")) {
				orderOfAmount = "asc";
			}

			if (interests.isEmpty()) { // 如果该用户没有添加兴趣列表
				// 分别设置任务对象task的属性值
				task.setTask_img_amount(Integer.parseInt(task_img_amount));
				Date task_start_time = new Date();
				task.setTask_start_time(task_start_time);
				task.setUser_id(user.getUser_id());
				// 持久化到出数据库中
				int taskId = taskService.saveTask(task);

				if (taskId != 0) {
					// 调用markService的getComposition方法，给指定用户分配指定数量的图片
					marks = markService.getCompositionOfAll(Integer.parseInt(task_img_amount), user.getUser_id(),
							orderOfTime, orderOfAmount);
					if (marks.size() == Integer.parseInt(task_img_amount)) {
						// 如果获取数据图片
						for (int i = 0; i < marks.size(); i++) {
							Image_Composition image_Composition = new Image_Composition();
							image_Composition = marks.get(i);
							// 得到图片的路径
							String imgPath = image_Composition.getImg_path() + image_Composition.getImg_name();
							// 得到图片对应的机器识别标签结果
							List<Label> machineLabels = imagesUtil
									.getLabels(image_Composition.getImg_machine_tag_label());
							Images images = imagesUtil.getImages(image_Composition.getImg_id() + "", imgPath,
									machineLabels, manualLabels, optionLabels);
							// 将每一个image对象添加到imageslist中
							imageslist.add(images);
						}
						// 调用imagesUtil的getImagesTaskJson方法得到最后输出的json字符串对象
						ImageTask imageTask = imagesUtil.getImagesTaskJson(taskId, imageslist);
						Message<ImageTask> message = new Message<ImageTask>("200", "", imageTask); // 初始化message对象
						json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
						out.print(json);
					} else {
						Message<String> message = new Message<String>("-200", "没有足够图片", ""); // 初始化message对象
						json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
						out.print(json);
					}
				} else {
					Message<String> message = new Message<String>("-1", Constant.TASK_IS_NOT_SAVED, ""); // 初始化message对象
					json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
					out.print(json);
				}
			} else {// 该用户兴趣表不为空
					// 遍历该interests列表
				List<Integer> categories = new ArrayList<Integer>();
				for (int i = 0; i < interests.size(); i++) {
					Interest interest = new Interest();
					interest = interests.get(i);
					System.out.println(interest.getCategory_id());
					categories.add(interest.getCategory_id());
					// 将每一个兴趣类别对应的image图片记录的id（最多30条记录）添加到integer类型的imgIds列表中
					if (!imageService.seleteListOfCategory(interest.getCategory_id(), user.getUser_id(), orderOfTime,
							orderOfAmount).isEmpty()) {
						imgIds.addAll(imageService.seleteListOfCategory(interest.getCategory_id(), user.getUser_id(),
								orderOfTime, orderOfAmount));
					}
				}
				// 如果imgIds多于用户请求的数量，就根据管理员的任务分配策略参数选取图片
				if (imgIds.size() >= Integer.parseInt(task_img_amount)) {
					// 分别设置任务对象task的属性值
					task.setTask_img_amount(Integer.parseInt(task_img_amount));
					Date task_start_time = new Date();
					task.setTask_start_time(task_start_time);
					task.setUser_id(user.getUser_id());

					// 持久化到出数据库中
					int taskId = taskService.saveTask(task);

					if (taskId != 0) {
						List<Image_Composition> image_Compositions = imageService.getPriorityImages(imgIds,
								Integer.parseInt(task_img_amount), orderOfTime, orderOfAmount);

						for (int i = 0; i < image_Compositions.size(); i++) {
							Image_Composition image = image_Compositions.get(i);
							System.out.println(image.getImg_id());
							// 得到该图片的路径信息
							String imgPath = image.getImg_path() + image.getImg_name();
							// 调用imagesUtil的getLabels方法将image的getImg_machine_tag_label属性转化为label类型的列表
							List<Label> machineLabels = imagesUtil.getLabels(image.getImg_machine_tag_label());
							// 调用imagesUtil的getLabels方法转化为Images对象信息
							Images images = imagesUtil.getImages(image.getImg_id() + "", imgPath, machineLabels,
									manualLabels, optionLabels);
							// 添加到imageslist列表中
							imageslist.add(images);
						}
						ImageTask imageTask = imagesUtil.getImagesTaskJson(taskId, imageslist);
						Message<ImageTask> message = new Message<ImageTask>("200", Constant.GET_TASK_SUCCESS,
								imageTask); // 初始化message对象
						json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
						out.print(json);
					} else {
						Message<String> message = new Message<String>("-1", Constant.TASK_IS_NOT_SAVED, ""); // 初始化message对象
						json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
						out.print(json);
					}
				} else { // 如果imgIds少于用户请求的数量，就选取其他兴趣中按照管理员分配策略参数进行
							// 分别设置任务对象task的属性值
					task.setTask_img_amount(Integer.parseInt(task_img_amount));
					Date task_start_time = new Date();
					task.setTask_start_time(task_start_time);
					task.setUser_id(user.getUser_id());
					// 持久化到出数据库中
					int taskId = taskService.saveTask(task);

					if (taskId != 0) {
						// 遍历所有的imgIds对应的图片
						for (int i = 0; i < imgIds.size(); i++) {
							int img_id = imgIds.get(i);
							// 得到该img_id对应的图片记录
							Image image = imageService.findImageById(img_id);
							System.out.println(img_id);
							// 得到该图片的路径信息
							String imgPath = image.getImg_path() + image.getImg_name();
							// 调用imagesUtil的getLabels方法将image的getImg_machine_tag_label属性转化为label类型的列表
							List<Label> machineLabels = imagesUtil.getLabels(image.getImg_machine_tag_label());
							// 调用imagesUtil的getLabels方法转化为Images对象信息
							Images images = imagesUtil.getImages(image.getImg_id() + "", imgPath, machineLabels,
									manualLabels, optionLabels);
							// 添加到imageslist列表中
							imageslist.add(images);
						}

						int amountNeeded = (Integer.parseInt(task_img_amount) - imgIds.size());
						// 调用markService的getComposition方法，给指定用户分配指定不足的数量的图片
						marks = imageService.getNeededAmountImages(user.getUser_id(), imgIds, amountNeeded, categories,
								orderOfTime, orderOfAmount);
						System.out.println("marks" + marks.size());
						if (marks.size() == amountNeeded) {
							for (int i = 0; i < marks.size(); i++) {
								Image_Composition image_Composition = marks.get(i);
								System.out.println(image_Composition.getImg_id());
								String imgPath = image_Composition.getImg_path() + image_Composition.getImg_name();
								List<Label> machineLabels = imagesUtil
										.getLabels(image_Composition.getImg_machine_tag_label());
								Images images = imagesUtil.getImages(image_Composition.getImg_id() + "", imgPath,
										machineLabels, manualLabels, optionLabels);
								imageslist.add(images);
							}
							// 获取ImageTask类型的json数组并返回给客户端
							ImageTask imageTask = imagesUtil.getImagesTaskJson(taskId, imageslist);
							Message<ImageTask> message = new Message<ImageTask>("200", Constant.GET_TASK_SUCCESS,
									imageTask); // 初始化message对象
							json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
							out.print(json);
						} else {
							Message<String> message = new Message<String>("-1", Constant.HAVA_NO_IMAGE, ""); // 初始化message对象
							json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
							out.print(json);
						}
					}
				}
			}
		} else {
			// 后台配置为空
		}
	}

	/**
	 * 提交任务
	 * 
	 * @param oauth_token
	 * @param task_id
	 * @param img_id
	 * @param tasks
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/task/committask", method = RequestMethod.POST)
	public void commitTask(@RequestParam("oauth_token") String oauth_token, @RequestParam("task") String tasks,
			@RequestParam("is_commit") String isCommit, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		System.out.println(oauth_token);
		System.out.println(isCommit);
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		boolean issaved = true;
		boolean issavedComp = true;
		boolean isupdate = true;
		Gson gson = new Gson();
		String json = null;

		PrintWriter out = response.getWriter();
		// 找到oauth_token对应的oauth对象
		Oauth oauth = oauthService.findOauthByOauthToken(oauth_token);
		User user = oauth.getUser();
		// gson解析tasks为TagResult对象
		TagResult tagResult = gson.fromJson(tasks, TagResult.class);
		int taskid = Integer.parseInt(tagResult.getTask_id());
		Task task = new Task();
		// 调用taskService的findTaskById找到唯一的task对象
		task = taskService.findTaskById(taskid);
		// 找到composition是否存在taskid即是否第一次提交
		List<Integer> list = compositionService.getComposition(taskid);
		List<ImgLabel> imgLabels = tagResult.getImg_label();
		// 如果第一次提交
		if (list.isEmpty()) {
			// tagResult的getImg_label方法找到json数组的所有ImgLabel对象列表

			// private String img_id;
			// private String option_label;
			// private String manual_label;
			for (int i = 0; i < imgLabels.size(); i++) {// 遍历imgLabels列表
				ImgLabel imgLabel = imgLabels.get(i);// 取第i个imgLabels对象
				// 分别获取imgLabel对象的若干属性
				int imgid = Integer.parseInt(imgLabel.getImg_id());
				String option_label = imgLabel.getOption_label().split(";")[0];
				String manual_label = imgLabel.getManual_label().split(";")[0];
				Mark mark = new Mark();
				Composition composition = new Composition();
				// 如果用户选择或者输入了标签信息
				if ((option_label != null && !("").equals(option_label))
						|| (manual_label != null && !("").equals(manual_label))) {

					// 设置mark对象的若干属性
					mark.setImg_id(imgid);
					mark.setUser_id(user.getUser_id());
					mark.setManual_mark_name(imgLabel.getManual_label());
					mark.setOption_mark_name(imgLabel.getOption_label());
					mark.setMark_accuracy("");
					Date date = new Date();
					mark.setMark_time(date);

					// 设置composition对象的若干属性
					composition.setImg_id(imgid);
					composition.setTask_id(taskid);
					composition.setIs_masked(1);
					// 分别对mark对象和composition对象进行持久化到数据库

					issaved = (issaved && markService.saveMark(mark));
					issavedComp = (issavedComp && compositionService.saveComp(composition));

				} else {
					// 如果用户没有输入也没有选择标签信息，则只需要设置composition对象并持久化到数据库
					composition.setImg_id(imgid);
					composition.setTask_id(taskid);
					composition.setIs_masked(0);
					issavedComp = (issavedComp && compositionService.saveComp(composition));
				}
			}
		} else {
			// 如果组成表有相关记录，说明之前已经提交，则需要更新mark表与composition表的标记字段
			for (int i = 0; i < imgLabels.size(); i++) {// 遍历imgLabels列表
				ImgLabel imgLabel = imgLabels.get(i);// 取第i个imgLabels对象
				// 分别获取imgLabel对象的若干属性
				int imgid = Integer.parseInt(imgLabel.getImg_id());
				String option_label = imgLabel.getOption_label().split(";")[0];
				String manual_label = imgLabel.getManual_label().split(";")[0];

				List<Mark> marks = markService.getMarkListByUserAndImage(user.getUser_id(),
						Integer.parseInt(imgLabel.getImg_id()));
				// 如果mark表为空，则增加此记录并更改composition的标识为1
				if (marks.isEmpty() || marks.size() == 0) {
					// 如果用户选择或者输入了标签信息
					if ((option_label != null && !("").equals(option_label))
							|| (!("").equals(manual_label) && manual_label != null)) {
						Mark mark = new Mark();
						// 设置mark对象的若干属性
						mark.setImg_id(imgid);
						mark.setUser_id(user.getUser_id());
						mark.setManual_mark_name(imgLabel.getManual_label());
						mark.setOption_mark_name(imgLabel.getOption_label());
						mark.setMark_accuracy("");
						Date date1 = new Date();
						mark.setMark_time(date1);
						issaved = (issaved && markService.saveMark(mark));
						Composition composition = compositionService.getComposition(taskid, imgid);
						composition.setIs_masked(1);
						compositionService.update(composition);
					}
				} else {
					// 找到对应的mark表中记录，并进行更新操作
					if ((!("").equals(option_label) && option_label != null)
							|| (!("").equals(manual_label) && manual_label != null)) {

						Mark mark2 = marks.get(0);
						mark2.setOption_mark_name(imgLabel.getOption_label());
						mark2.setManual_mark_name(imgLabel.getManual_label());
						mark2.setMark_accuracy("");
						Date date3 = new Date();
						mark2.setMark_time(date3);
						isupdate = (isupdate && markService.updateMarkAll(mark2));
					} else {
						markService.deleteMark(user.getUser_id(), imgid);
					}
				}
			}
		}
		// 如果用户所有图片的标签信息都已经持久化
		if (issaved || issavedComp || isupdate) {
			// 设置task的提交状态并持久化到数据库中
			task.setTask_iscommit(Integer.parseInt(isCommit));
			boolean isUpdate = taskService.updateTask(task);
			if (isUpdate) {
				Message<String> message = new Message<String>("200", Constant.COMMIT_SUCCESS, ""); // 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			} else {
				Message<String> message = new Message<String>("-1", Constant.COMMIT_FAIL, ""); // 初始化message对象
				json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
				out.print(json);
			}
		} else {
			Message<String> message = new Message<String>("-1", Constant.COMMIT_FAIL, ""); // 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	/**
	 * 获取用户未完成(未提交)的任务列表
	 * 
	 * @param oauth_token
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/task/getunfinishedtask", method = RequestMethod.POST)
	public void getUnfinishedTask(@RequestParam(Constant.OAUTH_TOKEN) String oauth_token,
			@RequestParam("start") String start, @RequestParam("page_num") String num, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		String json = null;
		PrintWriter out = response.getWriter();

		DateUtils dateUtil = new DateUtils();
		// 得到oauth_token对应的oauth对象
		Oauth oauth = oauthService.findOauthByOauthToken(oauth_token);
		User user = oauth.getUser();
		// 根据指定的user_id提交标识未为0的task列表
		List<Task> tasks = taskService.geTasksOfUnfinished(user.getUser_id(), Integer.parseInt(start),
				Integer.parseInt(num));

		List<UnfinishedTaskLabel> labels = new ArrayList<UnfinishedTaskLabel>();

		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			UnfinishedTaskLabel unfinishedTaskLabel = new UnfinishedTaskLabel();
			unfinishedTaskLabel.setTask_id(task.getTask_id() + "");
			// 根据task_id查找对应的所有图片信息列表
			List<Image> images = compositionService.getTaskOfImages(task.getTask_id());

			List<String> image_path_five = new ArrayList<String>();
			// 根据images列表，得到其中5张图片的url并存入到list中
			for (int j = 0; j < images.size(); j++) {
				Image image = new Image();
				image = images.get(j);
				String allname = image.getImg_path() + image.getImg_name();
				image_path_five.add(allname);
			}
			// 设置unfinishedTaskLabel对象的属性
			unfinishedTaskLabel.setTask_img_amount(task.getTask_img_amount() + "");
			unfinishedTaskLabel.setTask_start_time(dateUtil.getFormatDateOfNoneSs(task.getTask_start_time()));
			unfinishedTaskLabel.setImage_path_five(image_path_five);
			// 增添到labels列表中
			labels.add(unfinishedTaskLabel);
		}
		Message<List<UnfinishedTaskLabel>> message = new Message<List<UnfinishedTaskLabel>>("200", "", labels); // 初始化message对象
		json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
		out.print(json);

	}

	@RequestMapping(value = "/task/getunconfirmedtask", method = RequestMethod.POST)
	public void getUnconfirmedTask(@RequestParam(Constant.OAUTH_TOKEN) String oauth_token,
			@RequestParam("start") String start, @RequestParam("page_num") String num, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		String json = null;
		PrintWriter out = response.getWriter();

		DateUtils dateUtil = new DateUtils();
		// 得到oauth_token对应的oauth对象
		Oauth oauth = oauthService.findOauthByOauthToken(oauth_token);
		User user = oauth.getUser();
		// 根据指定的user_id提交标识未为1即已提交的task列表
		List<Task> tasks = taskService.geTasksOfUnconfirmed(user.getUser_id(), Integer.parseInt(start),
				Integer.parseInt(num));

		if (tasks != null) {
			List<UnconfirmedLabel> labels = new ArrayList<UnconfirmedLabel>();
			for (int i = 0; i < tasks.size(); i++) {
				Task task = tasks.get(i);
				System.out.println(task.getTask_id());
				UnconfirmedLabel unconfirmedLabel = new UnconfirmedLabel();
				unconfirmedLabel.setTask_id(task.getTask_id() + "");
				// 根据task_id查找对应的所有图片信息列表
				List<Image> images = compositionService.getTaskOfImages(task.getTask_id());
				List<String> image_path_five = new ArrayList<>();
				// 根据images列表，得到其中5张图片的url并存入到list中
				for (int j = 0; j < images.size(); j++) {
					String img_name = images.get(j).getImg_path() + images.get(j).getImg_name();
					image_path_five.add(img_name);
				}
				// 找到指定task_id对应的所有图片未被系统接收的个数
				String img_amount = compositionService.getUnconfirmedOfImages(task.getTask_id());
				// 设置unfinishedTaskLabel对象的属性
				unconfirmedLabel.setTask_img_amount(task.getTask_img_amount() + "");
				unconfirmedLabel.setTask_start_time(dateUtil.getFormatDateOfNoneSs(task.getTask_start_time()));
				unconfirmedLabel.setImage_path_five(image_path_five);
				unconfirmedLabel.setImg_amount(img_amount);
				// 增添到labels列表中
				labels.add(unconfirmedLabel);
			}
			Message<List<UnconfirmedLabel>> message = new Message<List<UnconfirmedLabel>>("200", "", labels); // 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		} else {
			Message<String> message = new Message<String>("-1", Constant.GET_TASK_CONMMIT_FAIl, ""); // 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	@RequestMapping(value = "/task/getfinishedtask", method = RequestMethod.POST)
	public void getConfirmedTask(@RequestParam(Constant.OAUTH_TOKEN) String oauth_token,
			@RequestParam("start") String start, @RequestParam("page_num") String num, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		String json = null;
		PrintWriter out = response.getWriter();
		DateUtils dateUtil = new DateUtils();
		// 得到oauth_token对应的oauth对象
		Oauth oauth = oauthService.findOauthByOauthToken(oauth_token);
		User user = oauth.getUser();
		// 根据指定的user_id提交标识未为1即已提交的task列表
		List<Task> tasks = taskService.geTasksOfUnconfirmed(user.getUser_id(), Integer.parseInt(start),
				Integer.parseInt(num));
		if (tasks != null) {
			List<UnconfirmedLabel> labels = new ArrayList<UnconfirmedLabel>();
			System.out.println(tasks.size());
			for (int i = 0; i < tasks.size(); i++) {
				Task task = tasks.get(i);
				System.out.println(task.getTask_id());

				UnconfirmedLabel unconfirmedLabel = new UnconfirmedLabel();
				unconfirmedLabel.setTask_id(task.getTask_id() + "");
				// 根据task_id查找对应的所有图片信息列表

				List<Image> images = compositionService.getTaskOfImages(task.getTask_id());

				System.out.println("取到5张图片的个数：" + images.size());
				List<String> image_path_five = new ArrayList<>();
				// 根据images列表，得到其中5张图片的url并存入到list中
				for (int j = 0; j < images.size(); j++) {
					String img_name = images.get(j).getImg_path() + images.get(j).getImg_name();
					image_path_five.add(img_name);
				}
				// 找到指定task_id对应的所有图片未被系统接收的个数
				String img_amount = compositionService.getUnconfirmedOfImages(task.getTask_id());
				if ((task.getTask_img_amount() - Integer.parseInt(img_amount)) != 0) {
					// 设置unfinishedTaskLabel对象的属性
					unconfirmedLabel.setTask_img_amount(task.getTask_img_amount() + "");
					unconfirmedLabel.setTask_start_time(dateUtil.getFormatDateOfNoneSs(task.getTask_start_time()));
					unconfirmedLabel.setImage_path_five(image_path_five);
					// unconfirmedLabel.setUnfinishedTaskLabel(unfinishedTaskLabel);
					unconfirmedLabel.setImg_amount((task.getTask_img_amount() - Integer.parseInt(img_amount)) + "");
					// 增添到labels列表中
					labels.add(unconfirmedLabel);
				}

			}
			Message<List<UnconfirmedLabel>> message = new Message<List<UnconfirmedLabel>>("200", "", labels); // 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		} else {
			Message<String> message = new Message<String>("-1", Constant.GET_TASK_CONMMIT_FAIl, ""); // 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	/**
	 * 根据task_id及oauth_token找到对应任务中未被系统接收的图片列表
	 * 
	 * @param oauth_token
	 * @param task_id
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/task/gettaskbyid", method = RequestMethod.POST)
	public void getUnconfirmedTaskDetail(@RequestParam(Constant.OAUTH_TOKEN) String oauth_token,
			@RequestParam("task_id") String task_id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		ImagesUtil imagesUtil = new ImagesUtil();
		String json = null;
		PrintWriter out = response.getWriter();

		Oauth oauth = oauthService.findOauthByOauthToken(oauth_token);
		User user = oauth.getUser();
		int user_id = user.getUser_id();

		ImageTask imageTask = new ImageTask();

		// 从数据库中查询此用户user_id对应的task_id的所有带判定图片的相关属性对象列表
		List<Image_Mark> images = taskService.getImageMarkOfTask(user_id, Integer.parseInt(task_id));
		List<Images> imageLabel = new ArrayList<Images>();
		if (images != null) {
			for (int i = 0; i < images.size(); i++) {

				List<Label> optionLabelName = new ArrayList<Label>();
				List<Label> machineLabel = new ArrayList<Label>();
				List<Label> manualLabelName = new ArrayList<Label>();

				Image_Mark image_Mark = new Image_Mark();
				image_Mark = images.get(i);
				// 获得列表中每条记录的各字段
				String img_id = image_Mark.getImg_id();
				String img_path = image_Mark.getImg_path() + image_Mark.getImg_name();
				if (image_Mark.getImg_machine_tag_label() != null
						&& !image_Mark.getImg_machine_tag_label().equals("")) {
					machineLabel = imagesUtil.getLabels(image_Mark.getImg_machine_tag_label());
					System.out.println("machineLabel:" + machineLabel);
				}
				if (image_Mark.getManual_mark_name() != null && !image_Mark.getManual_mark_name().equals("")) {
					manualLabelName = imagesUtil.getUserLabels(image_Mark.getManual_mark_name());
					System.out.println("manualLabelName:" + manualLabelName);
				}
				if (image_Mark.getOption_mark_name() != null && !image_Mark.getOption_mark_name().equals("")) {
					optionLabelName = imagesUtil.getUserLabels(image_Mark.getOption_mark_name());
					System.out.println("optionLabelName:" + optionLabelName);
				}
				// 调用函数得到images2对象
				Images images2 = imagesUtil.getImages(img_id, img_path, machineLabel, manualLabelName, optionLabelName);
				// 将获得的images2对象添加到列表中
				imageLabel.add(images2);
			}
			// 设置imageTask对象的各字段
			imageTask.setTask_id(task_id);
			imageTask.setImages(imageLabel);
			Message<ImageTask> message = new Message<ImageTask>("200", "", imageTask); // 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		} else {
			Message<ImageTask> message = new Message<ImageTask>("-1", Constant.GETCHECKINLIST_FAILED, imageTask); // 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	/**
	 * 根据task_id及oauth_token找到对应任务中已经被系统接收的图片列表
	 * 
	 * @param oauth_token
	 * @param task_id
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/task/getfinishedtaskdetail", method = RequestMethod.POST)
	public void getConfirmedTaskDetail(@RequestParam(Constant.OAUTH_TOKEN) String oauth_token,
			@RequestParam("task_id") String task_id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		System.out.println(task_id);
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		String json = null;
		PrintWriter out = response.getWriter();

		Oauth oauth = oauthService.findOauthByOauthToken(oauth_token);
		User user = oauth.getUser();
		int user_id = user.getUser_id();
		List<ConfirmedImageDetail> imageDetails = new ArrayList<ConfirmedImageDetail>();
		// 根据task_id找到对应的已经完成即已经被系统接收的Image对象列表
		List<Image> images = compositionService.getConfirmedOfImages(Integer.parseInt(task_id));
		if (images != null) {
			System.out.println(images.size());
			for (int i = 0; i < images.size(); i++) {

				ConfirmedImageDetail confirmedImageDetail = new ConfirmedImageDetail();
				Image image = images.get(i);
				int img_id = image.getImg_id();
				String final_labels = image.getImg_label_name();
				String img_path = image.getImg_path() + image.getImg_name();
				System.out.println(img_path);
				// 查询数据表中user_id，img_id对应的mark字段，并取出mark的属性值
				List<Mark> marks = markService.getMarkListByUserAndTask(user_id, img_id);
				String user_labels = marks.get(0).getOption_mark_name() + marks.get(0).getManual_mark_name();
				String img_accuracy = marks.get(0).getMark_accuracy();
				// 设置对象confirmedImageDetail的属性值
				confirmedImageDetail.setFinal_labels(final_labels);
				confirmedImageDetail.setAccuracy(img_accuracy);
				confirmedImageDetail.setImg_path(img_path);
				confirmedImageDetail.setUser_labels(user_labels);
				// 添加到imageDetails列表中
				imageDetails.add(confirmedImageDetail);
			}
			Message<List<ConfirmedImageDetail>> message = new Message<List<ConfirmedImageDetail>>("200", "",
					imageDetails); // 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		} else {
			Message<String> message = new Message<String>("-1", Constant.GET_TASK_CONMMIT_FAIl, ""); // 初始化message对象
			json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
			out.print(json);
		}
	}

	/**
	 * 志愿者获取昨日今日未完成任务量
	 * 
	 * @param oauth_token
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/task/gethometaskstatusdetail", method = RequestMethod.POST)
	public void getCountOfTask(@RequestParam(Constant.OAUTH_TOKEN) String oauth_token, HttpServletResponse response)
			throws IOException {

		response.setCharacterEncoding("utf-8");// 设置编码格式为utf-8
		PrintWriter out = response.getWriter();
		String json;
		Gson gson = new Gson();
		Oauth oauth = new Oauth();
		User user = new User();

		oauth = oauthService.findOauthByOauthToken(oauth_token);
		user = oauth.getUser();
		TaskOfAmount taskOfAmount = taskService.getAmountTaskOfYesTodUnF(user.getUser_id());
		Message<TaskOfAmount> message = null;
		if (taskOfAmount != null) {
			message = new Message<TaskOfAmount>(Constant.QUERY_SUCCESS_RESPONSE_CODE, "", taskOfAmount);
		} else {
			message = new Message<TaskOfAmount>(Constant.QUERY_FAILED_RESPONSE_CODE, "", taskOfAmount);
		}

		json = gson.toJson(message, Message.class); // 使用gson将Message对象转化为字符串
		out.print(json);
	}
}
