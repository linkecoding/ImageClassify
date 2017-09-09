package cn.codekong.bean.label;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

public class ExportLabel {
	
	private int img_id;
	@Expose
	private String picture_name;
	@Expose
	private String finish_time;
	@Expose
	private List<String> labels;
	
	public int getImg_id() {
		return img_id;
	}
	public void setImg_id(int img_id) {
		this.img_id = img_id;
	} 
	public String getPicture_name() {
		return picture_name;
	}
	public void setPicture_name(String picture_name) {
		this.picture_name = picture_name;
	}
	public String getFinish_time() {
		return finish_time;
	}
	public void setFinish_time(Date finish_time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		this.finish_time = simpleDateFormat.format(finish_time);
	}
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	
	
	
}
