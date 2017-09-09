package cn.codekong.service;
 
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import cn.codekong.bean.Category;
import cn.codekong.bean.Interest; 
import cn.codekong.dao.InterestDao;
import cn.codekong.util.HibernateUtils;
import cn.codekong.util.ListToString;

@Service
public class InterestService implements InterestDao{
 
 
	/**
	 * 插入兴趣表数据
	 */
	@Override
	public boolean saveInterest(Interest interest) {
		 Session session = HibernateUtils.getSession(); //生成session实例
		    Transaction tx = session.beginTransaction();  //创建transaction实例
		     
		    try {   
		        session.save(interest); 
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
	public int deleteInterests(int user_id) {  
				Session session = HibernateUtils.getSession(); //生成session实例
			    Transaction tx = session.beginTransaction();  //创建transaction实例 
			    int temp = 0;
			    try {  
			    	String hql = "delete from Interest where user_id  =  ?"; 
			    	Query query = session.createQuery(hql);
			    	query.setInteger(0, user_id);  
			    	temp = query.executeUpdate(); 
			    	tx.commit();            //提交事务  
				} catch (Exception e) {
					e.printStackTrace();
					tx.rollback();   
				}finally {
				  HibernateUtils.closeSession();  //关闭Session实例
				} 
			    return temp;
	}


	@Override
	public int deleteInterest(int user_id, int category_id) {
		Session session = HibernateUtils.getSession(); //生成session实例
	    Transaction tx = session.beginTransaction();  //创建transaction实例 
	    int temp = 0;
	    try {  
	    	String hql = "delete from Interest where user_id  = ? and category_id = ?"; 
	    	Query query = session.createQuery(hql);
	    	query.setInteger(0, user_id);  
	    	query.setInteger(1, category_id);
	    	temp = query.executeUpdate(); 
	    	tx.commit();            //提交事务  
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();   
		}finally {
		  HibernateUtils.closeSession();  //关闭Session实例
		} 
	    return temp;
	} 
	
	@Override
	public List<Interest> getInterestById(int user_id) {
		 List<Interest> interests = new ArrayList<Interest>();
		 Session session  =  HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例
		 try {
			  String hql =  "select new Interest(user_id,category_id) from Interest where user_id=?";
			  Query query = session.createQuery(hql);
			  query.setInteger(0, user_id);
			  interests = query.list();
			  tx.commit();
		     //调用session的get()方法，找到此用户到内存中 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}
		 return interests; 
	}


	@Override
	public List<Category> getInterestCategory(List<Integer> interestIds) {
		
		 List<Category> categories = new ArrayList<Category>();
		 Session session  =  HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例
		 try {
			 ListToString listToString = new ListToString();
			 String idString = listToString.getListToString(interestIds);
			  String hql =  "select new Category(category_id,category_name) from Category";
			  String hql2 = " where category_id in " +idString;
			   if (!idString.equals("")) {
				   Query query = session.createQuery(hql+hql2);
				   categories = query.list();
				   tx.commit();
			   }
		     //调用session的get()方法，找到此用户到内存中 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}
		 return categories;  
	}  
 
}
