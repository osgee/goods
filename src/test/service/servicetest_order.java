package test.service;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.nudt.goods.bean.Order;
import cn.nudt.goods.bean.OrderItem;
import cn.nudt.goods.bean.PageBean;
import cn.nudt.goods.service.OrderService;

public class servicetest_order {
	static OrderService orderService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		orderService = (OrderService) applicationContext
				.getBean("orderServiceBean");
	}

	@Test
	public void updateStatus() {
		orderService.updateStatus("058F48DA33694C6D8F5C2C13F3D26CQQ", 5);
	}

	@Test
	public void findStatus() {
		System.out.println(orderService
				.findStatus("058F48DA33694C6D8F5C2C13F3D26CQQ"));
	}

	@Test
	public void load() {
		System.out.println(orderService
				.load("0ADCEE0510844D2697E7A5C0903A8D3B"));
	}

	@Test
	public void createOrder() {
		Order order = new Order();
		order.setOid("058F48DA33694C6D8F5C2C13F3D26CQQ");
		order.setAddress("湖南省湘潭市");
		order.setOrdertime("2013-12-26 21:47:04");
		orderService.createOrder(order);
	}

	@Test
	public void myOrders() {
		for (int i = 1; i < 10; i++) {
			PageBean<Order> oBean = orderService.myOrders(
					"32DB3700D2564254982BC58B0E4D95BC", i);
			List<Order> orders = oBean.getBeanList();
			System.out.println();
			System.out.println("第"+i+"页：");
			for (Order order : orders) {
				System.out.println(order);
				System.out.println("AAAA:");
				List<OrderItem> orderItems=order.getOrderItemList();
				for (OrderItem orderItem : orderItems) {
					System.out.println(orderItem);
				}
			}
		}
	}

	@Test
	public void findByStatus() {

		PageBean<Order> orderPageBean = orderService.findByStatus(2, 12);
		List<Order> orders = orderPageBean.getBeanList();
		for (Order order : orders) {
			System.out.println();
			System.out.println(order);
			List<OrderItem> orderItems=order.getOrderItemList();
			for (OrderItem orderItem : orderItems) {
				System.out.println(orderItem);
			}
		}

	}

	@Test
	public void findAll() {

		PageBean<Order> orderPageBean = orderService.findAll(12);
		List<Order> orders = orderPageBean.getBeanList();
		for (Order order : orders) {
			System.out.println(order);
		}

	}

}
