package cn.codekong.util;

import java.io.File; 
import javax.servlet.http.HttpServletRequest; 
import org.springframework.web.multipart.MultipartFile; 

import cn.codekong.config.Constant;

public class FileUploadUtil {
	public static Boolean uploadFile(HttpServletRequest request, MultipartFile file,String format) {  
        System.out.println("开始");  
        String path =request.getSession().getServletContext().getRealPath("/")+Constant.ZIP_FILE_FOLDER + File.separator;  
        String fileName = file.getOriginalFilename();  
        System.out.println(path); 
        if (format.equals(Constant.FILE_FORMATE_ZIP)) {
        	File targetFile = new File(path, fileName);  
            if (!targetFile.exists()) {  
                targetFile.mkdirs();  
            }   
            // 保存  
            try {  
                file.transferTo(targetFile);  
                return true;  
            } catch (Exception e) {  
                e.printStackTrace();  
                return false;  
            }  
     
		}else {
			return false;
		}
      
   }  
}
