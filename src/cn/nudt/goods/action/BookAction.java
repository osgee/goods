package cn.nudt.goods.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import cn.nudt.goods.bean.Book;
import cn.nudt.goods.bean.PageBean;
import cn.nudt.goods.service.BookService;

@Controller
public class BookAction {
	@Resource
	BookService bookService;
	private String cid;
	private String bid;
	private String author;
	private String press;
	private String bname;
	
	private int getPc() {
		HttpServletRequest req = ServletActionContext.getRequest();
		int pc = 1;
		String param = req.getParameter("pc");
		if (param != null && !param.trim().isEmpty()) {
			try {
				pc = Integer.parseInt(param);
			} catch (RuntimeException e) {
			}
		}
		return pc;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	private String getUrl(HttpServletRequest req) {
		String url = req.getRequestURI() + "?" + req.getQueryString();
		int index = url.lastIndexOf("&pc=");
		if (index != -1) {
			url = url.substring(0, index);
		}
		return url;
	}

	public String load() {
		HttpServletRequest req = ServletActionContext.getRequest();
		Book book = bookService.load(bid);
		req.setAttribute("book", book);
		return "bookdesc";
	}

	public String findByCategory() {
		HttpServletRequest req = ServletActionContext.getRequest();
		PageBean<Book> pb = bookService.findByCategory(cid, getPc());
		String url = getUrl(req);
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "success";
	}
	/**
	 * 按作者查
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByAuthor() {
		HttpServletRequest req = ServletActionContext.getRequest();
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc();
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String author = req.getParameter("author");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByAuthor(author, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "success";
	}

	/**
	 * 按出版社查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByPress() {
		HttpServletRequest req = ServletActionContext.getRequest();
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc();
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String press = req.getParameter("press");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByPress(press, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "success";
	}

	public String findByBname() {
		HttpServletRequest req = ServletActionContext.getRequest();
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc();
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String bname = req.getParameter("bname");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByBname(bname, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "success";
	}
	
	public String findByAdminId() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String adminId= (String) req.getParameter("adminId");
		PageBean<Book> pb = bookService.findByAdminId(adminId, getPc());
		String url = getUrl(req);
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "success";
	}

	public String findBy() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String hints=req.getParameter("bname");
		if (hints.trim().equalsIgnoreCase("")) {
			return "faillist";
		}else {
			PageBean<Book> pb = bookService.findBy(getPc(), hints.split(" "));
			String url = getUrl(req);
			pb.setUrl(url);
			req.setAttribute("pb", pb);
			return "success";
		}
	}

}
