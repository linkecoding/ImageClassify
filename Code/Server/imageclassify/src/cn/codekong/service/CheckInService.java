package cn.codekong.service;

import java.util.ArrayList;
import java.util.List; 
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import cn.codekong.bean.CheckIn; 
import cn.codekong.dao.CheckInDao;
import cn.codekong.util.HibernateUtils;
@Service
public class CheckInService implements CheckInDao {

	@Override
	public boolean saveCheckIn(CheckIn checkIn) {
		  
		    Session session=HibernateUtils.getSession(); //生成session实例
		    Transaction tx=session.beginTransaction();  //创建transaction实例
		    try {  
		    	session.save(checkIn);    //使用session的sava方法将持久化对象保存到数据库中
				tx.commit();            //提交事务
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				tx.rollback();          //回滚事务
				return false;
			}finally {
			  HibernateUtils.closeSession();  //关闭Session实例
			} 
	}

	@Override
	public List<CheckIn> getCheckIns(int user_id) {
		
		 List<CheckIn> checkIns=new ArrayList<CheckIn>();
         Session session=HibernateUtils.getSession();//生成Session实例
		 Transaction tx=session.beginTransaction();//生成事务实例 
		 
		 try {
			 //select * from checkin where user_id=user_id order by checkin_time asc
			 checkIns= session.createCriteria(CheckIn.class).add(Restrictions.eq("user_id", user_id)).addOrder(Order.asc("checkin_time")).list();				 
			 tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
		 return checkIns; 
	}

}
