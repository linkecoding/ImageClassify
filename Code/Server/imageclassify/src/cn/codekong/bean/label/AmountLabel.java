package cn.codekong.bean.label;

public class AmountLabel {

	private String yesterday_task_amount ;
	private String today_task_amount;
	private String unfinished_task_amount;
	
	public String getYesterday_task_amount() {
		return yesterday_task_amount;
	}
	public void setYesterday_task_amount(String yesterday_task_amount) {
		this.yesterday_task_amount = yesterday_task_amount;
	}
	public String getToday_task_amount() {
		return today_task_amount;
	}
	public void setToday_task_amount(String today_task_amount) {
		this.today_task_amount = today_task_amount;
	}
	public String getUnfinished_task_amount() {
		return unfinished_task_amount;
	}
	public void setUnfinished_task_amount(String unfinished_task_amount) {
		this.unfinished_task_amount = unfinished_task_amount;
	} 
}
