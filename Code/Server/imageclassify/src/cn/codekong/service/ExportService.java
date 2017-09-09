package cn.codekong.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList; 
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import cn.codekong.bean.ExportRecord;
import cn.codekong.bean.ExportTag; 
import cn.codekong.bean.label.ExportHistoryLabel;
import cn.codekong.dao.ExportDao;
import cn.codekong.util.HibernateUtils;

public class ExportService implements ExportDao{

	@Override
	public int saveExportRecord(ExportRecord exportRecord) {
		Session session = HibernateUtils.getSession(); //生成session实例
	    Transaction tx = session.beginTransaction();  //创建transaction实例
	    int export_id = 0;
	    try {  
	    	session.save(exportRecord);    //使用session的sava方法将持久化对象保存到数据库中
			tx.commit();            //提交事务
			export_id = exportRecord.getExport_id();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();          //回滚事务
		}finally {
		  HibernateUtils.closeSession();  //关闭Session实例
		} 
	    return export_id;
	}

	@Override
	public boolean save(ExportTag exportTag) {
		Session session = HibernateUtils.getSession(); //生成session实例
	    Transaction tx = session.beginTransaction();  //创建transaction实例
	    try {  
	    	session.save(exportTag);    //使用session的sava方法将持久化对象保存到数据库中
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
	 * 根据img_id更新image对象
	 */
	@Override
	public int updateExport(int img_id) {
			Session session = HibernateUtils.getSession();
			 Transaction tx = session.beginTransaction();
			 int isUpdate = 0;
			 try {
				Query query = session.createQuery("update Image i set i.is_export = 1 where i.img_id = ?");  
			    
			    query.setInteger(0, img_id); 
			    isUpdate = query.executeUpdate();  
				tx.commit(); 
			} catch (Exception e) {
				 e.printStackTrace();
				 tx.rollback(); 
			}finally {
				HibernateUtils.closeSession();
			} 
			return isUpdate;
	}
	/*SELECT export_record.export_id,export_record.export_time,COUNT(*) AS amount
	  FROM  export_record,export_tag
  	  WHERE export_record.export_id = export_tag.export_id*/

	/**
	 * 所有历史标签列表
	 * @throws ParseException 
	 */
	@Override
	public List<ExportHistoryLabel> gExportHistoryLabels(int start,int page_num){
		 
		List<ExportHistoryLabel> images = new ArrayList<ExportHistoryLabel>();
        Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx=session.beginTransaction();//生成事务实例 
		 
		 try {
		 Query query = session.createSQLQuery("SELECT export_record.export_id,export_record.export_time,COUNT(*) AS amount FROM  "
		 		+ "export_record,export_tag WHERE export_record.export_id = export_tag.export_id GROUP BY export_record.export_id ORDER BY export_record.export_time DESC").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP) ; 
		 query.setFirstResult(start-1);
		 query.setMaxResults(page_num);
		 images = query.list();
		 tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		 List<ExportHistoryLabel> exportHistoryLabels = new ArrayList<ExportHistoryLabel>(); 
		 if (images.size()!=0) {
			 for (int i = 0; i < images.size(); i++) { 
				  ExportHistoryLabel image = new ExportHistoryLabel();
				  Map map = (Map) images.get(i);
				  image.setExport_id(Integer.parseInt(map.get("export_id")+""));
				  try {
					image.setExport_time(sdf.parse(map.get("export_time")+""));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  image.setAmount(Integer.parseInt(map.get("amount")+""));
				  exportHistoryLabels.add(image);
			 }   
		} 
		 return exportHistoryLabels;
	}

	@Override
	public long exportHistoryLabelsNum() { 
		 long pages_num = 0;
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx=session.beginTransaction();//生成事务实例 
		 
		 try {
		     Query query = session.createSQLQuery("SELECT export_record.export_id,export_record.export_time,COUNT(*) AS amount FROM  "
			 		+ "export_record,export_tag WHERE export_record.export_id = export_tag.export_id GROUP BY export_record.export_id ORDER BY export_record.export_time DESC").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP) ; 			  
		     pages_num  = query.list().size();	
		     tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
		return pages_num;
	}

	

}
