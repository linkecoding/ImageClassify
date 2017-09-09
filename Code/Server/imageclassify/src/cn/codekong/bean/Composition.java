package cn.codekong.bean;

public class Composition {

	private int is_masked;
	private int task_id;
	private int img_id;
	
	public Composition(){}
	public Composition(int img_id){
		this.img_id = img_id;
	}
	public Composition(int is_masked,int task_id,int img_id) {
		this.is_masked = is_masked;
		this.img_id = img_id;
		this.task_id = task_id;
	}
	
	public int getIs_masked(){
		return is_masked;
	}
	public void setIs_masked(int is_masked) {
		this.is_masked = is_masked;
	}
	public int getTask_id() {
		return task_id;
	}
	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}
	public int getImg_id() {
		return img_id;
	}
	public void setImg_id(int img_id) {
		this.img_id = img_id;
	} 
}
