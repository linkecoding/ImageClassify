package cn.codekong.interceptor;

import java.io.PrintWriter;
import java.util.Date; 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository; 
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView; 
import com.google.gson.Gson;

import cn.codekong.bean.Oauth;
import cn.codekong.bean.User;
import cn.codekong.bean.label.Message;
import cn.codekong.config.Constant;
import cn.codekong.service.OauthService; 

@Repository
public class UserInterceptor implements HandlerInterceptor {

	@Autowired
	OauthService oauthService;
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	} 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
	
		request.setCharacterEncoding("utf-8"); 
		String oauth_token = request.getParameter("oauth_token");
		System.out.println(oauth_token);
		Oauth oauth = oauthService.findOauthByOauthToken(oauth_token);
		
		PrintWriter out;  
		out = response.getWriter();
		String json;
		Gson gson = new Gson();
		   
		if (oauth!=null) {
			Date tokenTime = oauth.getOauth_token_expiration();
			Date today = new Date();
			if (tokenTime.compareTo(today)!=1) { 
					Message<String> message = new Message<String>("-1",Constant.LOGIN_EXPIRATION,"");//初始化message对象
					json = gson.toJson(message, Message.class); //使用gson将Message对象转化为字符串
					out.print(json);
					return false;
			}else {
				User user = oauth.getUser();
				if (user.getIs_frozen()==1) {
					Message<String> message = new Message<String>("400",Constant.USER_LOGIN_IS_FROZEN,"");//初始化message对象
					json = gson.toJson(message, Message.class); //使用gson将Message对象转化为字符串
					out.print(json);
					return false;
				}else {
					//登录成功
					return true; 
				} 
			} 
		 }else {
			//   response.sendRedirect(request.getContextPath()+"/user/login");  
			   Message<String> message = new Message<String>("-1",Constant.AUTHENTICATION_FAIL,"");//初始化message对象
               json = gson.toJson(message, Message.class); //使用gson将Message对象转化为字符串
			   out.print(json);
			   return false;
		 } 
	}
	
}
