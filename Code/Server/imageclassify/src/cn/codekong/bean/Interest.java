package cn.codekong.bean;

public class Interest {
    private int user_id;
    private int category_id;
	
    public Interest(){}
    public Interest(int user_id,int category_id){
    	this.user_id=user_id;
    	this.category_id=category_id;
    }
    public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	} 
}
