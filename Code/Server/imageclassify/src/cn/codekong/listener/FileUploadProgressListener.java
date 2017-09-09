package cn.codekong.listener;

import javax.servlet.http.HttpSession; 
import org.springframework.stereotype.Component; 
import cn.codekong.bean.Progress;

@Component
public class FileUploadProgressListener {
	private HttpSession session;  
    public void setSession(HttpSession session){  
        this.session=session;  
        Progress status = new Progress();//保存上传状态  
        session.setAttribute("status", status);  
    }  
    public void update(long pBytesRead, long pContentLength, int pItems) {  
        Progress status = (Progress) session.getAttribute("status");  
        try {  
            Thread.sleep(5);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
        status.setpBytesRead(pBytesRead);  
        status.setpContentLength(pContentLength);  
        status.setpItems(pItems); 
    }  
}
