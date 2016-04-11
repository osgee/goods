package cn.nudt.goods.dao;

import java.util.List;

import cn.nudt.goods.bean.Order;

public interface OrderDao {

	public void add(Order order);

	public void delete(String oid);

	public void update(Order order);

	public Order find(String oid);

	public List<Order> findBy(String key, String con);
	
	public List<Order> findBy(String key, String con, int pc, int ps) ;

	public List<Order> findAll();
	
	public List<Order> findAll(int pc, int ps);
	
	public List<Order> findBy(String key, String con,String adminId);
	
	public List<Order> findBy(String key, String con, int pc, int ps,String adminId) ;
	
	public List<Order> findAll(String adminId);
	
	public List<Order> findAll(int pc, int ps, String adminId);

}