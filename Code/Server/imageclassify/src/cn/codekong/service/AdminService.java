package cn.codekong.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList; 
import java.util.List;
import java.util.Map; 
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service; 
import cn.codekong.bean.Admin;
import cn.codekong.bean.Image;
import cn.codekong.bean.User;
import cn.codekong.bean.label.ExportLabel;
import cn.codekong.dao.AdminDao;
import cn.codekong.util.HibernateUtils;
import cn.codekong.util.ImagesUtil;

@Service
public class AdminService implements AdminDao {

	/**
	 * 通过管理员的用户名和密码找出该管理员对象
	 */
	@Override
	public Admin findAdminByNameAndPaw(String admin_name, String admin_password) {
		 
		 Admin admin=null;
		 Session session=HibernateUtils.getSession();
		 Transaction tx=session.beginTransaction(); //开启事务
		 try { 
			  admin= (Admin) session.createCriteria(Admin.class).add(Restrictions.eq("admin_name", admin_name))
					 .add(Restrictions.eq("admin_password", admin_password)).uniqueResult();
			  tx.commit();
		} catch (Exception e) {
		  e.printStackTrace();
		  tx.rollback();
		}finally{
			HibernateUtils.closeSession();
		}
		 return admin;
	}

	/**
	 * 修改管理员的密码
	 */
	@Override
	public boolean update(Admin admin) {
		 Session session=HibernateUtils.getSession();
		 Transaction tx=session.beginTransaction();
		 try {
			session.update(admin);
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
     * 
     */
	@Override
	public Admin findAdminByToken(String admin_token) {
		 Admin admin=null;
		 Session session=HibernateUtils.getSession();//生成Session实例
		 Transaction tx=session.beginTransaction();//生成事务实例
		 try {
			  admin=(Admin) session.createCriteria(Admin.class).add(Restrictions.eq("admin_token", admin_token)).uniqueResult();
			  tx.commit();
		     //调用session的get()方法，找到此用户到内存中 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}
		 return admin;
	}

	@Override
	public List<User> getAllUsers(int start,int num) { 
		 List<User> users = new ArrayList<User>();
         Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例 
		 
		 try {
			  Query query = session.createQuery("select new User(user_id,username,tel_num,integral,accuracy,is_frozen) from User order by user_id");
			  query.setFirstResult(start-1);
			  System.out.println(start-1);
			  System.out.println(num);
			  query.setMaxResults(num);
			  users = query.list();
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
	public int getAllUsers() { 
		 List<User> users = new ArrayList<User>();
         Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例 
		 
		 try {
			  Query query = session.createQuery("select new User(user_id,username,tel_num,integral,accuracy,is_frozen) from User order by user_id");
			  users = query.list();
			  tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
		 System.out.println(users.size());
		 return users.size();  
		
	}
	
	@Override
	public int updateUserByAdmin(User user){
		Session session = HibernateUtils.getSession();
		 Transaction tx = session.beginTransaction();
		 int aa = 0;
		 try {
			Query query = session.createQuery("update User u set u.username =?,u.sex = ?,u.integral=?,u.accuracy=? where u.user_id = ?");  
		    query.setString(0, user.getUsername());
		    query.setString(1, user.getSex());
		    query.setInteger(2, user.getIntegral());
		    query.setString(3, user.getAccuracy());
		    query.setInteger(4, user.getUser_id());
			aa = query.executeUpdate();  
			tx.commit(); 
		} catch (Exception e) {
			 e.printStackTrace();
			 tx.rollback(); 
		}finally {
			HibernateUtils.closeSession();
		}  
		return aa;
	}

	@Override
	public List<ExportLabel> getExportImage() {
		 List<Image> images = new ArrayList<Image>();
		 Session session=HibernateUtils.getSession();//生成Session实例
		 Transaction tx=session.beginTransaction();//生成事务实例
		 try {
			  Query query = session.createQuery("select new Image(img_id,img_name,img_finish_time,img_label_name) FROM Image where img_is_finish = 1 and is_export = 0");
			  images = query.list();
			  tx.commit();
		     //调用session的get()方法，找到此用户到内存中 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}
		 ImagesUtil imagesUtil = new ImagesUtil();
		 List<ExportLabel> exportLabels = new ArrayList<ExportLabel>(); 
		 for (int i = 0; i < images.size(); i++) { 
			  ExportLabel exportLabel = new ExportLabel();
			  Image img = images.get(i); 
			  exportLabel.setImg_id(img.getImg_id());
			  exportLabel.setPicture_name(img.getImg_name());
			  exportLabel.setFinish_time(img.getImg_finish_time());
			  String img_label_name = img.getImg_label_name();
			  List<String> labels = imagesUtil.getExportLabels(img_label_name);
			  exportLabel.setLabels(labels);
			  exportLabels.add(exportLabel);
		 }  
		 return exportLabels;   
	}

	@Override
	public List<ExportLabel> getExportHistoryFile(int export_id) { 
		
			 List<ExportLabel> exportLabels = new ArrayList<ExportLabel>();
			 Session session=HibernateUtils.getSession();//生成Session实例
			 Transaction tx=session.beginTransaction();//生成事务实例
			 try {
				  Query query = session.createSQLQuery("SELECT export_tag.img_id,img_name,img_finish_time,img_label_name FROM image,export_tag WHERE image.img_id = export_tag.img_id AND export_id = "+export_id).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				  exportLabels = query.list();
				  tx.commit();
			     //调用session的get()方法，找到此用户到内存中 
			} catch (Exception e) {
				e.printStackTrace();
				tx.rollback();//事务回滚
			}finally{
				HibernateUtils.closeSession();//关闭session实例
			}
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			 ImagesUtil imagesUtil = new ImagesUtil();
			 List<ExportLabel> exportLabels2 = new ArrayList<ExportLabel>(); 
			 for (int i = 0; i < exportLabels.size(); i++) {  
				  Map map = (Map) exportLabels.get(i); 
				  ExportLabel exportLabel = new ExportLabel();
				  exportLabel.setImg_id(Integer.parseInt(map.get("img_id")+""));
				  exportLabel.setPicture_name(map.get("img_name")+"");
				  try {
					exportLabel.setFinish_time(sdf.parse(map.get("img_finish_time")+""));
				  } catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				  }
				  String img_label_name = map.get("img_label_name")+"";
				  List<String> labels = imagesUtil.getExportLabels(img_label_name);
				  exportLabel.setLabels(labels);
				  exportLabels2.add(exportLabel);
			 }  
			 return exportLabels2;   
	}  
	
	
}
