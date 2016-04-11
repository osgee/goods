package cn.nudt.goods.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.nudt.goods.bean.Book;
import cn.nudt.goods.bean.PageBean;
import cn.nudt.goods.bean.PageConstants;
import cn.nudt.goods.dao.AdminDao;
import cn.nudt.goods.dao.BookDao;
import cn.nudt.goods.service.AdminService;
import cn.nudt.goods.service.BookService;

@Service
@Transactional
public class BookServiceBean implements BookService {
	@Resource
	BookDao bookDao;
	@Resource
	AdminDao adminDao;
	@Resource
	AdminService adminService;
	private final static int ps = PageConstants.BOOK_PAGE_SIZE;

	public void add(Book book) {
		bookDao.add(book);
	}

	public void delete(String... bids) {
		for (String bid : bids) {
			bookDao.delete(bid);
		}
	}

	public void edit(Book book) {
		bookDao.update(book);
	}

	public int findBookCountByCategory(String cid) {
		return bookDao.findByCid(cid).size();
	}

	public int findBookCountByCategory(String cid, String adminId) {
		return bookDao.findByCid(cid, adminId).size();
	}

	public Book load(String bid) {
		return bookDao.findByBid(bid);
	}

	public PageBean<Book> findByCategory(String cid, int pc) {
		PageBean<Book> pageBean = new PageBean<Book>();
		pageBean.setPc(pc);
		pageBean.setPs(ps);
		pageBean.setBeanList(bookDao.findBy("cid", cid, pc, ps));
		int Tr = bookDao.findBy("cid", cid).size();
		pageBean.setTr(Tr == 0 ? 1 : Tr);
		return pageBean;
	}

	public PageBean<Book> findByCategory(String cid, int pc, String adminId) {

		if (adminService.isManager(adminId)) {
			return findByCategory(cid, pc);
		} else {
			PageBean<Book> pageBean = new PageBean<Book>();
			pageBean.setPc(pc);
			pageBean.setPs(ps);
			pageBean.setBeanList(bookDao.findBy("cid", cid, pc, ps, adminId));
			int Tr = bookDao.findBy("cid", cid, adminId).size();
			pageBean.setTr(Tr == 0 ? 1 : Tr);
			return pageBean;
		}
	}

	public PageBean<Book> findByBname(String bname, int pc) {
		PageBean<Book> pageBean = new PageBean<Book>();
		pageBean.setPc(pc);
		pageBean.setPs(ps);
		pageBean.setBeanList(bookDao.findBy("bname", bname, pc, ps));
		int Tr = bookDao.findBy("bname", bname).size();
		pageBean.setTr(Tr == 0 ? 1 : Tr);
		return pageBean;
	}

	public PageBean<Book> findByBname(String bname, int pc, String adminId) {
		if (adminService.isManager(adminId)) {
			return findByBname(bname, pc);
		} else {

			PageBean<Book> pageBean = new PageBean<Book>();
			pageBean.setPc(pc);
			pageBean.setPs(ps);
			pageBean.setBeanList(bookDao
					.findBy("bname", bname, pc, ps, adminId));
			int Tr=bookDao.findBy("bname", bname, adminId).size();
			pageBean.setTr(Tr==0?1:Tr);
			return pageBean;
		}
	}

	public PageBean<Book> findByAuthor(String author, int pc) {
		PageBean<Book> pageBean = new PageBean<Book>();
		pageBean.setPc(pc);
		pageBean.setPs(ps);
		pageBean.setBeanList(bookDao.findBy("author", author, pc, ps));
		int Tr = bookDao.findBy("author", author).size();
		pageBean.setTr(Tr == 0 ? 1 : Tr);
		return pageBean;
	}

	public PageBean<Book> findByAuthor(String author, int pc, String adminId) {
		if (adminService.isManager(adminId)) {
			return findByAuthor(author, pc);
		} else {

			PageBean<Book> pageBean = new PageBean<Book>();
			pageBean.setPc(pc);
			pageBean.setPs(ps);
			pageBean.setBeanList(bookDao.findBy("author", author, pc, ps,
					adminId));
			int Tr = bookDao.findBy("author", author, adminId).size();
			pageBean.setTr(Tr == 0 ? 1 : Tr);
			return pageBean;
		}
	}

	public PageBean<Book> findByPress(String press, int pc) {
		PageBean<Book> pageBean = new PageBean<Book>();
		pageBean.setPc(pc);
		pageBean.setPs(ps);
		pageBean.setBeanList(bookDao.findBy("press", press, pc, ps));
		int Tr = bookDao.findBy("press", press).size();
		pageBean.setTr(Tr == 0 ? 1 : Tr);
		return pageBean;
	}

	public PageBean<Book> findByPress(String press, int pc, String adminId) {
		if (adminService.isManager(adminId)) {
			return findByPress(press, pc);
		} else {

			PageBean<Book> pageBean = new PageBean<Book>();
			pageBean.setPc(pc);
			pageBean.setPs(ps);
			pageBean.setBeanList(bookDao
					.findBy("press", press, pc, ps, adminId));
			int Tr = bookDao.findBy("press", press, adminId).size();
			pageBean.setTr(Tr == 0?1:Tr);
			return pageBean;
		}
	}

	public PageBean<Book> findByAdminId(String adminId, int pc) {
		PageBean<Book> pageBean = new PageBean<Book>();
		pageBean.setPc(pc);
		pageBean.setPs(ps);
		pageBean.setBeanList(bookDao.findBy("adminId", adminId, pc, ps));
		int Tr = bookDao.findBy("adminId", adminId).size();
		pageBean.setTr(Tr==0?1:Tr);
		return pageBean;
	}

	public PageBean<Book> findBy(int pc, String... hints) {
		PageBean<Book> books = new PageBean<>();
		List<Book> bookList = new ArrayList<Book>();
		for (String hint : hints) {
			List<Book> _bookList = bookDao.findBy("bname", hint);
			if (_bookList.size() == 0) {
				_bookList = bookDao.findBy("author", hint);
				if (_bookList.size() == 0) {
					_bookList = bookDao.findBy("press", hint);
					if (_bookList.size() == 0) {
						_bookList = bookDao.findBy("adminname", hint);
					}
				}

			}
			bookList.addAll(_bookList);
		}
		books.setPc(pc);
		books.setPs(ps);
		int Tr=bookList.size();
		books.setTr(Tr==0?1:Tr);

		if (!bookList.isEmpty()) {
			if (bookList.size() > (pc - 1) * ps && bookList.size() < ps * pc) {
				bookList = bookList.subList((pc - 1) * ps, bookList.size());
			} else {

				bookList = bookList.subList((pc - 1) * ps, (pc - 1) * ps + ps);
			}
		}

		books.setBeanList(bookList);
		return books;
	}
}
