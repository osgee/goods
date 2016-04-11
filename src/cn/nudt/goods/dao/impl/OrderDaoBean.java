package cn.nudt.goods.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.nudt.goods.bean.Order;
import cn.nudt.goods.bean.OrderItem;
import cn.nudt.goods.dao.OrderDao;

@Repository
@Transactional
public class OrderDaoBean implements OrderDao {
	@Resource
	SessionFactory factory;

	public void add(Order order) {
		factory.getCurrentSession().persist(order);

	}

	public void delete(String oid) {
		factory.getCurrentSession().delete(
				(Order) factory.getCurrentSession().load(Order.class, oid));
	}

	public void update(Order order) {
		factory.getCurrentSession().merge(order);
	}

	public Order find(String oid) {
		Order order = (Order) factory.getCurrentSession()
				.load(Order.class, oid);
		order = loadOrderItem(order);
		return order;
	}

	@SuppressWarnings("unchecked")
	public List<Order> findBy(String key, String con) {
		List<Order> orders = new ArrayList<Order>();
		switch (key) {
		case "uid":
			orders = (List<Order>) factory
					.getCurrentSession()
					.createQuery("FROM Order WHERE uid=? ORDER BY orderBy DESC")
					.setParameter(0, con).list();
			break;
		case "adminId":
			orders = (List<Order>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Order WHERE adminId=? ORDER BY orderBy DESC")
					.setParameter(0, con).list();
			break;
		case "status":
			orders = (List<Order>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Order WHERE status=? ORDER BY orderBy DESC")
					.setParameter(0, Integer.parseInt(con)).list();
			break;

		default:
			orders = (List<Order>) factory
					.getCurrentSession()
					.createQuery("FROM Order WHERE uid=? ORDER BY orderBy DESC")
					.setParameter(0, con).list();
			break;
		}

		for (Order order : orders) {
			order = loadOrderItem(order);
		}
		return orders;
	}

	@SuppressWarnings("unchecked")
	public List<Order> findBy(String key, String con, int pc, int ps) {
		List<Order> orders = new ArrayList<Order>();
		switch (key) {
		case "uid":
			orders = (List<Order>) factory
					.getCurrentSession()
					.createQuery("FROM Order WHERE uid=? ORDER BY orderBy DESC")
					.setFirstResult((pc - 1) * ps).setMaxResults(ps)
					.setParameter(0, con).list();
			break;
		case "adminId":
			orders = (List<Order>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Order WHERE adminId=? ORDER BY orderBy DESC")
					.setFirstResult((pc - 1) * ps).setMaxResults(ps)
					.setParameter(0, con).list();
			break;
		case "status":
			orders = (List<Order>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Order WHERE status=? ORDER BY orderBy DESC")
					.setFirstResult((pc - 1) * ps).setMaxResults(ps)
					.setParameter(0, Integer.parseInt(con)).list();
			break;
		default:
			orders = (List<Order>) factory
					.getCurrentSession()
					.createQuery("FROM Order WHERE uid=? ORDER BY orderBy DESC")
					.setFirstResult((pc - 1) * ps).setMaxResults(ps)
					.setParameter(0, con).list();
			break;
		}

		for (Order order : orders) {
			order = loadOrderItem(order);
		}
		return orders;
	}

	@SuppressWarnings("unchecked")
	public List<Order> findAll() {
		List<Order> orders = (List<Order>) factory.getCurrentSession()
				.createQuery("FROM Order ORDER BY orderBy DESC").list();
		for (Order order : orders) {
			order = loadOrderItem(order);
		}
		return orders;
	}

	@SuppressWarnings("unchecked")
	public List<Order> findAll(int pc, int ps) {
		List<Order> orders = (List<Order>) factory.getCurrentSession()
				.createQuery("FROM Order ORDER BY orderBy DESC")
				.setFirstResult((pc - 1) * ps).setMaxResults(ps).list();
		for (Order order : orders) {
			order = loadOrderItem(order);
		}
		return orders;
	}

	private Order loadOrderItem(Order order) {
		@SuppressWarnings("unchecked")
		List<OrderItem> orderItemList = (List<OrderItem>) factory
				.getCurrentSession()
				.createQuery("FROM OrderItem WHERE oid=? ORDER BY orderBy DESC")
				.setParameter(0, order.getOid()).list();
		order.setOrderItemList(orderItemList);
		return order;
	}

	@SuppressWarnings("unchecked")
	public List<Order> findBy(String key, String con, String adminId) {
		List<Order> orders = new ArrayList<Order>();
		switch (key) {
		case "uid":
			orders = (List<Order>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Order WHERE uid=? AND adminId=? ORDER BY orderBy DESC")
					.setParameter(0, con).setParameter(1, adminId).list();
			break;
		case "adminId":
			orders = (List<Order>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Order WHERE adminId=? ORDER BY orderBy DESC")
					.setParameter(0, adminId).list();
			break;
		case "status":
			orders = (List<Order>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Order WHERE status=? AND adminId=? ORDER BY orderBy DESC")
					.setParameter(0, Integer.parseInt(con.trim()))
					.setParameter(1, adminId).list();
			break;

		default:
			break;
		}

		for (Order order : orders) {
			order = loadOrderItem(order);
		}
		return orders;
	}

	@SuppressWarnings("unchecked")
	public List<Order> findBy(String key, String con, int pc, int ps,
			String adminId) {
		List<Order> orders = new ArrayList<Order>();
		switch (key) {
		case "uid":
			orders = (List<Order>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Order WHERE uid=? AND adminId=? ORDER BY orderBy DESC")
					.setFirstResult((pc - 1) * ps).setMaxResults(ps)
					.setParameter(0, con).setParameter(1, adminId).list();
			break;
		case "adminId":
			orders = (List<Order>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Order WHERE adminId=? ORDER BY orderBy DESC")
					.setFirstResult((pc - 1) * ps).setMaxResults(ps)
					.setParameter(0, adminId).list();
			break;
		case "status":
			orders = (List<Order>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Order WHERE status=? AND adminId=? ORDER BY orderBy DESC")
					.setFirstResult((pc - 1) * ps).setMaxResults(ps)
					.setParameter(0, Integer.parseInt(con)).setParameter(1, adminId).list();
			break;
		default:
			break;
		}

		for (Order order : orders) {
			order = loadOrderItem(order);
		}
		return orders;
	}

	@SuppressWarnings("unchecked")
	public List<Order> findAll(String adminId) {
		List<Order> orders = (List<Order>) factory
				.getCurrentSession()
				.createQuery("FROM Order WHERE adminId=? ORDER BY orderBy DESC")
				.setParameter(0, adminId).list();
		for (Order order : orders) {
			order = loadOrderItem(order);
		}
		return orders;
	}

	@Override
	public List<Order> findAll(int pc, int ps, String adminId) {
		@SuppressWarnings("unchecked")
		List<Order> orders = (List<Order>) factory
				.getCurrentSession()
				.createQuery("FROM Order WHERE adminId=? ORDER BY orderBy DESC")
				.setFirstResult((pc - 1) * ps).setMaxResults(ps)
				.setParameter(0, adminId).list();
		for (Order order : orders) {
			order = loadOrderItem(order);
		}
		return orders;
	}
}
