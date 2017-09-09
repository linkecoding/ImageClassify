package cn.codekong.bean.label;

import java.util.List;

public class SplitObject<T> {

	private List<T> list ;
	private int pages_num;
	 
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getPages_num() {
		return pages_num;
	}
	public void setPages_num(int pages_num) {
		this.pages_num = pages_num;
	} 
}
