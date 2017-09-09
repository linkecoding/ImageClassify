package cn.codekong.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class DateTest {
	@Test
	public void getCountOfTaskZuoTian(){ 
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		Date date = new Date();
//		Date yesterday = new Date(date.getTime()-24*60*60*1000);
//		String yesterdayTime = sdf.format(yesterday);
//		String todayTime = sdf.format(date);
//		System.out.println(yesterdayTime);
//		System.out.println(todayTime);
		  int i= compare_date("2000-11-12 15:21:00", "1999-12-11 09:59:00");
	       System.out.println("i=="+i);
	}

	public int compare_date(String date1,String date2) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date date11 = sdf.parse(date1);
			Date date22 = sdf.parse(date2);
			if (date11.before(date22)) {
				return 1;  //date11在date22之前
			}else {
				return 0; //date11在date22之后
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1; 
	}
}
