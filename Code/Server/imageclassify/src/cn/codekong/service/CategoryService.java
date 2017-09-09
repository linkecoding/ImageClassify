package cn.codekong.service;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import cn.codekong.bean.Category;
import cn.codekong.dao.CategoryDao;
import cn.codekong.util.HibernateUtils;

@Service
public class CategoryService implements CategoryDao {

	/**
	 * 持久化category对象
	 */
	@Override
	public boolean saveCatogory(Category category) {
		Session session = HibernateUtils.getSession(); // 生成session实例
		Transaction tx = session.beginTransaction(); // 创建transaction实例
		try {
			session.save(category); // 使用session的sava方法将持久化对象保存到数据库中
			tx.commit(); // 提交事务
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback(); // 回滚事务
			return false;
		} finally {
			HibernateUtils.closeSession(); // 关闭Session实例
		}
	}

	/**
	 * FindCatogory根据category_name查询是否已经有此分类
	 */
	@Override
	public Category findCatogoryByName(String category_name) {
		Category category = null;
		Session session = HibernateUtils.getSession();
		Transaction tx = session.beginTransaction(); // 开启事务
		try {

			category = (Category) session.createCriteria(Category.class)
					.add(Restrictions.eq("category_name", category_name)).uniqueResult();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			HibernateUtils.closeSession();
		}
		return category;
	}

	@Override
	public List<Category> getCategotyList() {
		List<Category> categories = new ArrayList<Category>();
		Session session = HibernateUtils.getSession();// 生成Session实例
		Transaction tx = session.beginTransaction();// 生成事务实例

		try {
			categories = session
					.createQuery("select new Category(category_id,category_name) from Category order by category_id")
					.list();
			tx.commit();// 提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			HibernateUtils.closeSession();// 关闭session实例
		}
		return categories;
	}

	
	
	
	/**
	 * 根据类别id找到类别对象
	 */
	@Override
	public Category findCategoryById(int category_id) {
		Category category = null;
		Session session = HibernateUtils.getSession();// 生成Session实例
		Transaction tx = session.beginTransaction();// 生成事务实例
		try {
			category = (Category) session.get(Category.class, category_id);
			tx.commit();// 提交事务
			// 调用session的get()方法，找到此用户到内存中
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();// 事务回滚
		} finally {
			HibernateUtils.closeSession();// 关闭session实例
		}
		return category;
	}

}
