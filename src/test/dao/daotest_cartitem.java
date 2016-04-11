package test.dao;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.nudt.goods.dao.CartItemDao;

public class daotest_cartitem {
	@Resource
	static
	CartItemDao cartItemDao;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		 cartItemDao=(CartItemDao)applicationContext.getBean("cartItemDaoBean");
	}

	@Test
	public void findByCartItemId() {
		System.out.println(cartItemDao.findByCartItemId("22"));
	}

}
