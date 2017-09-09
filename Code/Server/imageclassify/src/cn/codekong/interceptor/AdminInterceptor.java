package cn.codekong.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; 
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView; 
import com.google.gson.Gson; 
import cn.codekong.bean.label.Message;
import cn.codekong.config.Constant;
 
@Repository
public class AdminInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		  
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8"); 
		HttpSession session = request.getSession();
		if (!"".equals(session.getAttribute(Constant.USERNAME))&& session.getAttribute(Constant.USERNAME)!=null && !"".equals(session.getAttribute(Constant.TOKEN)) && session.getAttribute(Constant.TOKEN)!=null ) {
			return true; 
		} else {
			PrintWriter out;  
			out = response.getWriter();
			String json;
			Gson gson = new Gson();
			Message<String> message = new Message<String>("-200","管理员未登录，请重新登录","");//初始化message对象
            json = gson.toJson(message, Message.class); //使用gson将Message对象转化为字符串
			out.print(json);  
			return false;
		}
			
	}

}
