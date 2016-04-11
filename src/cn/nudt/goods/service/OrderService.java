package cn.nudt.goods.service;

import cn.nudt.goods.bean.Order;
import cn.nudt.goods.bean.PageBean;

public interface OrderService {

	public void updateStatus(String oid, int status);

	public int findStatus(String oid);

	public Order load(String oid);

	public void createOrder(Order order);

	public PageBean<Order> findByStatus(int status, int pc);

	public PageBean<Order> myOrders(String uid, int pc);

	public PageBean<Order> findAll(int pc);

	public PageBean<Order> findByStatus(int status, int pc, String adminId);

	public PageBean<Order> myOrders(int pc, String adminId);

	public PageBean<Order> findAll(int pc, String adminId);

}