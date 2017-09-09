package cn.codekong.listener;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import java.util.List; 
import org.apache.commons.fileupload.servlet.ServletFileUpload;  
import org.apache.commons.fileupload.FileItem;   
import org.apache.commons.fileupload.FileUploadBase;  
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.web.multipart.MaxUploadSizeExceededException;    
public class CustomMultipartResolver extends CommonsMultipartResolver {
	@Autowired  
    private FileUploadProgressListener progressListener;  
  
    public void setFileUploadProgressListener(  
            FileUploadProgressListener progressListener) {  
        this.progressListener = progressListener;  
    }   
    
    @Override  
    @SuppressWarnings("unchecked")  
    public MultipartParsingResult parseRequest(HttpServletRequest request)  
            throws MultipartException {  
        String encoding = determineEncoding(request);  
        FileUpload fileUpload = prepareFileUpload(encoding);  
        progressListener.setSession(request.getSession()); 
        fileUpload.setProgressListener((ProgressListener) progressListener);  
        try {  
            List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);  
            return parseFileItems(fileItems, encoding);  
        }  
        catch (FileUploadBase.SizeLimitExceededException ex) {  
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);  
        }  
        catch (FileUploadException ex) {  
            throw new MultipartException("Could not parse multipart servlet request", ex);  
        }  
    }
}
