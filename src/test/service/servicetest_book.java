package test.service;




import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.nudt.goods.bean.Book;
import cn.nudt.goods.bean.PageBean;
import cn.nudt.goods.service.BookService;

public class servicetest_book {
	static BookService bookService;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		bookService=(BookService) applicationContext.getBean("bookServiceBean");
	}

	@Test
	public void delete() {
		bookService.delete("abcd");
	}
	
	@Test
	public void edit() {
		Book book=new Book();
		book.setBid("abc");
		book.setAuthor("author2");
		
		bookService.edit(book);
	}
	@Test
	public void findBookCountByCategory() {
		System.out.println(bookService.findBookCountByCategory("5F79D0D246AD4216AC04E9C5FAB3199E"));
	}
	@Test
	public void load() {
		Book book=bookService.load("A3D464D1D1344ED5983920B472826730");
		System.out.println(book);
	}
	
	@Test
	public void findByCategory() {
		for (int i = 1; i <10; i++) {
			PageBean<Book> books=bookService.findByCategory("5F79D0D246AD4216AC04E9C5FAB3199E", i);
			List<Book> booklist=(List<Book>) books.getBeanList();
			System.out.println("");
			System.out.println("第"+i+"页:");
			for (Iterator<Book> iterator = booklist.iterator(); iterator.hasNext();) {
				Book book = (Book) iterator.next();
				System.out.println(book);
				
			}
		}
	}
	@Test
	public void findByAuthor() {
		for (int i = 1; i <10; i++) {
			PageBean<Book> books=bookService.findByAuthor("李刚", i);
			List<Book> booklist=(List<Book>) books.getBeanList();
			System.out.println("");
			System.out.println("第"+i+"页:");
			for (Iterator<Book> iterator = booklist.iterator(); iterator.hasNext();) {
				Book book = (Book) iterator.next();
				System.out.println(book);
				
			}
		}
	}
	@Test
	public void findByBname() {
		PageBean<Book> books=bookService.findByBname("JavaScript经典教程套装", 1);
		List<Book> booklist=(List<Book>) books.getBeanList();
		for (Book book : booklist) {
			System.out.println(book);
		}
	}
	@Test
	public void findByPress() {
		PageBean<Book> books=bookService.findByPress("电子工业出版社", 1);
		List<Book> booklist=(List<Book>) books.getBeanList();
		for (Book book : booklist) {
			System.out.println(book);
		}
	}
	@Test
	public void add() {
		Book book=new Book();
		book.setBid("abcd");
		book.setAuthor("author");
		bookService.add(book);
	}
	@Test
	public void findCategoryByBook() {
		System.out.println(bookService.load("A3D464D1D1344ED5983920B472826730").getCategory());
	}

}
