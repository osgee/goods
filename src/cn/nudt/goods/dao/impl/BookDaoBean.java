package cn.nudt.goods.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.nudt.goods.bean.Admin;
import cn.nudt.goods.bean.Book;
import cn.nudt.goods.bean.PageConstants;
import cn.nudt.goods.dao.BookDao;

@Repository
@Transactional
public class BookDaoBean implements BookDao {
	private final static int max = 1<<30;
	@Resource
	SessionFactory factory;

	public void add(Book book) {

		factory.getCurrentSession().persist(book);

	}

	public void delete(String bid) {

		factory.getCurrentSession().delete(
				(Book) factory.getCurrentSession().load(Book.class, bid));

	}

	public void update(Book book) {
		factory.getCurrentSession().merge(book);
	}

	public Book findByBid(String bid) {
		Book book = new Book();

		book = (Book) factory.getCurrentSession().load(Book.class, bid);

		return book;
	}

	@SuppressWarnings("unchecked")
	public List<Book> findByCid(String cid) {
		return (List<Book>) factory.getCurrentSession()
				.createQuery("FROM Book WHERE cid=? ORDER BY orderBy ASC")
				.setParameter(0, cid).list();
	}

	@SuppressWarnings("unchecked")
	public List<Book> findBy(String key, String con) {
		List<Book> books = new ArrayList<Book>();
		switch (key) {
		case "cid":
			books = (List<Book>) factory.getCurrentSession()
					.createQuery("FROM Book WHERE cid=? ORDER BY orderBy ASC")
					.setParameter(0, con).list();
			break;
		case "bname":
			books = (List<Book>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Book WHERE bname like ? ORDER BY orderBy ASC")
					.setParameter(0, "%" + con + "%").list();
			break;
		case "author":
			books = (List<Book>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Book WHERE author like ? ORDER BY orderBy ASC")
					.setParameter(0, "%" + con + "%").list();
			break;
		case "press":
			books = (List<Book>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Book WHERE press like ? ORDER BY orderBy ASC")
					.setParameter(0, "%" + con + "%").list();
			break;
		case "adminId":
			books = (List<Book>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Book WHERE adminId=? ORDER BY orderBy ASC")
					.setParameter(0, con).list();
			break;
		case "adminname":
			List<Admin> admins = (List<Admin>) factory.getCurrentSession()
					.createQuery("FROM Admin WHERE adminname like ?")
					.setParameter(0, "%" + con + "%").list();
			for (Admin admin : admins) {
				books.addAll((List<Book>) factory
						.getCurrentSession()
						.createQuery(
								"FROM Book WHERE adminId=? ORDER BY orderBy ASC")
						.setParameter(0, admin.getAdminId()).list());
			}
			break;
		default:
			break;
		}
		return books;
	}

	@SuppressWarnings("unchecked")
	public List<Book> findBy(String key, String con, int pc, int ps) {
		List<Book> books = new ArrayList<Book>();
		switch (key) {
		case "cid":
			books = (List<Book>) factory.getCurrentSession()
					.createQuery("FROM Book WHERE cid=? ORDER BY orderBy ASC")
					.setFirstResult((pc - 1) * ps).setMaxResults(ps)
					.setParameter(0, con).list();
			break;
		case "bname":
			books = (List<Book>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Book WHERE bname like ? ORDER BY orderBy ASC")
					.setFirstResult((pc - 1) * ps).setMaxResults(ps)
					.setParameter(0, "%" + con + "%").list();
			break;
		case "author":
			books = (List<Book>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Book WHERE author like ? ORDER BY orderBy ASC")
					.setFirstResult((pc - 1) * ps).setMaxResults(ps)
					.setParameter(0, "%" + con + "%").list();
			break;
		case "press":
			books = (List<Book>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Book WHERE press like ? ORDER BY orderBy ASC")
					.setFirstResult((pc - 1) * ps).setMaxResults(ps)
					.setParameter(0, "%" + con + "%").list();
			break;
		case "adminId":
			books = (List<Book>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Book WHERE adminId= ? ORDER BY orderBy ASC")
					.setFirstResult((pc - 1) * ps).setMaxResults(ps)
					.setParameter(0, con).list();
			break;
		case "adminname":
			Admin admin = (Admin) factory
					.getCurrentSession()
					.createQuery(
							"FROM Admin WHERE adminname=? ORDER BY orderBy ASC")
					.setParameter(0, con).uniqueResult();
			books = (List<Book>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Book WHERE adminId=? ORDER BY orderBy ASC")
					.setFirstResult((pc - 1) * ps).setMaxResults(ps)
					.setParameter(0, admin.getAdminId()).list();
			break;
		default:
			break;
		}
		return books;
	}

	@SuppressWarnings("unchecked")
	public List<Book> findByCid(String cid, String adminId) {
		return (List<Book>) factory
				.getCurrentSession()
				.createQuery(
						"FROM Book WHERE cid=? AND adminId=? ORDER BY orderBy ASC")
				.setParameter(0, cid).setParameter(1, adminId).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> findBy(String key, String con, int pc, int ps,
			String adminId) {
		List<Book> books = new ArrayList<Book>();
		switch (key) {
		case "cid":
			books = (List<Book>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Book WHERE cid=? AND adminId=? ORDER BY orderBy ASC")
					.setFirstResult((pc - 1) * ps).setMaxResults(ps)
					.setParameter(0, con).setParameter(1, adminId).list();
			break;
		case "bname":
			books = (List<Book>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Book WHERE bname like ? AND adminId=? ORDER BY orderBy ASC")
					.setFirstResult((pc - 1) * ps).setMaxResults(ps)
					.setParameter(0, "%" + con + "%").setParameter(1, adminId)
					.list();
			break;
		case "author":
			books = (List<Book>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Book WHERE author like ? AND adminId=? ORDER BY orderBy ASC")
					.setFirstResult((pc - 1) * ps).setMaxResults(ps)
					.setParameter(0, "%" + con + "%").setParameter(1, adminId)
					.list();
			break;
		case "press":
			books = (List<Book>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Book WHERE press like ? AND adminId=? ORDER BY orderBy ASC")
					.setFirstResult((pc - 1) * ps).setMaxResults(ps)
					.setParameter(0, "%" + con + "%").setParameter(1, adminId)
					.list();
			break;
		case "adminId":
			books = (List<Book>) factory
					.getCurrentSession()
					.createQuery(
							"FROM Book WHERE adminId=? ORDER BY orderBy ASC")
					.setFirstResult((pc - 1) * ps).setMaxResults(ps)
					.setParameter(0, adminId).list();
			break;
		default:
			break;
		}
		return books;
	}

	@Override
	public List<Book> findBy(String key, String con, String adminId) {
		
		return findBy( key,  con,  1,  max, adminId);
	}
}
