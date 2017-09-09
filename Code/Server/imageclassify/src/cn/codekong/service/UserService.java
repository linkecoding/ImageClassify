package cn.codekong.service;

import java.util.ArrayList;
import java.util.List; 
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions; 
import org.springframework.stereotype.Service; 
import cn.codekong.bean.User;
import cn.codekong.dao.UserDao;
import cn.codekong.util.HibernateUtils;

@Service
public  class UserService implements UserDao {

	/**
	 * 用于验证登录，从数据库中查找是否存在此用户电话号码和密码
	 * 并返回此用户
	 * @param phonenum
	 * @param password
	 * @return
	 */
	@Override
	public User findUserByPawTel(String password, String tel_num) {
		 User user = null;
		 Session session = HibernateUtils.getSession();
		 Transaction tx = session.beginTransaction(); //开启事务
		 try {
			 
			 user = (User) session.createCriteria(User.class).add(Restrictions.eq("password", password))
					 .add(Restrictions.eq("tel_num", tel_num)).uniqueResult();
			  tx.commit();
		} catch (Exception e) {
		  e.printStackTrace();
		  tx.rollback();
		}finally{
			HibernateUtils.closeSession();
		}
		 return user;
	} 
	/**
	 * @param user
	 * 保存此用户
	 * 用于用户注册，并持久化到数据库中
	 */
	@Override
	public boolean save(User user) {
		    Session session = HibernateUtils.getSession(); //生成session实例
		    Transaction tx = session.beginTransaction();  //创建transaction实例
		    try {  
		    	session.save(user);    //使用session的sava方法将持久化对象保存到数据库中
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
	 * @param id
	 * 根据用户的标识id找到该用户并返回
	 */
	@Override
	public User findUserById(int user_id) {
		 User user = null;
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例
		 try {
		     user =  (User) session.get(User.class, user_id);
		     //调用session的get()方法，找到此用户到内存中
		    tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}
		 return user;
	}

	/**
	 * 删除指定用户
	 */
	@Override
	public boolean delete(User user) {
		 Session session = HibernateUtils.getSession();//实例化session对象
		 Transaction tx = session.beginTransaction();//实例化事务对象
		 try {
			session.delete(user);//调用session的delete方法删除传进来的指定user
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

	/**
	 * 更新用户信息
	 */
	@Override
	public boolean update(User user) {
		 Session session = HibernateUtils.getSession();
		 Transaction tx = session.beginTransaction();
		 try {
			session.update(user);
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
	 * 所有用户列表
	 */
	@Override
	public List<User> getAllUser() {
         List<User> users = new ArrayList<User>();
         Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例 
		 
		 try {
			 users = session.createQuery("select new User(user_id,username,password,sex,tel_num,integral,avatar_url,accuracy) from User order by user_id").list();
			 tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
		 return users; 
	}
	@Override
	public User findUserByTelnum(String tel_num) {

		 User user = null;
		 Session session  =  HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例
		 try {
			 user=(User) session.createCriteria(User.class).add(Restrictions.eq("tel_num", tel_num)).uniqueResult();
			  tx.commit();
		     //调用session的get()方法，找到此用户到内存中 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}
		 return user; 
	}
	/**
	 * 根据用户id寻找头像的路径
	 */ 
	@Override
	public String findAvatarUrl(int user_id) {
		 User user = null; 
		 Session session  =  HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例
		 try {
			 user=(User) session.createCriteria(User.class).add(Restrictions.eq("user_id", user_id)).uniqueResult();
			 tx.commit();
		     //调用session的get()方法，找到此用户到内存中 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}
		 return user.getAvatar_url();
	}
	/**
	 * 获取用户的准确率
	 */
	@Override
	public String getAvgAccuracy(int user_id) {
		List<Double> accuracy = new ArrayList<Double>();
        Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例 
		 
		 try {
			 accuracy = session.createQuery("SELECT AVG(mark_accuracy) FROM Mark WHERE user_id = ?")
					 .setInteger(0, user_id).list();
			 tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
		  return accuracy.get(0)+"";
	}
	
	@Override
	public boolean updateAccuracy(User user) {
		Session session = HibernateUtils.getSession();
		 Transaction tx = session.beginTransaction();
		 try {
			Query query = session.createQuery("update User u set u.accuracy =? where u.user_id = ?");  
		    query.setString(0, user.getAccuracy());
		    query.setInteger(1, user.getUser_id()); 
			query.executeUpdate();  
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
	@Override
	public int updateUserFrozen(int user_id,int is_frozen) {
		Session session = HibernateUtils.getSession();
		 Transaction tx = session.beginTransaction();
		 int update = 0;
		 try {
			Query query = session.createQuery("update User u set u.is_frozen =? where u.user_id = ?");  
		    query.setInteger(0, is_frozen);
		    query.setInteger(1,user_id); 
		    update = query.executeUpdate();  
			tx.commit();  
		} catch (Exception e) {
			 e.printStackTrace();
			 tx.rollback(); 
		}finally {
			HibernateUtils.closeSession();
		}  
		 return update;
	}
	@Override
	public List<User> getUsersOfFrozenOrUnfrozen(int is_frozen,int start,int num) {
		 List<User> users = new ArrayList<User>();
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例 
		 try { 
		    Query query = session.createQuery("SELECT new User(user_id,username,tel_num,integral,accuracy) from User where is_frozen = ?"); 
 		    query.setInteger(0,is_frozen);
		    query.setFirstResult(start-1);
 		    query.setMaxResults(num);
		    users = query.list();
		     //调用session的get()方法，找到此用户到内存中
		    tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}   
		 return users;
	}
	
	/**
	 * 获取冻结未冻结用户的总数
	 */
	@Override
	public long getUsersNumOfFrozenOrUnfrozen(int is_frozen) {
		 long num =0l;
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例 
		 try { 
		    Query query = session.createQuery("SELECT count(*) from User where is_frozen = ?"); 
 		    query.setInteger(0, is_frozen);
		    num = (long) query.uniqueResult();
		     //调用session的get()方法，找到此用户到内存中
		    tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}   
		 return num;
	}
	
	 
//	/**
//	 * 在interest表中添加属性
//	 */
//	@Override
//	public boolean addCategory(User user, Set<Category> categories) {  
//				Session session = HibernateUtils.getSession(); //生成session实例
//			    Transaction tx = session.beginTransaction();  //创建transaction实例  
//			    try {  
//			    	user.setCategories(categories);;
//			    	tx.commit();            //提交事务  
//			    	return true;
//				} catch (Exception e) {
//					e.printStackTrace();
//					tx.rollback(); 
//					return false;
//				}finally {
//				  HibernateUtils.closeSession();  //关闭Session实例
//				}  
//	}  
	
	
	
}
	
