package cn.nudt.goods.service;

import cn.nudt.goods.bean.Book;
import cn.nudt.goods.bean.PageBean;

public interface BookService {

	public void delete(String... bid);

	public void edit(Book book);

	public int findBookCountByCategory(String cid);

	public Book load(String bid);

	public PageBean<Book> findByCategory(String cid, int pc);
	
	public PageBean<Book> findByBname(String bname, int pc);
	
	public PageBean<Book> findByAuthor(String author, int pc);
	
	public PageBean<Book> findByPress(String press, int pc);
	
	public int findBookCountByCategory(String cid,String adminId);
	
	public PageBean<Book> findByCategory(String cid, int pc,String adminId);

	public PageBean<Book> findByBname(String bname, int pc,String adminId);

	public PageBean<Book> findByAuthor(String author, int pc,String adminId);

	public PageBean<Book> findByPress(String press, int pc,String adminId);
	
	public PageBean<Book> findByAdminId(String adminId, int pc);

	public void add(Book book);

	public PageBean<Book> findBy(int pc, String... hints);

}