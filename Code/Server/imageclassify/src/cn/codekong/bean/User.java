package cn.codekong.bean;
 
public class User {
     private int user_id;
     private String username;
     private String password;
     private String sex;
     private String tel_num;
     private int integral;
     private String avatar_url;
     private String accuracy;  
     private int is_frozen;
     
    public User(){}
    public User(int user_id,String username,String password,String sex,String tel_num,int integral,
    		String avatar_url,String accurary,int is_frozen){
    	this.user_id = user_id;
    	this.username = username;
    	this.password = password;
    	this.tel_num = tel_num;
    	this.sex = sex;
    	this.avatar_url = avatar_url;
    	this.integral = integral;
    	this.accuracy = accurary;  
    }

    public User(int user_id,String username,String tel_num,int integral,String accurary,int is_frozen){
    	this.user_id = user_id;
    	this.username = username;
    	this.tel_num = tel_num;
    	this.integral = integral;
    	this.accuracy = accurary;
    	this.is_frozen = is_frozen;
    }
    public User(int user_id,String username,String tel_num,int integral,String accurary){
    	this.user_id = user_id;
    	this.username = username;
    	this.tel_num = tel_num;
    	this.integral = integral;
    	this.accuracy = accurary;
    }
    
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTel_num() {
		return tel_num;
	}
	public void setTel_num(String tel_num) {
		this.tel_num = tel_num;
	} 
	public int getIntegral() {
		return integral;
	}
	public void setIntegral(int integral) {
		this.integral = integral;
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	public String getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}
	public int getIs_frozen() {
		return is_frozen;
	}
	public void setIs_frozen(int is_frozen) {
		this.is_frozen = is_frozen;
	}  
	 
}
