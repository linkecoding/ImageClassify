package cn.codekong.service;
 
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
import cn.codekong.bean.Image_Composition;
import cn.codekong.bean.Image_Mark;
import cn.codekong.bean.Mark; 
import cn.codekong.dao.MarkDao;
import cn.codekong.util.HibernateUtils;
import cn.codekong.util.ListToString;
@Service
public class MarkService implements MarkDao{

	@Override
	public boolean saveMark(Mark mark) {
		Session session = HibernateUtils.getSession(); //生成session实例
	    Transaction tx = session.beginTransaction();  //创建transaction实例
	    try {  
	    	session.save(mark);    //使用session的sava方法将持久化对象保存到数据库中
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
	public boolean updateMark(Mark mark) {
		Session session = HibernateUtils.getSession();
		 Transaction tx = session.beginTransaction();
		 try {
			Query query = session.createQuery("update Mark m set m.mark_accuracy =? where m.user_id = ? and m.img_id = ?");  
		    query.setString(0, mark.getMark_accuracy());
		    query.setInteger(1, mark.getUser_id());
		    query.setInteger(2, mark.getImg_id()); 
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
	public List<Image_Mark> getComposition(int amount,int user_id) {
		 List<Image_Mark> marks = new ArrayList<Image_Mark>();
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例
		 
		 try { 
		    Query query = session.createSQLQuery("SELECT img_machine_tag_label,img_path,img_name,user_id,img_id FROM image_mark GROUP BY img_id HAVING img_id NOT IN(SELECT img_id FROM Mark WHERE user_id = "+user_id+") ORDER BY COUNT(*) ASC")
		    		.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
 		    query.setFirstResult(0);
 		    query.setMaxResults(amount);
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
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
		 for (int i = 0; i < marks.size(); i++) { 
			 Image_Mark image_Mark = new Image_Mark();
			  Map map = (Map) marks.get(i);
			  //img_machine_tag_label,img_path,img_name,user_id,img_id
			  image_Mark.setImg_id(map.get("img_id")+"");
			  image_Mark.setImg_machine_tag_label(map.get("img_machine_tag_label")+"");
			  image_Mark.setImg_name(map.get("img_name")+"");
			  image_Mark.setImg_path(map.get("img_path")+"");
			   marks2.add(image_Mark);
		 }  
		 return marks2;
	}
	
	@Override
	public List<Image_Mark> getInterestComposition(int amount,int user_id,List<Integer> imgIds) {
		 List<Image_Mark> marks = new ArrayList<Image_Mark>();
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例String
		 String listToString = "(";
		 for (int i = 0; i < imgIds.size()-1; i++) {
			 listToString += imgIds.get(i)+",";
		}
		 listToString += imgIds.get(imgIds.size()-1)+")";
		 
		 try {  
		    Query query = session.createSQLQuery("SELECT img_machine_tag_label,img_path,img_name,user_id,img_id FROM"
		    		+ " image_mark GROUP BY img_id HAVING img_id NOT IN(SELECT img_id FROM Mark WHERE user_id = "+user_id+" OR"
		    		+ " img_id IN "+listToString+") ORDER BY COUNT(*) ASC")
		    		.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
 		    query.setFirstResult(0);
 		    query.setMaxResults(amount);
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
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
		 for (int i = 0; i < marks.size(); i++) { 
			 Image_Mark image_Mark = new Image_Mark();
			  Map map = (Map) marks.get(i);
			  //img_machine_tag_label,img_path,img_name,user_id,img_id
			  image_Mark.setImg_id(map.get("img_id")+"");
			  image_Mark.setImg_machine_tag_label(map.get("img_machine_tag_label")+"");
			  image_Mark.setImg_name(map.get("img_name")+"");
			  image_Mark.setImg_path(map.get("img_path")+"");
			   marks2.add(image_Mark);
		 }  
		 return marks2;
	}

	@Override
	public List<Integer> getImgIdAble(int thresholdValue) {
		List<Integer> imgidList = new ArrayList<Integer>();
		Session session = HibernateUtils.getSession(); //生成session实例
	    Transaction tx = session.beginTransaction();  //创建transaction实例
	    try {  
	    	Query query = session.createSQLQuery("SELECT img_id FROM image_mark  GROUP BY img_id HAVING COUNT(*) >= " + thresholdValue);  
	 		imgidList = query.list();
	    	tx.commit();            //提交事务 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();          //回滚事务 
		}finally {
		  HibernateUtils.closeSession();  //关闭Session实例
		}
		return imgidList;
	}

	@Override
	public List<Image_Mark> getImageMark(int img_id) {
		List<Image_Mark> image_Marks = new ArrayList<Image_Mark>();
		Session session = HibernateUtils.getSession(); //生成session实例
	    Transaction tx = session.beginTransaction();  //创建transaction实例
	    try {  
	    	 
	    	Query query = session.createSQLQuery("SELECT option_mark_name,manual_mark_name FROM image_mark where img_id = "+img_id)
		    		.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP) ; 
	    	image_Marks = query.list();
	    	tx.commit();            //提交事务 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();          //回滚事务 
		}finally {
		  HibernateUtils.closeSession();  //关闭Session实例
		}
	    
		 List<Image_Mark> marks = new ArrayList<Image_Mark>();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		 for (int i = 0; i < image_Marks.size(); i++) { 
			 Image_Mark image_Mark = new Image_Mark();
			  Map map = (Map) image_Marks.get(i);
			  //img_machine_tag_label,img_path,img_name,user_id,img_id 
			  image_Mark.setManual_mark_name(map.get("manual_mark_name")+"");
			  image_Mark.setOption_mark_name(map.get("option_mark_name")+"");
			  marks.add(image_Mark);
		 }  
		 return marks; 
	}

	@Override
	public List<Image_Composition> getCompositionOfAll(int amount, int user_id,String orderOfTime,String orderOfAmount) {
		 List<Image_Composition> marks = new ArrayList<Image_Composition>();
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例
		 
		 ListToString listToString = new ListToString();
		 try { 
			  
			 String hql = "SELECT image_composition.img_id,image_composition.img_machine_tag_label,image_composition.img_path,image_composition.img_name "
			 		+ "FROM image_composition ,image WHERE image_composition.img_id = image.img_id AND image.img_is_finish <> 1";
			 
		     String hql1 = " AND  image_composition.img_id NOT IN";
		     String hql2 = "SELECT img_id FROM mark WHERE user_id = " +user_id;
		     String append = " ORDER BY image_composition.upload_time " + orderOfTime + ", image_composition.amount " + orderOfAmount;
			 List<Integer> imgIds = session.createSQLQuery(hql2).list();
			 
			 if (!imgIds.isEmpty()) {
				hql = hql + hql1 + listToString.getListToString(imgIds) + append;
			 } else {
				hql = hql + append;
			}
		    Query query = session.createSQLQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
 		    query.setFirstResult(0);
 		    query.setMaxResults(amount);
		    marks = query.list();
		     //调用session的get()方法，找到此用户到内存中
		    tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		} 
		 List<Image_Composition> marks2 = new ArrayList<Image_Composition>();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
		 for (int i = 0; i < marks.size(); i++) { 
			 Image_Composition image_Composition = new Image_Composition();
			  Map map = (Map) marks.get(i);
			  //img_machine_tag_label,img_path,img_name,user_id,img_id
			  image_Composition.setImg_id(Integer.parseInt(map.get("img_id")+""));
			  image_Composition.setImg_machine_tag_label(map.get("img_machine_tag_label")+"");
			  image_Composition.setImg_name(map.get("img_name")+"");
			  image_Composition.setImg_path(map.get("img_path")+"");
			   marks2.add(image_Composition);
		 }  
		 return marks2; 
	}

	@Override
	public List<Mark> getMarkList(int img_id) {
		List<Mark> marks = new ArrayList<Mark>();
        Session session = HibernateUtils.getSession();//生成Session实例
		Transaction tx = session.beginTransaction();//生成事务实例 
		 
		 try {
			 marks = session.createQuery("select new Mark(option_mark_name,manual_mark_name,img_id,user_id) from Mark where img_id = "+img_id).list();
			 tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
		 return marks;
	}

	@Override
	public List<String> getMarkListByUserId(int user_id) {
		List<String> accuracy = new ArrayList<String>();
        Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例 
		 
		 try {
			 accuracy = session.createQuery("select mark_accuracy from Mark where user_id = ?").setInteger(0, user_id).list();
			 tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
		 return accuracy;
	}

	@Override
	public List<Mark> getMarkListByUserAndTask(int user_id, int img_id) {
		 List<Mark> marks = new ArrayList<Mark>();
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例 
		 try { 
			 marks = session.createSQLQuery("SELECT option_mark_name,manual_mark_name,mark_accuracy FROM mark WHERE user_id = "+user_id+" AND img_id = "+img_id).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		     //调用session的get()方法，找到此用户到内存中
		    tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}     
	
		 List<Mark> marks2 = new ArrayList<Mark>(); 
		 
		 for (int i = 0; i < marks.size(); i++) { 
			  Mark mark = new Mark();
			  Map map = (Map) marks.get(i); 
			  mark.setOption_mark_name(map.get("option_mark_name")+"");
			  mark.setManual_mark_name(map.get("manual_mark_name")+""); 
			  mark.setMark_accuracy(map.get("mark_accuracy")+"");
			  marks2.add(mark);
		 }  
		 return marks2; 
	}

	@Override
	public int deleteMark(int user_id, int img_id) {
		Session session = HibernateUtils.getSession(); //生成session实例
	    Transaction tx = session.beginTransaction();  //创建transaction实例 
	    int temp = 0;
	    try {  
	    	String hql = "delete from Mark where user_id  = ? and img_id = ?"; 
	    	Query query = session.createQuery(hql);
	    	query.setInteger(0, user_id);
	    	query.setInteger(1, img_id);
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
	public List<Mark> getMarkListByUserAndImage(int user_id, int img_id) {
		 List<Mark> marks = null;
		 Session session = HibernateUtils.getSession();
		 Transaction tx = session.beginTransaction(); //开启事务
		 try {
			 
			 marks = session.createCriteria(Mark.class).add(Restrictions.eq("user_id", user_id))
					 .add(Restrictions.eq("img_id", img_id)).list();
			  tx.commit();
		} catch (Exception e) {
		  e.printStackTrace();
		  tx.rollback();
		}finally{
			HibernateUtils.closeSession();
		} 
		return marks; 
	}

	@Override
	public boolean updateMarkAll(Mark mark) {
		 Session session = HibernateUtils.getSession();
		 Transaction tx = session.beginTransaction();
		 boolean b = false;
		 try {
			Query query = session.createQuery("update Mark m set m.mark_accuracy =?,m.mark_time =?,m.option_mark_name =?,"
					+ "m.manual_mark_name=? where m.user_id = ? and m.img_id = ?");  
		    query.setString(0, mark.getMark_accuracy());
		    query.setTimestamp(1, mark.getMark_time());
		    query.setString(2,mark.getOption_mark_name());
		    query.setString(3, mark.getManual_mark_name()); 
		    query.setInteger(4, mark.getUser_id());
		    query.setInteger(5, mark.getImg_id()); 
			query.executeUpdate();  
			tx.commit();
			b = true;
		} catch (Exception e) {
			 e.printStackTrace();
			 tx.rollback(); 
		}finally {
			HibernateUtils.closeSession();
		} 
		 return b;
	} 
	
	
	
}
