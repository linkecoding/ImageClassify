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
import cn.codekong.bean.Image;
import cn.codekong.bean.Image_Category;
import cn.codekong.bean.Image_Composition;
import cn.codekong.bean.Zip;
import cn.codekong.bean.label.ImgUrl;
import cn.codekong.dao.ImageDao;
import cn.codekong.util.HibernateUtils;
import cn.codekong.util.ListToString;
@Service
public class ImageService implements ImageDao {

	/**
	 * 持久化image到数据库中
	 */
	@Override
	public boolean savaImage(Image image){
		 
		    Session session = HibernateUtils.getSession(); //生成session实例
		    Transaction tx = session.beginTransaction();  //创建transaction实例
		    try {  
		    	session.save(image);    //使用session的sava方法将持久化对象保存到数据库中
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
	 * 通过图片解压后并重命名的名字 查找存入数据库的img_id
	 */
	@Override
	public Image findImageByUrl(String img_path) {
		
		return null;
	}

	/**
	 * 通过zip找到对应的image
	 */
	@Override
	public List<Image> findImageByZip(Zip zip) {
		 List<Image> images = null; 
		 Session session  =  HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例
		 try {
			 images =  session.createCriteria(Image.class).add(Restrictions.eq("zip", zip)).list();
			 tx.commit();
		     //调用session的get()方法，找到此用户到内存中 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}
		 return images; 
	}

	/**
	 * 更新image对象
	 */
	@Override
	public boolean updateImage(Image image) {
		 
		Session session = HibernateUtils.getSession();
		 Transaction tx = session.beginTransaction();
		 try {
			session.update(image);
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
	 * 根据img_id找到image对象
	 */
	@Override
	public Image findImageById(int img_id) {
		 Image image = null; 
		 Session session  =  HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例
		 try {
			 image= (Image) session.createCriteria(Image.class).add(Restrictions.eq("img_id", img_id)).uniqueResult();
			 tx.commit();
		     //调用session的get()方法，找到此用户到内存中 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		}
		 return image; 
	}

	@Override
	public List<Image> getImageList() {
		 List<Image> images = new ArrayList<Image>();
         Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx=session.beginTransaction();//生成事务实例 
		 
		 try {
		 Query query = session.createSQLQuery("SELECT img_id,img_machine_tag_label FROM image where is_clustered = 0")
			    		.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP) ; 
		 images = query.list();
		 tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
		 
		 List<Image> lImages = new ArrayList<Image>(); 
		 for (int i = 0; i < images.size(); i++) { 
			  Image image = new Image();
			  Map map = (Map) images.get(i); 
			  image.setImg_id(Integer.parseInt(map.get("img_id")+""));
			  image.setImg_machine_tag_label(map.get("img_machine_tag_label")+"");
			  lImages.add(image);
		 }  
		 return lImages; 
	}

	@Override
	public boolean saveImageCategory(Image_Category image_Category) {
		Session session = HibernateUtils.getSession(); //生成session实例
	    Transaction tx = session.beginTransaction();  //创建transaction实例
	     
	    try {   
	        session.save(image_Category); 
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
	public List<Integer> seleteListOfCategory(int category_id,int user_id,String orderOfTime,String orderOfAmount) {
		Session session = HibernateUtils.getSession(); //生成session实例
	    Transaction tx = session.beginTransaction();  //创建transaction实例
	    List<Integer> imgIds = new ArrayList<Integer>();
	    ListToString listToString = new ListToString();
	    try {   
	    	String hql = "SELECT image_category.img_id FROM image_category,image,image_composition WHERE image_category.img_id = image.img_id AND image.img_id = image_composition.img_id AND image.img_is_finish <> 1 AND  category_id ="+category_id;
	    	String hql2 = " AND image.img_id NOT IN";
	    	String hq3 = "SELECT img_id FROM mark WHERE user_id = "+user_id; 
	    	String append = " ORDER BY image_composition.upload_time "+ orderOfTime + ",image_composition.amount " + orderOfAmount;
	    List<Integer> integers  = session.createSQLQuery(hq3).list(); 
	    if (!integers.isEmpty()) {
			hql = hql + hql2 + listToString.getListToString(integers) + append;
		}else {
			hql = hql + append;
		}
	    Query query = session.createSQLQuery(hql);
	    query.setFirstResult(0);
		query.setMaxResults(30);
	    imgIds = query.list();
	    tx.commit();            //提交事务  
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();          //回滚事务 
		}finally {
		  HibernateUtils.closeSession();  //关闭Session实例
		} 
	    return imgIds;
	} 
	@Override
	public List<Image_Composition> getNeededAmountImages(int user_id,List<Integer> imgIds,int task_img_amount,List<Integer> categories,String orderOfTime,String orderOfAmount) {
		 List<Image_Composition> marks = new ArrayList<Image_Composition>();
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例
		 ListToString listToString = new ListToString();
		 
		 try { 
			 String hql = "SELECT DISTINCT image_composition.img_id,image_composition.img_machine_tag_label,image_composition.img_name,image_composition.img_path FROM image_category,image_composition,image"
			 		+ " WHERE image_category.img_id = image_composition.img_id AND image.img_id= image_composition.img_id  AND image.img_is_finish<>1";
			 String hql2 = " AND category_id NOT IN ";
			 String hql3 = " AND image_category.img_id NOT IN ";
			String hql4 = " AND image_composition.img_id NOT IN ";
			String hql5 = " SELECT img_id FROM mark WHERE user_id = "+user_id;
			String append = " ORDER BY image_composition.upload_time ASC,image_composition.amount ASC";
			List<Integer> integers = session.createSQLQuery(hql5).list();
			 String categoriesString = listToString.getListToString(categories); 
			//有兴趣类别列表，不一定有图片ids列表，也不一定打过标签
			if (!integers.isEmpty()) {//志愿者打过标签
				 String integersString = listToString.getListToString(integers);
					if (!imgIds.isEmpty()) {
						String imgIdsString = listToString.getListToString(imgIds); 
						hql = hql+hql2+categoriesString+hql3+imgIdsString +hql4+integersString+append;
					}else {
						hql = hql+hql2+categoriesString+hql4+integersString + append;
					} 
			 }else{ //志愿者没有打过标签
					if (!imgIds.isEmpty()) {
						String imgIdsString = listToString.getListToString(imgIds); 
						hql = hql+hql2+categoriesString+hql3+imgIdsString + append;
					}else {
						hql = hql+hql2+categoriesString + append;
					}
				 
			 } 
			 Query query = session.createSQLQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
	 		 query.setFirstResult(0);
			 query.setMaxResults(task_img_amount);
			 marks = query.list();
			 tx.commit();//提交事务 
		     //调用session的get()方法，找到此用户到内存中
		   
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//事务回滚
		}finally{
			HibernateUtils.closeSession();//关闭session实例
		} 
		 List<Image_Composition> marks2 = new ArrayList<Image_Composition>(); 
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
	public List<Image_Composition> getPriorityImages(List<Integer> imgIds,int task_img_amount,String orderOfTime,String orderOfAmount) {
		 List<Image_Composition> marks = new ArrayList<Image_Composition>();
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例
		 //此处img_ids不为空
		 String imgIdsString = "(";
		 for (int i = 0; i < imgIds.size()-1; i++) {
			imgIdsString += imgIds.get(i)+","; 
		 }
		 imgIdsString +=imgIds.get(imgIds.size()-1)+")";
		 try { 
		    Query query = session.createSQLQuery("SELECT img_id,img_machine_tag_label,img_path,img_name"
		    		+ " FROM image_composition WHERE img_id IN "+imgIdsString + " ORDER BY image_composition.upload_time "+orderOfTime +",image_composition.amount " +orderOfAmount)
		    		.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
 		    query.setFirstResult(0);
 		    query.setMaxResults(task_img_amount);
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
	public List<Image> getFinishedImages() {
		List<Image> images = new ArrayList<Image>();
        Session session = HibernateUtils.getSession();//生成Session实例
		Transaction tx = session.beginTransaction();//生成事务实例 
		 
		 try {
			 images = session.createQuery("select new Image(img_id,img_label_name) from Image where img_is_finish = 1").list();
			 tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
		 return images; 
	}

	@Override
	public int updateImageOfCLus(Image image) {
		Session session = HibernateUtils.getSession();
		 Transaction tx = session.beginTransaction();
		 int aa = 0;
		 try {
			Query query = session.createQuery("update Image i set i.is_clustered  =1 where i.img_id = ?");  
		    
		    query.setInteger(0, image.getImg_id()); 
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
	public long getImageAmount() {
	 
		long amount = 0l;
        Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例 
		 
		 try {
			 amount =  (long)session.createQuery("select count(*) from Image").uniqueResult();
			 tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
		 return amount; 
		
	}

	@Override
	public long getTaskAmount() {
		long amount = 0l;
        Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例 
		 
		 try {
			 amount =  (long)session.createQuery("select count(*) from Task").uniqueResult();
			 tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
		 return amount; 
	}

	@Override
	public long getAmountOfDefinedTask(int amount) {
		long amountofTask = 0l;
        Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例 
		 
		 try {
			 amountofTask =  (long)session.createQuery("SELECT COUNT(*) FROM Task WHERE task_img_amount = "+amount).uniqueResult();
			 tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
		 return amountofTask; 
	}

	@Override
	public long getAmountOfDefinedImage(int is_finished) {
		long amountofImage = 0l;
        Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例 
		 
		 try {
			 amountofImage =  (long)session.createQuery("SELECT COUNT(*) FROM Image WHERE img_is_finish =  "+is_finished).uniqueResult();
			 tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
		 return amountofImage;
	}

	@Override
	public long getAmountOfUserSexAmount(String sex) {
		long amountofImage = 0l;
        Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例 
		 
		 try {
			 amountofImage =  (long)session.createQuery("SELECT COUNT(*) FROM User WHERE sex = '" + sex+"'").uniqueResult();
			 tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
		 return amountofImage; 
	}

	@Override
	public long getAmountOfUser() {
		long amountofImage = 0l;
        Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例  
		 try {
			 amountofImage =  (long)session.createQuery("SELECT COUNT(*) FROM User").uniqueResult();
			 tx.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		}
		 return amountofImage; 
	}

	@Override
	public List<ImgUrl> getImageByCategory(int category_id) {
		String sql = "SELECT DISTINCT img_path,img_name,image.img_id FROM image,image_category,category WHERE image.img_id = image_category.img_id AND category.category_id = image_category.category_id AND image.img_is_finish <>0 AND category.category_id = "+category_id;
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例  
		 List<ImgUrl> imgUrls = new ArrayList<ImgUrl>();
		 
		 try { 
		 Query query =  session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP) ;
		 imgUrls = query.list();
		 tx.commit();//提交事务 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		} 

		 List<ImgUrl> imgUrls2 = new ArrayList<ImgUrl>(); 
		 for (int i = 0; i < imgUrls.size(); i++) { 
			  ImgUrl image = new ImgUrl();
			  Map map = (Map) imgUrls.get(i); 
			  image.setImgName(map.get("img_name")+"");
			  image.setImgUrl(map.get("img_path")+"");  
			  imgUrls2.add(image);
		 }  
		 return imgUrls2;
	}
	
/*
 * SELECT COUNT(*)
FROM image,image_category,category
 WHERE image.img_id = image_category.img_id 
 AND category.category_id = image_category.category_id 
 AND image.img_is_finish <>0 AND category.category_id = 12
 */
	@Override
	public int getAmountByCategoryId(int category_id) {
		 String sql = "SELECT COUNT(*) FROM image,image_category,category WHERE image.img_id = image_category.img_id AND category.category_id = image_category.category_id AND image.img_is_finish <>0 AND category.category_id = "+category_id;
		 Session session = HibernateUtils.getSession();//生成Session实例
		 Transaction tx = session.beginTransaction();//生成事务实例  
		  int amount = 0;
		 try { 
		 List arr  =  session.createSQLQuery(sql).list(); 
		 amount = Integer.parseInt(arr.get(0).toString());
		 tx.commit();//提交事务 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession();//关闭session实例
		} 
		return amount;
	}
}
