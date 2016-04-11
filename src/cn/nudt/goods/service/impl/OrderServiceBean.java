package cn.nudt.goods.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.nudt.goods.bean.Order;
import cn.nudt.goods.bean.OrderItem;
import cn.nudt.goods.bean.PageBean;
import cn.nudt.goods.bean.PageConstants;
import cn.nudt.goods.dao.BookDao;
import cn.nudt.goods.dao.OrderDao;
import cn.nudt.goods.dao.OrderItemDao;
import cn.nudt.goods.service.AdminService;
import cn.nudt.goods.service.OrderService;

@Service
@Transactional
public class OrderServiceBean implements OrderService {
	@Resource
	OrderDao orderDao;
	@Resource
	OrderItemDao orderItemDao;
	@Resource
	BookDao bookDao;
	@Resource
	AdminService adminService;
	final int ps = PageConstants.ORDER_PAGE_SIZE;

	public void updateStatus(String oid, int status) {
		Order order = orderDao.find(oid);
		order.setStatus(status);
		orderDao.update(order);
	}

	public Order load(String oid) {
		return orderDao.find(oid);
	}

	public void createOrder(Order order) {
		orderDao.add(order);
		List<OrderItem> orderItems = order.getOrderItemList();
		for (OrderItem orderItem : orderItems) {
			orderItemDao.add(orderItem);
		}
	}

	public int findStatus(String oid) {
		return orderDao.find(oid).getStatus();
	}

	public PageBean<Order> myOrders(String uid, int pc) {
		PageBean<Order> pageBean = new PageBean<Order>();
		pageBean.setPc(pc);
		pageBean.setPs(ps);
		pageBean.setBeanList(orderDao.findBy("uid", uid, pc, ps));
		int Tr = orderDao.findBy("uid", uid).size();
		pageBean.setTr(Tr == 0 ? 1 : Tr);
		return pageBean;

	}

	public PageBean<Order> findAll(int pc) {
		PageBean<Order> orders = new PageBean<Order>();
		orders.setPc(pc);
		orders.setPs(ps);
		orders.setBeanList(orderDao.findAll(pc, ps));
		int Tr = orderDao.findAll().size();
		orders.setTr(Tr == 0 ? 1 : Tr);
		return orders;
	}

	public PageBean<Order> findByStatus(int status, int pc) {
		PageBean<Order> pageBean = new PageBean<Order>();
		pageBean.setPc(pc);
		pageBean.setPs(ps);
		pageBean.setBeanList(orderDao.findBy("status",
				Integer.toString(status), pc, ps));
		int Tr = orderDao.findBy("status", Integer.toString(status)).size();
		pageBean.setTr(Tr == 0 ? 1 : Tr);
		return pageBean;
	}

	@Override
	public PageBean<Order> findByStatus(int status, int pc, String adminId) {
		if (adminService.isManager(adminId)) {
			return findByStatus(status, pc);
		} else {

			PageBean<Order> pageBean = new PageBean<Order>();
			pageBean.setPc(pc);
			pageBean.setPs(ps);
			pageBean.setBeanList(orderDao.findBy("status",
					Integer.toString(status), pc, ps, adminId));
			int Tr = orderDao.findBy("status", Integer.toString(status),
					adminId).size();
			pageBean.setTr(Tr==0?1:Tr);
			return pageBean;
		}
	}

	@Override
	public PageBean<Order> myOrders(int pc, String adminId) {
		if (adminService.isManager(adminId)) {
			return findAll(pc);
		} else {

			PageBean<Order> pageBean = new PageBean<Order>();
			pageBean.setPc(pc);
			pageBean.setPs(ps);
			pageBean.setBeanList(orderDao.findBy("adminId", adminId, pc, ps));
			int Tr=orderDao.findBy("adminId", "", adminId).size();
			pageBean.setTr(Tr==0?1:Tr);
			return pageBean;
		}
	}

	@Override
	public PageBean<Order> findAll(int pc, String adminId) {
		if (adminService.isManager(adminId)) {
			return findAll(pc);
		} else {

			PageBean<Order> orders = new PageBean<Order>();
			orders.setPc(pc);
			orders.setPs(ps);
			orders.setBeanList(orderDao.findAll(pc, ps, adminId));
			int Tr=orderDao.findAll(adminId).size();
			orders.setTr(Tr==0?1:Tr);
			return orders;
		}
	}

}
