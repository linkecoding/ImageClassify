package cn.codekong.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List; 
import javax.servlet.http.HttpServletResponse; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam; 
import com.google.gson.Gson; 
import cn.codekong.bean.Oauth;
import cn.codekong.bean.label.Message;
import cn.codekong.bean.label.UserRank;
import cn.codekong.config.Constant;
import cn.codekong.service.CompositionService;
import cn.codekong.service.OauthService;

@Controller
public class GetRankListController {

	@Autowired
	OauthService oauthService;
	@Autowired
	CompositionService compositionService;
	
	 @RequestMapping(value="/rank/getranking",method=RequestMethod.POST)
	 public void getTask(
			 @RequestParam(Constant.OAUTH_TOKEN) String oauth_token,
			 @RequestParam(Constant.RANKIDENTIFY) String rankidentification,
			 @RequestParam("start") String start ,
			 @RequestParam("page_num") String num,
			 HttpServletResponse response) throws IOException{
		 
		 Gson gson = new Gson();
		 String json = null;
		 PrintWriter out = response.getWriter();
		 Oauth oauth = oauthService.findOauthByOauthToken(oauth_token);
		 oauth.getUser();
		 List<UserRank> userRanks = new ArrayList<UserRank>(); 
		 System.out.println(rankidentification.equals(Constant.ACCURACY));
		 if (rankidentification.equals(Constant.INTEGRAL)|| rankidentification.equals(Constant.ACCURACY)) {
			 
			 userRanks = compositionService.getRankListOfAll(Integer.parseInt(start), Integer.parseInt(num),rankidentification);
			 Message<List<UserRank>> message = new Message<List<UserRank>>("200","",userRanks); //初始化message对象
			 json = gson.toJson(message, Message.class); //使用gson将Message对象转化为字符串
			 out.print(json);
			
		 }else {
			 userRanks = compositionService.getRankListOfAmountTask(Integer.parseInt(start), Integer.parseInt(num));
			 Message<List<UserRank>> message = new Message<List<UserRank>>("200","",userRanks); //初始化message对象
			 json = gson.toJson(message, Message.class); //使用gson将Message对象转化为字符串
			 out.print(json);
			
		 } 
	 }
}
