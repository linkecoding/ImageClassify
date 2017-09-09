package cn.codekong.bean;

import java.util.Date; 

public class Image {
	
      private int img_id;
      private String img_name;
      private String img_path;
      private Date img_finish_time;
      private String img_label_name;
      private String img_is_finish;
      private String img_machine_tag_label; 
      private int is_clustered;
      private Zip zip; 
      private int is_export;
      
      public Image(int img_id,String img_label_name){
    	  this.img_id = img_id;
    	  this.img_label_name = img_label_name;
      } 
      public Image(int img_id,String img_name,String img_path,Date img_finish_time,
    		  String img_label_name,String img_is_finish,String img_machine_tag_label,int is_clustered){
    	  
    	  this.img_id = img_id;
    	  this.img_name = img_name;
    	  this.img_path = img_path;
    	  this.img_finish_time = img_finish_time;
    	  this.img_label_name = img_label_name;
    	  this.img_is_finish = img_is_finish;
    	  this.img_machine_tag_label = img_machine_tag_label;
    	  this.is_clustered = is_clustered;
      } 
      public Image(int img_id,String img_label_name,String img_name,String img_path){
    	  this.img_id = img_id;
    	  this.img_label_name = img_label_name;
    	  this.img_name = img_name;
    	  this.img_path = img_path;
      }
      //img_id,img_name,img_finish_time,img_label_name
      public Image(int img_id,String img_name,Date img_finish_time,String img_label_name){
    	  this.img_id = img_id;
    	  this.img_label_name = img_label_name;
    	  this.img_name = img_name;
    	  this.img_finish_time = img_finish_time;
      }
      public Image(){} 
      
      
	public int getImg_id() {
		return img_id;
	}
	public void setImg_id(int img_id) {
		this.img_id = img_id;
	}
	public String getImg_name() {
		return img_name;
	}
	public void setImg_name(String img_name) {
		this.img_name = img_name;
	}
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	public Date getImg_finish_time() {
		return img_finish_time;
	}
	public void setImg_finish_time(Date img_finish_time) {
		this.img_finish_time = img_finish_time;
	}
	public String getImg_label_name() {
		return img_label_name;
	}
	public void setImg_label_name(String img_label_name) {
		this.img_label_name = img_label_name;
	}
	public String getImg_is_finish() {
		return img_is_finish;
	}
	public void setImg_is_finish(String img_is_finish) {
		this.img_is_finish = img_is_finish;
	}
	public Zip getZip() {
		return zip;
	} 
	public void setZip(Zip zip) {
		this.zip = zip;
	} 
	public String getImg_machine_tag_label() {
		return img_machine_tag_label;
	} 
	public void setImg_machine_tag_label(String img_machine_tag_label) {
		this.img_machine_tag_label = img_machine_tag_label;
	}
	public int getIs_clustered() {
		return is_clustered;
	}
	public void setIs_clustered(int is_clustered) {
		this.is_clustered = is_clustered;
	}
	public int getIs_export() {
		return is_export;
	}
	public void setIs_export(int is_export) {
		this.is_export = is_export;
	}  
	
	  
}
