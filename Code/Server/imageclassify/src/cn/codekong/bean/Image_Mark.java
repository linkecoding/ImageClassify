package cn.codekong.bean;

public class Image_Mark {
	private String img_machine_tag_label;
	private String img_path;
	private String img_name;
	private String user_id;
	private String img_id;
	private String option_mark_name;
	private String manual_mark_name;
	
	public Image_Mark(){}
	public Image_Mark(String img_id,String img_path,String img_name,String img_machine_tag_label,String option_mark_name,String manual_mark_name){
		this.img_id = img_id;
		this.img_machine_tag_label = img_machine_tag_label;
		this.img_name = img_name;
		this.img_path = img_path;
		this.manual_mark_name = manual_mark_name;
		this.option_mark_name = option_mark_name;
	}
	public String getImg_machine_tag_label() {
		return img_machine_tag_label;
	}
	public void setImg_machine_tag_label(String img_machine_tag_label) {
		this.img_machine_tag_label = img_machine_tag_label;
	}
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	public String getImg_name() {
		return img_name;
	}
	public void setImg_name(String img_name) {
		this.img_name = img_name;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getImg_id() {
		return img_id;
	}
	public void setImg_id(String img_id) {
		this.img_id = img_id;
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
	
}
