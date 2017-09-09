package cn.codekong.util;
 
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.stream.FileImageInputStream;

public class ImgToByte { 
 
	    /**
	     * 将图片转换为byte数组
	     * @param path 图片路径
	     * @return
	     */
	    public static byte[] image2byte(String path){
	        //定义byte数组
	        byte[] data = null;
	        //输入流
	        FileImageInputStream input = null;
	        try {
	          input = new FileImageInputStream(new File(path));
	          ByteArrayOutputStream output = new ByteArrayOutputStream();
	          byte[] buf = new byte[1024];
	          int numBytesRead = 0;
	          while ((numBytesRead = input.read(buf)) != -1) {
	          output.write(buf, 0, numBytesRead);
	          }
	          data = output.toByteArray();
	          output.close();
	          input.close();
	        }
	        catch (FileNotFoundException ex1) {
	          ex1.printStackTrace();
	        }
	        catch (IOException ex1) {
	          ex1.printStackTrace();
	        }
	        return data;
	     }
}
