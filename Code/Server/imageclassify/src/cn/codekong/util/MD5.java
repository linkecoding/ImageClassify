package cn.codekong.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	  /*
	   * 生成md5 有传入参数字符串
	   */
	  public String generateMD5(String input){ 
	    	//4.将byte[] 转换为找度为32位的16进制字符串
	        //声明StringBuffer对象来存放最后的值 
		  StringBuffer sb=new StringBuffer();
	    try {
	      //1.初始化MessageDigest信息摘要对象,并指定为MD5不分大小写都可以
	      MessageDigest md=MessageDigest.getInstance("md5");
	       //2.传入需要计算的字符串更新摘要信息，传入的为字节数组byte[],
	      //将字符串转换为字节数组使用getBytes()方法完成
	      //指定时其字符编码 为utf-8
	      md.update(input.getBytes("utf-8"));
	       //3.计算信息摘要digest()方法
	      //返回值为字节数组
	      byte [] hashCode=md.digest();
	       
	      //遍历字节数组
	      for(byte b:hashCode){
	        //对数组内容转化为16进制，
	        sb.append(Character.forDigit(b>>4&0xf, 16));
	        //换2次为32位的16进制
	        sb.append(Character.forDigit(b&0xf, 16));
	      }
	     
	    } catch (NoSuchAlgorithmException e) { 
	      e.printStackTrace();
	    } catch (UnsupportedEncodingException e) { 
	      e.printStackTrace();
	    }
	    return sb.toString();
	  }  
}
