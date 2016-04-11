package cn.nudt.goods.dao;

import java.util.List;

import cn.nudt.goods.bean.Book;

public interface BookDao {

	public void add(Book book);
	
	public void delete(String bid);
	
	public void update(Book book);
	
	public Book findByBid(String bid);
	
	public List<Book> findByCid(String cid);
	
	public List<Book> findByCid(String cid,String adminId);
	
	public List<Book> findBy(String key, String con);
	
	public List<Book> findBy(String key, String con,String adminId);
	
	public List<Book> findBy(String key, String con, int pc, int ps);
	
	public List<Book> findBy(String key, String con, int pc, int ps,String adminId);
	
}