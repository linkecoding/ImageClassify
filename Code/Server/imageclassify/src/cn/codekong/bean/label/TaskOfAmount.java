package cn.codekong.bean.label;

public class TaskOfAmount {

	int amount_of_task_yesterday;//昨日完成的任务数量
	int amount_of_task_today;//今日完成的任务数量
	int amount_of_task_unfinished;//志愿者所有未完成任务数量
	 
	public int getAmount_of_task_yesterday() {
		return amount_of_task_yesterday;
	}
	public void setAmount_of_task_yesterday(int amount_of_task_yesterday) {
		this.amount_of_task_yesterday = amount_of_task_yesterday;
	}
	public int getAmount_of_task_today() {
		return amount_of_task_today;
	}
	public void setAmount_of_task_today(int amount_of_task_today) {
		this.amount_of_task_today = amount_of_task_today;
	}
	public int getAmount_of_task_unfinished() {
		return amount_of_task_unfinished;
	}
	public void setAmount_of_task_unfinished(int amount_of_task_unfinished) {
		this.amount_of_task_unfinished = amount_of_task_unfinished;
	} 
}
