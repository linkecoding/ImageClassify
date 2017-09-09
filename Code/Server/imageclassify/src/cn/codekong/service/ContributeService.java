package cn.codekong.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import cn.codekong.bean.Contribute_Img;
import cn.codekong.bean.label.ContributeImgListOfAll;
import cn.codekong.dao.ContributeDao;
import cn.codekong.util.HibernateUtils;

public class ContributeService implements ContributeDao{

	/**
	 * 插入记录
	 */
	@Override
	public boolean saveContribute(Contribute_Img cImg) {
		Session session = HibernateUtils.getSession(); // 生成session实例
		Transaction tx = session.beginTransaction(); // 创建transaction实例
		boolean isSaved = true;
		try {
			session.save(cImg); // 使用session的sava方法将持久化对象保存到数据库中
			tx.commit(); // 提交事务
			 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback(); // 回滚事务
			isSaved = false;
		} finally {
			HibernateUtils.closeSession(); // 关闭Session实例
		}
		return isSaved;
	}

	/**
	 * 根据指定的用户id获取其贡献的压缩列表
	 */
	@Override
	public List<Contribute_Img> getContributeBySelf(int user_id,int start,int page_num) {
		 
		Session session = HibernateUtils.getSession(); // 生成session实例
		Transaction tx = session.beginTransaction(); // 创建transaction实例
		List<Contribute_Img> contribute_Imgs = new ArrayList<Contribute_Img>();
		try {
			contribute_Imgs = session.createCriteria(Contribute_Img.class).add(Restrictions.eq("user_id", user_id))
					.setFirstResult(start-1).setMaxResults(page_num).addOrder(Order.desc("upload_img_time")).list();
			tx.commit(); // 提交事务
			 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback(); // 回滚事务 
		} finally {
			HibernateUtils.closeSession(); // 关闭Session实例
		} 
		return contribute_Imgs;
	}

	/**
	 * 管理员端获取所有的用户贡献列表
	 */
	@Override
	public List<ContributeImgListOfAll> getContributeOfAllByUser(int start, int page_num) {
		Session session = HibernateUtils.getSession();
		Transaction tx = session.beginTransaction();
		String sql = "SELECT id,upload_img_time,upload_img_review_status,username,tel_num FROM user_contribute_img WHERE upload_img_review_status=0 ORDER BY upload_img_time ASC";
		List<ContributeImgListOfAll> cImgs = new ArrayList<ContributeImgListOfAll>();
		try { 
			cImgs = session.createSQLQuery(sql).setFirstResult(start-1).setMaxResults(page_num).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			tx.commit(); 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			HibernateUtils.closeSession(); // 关闭Session实例
		} 
		
		List<ContributeImgListOfAll> cAlls = new ArrayList<ContributeImgListOfAll>();
		for(int i = 0;i<cImgs.size();i++){
			ContributeImgListOfAll cOfAll = new ContributeImgListOfAll();
			Map map = (Map) cImgs.get(i);
			cOfAll.setId((int) map.get("id"));
			cOfAll.setTel_num(map.get("tel_num")+"");
			cOfAll.setUpload_img_review_status(map.get("upload_img_review_status")+"");
			cOfAll.setUpload_img_time(map.get("upload_img_time")+"");
			cOfAll.setUsername(map.get("username")+"");
			cAlls.add(cOfAll);
		} 
		return cAlls;
	}

	/**
	 * 根据id查询到志愿者上传的贡献压缩包信息
	 */
	@Override
	public Contribute_Img getContributeImgById(int id) {
		 
		Session session = HibernateUtils.getSession();
		Transaction tx = session.beginTransaction();
		Contribute_Img cImg = new Contribute_Img();
		try { 
			cImg = (Contribute_Img) session.createCriteria(Contribute_Img.class).add(Restrictions.eq("id", id)).uniqueResult();
			tx.commit(); 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			HibernateUtils.closeSession(); // 关闭Session实例
		} 
		return cImg;
	}

	@Override
	public int getAmountContributeOfAllByUser() { 
		Session session = HibernateUtils.getSession();
		Transaction tx = session.beginTransaction();
		String sql = "SELECT id,upload_img_time,upload_img_review_status,username,tel_num FROM user_contribute_img WHERE upload_img_review_status=0 ORDER BY upload_img_time ASC";
		List<Contribute_Img > cImgs = new ArrayList<Contribute_Img>();
		try { 
			cImgs = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			tx.commit(); 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			HibernateUtils.closeSession(); // 关闭Session实例
		} 
		return cImgs.size();
	}

	/**
	 * 根据id更新审核状态字段
	 */
	@Override
	public boolean updateZipUploadByUser(int id,int status) {
		Session session = HibernateUtils.getSession();
		Transaction tx = session.beginTransaction();
		boolean isUpdate = true;
		try { 
			Query query = session.createQuery("update Contribute_Img c set c.upload_img_review_status = ? where c.id = ?");
			query.setInteger(0, status);
			query.setInteger(1, id);
			query.executeUpdate();
			tx.commit(); 
		} catch (Exception e) {
			isUpdate = false;
			e.printStackTrace();
			tx.rollback(); 
		} finally {
			HibernateUtils.closeSession(); // 关闭Session实例
		}  
		return isUpdate;
	}

}
