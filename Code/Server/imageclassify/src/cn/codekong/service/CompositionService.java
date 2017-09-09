package cn.codekong.service;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import cn.codekong.bean.Composition;
import cn.codekong.bean.Image; 
import cn.codekong.bean.label.UserRank;
import cn.codekong.config.Constant;
import cn.codekong.dao.CompositionDao;
import cn.codekong.util.HibernateUtils;

@Service
public class CompositionService implements CompositionDao{

	/**
	 *  持久化composition
	 */
	@Override
	public boolean saveComp(Composition composition) {
		Session session = HibernateUtils.getSession(); //生成session实例
	    Transaction tx = session.beginTransaction();  //创建transaction实例
	    try {  
	    	session.save(composition);    //使用session的sava方法将持久化对象保存到数据库中
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

	/**
	 * 获取指定task_id对应的任务所有图片
	 */
	@Override
	public List<Image> getTaskOfImages(int task_id) {
		
		 List<Image> images = new ArrayList<Image>();
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例 
		 try { 
		    Query query = session.createSQLQuery("SELECT  image.img_id,img_name,img_path FROM composition,image WHERE composition.img_id = image.img_id AND composition.task_id = "+task_id).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
 		    query.setFirstResult(0);
 		    query.setMaxResults(5);
		    images = query.list();
		     //调用session的get()方法，找到此用户到内存中
		    tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}    
		 List<Image> images2 = new ArrayList<Image>(); 
		 
		 for (int i = 0; i < images.size(); i++) { 
			  Image image = new Image();
			  Map map = (Map) images.get(i); 
			  image.setImg_id(Integer.parseInt(map.get("img_id")+"")); 
			  image.setImg_name(map.get("img_name")+"");
			  image.setImg_path(map.get("img_path")+"");
			  images2.add(image);
		 }  
		 return images2;
	}

	/**
	 * 获取task_id对应的带判定的图片
	 */
	@Override
	public String getUnconfirmedOfImages(int task_id) {
		 Object amountOfUnconfirmed = 0.0;
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例 
		 try { 
			 amountOfUnconfirmed = session.createSQLQuery("SELECT COUNT(*) FROM composition,image WHERE composition.img_id = image.img_id AND composition.task_id = ? AND image.img_is_finish = 0").setInteger(0, task_id).uniqueResult();
		     //调用session的get()方法，找到此用户到内存中
		    tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}     
		return amountOfUnconfirmed.toString();
	}
	
	@Override
	public List<Image> getConfirmedOfImages(int task_id) {
		 List<Image> images = new ArrayList<Image>();
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例 
		 try { 
			 images = session.createSQLQuery("SELECT image.img_id,img_label_name,img_name,img_path "
			 		+ "FROM composition,image WHERE composition.img_id = image.img_id"
			 		+ " AND composition.task_id = "+task_id+" AND image.img_is_finish = 1").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		     //调用session的get()方法，找到此用户到内存中
		    tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}     
	
		 List<Image> images2 = new ArrayList<Image>(); 
		 
		 for (int i = 0; i < images.size(); i++) { 
			  Image image = new Image();
			  Map map = (Map) images.get(i);
			  image.setImg_id(Integer.parseInt(map.get("img_id")+"")); 
			  image.setImg_label_name(map.get("img_label_name")+"");
			  image.setImg_name(map.get("img_name")+"");
			  image.setImg_path(map.get("img_path")+"");
			  images2.add(image);
		 }  
		 return images2;  
	}

	@Override
	public List<UserRank> getRankListOfAll(int start, int num,String rankidentification) {
		 List<UserRank> userRanks = new ArrayList<UserRank>();
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例 
		 String sqlOfIntegral = "SELECT username,sex,integral,AVG(mark_accuracy) AS accuracy,avatar_url FROM USER LEFT JOIN image_mark ON user.user_id = image_mark.user_id  GROUP BY USER.user_id ORDER BY integral DESC";
		 String sqlOfAccuracy = "SELECT username,sex,integral,AVG(mark_accuracy) AS accuracy,avatar_url FROM USER LEFT JOIN image_mark ON user.user_id = image_mark.user_id  GROUP BY USER.user_id ORDER BY accuracy DESC";
		 String sql = "";
		 if (rankidentification.equals(Constant.INTEGRAL)) {
				sql = sqlOfIntegral;
		 }else if (rankidentification.equals(Constant.ACCURACY)) {
			 	sql = sqlOfAccuracy;
		 }
		 try { 
			 Query query = session.createSQLQuery(sql); 
			 query.setFirstResult(start-1);
			 query.setMaxResults(num);
			 userRanks = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		     //调用session的get()方法，找到此用户到内存中
		    tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}     
		 List<UserRank> userRanks2 = new ArrayList<UserRank>(); 
		 
		 for (int i = 0; i < userRanks.size(); i++) { 
			  UserRank userRank = new UserRank();
			  Map map = (Map) userRanks.get(i); 
			  userRank.setUsername(map.get("username")+""); 
			  userRank.setSex(map.get("sex")+"");
			  userRank.setIntegral(map.get("integral")+"");
			  userRank.setAvatar_url(map.get("avatar_url")+"");
			  userRank.setAccuracy(map.get("accuracy")+""); 
			  userRank.setAmount("");
			  userRanks2.add(userRank);
		 }  
		 return userRanks2;  
	}

	@Override
	public List<Integer> getComposition(int task_id) {
		 
		List<Integer> img_ids = new ArrayList<Integer>();
        Session session = HibernateUtils.getSession();//生成Session实例
		Transaction tx = session.beginTransaction();//生成事务实例  
		 try {
			 img_ids = session.createQuery("SELECT img_id FROM Composition WHERE task_id = ?").setInteger(0, task_id).list();
			 tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
		 return img_ids;
	}

	@Override
	public Composition getComposition(int task_id, int img_id) {
		 Composition composition = null;
		 Session session = HibernateUtils.getSession();
		 Transaction tx = session.beginTransaction(); //开启事务
		 try {
			 
			 composition = (Composition) session.createCriteria(Composition.class).add(Restrictions.eq("task_id", task_id))
					 .add(Restrictions.eq("img_id", img_id)).uniqueResult();
			  tx.commit();
		} catch (Exception e) {
		  e.printStackTrace();
		  tx.rollback();
		}finally{
			HibernateUtils.closeSession();
		} 
		return composition;
	}

	@Override
	public int update(Composition composition) {
		int isupdate = 0;
		Session session = HibernateUtils.getSession();
		 Transaction tx = session.beginTransaction();
		 try {
			Query query = session.createQuery("update Composition c set c.is_masked =? where c.task_id = ? and c.img_id = ?");  
		    query.setInteger(0, composition.getIs_masked());
		    query.setInteger(1, composition.getTask_id()); 
		    query.setInteger(2, composition.getImg_id()); 
			isupdate = query.executeUpdate();  
			tx.commit(); 
		} catch (Exception e) {
			 e.printStackTrace();
			 tx.rollback(); 
		}finally {
			HibernateUtils.closeSession();
		}  
		 return isupdate;
	}

	/**
	 * 获取总任务量排行
	 */
	@Override
	public List<UserRank> getRankListOfAmountTask(int start, int num) {
		// String sqlOfAmountOfTask = "";
		 List<UserRank> amountOfTasks = new ArrayList<UserRank>();
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例 
		 String sql = "SELECT username,sex,avatar_url,COUNT(*) AS amount FROM task LEFT JOIN USER ON user.user_id = task.user_id WHERE task.task_iscommit = 1 GROUP BY task.user_id ORDER BY amount DESC";
		  
		 try { 
			 Query query = session.createSQLQuery(sql); 
			 query.setFirstResult(start-1);
			 query.setMaxResults(num);
			 amountOfTasks = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		     //调用session的get()方法，找到此用户到内存中
		    tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}     
		 List<UserRank> amountOfTasks2 = new ArrayList<UserRank>(); 
		 
		 for (int i = 0; i < amountOfTasks.size(); i++) { 
			 UserRank amountOfTask = new UserRank();
			  Map map = (Map) amountOfTasks.get(i); 
			  amountOfTask.setUsername(map.get("username")+""); 
			  amountOfTask.setSex(map.get("sex")+"");
			  amountOfTask.setAvatar_url(map.get("avatar_url")+"");
			  amountOfTask.setAmount(map.get("amount")+"");
			  amountOfTask.setIntegral("");
			  amountOfTask.setAccuracy("");
			  amountOfTasks2.add(amountOfTask);
		 }  
		 return amountOfTasks2; 
	} 
	
	
}
