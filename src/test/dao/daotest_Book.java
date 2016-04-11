package test.dao;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.nudt.goods.bean.Book;
import cn.nudt.goods.dao.BookDao;

public class daotest_Book {
	static BookDao bookDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		bookDao=(BookDao) applicationContext.getBean("bookDaoBean");
	}
	

	@Test
	public void add() {
		Book book=new Book();
		book.setBid("aaaaaaa");
		book.setAuthor("ffffftttt");
		bookDao.add(book);
		System.out.println("fdsfds");
	}
	@Test
	public void delete() {
		bookDao.delete("aaaaaaa");
		System.out.println("fdsfds");
	}
	@Test
	public void find() {
		List< Book> books=bookDao.findBy("bname","Java web");
		for (Book book : books) {
			System.out.println(book);
		}
	}
	@Test
	public void findByBid() {
		List< Book> books=bookDao.findByBid("000A18FDB38F470DBE9CD0972BADB23F","230A00EC22BF4A1DBA87C64800B54C8D");
		for (Book book : books) {
			System.out.println(book);
		}
	}
	@Test
	public void findByCid() {
		List< Book> books=bookDao.findByCid("5F79D0D246AD4216AC04E9C5FAB3199E");
		for (Book book : books) {
			System.out.println(book);
		}
	}

}
