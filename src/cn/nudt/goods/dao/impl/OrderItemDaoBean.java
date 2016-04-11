package cn.nudt.goods.dao.impl;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.nudt.goods.bean.OrderItem;
import cn.nudt.goods.dao.OrderItemDao;

@Repository
@Transactional
public class OrderItemDaoBean implements OrderItemDao {
	@Resource
	SessionFactory factory;

	public void add(OrderItem orderItem) {
		factory.getCurrentSession().persist(orderItem);
	}

	public void delete(String orderItemId) {
		factory.getCurrentSession().delete(
				(OrderItem) factory.getCurrentSession().load(OrderItem.class,
						orderItemId));
	}

	public OrderItem find(String orderItemId) {
		OrderItem orderItem = (OrderItem) factory.getCurrentSession().load(
				OrderItem.class, orderItemId);
		return orderItem;
	}

}
