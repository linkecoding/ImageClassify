package cn.codekong.service;

import java.util.ArrayList; 
import java.util.List; 
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service; 
import cn.codekong.bean.Zip;
import cn.codekong.dao.ZipDao;
import cn.codekong.util.HibernateUtils;

@Service
public class ZipService implements ZipDao {

	/**
	 * 将上传后的压缩包zip存入数据库中
	 */
	@Override
	public boolean saveZip(Zip zip) {
		Session session = HibernateUtils.getSession(); //生成session实例
	    Transaction tx = session.beginTransaction();  //创建transaction实例
	    try {  
	    	session.save(zip);    //使用session的sava方法将持久化对象保存到数据库中
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
		 * showZip函数用于展示所有的zip文件
		 */ 
		@Override
		public List<Zip> showZip(int start,int num) { 
			 List<Zip> zips = new ArrayList<Zip>();
	         Session session = HibernateUtils.getSession();//生成Session实例
			 Transaction tx = session.beginTransaction();//生成事务实例  
			 try {
				  zips = session.createCriteria(Zip.class).setFirstResult(start-1).setMaxResults(num).list();
				  tx.commit();//提交事务
			} catch (Exception e) {
				e.printStackTrace();
				tx.rollback();
			}finally {
				HibernateUtils.closeSession();//关闭session实例
			}
			 return zips;
		}
		/**
		 * showZip函数用于展示所有的zip文件
		 */ 
//		@Override
//		public List<Zip> showZip() { 
//			 List<Zip> zips = new ArrayList<Zip>();
//	         Session session = HibernateUtils.getSession();//生成Session实例
//			 Transaction tx = session.beginTransaction();//生成事务实例  
//			 try {
//				  zips = session.createCriteria(Zip.class).add(Restrictions.eq("isZip",0)).list();
//				  tx.commit();//提交事务
//			} catch (Exception e) {
//				e.printStackTrace();
//				tx.rollback();
//			}finally {
//				HibernateUtils.closeSession();//关闭session实例
//			}
//			 return zips;
//		}
		/**
		 * 根据id查找对应Zip对象
		 */
		@Override
		public Zip findZipById(int zip_id) {
			 Zip zip = null; 
			 Session session  =  HibernateUtils.getSession();//生成Session实例
			 Transaction tx = session.beginTransaction();//生成事务实例
			 try {
				 zip=(Zip) session.createCriteria(Zip.class).add(Restrictions.eq("zip_id", zip_id)).uniqueResult();
				 tx.commit();
			     //调用session的get()方法，找到此用户到内存中 
			} catch (Exception e) {
				e.printStackTrace();
				tx.rollback();//事务回滚
			}finally{
				HibernateUtils.closeSession();//关闭session实例
			}
			 return zip;
		} 
	  /**
	   * 更新zip对象
	   */

		@Override
		public boolean updateZip(Zip zip) {
			Session session = HibernateUtils.getSession();
			 Transaction tx = session.beginTransaction();
			 try {
				session.update(zip);
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
		 * 展示已经解压未识别的列表
		 */
	@Override
	public List<Zip> showZipAndUnClassifyFolder() {
		List<Zip> zips = new ArrayList<Zip>();
        Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例  
		 try {
			  zips = session.createCriteria(Zip.class).add(Restrictions.eq("isZip", 1)).add(Restrictions.eq("isClassify", 0)).list();
			  tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
		 return zips;
	}

		@Override
		public long showZip() {
			  long num = 0;
	         Session session = HibernateUtils.getSession();//生成Session实例
			 Transaction tx = session.beginTransaction();//生成事务实例  
			 try {
				  num = session.createCriteria(Zip.class).list().size();
				  tx.commit();//提交事务
			} catch (Exception e) {
				e.printStackTrace();
				tx.rollback();
			}finally {
				HibernateUtils.closeSession();//关闭session实例
			}
			 return num;
		} 
}