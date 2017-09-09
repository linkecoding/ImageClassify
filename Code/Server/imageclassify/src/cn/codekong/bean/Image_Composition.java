package cn.codekong.bean;

public class Image_Composition {
	private int img_id;
	private String img_machine_tag_label;
	private String img_path;
	private String img_name;
	private int user_id;
	
	public Image_Composition(){}
	public Image_Composition(int img_id,String img_machine_tag_label,String img_path,
			String img_name,int user_id){
		this.img_id = img_id;
		this.img_machine_tag_label = img_machine_tag_label;
		this.img_path = img_path;
		this.img_name = img_name;
		this.user_id = user_id;
	}
	public Image_Composition(int img_id){
		this.img_id = img_id;
	}
	public int getImg_id() {
		return img_id;
	}
	public void setImg_id(int img_id) {
		this.img_id = img_id;
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
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	} 
}
