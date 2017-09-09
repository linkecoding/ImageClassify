package cn.codekong.bean;

import java.util.Date;

public class Zip {

	private int zip_id;
	private String zip_name;
	private String zip_url;
	private Date upload_time;
	private String old_zip_name;
	private int isZip;
	private int isClassify;
	private Date unzip_time;
	
	public Zip(){}
	public Zip(int zip_id,String zip_name,String zip_url,Date upload_time,String old_zip_name,int isZip,int isClassify,Date unzip_time){
		this.zip_id = zip_id;
		this.zip_name = zip_name;
		this.zip_url = zip_url;
		this.upload_time = upload_time;
		this.old_zip_name = old_zip_name;
		this.isZip = isZip;
		this.isClassify = isClassify;
		this.unzip_time = unzip_time;
	}  
	public int getZip_id() {
		return zip_id;
	}
	public void setZip_id(int zip_id) {
		this.zip_id = zip_id;
	}
	public String getZip_name() {
		return zip_name;
	}
	public void setZip_name(String zip_name) {
		this.zip_name = zip_name;
	}
	public String getZip_url() {
		return zip_url;
	}
	public void setZip_url(String zip_url) {
		this.zip_url = zip_url;
	}
	public Date getUpload_time() {
		return upload_time;
	}
	public void setUpload_time(Date upload_time) {
		this.upload_time = upload_time;
	}
	public String getOld_zip_name() {
		return old_zip_name;
	}
	public void setOld_zip_name(String old_zip_name) {
		this.old_zip_name = old_zip_name;
	}
	public int getIsZip() {
		return isZip;
	}
	public void setIsZip(int isZip) {
		this.isZip = isZip;
	}
	public int getIsClassify() {
		return isClassify;
	}
	public void setIsClassify(int isClassify) {
		this.isClassify = isClassify;
	}
	public Date getUnzip_time() {
		return unzip_time;
	}
	public void setUnzip_time(Date unzip_time) {
		this.unzip_time = unzip_time;
	}    
}
