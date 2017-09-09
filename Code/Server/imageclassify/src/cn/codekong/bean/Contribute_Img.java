package cn.codekong.bean;

import java.util.Date;

public class Contribute_Img {

	private int id;
	private Date upload_img_time;
	private String storage_path;
	private String upload_img_review_status;
	private int user_id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getUpload_img_time() {
		return upload_img_time;
	}
	public void setUpload_img_time(Date upload_img_time) {
		this.upload_img_time = upload_img_time;
	}
	public String getStorage_path() {
		return storage_path;
	}
	public void setStorage_path(String storage_path) {
		this.storage_path = storage_path;
	}
	public String getUpload_img_review_status() {
		return upload_img_review_status;
	}
	public void setUpload_img_review_status(String upload_img_review_status) {
		this.upload_img_review_status = upload_img_review_status;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	
}
