package cn.codekong.service;
  
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service; 
import cn.codekong.bean.Oauth;
import cn.codekong.bean.User;
import cn.codekong.dao.OauthDao;
import cn.codekong.util.HibernateUtils;
@Service
public class OauthService implements OauthDao {

	/**
	 * 保存Oauth对象
	 */
	@Override
	public boolean saveOauth(Oauth oauth) {
		Session session = HibernateUtils.getSession(); //生成session实例
	    Transaction tx = session.beginTransaction();  //创建transaction实例
	    try {   
	    	session.save(oauth);    //使用session的sava方法将持久化对象保存到数据库中
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
	 * 删除Oauth对象方法
	 */
	@Override
	public boolean deleteOauth(Oauth oauth) {
		 Session session = HibernateUtils.getSession();//实例化session对象
		 Transaction tx = session.beginTransaction();//实例化事务对象
		 try {
			session.delete(oauth);//调用session的delete方法删除传进来的指定user
			tx.commit();       //提交事务
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();     //事务回滚
			return false;
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
	}

	@Override
	 /**
	  * 更新token对象
	  * @param oauth
	  * @return
	  */
	public boolean updateOauth(Oauth oauth) {
		 Session session = HibernateUtils.getSession();
		 Transaction tx = session.beginTransaction();
		 
		 try { 
			session.update(oauth); //调用session的update方法更新oauth对象
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
	 * 依据oauth_token找到oauth实体对象
	 */
	@Override
	public Oauth findOauthByOauthToken(String oauth_token) {
		 Oauth oauth = null;
		 Session session = HibernateUtils.getSession();
		 Transaction tx = session.beginTransaction(); //开启事务
		 try {
			 //使用criteria查询oauth对象
			  oauth = (Oauth) session.createCriteria(Oauth.class).add(Restrictions.eq("oauth_token", oauth_token)).uniqueResult();
			  tx.commit();
		} catch (Exception e) {
		  e.printStackTrace();
		  tx.rollback();
		}finally{
			HibernateUtils.closeSession();
		}
		 return oauth; //返回oauth对象
	}

	/**
	 * 依据oauth_id找到Oauth对象并返回此对象
	 */
	@Override
	public Oauth findOauthByOauthId(int oauth_id) {
		 Oauth oauth = null;
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例
		 try {
		     oauth =  (Oauth) session.get(Oauth.class, oauth_id);
		     //调用session的get()方法，找到此用户到内存中
		    tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}
		 return oauth;
	}

	@Override
	public Oauth findOauthByUser(User user) { 
		     Oauth oauth = null;
			 Session session = HibernateUtils.getSession();//生成Session实例
			 Transaction tx = session.beginTransaction();//生成事务实例
			 try {
				 oauth = (Oauth) session.createCriteria(Oauth.class).add(Restrictions.eq("user", user)).uniqueResult();
				 
			     //调用session的get()方法，找到此用户到内存中
			    tx.commit();//提交事务
			} catch (Exception e) {
				e.printStackTrace();
				tx.rollback();//事务回滚
			}finally{
				HibernateUtils.closeSession();//关闭session实例
			}
			 return oauth;
	}
	
	 
}
