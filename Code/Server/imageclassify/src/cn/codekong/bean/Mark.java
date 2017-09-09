package cn.codekong.bean;

import java.util.Date;

public class Mark {
	
	private Date mark_time;
	private String option_mark_name;
	private String manual_mark_name;
	private int user_id;
	private int img_id;
	private String mark_accuracy;
	
	public Mark(){}
	public Mark(String option_mark_name,String manual_mark_name,int img_id,int user_id){
		this.option_mark_name = option_mark_name;
		this.manual_mark_name = manual_mark_name;
		this.img_id = img_id;
		this.user_id = user_id;
	}
	public Mark(String option_mark_name,String manual_mark_name,String mark_accuracy){
		this.option_mark_name = option_mark_name;
		this.manual_mark_name = manual_mark_name;
		this.mark_accuracy = mark_accuracy;
	}
	public Mark(String option_mark_name,String manual_mark_name,int img_id,Date mark_time,int user_id,String mark_accuracy){
		this.mark_time = mark_time;
		this.option_mark_name = option_mark_name;
		this.manual_mark_name = manual_mark_name;
		this.user_id = user_id;
		this.img_id = img_id;
		this.mark_accuracy = mark_accuracy;
	}
	public Mark(int img_id){
		this.img_id = img_id;
	}
	
	public Date getMark_time() {
		return mark_time;
	}
	public void setMark_time(Date mark_time) {
		this.mark_time = mark_time;
	}
	public String getOption_mark_name() {
		return option_mark_name;
	}
	public void setOption_mark_name(String option_mark_name) {
		this.option_mark_name = option_mark_name;
	}
	public String getManual_mark_name() {
		return manual_mark_name;
	}
	public void setManual_mark_name(String manual_mark_name) {
		this.manual_mark_name = manual_mark_name;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getImg_id() {
		return img_id;
	}
	public void setImg_id(int img_id) {
		this.img_id = img_id;
	}
	public String getMark_accuracy() {
		return mark_accuracy;
	}
	public void setMark_accuracy(String mark_accuracy) {
		this.mark_accuracy = mark_accuracy;
	} 
	
}
