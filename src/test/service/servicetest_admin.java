package test.service;

import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.nudt.goods.bean.Admin;
import cn.nudt.goods.service.AdminService;

public class servicetest_admin {
	static AdminService adminService;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		adminService=(AdminService) applicationContext.getBean("adminServiceBean");
	}

	@Test
	public void login() throws SQLException {
		Admin admin=new Admin();
		admin.setAdminname("张三");
		admin.setAdminpwd("1234");
		Admin admin2=adminService.login(admin);
		System.out.println(admin2);
	}
	

}
