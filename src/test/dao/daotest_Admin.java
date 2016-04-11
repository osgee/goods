package test.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.nudt.goods.bean.Admin;
import cn.nudt.goods.dao.AdminDao;

public class daotest_Admin {
	static AdminDao adminDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		adminDao = (AdminDao) applicationContext.getBean("adminDaoBean");
	}
	
	

	@Test
	public void add() {
		Admin admin=new Admin();
		admin.setAdminId("admin");
		admin.setAdminname("冯滔");
		admin.setAdminpwd("199511");
		adminDao.add(admin);
	}
	
	@Test
	public void find() {
		System.out.println(adminDao.find("冯滔", "19951"));
	}

}
