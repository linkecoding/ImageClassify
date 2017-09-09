package cn.codekong.bean;

import java.util.Date;

public class Task {

	private int task_id;
	private int task_img_amount;
	private Date task_start_time; 
	private int task_iscommit;
	private int user_id;
	
	public Task(){} 
	public Task(int task_id,int task_img_amount,Date task_start_time,int task_iscommit,
			int user_id){
		this.task_id=task_id;
		this.task_img_amount = task_img_amount;
		this.task_start_time = task_start_time;
		this.task_iscommit = task_iscommit;
		this.user_id = user_id;
	}
	public Task(int task_id,int task_img_amount,Date task_start_time,int user_id){
		this.task_id=task_id;
		this.task_img_amount = task_img_amount;
		this.task_start_time = task_start_time; 
		this.user_id = user_id;
	}
	public int getTask_id() {
		return task_id;
	}
	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}
	public int getTask_img_amount() {
		return task_img_amount;
	}
	public void setTask_img_amount(int task_img_amount) {
		this.task_img_amount = task_img_amount;
	}
	public Date getTask_start_time() {
		return task_start_time;
	}
	public void setTask_start_time(Date task_start_time) {
		this.task_start_time = task_start_time;
	}
	public int getTask_iscommit() {
		return task_iscommit;
	}
	public void setTask_iscommit(int task_iscommit) {
		this.task_iscommit = task_iscommit;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	} 
}
