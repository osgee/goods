package cn.nudt.goods.dao;

import cn.nudt.goods.bean.OrderItem;

public interface OrderItemDao {

	public void add(OrderItem orderItem);

	public void delete(String orderItemId);

	public OrderItem find(String orderItemId);

}