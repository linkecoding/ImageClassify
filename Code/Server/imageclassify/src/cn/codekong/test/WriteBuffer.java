package cn.codekong.test;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException; 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar; 
import java.util.List;  
import org.junit.Test; 
import cn.codekong.bean.Category; 
import cn.codekong.service.CategoryService; 

public class WriteBuffer {

	@Test
	public void writeTxt() throws IOException{
		List<Object> retList = new ArrayList<Object>();
	    Category category = new Category();
		CategoryService categoryService = new CategoryService();
		List<Category> categories = categoryService.getCategotyList();
		for (int i = 0; i < categories.size(); i++) {
			category = categories.get(i);
			retList.add(category.getCategory_id());
			retList.add("|");
			retList.add(category.getCategory_name());
			System.out.println(category.getCategory_id());
		}
		
	     //创建文件 
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE,-1);//昨天日期
		String  yestedayDate = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
		System.out.println(yestedayDate);
		String path = "C:\\test"+File.separator;
		String filePath=path+yestedayDate+".txt";
		File file=new File(filePath);
		if (!file.exists()) { 
			try {
				file.createNewFile();
				System.out.println("创建");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//写入信息 
		OutputStreamWriter write;
		String encoding="UTF-8"; 
		try {
		 	write = new OutputStreamWriter(new FileOutputStream(file),encoding);
		    BufferedWriter writer = new BufferedWriter(write);
		    for (Object obj:retList) {
				writer.write(obj.toString());
			} 
		    writer.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
