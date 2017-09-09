package cn.codekong.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.codekong.config.Constant;

public class DateUtils { 
	
	public static String DateToMD5(){
	 Date date = new Date();
	 MD5 md5 = new MD5();
	 SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT); 
     String dd = sdf.format(date); 
     String dateToMD5 = md5.generateMD5(md5.generateMD5(dd));
     return dateToMD5;
	} 
	
	public  String Date2String(){
		 Date date = new Date(); 
		 SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_TO_NAME); 
	     String dd = sdf.format(date);  
	     return dd;
		} 
	
	public String DateTwoMD5(String dd){  
	   	 MD5 md5 = new MD5();
	     String dateToMD5 = md5.generateMD5(md5.generateMD5(dd));
	     return dateToMD5;
	} 
	
	public String pwdOneMD5(String pwd){  
	   	 MD5 md5 = new MD5();
	     String dateToMD5 = md5.generateMD5(pwd);
	     return dateToMD5;
	} 
	
	public Date getOauthTime(){
	Calendar curr = Calendar.getInstance();
	//推迟一周过期
    curr.set(Calendar.DAY_OF_MONTH,curr.get(Calendar.DAY_OF_MONTH)+7);
    Date date2 = curr.getTime(); 
    return date2;
	}
	 
	Calendar curr = Calendar.getInstance();
	public Date getCheckInDate() throws ParseException{
		 Date date = new Date();  
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	     String dd = sdf.format(date);
		 java.util.Date checkin_time =  sdf.parse(dd); 
		 return checkin_time;
	}
	
	public String getAvatarName(String tel_num){ 
		 MD5 md5 = new MD5();  
	     String avatername = md5.generateMD5(tel_num);
	     return avatername;
	}
	public Date getNowDate(){
		Date date = new Date();
		return date;
	}
	public static String getFormatDateOfNoneSs(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		String dd = sdf.format(date);
		return dd;
	} 
}
