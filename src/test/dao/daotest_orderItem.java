package test.dao;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.nudt.goods.dao.OrderDao;
import cn.nudt.goods.dao.OrderItemDao;

public class daotest_orderItem {
	static OrderItemDao orderItemDao;
	static OrderDao orderDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		orderItemDao=(OrderItemDao)applicationContext.getBean("orderItemDaoBean");
		orderDao=(OrderDao)applicationContext.getBean("orderDaoBean");
	}

	@Test
	public void find() {
		System.out.println(orderItemDao.find("0844DB419E91407FAE223E3ED6B63707"));
	}

}
