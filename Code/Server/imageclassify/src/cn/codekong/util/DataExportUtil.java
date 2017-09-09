package cn.codekong.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
 

public class DataExportUtil {
	//JSON字符串格式化
	public static String jsonFormatter(Object object){
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.excludeFieldsWithoutExposeAnnotation()
				.create();
		String prettyJsonString = gson.toJson(object);
		return prettyJsonString;
	} 
	//下载格式化后的json字符串
	public static void downloadJsonResult(Object object, HttpServletResponse response) throws IOException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String filename = simpleDateFormat.format(new Date()) + "-result.txt";
	    response.setContentType("text/html;charset=UTF-8");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" 
	        + filename + "\"");
	    PrintWriter out = response.getWriter();
	    //格式化后的json数据
	    String jsonStr = jsonFormatter(object);
	    //输出下载文件
	    out.write(jsonStr);
	    out.flush();
	    out.close();
	} 
		 
}
