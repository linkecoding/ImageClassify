package cn.codekong.bean;

import java.util.Date;

public class CheckIn {
	
	private int checkin_id;
    private Date checkin_time; 
    private int user_id;
    
	public int getCheckin_id() {
		return checkin_id;
	}
	public void setCheckin_id(int checkin_id) {
		this.checkin_id = checkin_id;
	}
	public Date getCheckin_time() {
		return checkin_time;
	} 
	public void setCheckin_time(Date checkin_time) {
		this.checkin_time = checkin_time;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	} 
}
