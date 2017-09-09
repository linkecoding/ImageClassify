package cn.codekong.service;
  
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import cn.codekong.bean.Image_Mark;
import cn.codekong.bean.Mark;
import cn.codekong.bean.Task;
import cn.codekong.bean.label.TaskOfAmount;
import cn.codekong.config.Constant;
import cn.codekong.dao.TaskDao;
import cn.codekong.util.HibernateUtils;

@Service
public class TaskService implements TaskDao{

	/**
	 * 持久化task到数据库中
	 */
	@Override
	public int saveTask(Task task) {
		Session session = HibernateUtils.getSession(); //生成session实例
	    Transaction tx = session.beginTransaction();  //创建transaction实例
	    int taskId = 0;
	    try {  
	    	session.save(task);    //使用session的sava方法将持久化对象保存到数据库中
			tx.commit();
			taskId = task.getTask_id();
			//提交事务 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();          //回滚事务 
		}finally {
		  HibernateUtils.closeSession();  //关闭Session实例
		}	 
	    return taskId;    
	}

	/**
	 * 更新task对象
	 */
	@Override
	public boolean updateTask(Task task) {
		Session session = HibernateUtils.getSession();
		 Transaction tx = session.beginTransaction();
		 try {
			session.update(task);
			tx.commit();
			return true;
		} catch (Exception e) {
			 e.printStackTrace();
			 tx.rollback();
			 return false;
		}finally {
			HibernateUtils.closeSession();
		} 
	}

	/**
	 * 根据参数task_id查找数据库对象
	 */
	@Override
	public Task findTaskById(int task_id) {
		 Task task = null;
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例
		 try {
		     task =  (Task) session.get(Task.class, task_id);
		     //调用session的get()方法，找到此用户到内存中
		    tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}
		 return task;
	}

	@Override
	public Task findTaskIdByTask(int task_img_amount, Date task_start_time, int user_id) {
		Task task = new Task();  
		Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例
		 try {
			 task=(Task) session.createCriteria(Task.class)
					 .add(Restrictions.eq("task_img_amount", task_img_amount))
					 .add(Restrictions.eq("task_start_time", task_start_time))
					 .add(Restrictions.eq("user_id", user_id))
					 .uniqueResult();
				   //调用session的get()方法，找到此用户到内存中
		     tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}
		 return task; 
	}

	/**
	 * 获取该用户未提交或者未完成的任务
	 */
	@Override
	public List<Task> geTasksOfUnfinished(int user_id,int start,int num) { 
		List<Task> tasks = new ArrayList<Task>();  
		Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例
		 try {
			 Query query =  session.createQuery("SELECT  new Task( task_id,task_img_amount,task_start_time,user_id) FROM Task WHERE task_iscommit = 0 AND user_id = "+user_id);
			 query.setFirstResult(start-1);
			 query.setMaxResults(num);
			 tasks = query.list();
		     tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}
		 return tasks;  
	}

	/**
	 * 获取user_id对应的已提交的Task
	 */
	@Override
	public List<Task> geTasksOfUnconfirmed(int user_id,int start,int num) {
		List<Task> tasks = new ArrayList<Task>();  
		Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例
		 try {
			 
			 Query query =  session.createQuery("SELECT  new Task( task_id,task_img_amount,task_start_time,user_id) FROM Task WHERE task_iscommit = 1 AND user_id = "+user_id);
			 query.setFirstResult(start-1);
			 query.setMaxResults(num);
			 tasks = query.list(); 
		     tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}
		 return tasks; 
	}

	@Override
	public List<Image_Mark> getImageMarkOfTask(int user_id, int task_id) {
		 List<Image_Mark> marks = new ArrayList<Image_Mark>();
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例String 
		 try {  
		    Query query = session.createSQLQuery(Constant.GET_TASK_IMAGES_OF_SQL).setInteger(0,user_id).setInteger(1,task_id)
		    		.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		    marks = query.list();
		     //调用session的get()方法，找到此用户到内存中
		    tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		} 
		 List<Image_Mark> marks2 = new ArrayList<Image_Mark>(); 
		 for (int i = 0; i < marks.size(); i++) { 
			 Image_Mark image_Mark = new Image_Mark();
			  Map map = (Map) marks.get(i); 
			  //String img_id,String img_path,String img_name,String img_machine_tag_label,String option_mark_name,String manual_mark_name
			  image_Mark.setImg_id(map.get("img_id")+"");
			  image_Mark.setImg_machine_tag_label(map.get("img_machine_tag_label")+"");
			  image_Mark.setImg_name(map.get("img_name")+"");
			  image_Mark.setImg_path(map.get("img_path")+"");
			  image_Mark.setManual_mark_name(map.get("manual_mark_name")+"");
			  image_Mark.setOption_mark_name(map.get("option_mark_name")+"");
			  marks2.add(image_Mark);
		 }
		 return marks2;
	}
 
	/**
	 *  查询此用户昨日完成、今日完成、未完成任务量
	 */
	@Override
	public TaskOfAmount getAmountTaskOfYesTodUnF(int user_id) {
		//查询昨日任务量
		String sql1 = "SELECT COUNT(*) AS amount_yes FROM task WHERE TO_DAYS(NOW()) - TO_DAYS(task_start_time) = 1 AND task_iscommit = 1 AND user_id = " + user_id;
		//查询今日任务量
		String sql2 = "SELECT COUNT(*) AS amount_today FROM task WHERE TO_DAYS(NOW()) - TO_DAYS(task_start_time) < 1 AND task_iscommit = 1 and user_id = " + user_id;
		//查询未完成任务量
		String sql3 = "SELECT COUNT(*) AS amount_uncommit FROM task WHERE task_iscommit = 0 and user_id = " + user_id; 
		TaskOfAmount taskOfAmount = new TaskOfAmount(); 
		Session session = HibernateUtils.getSession();
		Transaction tx = session.beginTransaction(); //开启事务
		try {
			//调用session的get()方法，找到此用户到内存中
			taskOfAmount.setAmount_of_task_yesterday(Integer.parseInt(session.createSQLQuery(sql1).list().get(0).toString()));
			taskOfAmount.setAmount_of_task_today(Integer.parseInt(session.createSQLQuery(sql2).list().get(0).toString()));
			taskOfAmount.setAmount_of_task_unfinished(Integer.parseInt(session.createSQLQuery(sql3).list().get(0).toString())); 
		    tx.commit();//提交事务 
		} catch (Exception e) {
		  e.printStackTrace();
		  tx.rollback();
		}finally{
			HibernateUtils.closeSession();
		} 
		return taskOfAmount;   
	}

	@Override
	public String getAmountTaskOfCommit(int user_id) {
		String sql = "SELECT COUNT(*) FROM task WHERE task_iscommit=1 AND user_id =" + user_id;
		Session session = HibernateUtils.getSession();
		Transaction tx = session.beginTransaction(); //开启事务
		int num = 0;
		try {
			num = Integer.parseInt(session.createSQLQuery(sql).list().get(0).toString());
			tx.commit();//提交事务 
		} catch (Exception e) {
		  e.printStackTrace();
		  tx.rollback();
		}finally{
			HibernateUtils.closeSession();
		} 
		return num+"";
	}
 

}
