package test.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.nudt.goods.bean.Admin;
import cn.nudt.goods.bean.Book;
import cn.nudt.goods.bean.CartItem;
import cn.nudt.goods.bean.Category;
import cn.nudt.goods.bean.Order;
import cn.nudt.goods.bean.OrderItem;
import cn.nudt.goods.bean.User;
import cn.nudt.goods.utils.ConnectionSafer;

public class test {

	static SessionFactory sessionFactory;

//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
//		sessionFactory=(SessionFactory)applicationContext.getBean("sessionFactory");
//	}

	@Test
	public void save() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		Admin user = new Admin();
		// user.setId(UUID.randomUUID().toString());
		user.setAdminId("2");
		user.setAdminpwd("1234");
		user.setAdminname("lisi");

		session.save(user);
		// session.save(new User());

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();

	}

	@Test
	public void getAdmin() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Admin user = (Admin) session.get(Admin.class, "a1"); // ��ȡ
		System.out.println(user);
		tx.commit();
		session.close();

	}

	@Test
	public void getBook() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Book user = (Book) session.get(Book.class,
				"000A18FDB38F470DBE9CD0972BADB23F"); // ��ȡ
		System.out.println(user);
		tx.commit();
		session.close();

	}

	@Test
	public void getUser() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		User user = (User) session.get(User.class,
				"531D8A16D524478D86F8A115FE95D93F"); // ��ȡ
		System.out.println(user);
		tx.commit();
		session.close();

	}

	@Test
	public void getCategorybyBook() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Book book = (Book) session.get(Book.class,
				"39F1D0803E8F4592AE1245CACE683214"); // ��ȡ
		System.out.println(book);
		System.out.println(book.getCategory());
		tx.commit();
		session.close();
	}

	@Test
	public void getParentbyCategory() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Category category = (Category) session.get(Category.class,
				"458795C27E7346A8A5F1B942319297E0"); // ��ȡ
		System.out.println(category);

		System.out.println(category.getParent());
		tx.commit();
		session.close();
	}

	@Test
	public void getChildrenbyCategory() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Category category = (Category) session.get(Category.class, "1"); // ��ȡ
		System.out.println(category);
		List<Category> childrenList = category.getChildren();
//		for (Iterator<Category> iterator = childrenList.iterator(); iterator
//				.hasNext();) {
//			Category type = (Category) iterator.next();
//			System.out.println(type);
//		}
		System.out.println(childrenList.get(0));
		tx.commit();
		session.close();
	}

	@Test
	public void getBookbyCartItem() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		CartItem cartItem = (CartItem) session.get(CartItem.class,
				"084701B6296340F294C75B2D33E3116F"); // ��ȡ
		System.out.println(cartItem);
		System.out.println(cartItem.getBook());

		tx.commit();
		session.close();
	}

	@Test
	public void getUserbyCartItem() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		CartItem cartItem = (CartItem) session.get(CartItem.class,
				"084701B6296340F294C75B2D33E3116F"); // ��ȡ
		System.out.println(cartItem);
		System.out.println(cartItem.getUser());

		tx.commit();
		session.close();
	}

	@Test
	public void getUserbyOrder() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Order order = (Order) session.get(Order.class,
				"058F48DA33694C6D8F5C2C13F3D26CEA"); // ��ȡ
		System.out.println(order);
		System.out.println(order.getOwner());
		tx.commit();
		session.close();
	}

	@Test
	public void getOrderItembyOrder() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Order order = (Order) session.get(Order.class,
				"058F48DA33694C6D8F5C2C13F3D26CEA"); // ��ȡ
		System.out.println(order);
		List<OrderItem> orderItem = order.getOrderItemList();
		for (Iterator<OrderItem> iterator = orderItem.iterator(); iterator
				.hasNext();) {
			OrderItem orderItem2 = (OrderItem) iterator.next();
			System.out.println(orderItem2);
		}
		tx.commit();
		session.close();
	}

	@Test
	public void getBookbyOrderItem() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		OrderItem orderItem = (OrderItem) session.get(OrderItem.class,
				"01D2DF3E5BB34E9F9D2477180C8D94D3"); // ��ȡ
		System.out.println(orderItem);
		System.out.println(orderItem.getBook());
		tx.commit();
		session.close();
	}

	@Test
	public void getOrderbyOrderItem() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		OrderItem orderItem = (OrderItem) session.get(OrderItem.class,
				"01D2DF3E5BB34E9F9D2477180C8D94D3"); // ��ȡ
		System.out.println(orderItem);
		System.out.println(orderItem.getOrder());
		tx.commit();
		session.close();
	}
	@Test
	public void getprop() {
		try {
			System.out.println(ConnectionSafer.getProperties("gomeuznepqnfgv"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
