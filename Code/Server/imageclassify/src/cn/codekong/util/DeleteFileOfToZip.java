package cn.codekong.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DeleteFileOfToZip {

	 public static boolean deleteFile(String delpath)  
	            throws FileNotFoundException, IOException {  
	        try {  
	            File file = new File(delpath);  
	            if (!file.isDirectory()) {    
	                file.delete();  
	            } else if (file.isDirectory()) { 
	                File[] fileList = file.listFiles();  
	                for (int i = 0; i < fileList.length; i++) {  
	                    File delfile = fileList[i];  
	                    if (!delfile.isDirectory()) {    
	                        delfile.delete();  
	                        System.out.println("删除文件成功");  
	                    } else if (delfile.isDirectory()) {  
	                        deleteFile(fileList[i].getPath());  
	                    }  
	                }  
	                
	            }  
	        } catch (FileNotFoundException e) {  
	            System.out.println("deletefile()   Exception:" + e.getMessage());  
	        }  
	        return true;  
	    }
}
