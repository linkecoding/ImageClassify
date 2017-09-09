package cn.codekong.util; 
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils{
     
	private static SessionFactory sessionFactory;
	//创建ThreadLocal静态局部变量来存放Hibernate的session
	private static final ThreadLocal<Session> threadlocal=new ThreadLocal<Session>();
   //1.使用静态代码块来初始化Hibernate
	static{
		try {
			//读取hibernate.cfg.xml的配置信息
			Configuration configuration=new Configuration().configure();
		 	sessionFactory=configuration.buildSessionFactory();//创建sessionFactory
		} catch (Throwable ex) { 
			throw new ExceptionInInitializerError(ex);
		} 
	}
	//2.获得sessionFactory实例
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	//3.获得ThreadLocal对象管理的session
	public static Session getSession() throws HibernateException{
		Session session=threadlocal.get();
		if (session==null || !session.isOpen()) {
			if (sessionFactory==null) {
				rebuildSessionFactory();
			}
			//通过sessionFactory对象创建session对象
			session=(sessionFactory!=null)?sessionFactory.openSession():null;
			threadlocal.set(session);//将新打开的session实例保存到threadlocal线程局部变量中
		}
		return session;  
	}
	
	//4.关闭session实例
	public static void closeSession()throws HibernateException{
		Session session=threadlocal.get();//从线程局部变量中获取之前保存的session实例对象
		threadlocal.set(null);
		if (session!=null) {
			session.close();
		}
	}
	
	//5.重建sessionFactory
	public static void rebuildSessionFactory(){
		try {
			//读取配置信息
		 Configuration configuration= new Configuration().configure();
		 sessionFactory=configuration.buildSessionFactory();//创建sessionFactory
		} catch (Exception e) {
			System.out.println("Error creating sessionFactory!");
			e.printStackTrace();
		}
	}
	//6.关闭缓存和连接池
	public static void shutdown(){
		getSessionFactory().close();
	} 
}

