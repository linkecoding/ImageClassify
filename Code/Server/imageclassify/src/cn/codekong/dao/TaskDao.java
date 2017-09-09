package cn.codekong.dao;
 

import java.util.Date;
import java.util.List; 
import cn.codekong.bean.Image_Mark;
import cn.codekong.bean.Task;
import cn.codekong.bean.label.TaskOfAmount;

public interface TaskDao {

	int saveTask(Task task);
	boolean updateTask(Task task);
	Task findTaskById(int task_id);
	Task findTaskIdByTask(int task_img_amount,Date task_start_time,int user_id);
	List<Task> geTasksOfUnfinished(int user_id,int start,int num);
	List<Task> geTasksOfUnconfirmed(int user_id,int start,int num);
	List<Image_Mark> getImageMarkOfTask(int user_id,int task_id);
	TaskOfAmount getAmountTaskOfYesTodUnF(int user_id);
	String getAmountTaskOfCommit(int user_id);
}
