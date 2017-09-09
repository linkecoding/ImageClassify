package cn.codekong.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service; 
import cn.codekong.bean.Constant; 
import cn.codekong.dao.ConstantDao;
import cn.codekong.util.HibernateUtils;

@Service
public class ConstantService implements ConstantDao {

	@Override
	public boolean updateValueOfKey(Constant constant) { 
			Session session = HibernateUtils.getSession();
			 Transaction tx = session.beginTransaction();
			 boolean isUpdate = false;
			 try {
				Query query = session.createQuery("update Constant c set c.value =? where c.id = ?");  
			    query.setString(0,constant.getValue());
			    query.setInteger(1, constant.getId()); 
				int temp = query.executeUpdate();  
				tx.commit();
				if (temp>0) {
					isUpdate = true;
				}
			} catch (Exception e) {
				 e.printStackTrace();
				 tx.rollback();
			}finally {
				HibernateUtils.closeSession();
			}   
			 return isUpdate;
	}

	/**
	 * 根据key找到constant对象
	 */
	@Override
	public Constant getConstantByKey(String key) {
		 Session session = HibernateUtils.getSession();
		 Transaction tx = session.beginTransaction(); 
		 Constant constant = null;
		 try {
			 constant = (Constant) session.createCriteria(Constant.class).add(Restrictions.eq("key", key)).uniqueResult();			 
			 tx.commit();
		 } catch (Exception e) {
			 e.printStackTrace();
			 tx.rollback();
		}finally {
			HibernateUtils.closeSession();
		}   
		 return constant;
	}

	@Override
	public List<Constant> getConstantByKey() {
		
		 Session session = HibernateUtils.getSession();
		 Transaction tx = session.beginTransaction(); 
		 List<Constant> constants = new ArrayList<Constant>();
		 try {
			 constants.add((Constant) session.createCriteria(Constant.class).add(Restrictions.eq("key", cn.codekong.config.Constant.IDENTITY_TIME)).uniqueResult());
			 constants.add((Constant) session.createCriteria(Constant.class).add(Restrictions.eq("key", cn.codekong.config.Constant.IDENTIFY_FREQUENCY_MARKS)).uniqueResult());
			 tx.commit();
		 } catch (Exception e) {
			 e.printStackTrace();
			 tx.rollback();
		}finally {
			HibernateUtils.closeSession();
		}   
		 return constants;
	}

	@Override
	public List<Constant> getConstantConfig() {
		
		 Session session = HibernateUtils.getSession();
		 Transaction tx = session.beginTransaction(); 
		 List<Constant> constants = new ArrayList<Constant>();
		 try {
			 constants.add((Constant)session.createCriteria(Constant.class).add(Restrictions.eq("key", cn.codekong.config.Constant.THREHOLD)).uniqueResult());
			 constants.add((Constant) session.createCriteria(Constant.class).add(Restrictions.eq("key", cn.codekong.config.Constant.IDENTITY_TIME)).uniqueResult());
			 constants.add((Constant) session.createCriteria(Constant.class).add(Restrictions.eq("key", cn.codekong.config.Constant.IDENTIFY_FREQUENCY_MARKS)).uniqueResult());
			 tx.commit();
		 } catch (Exception e) {
			 e.printStackTrace();
			 tx.rollback();
		}finally {
			HibernateUtils.closeSession();
		}   
		 return constants; 
	}

}
