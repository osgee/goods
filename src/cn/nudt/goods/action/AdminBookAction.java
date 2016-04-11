package cn.nudt.goods.action;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import cn.itcast.commons.CommonUtils;
import cn.nudt.goods.bean.Admin;
import cn.nudt.goods.bean.Book;
import cn.nudt.goods.bean.Category;
import cn.nudt.goods.bean.PageBean;
import cn.nudt.goods.service.AdminService;
import cn.nudt.goods.service.BookService;
import cn.nudt.goods.service.CategoryService;

import com.opensymphony.xwork2.ActionSupport;

@Controller
public class AdminBookAction extends ActionSupport {
	@Resource
	BookService bookService;
	@Resource
	CategoryService categoryService;
	@Resource
	AdminService adminService;

	public String delete() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String bid = req.getParameter("bid");

		/*
		 * 删除图片
		 */
		Book book = bookService.load(bid);
		String savepath = ServletActionContext.getServletContext().getRealPath(
				"/");// 获取真实的路径
		new File(savepath, book.getImage_w()).delete();// 删除文件
		new File(savepath, book.getImage_b()).delete();// 删除文件

		bookService.delete(bid);// 删除数据库的记录

		req.setAttribute("msg", "删除图书成功！");
		return "message";
	}

	public String edit() {
		HttpServletRequest req = ServletActionContext.getRequest();
		/*
		 * 1. 把表单数据封装到Book对象中 2. 封装cid到Category中 3. 把Category赋给Book 4.
		 * 调用service完成工作 5. 保存成功信息，转发到msg.jsp
		 */
		Map map = req.getParameterMap();
		Book book = CommonUtils.toBean(map, Book.class);
		Category category = CommonUtils.toBean(map, Category.class);
		Admin admin=CommonUtils.toBean(map, Admin.class);
		book.setCategory(category);
		bookService.edit(book);
		req.setAttribute("msg", "修改图书成功！");
		return "message";
	}

	public String load() {
		HttpServletRequest req = ServletActionContext.getRequest();
		/*
		 * 1. 获取bid，得到Book对象，保存之
		 */
		String bid = req.getParameter("bid");
		Book book = bookService.load(bid);
		req.setAttribute("book", book);

		/*
		 * 2. 获取所有一级分类，保存之
		 */
		req.setAttribute("parents", categoryService.findParents());
		/*
		 * 3. 获取当前图书所属的一级分类下所有2级分类
		 */
		String pid = book.getCategory().getParent().getCid();
		req.setAttribute("children", categoryService.findChildren(pid));

		/*
		 * 4. 转发到desc.jsp显示
		 */
		return "bookdesc";
	}

	public String addPre() {
		HttpServletRequest req = ServletActionContext.getRequest();
		/*
		 * 1. 获取所有一级分类，保存之 2. 转发到add.jsp，该页面会在下拉列表中显示所有一级分类
		 */
		List<Category> parents = categoryService.findParents();
		req.setAttribute("parents", parents);
		return "bookpre";
	}

	public String ajaxFindChildren() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		/*
		 * 1. 获取pid 2. 通过pid查询出所有2级分类 3. 把List<Category>转换成json，输出给客户端
		 */
		String pid = req.getParameter("pid");
		List<Category> children = categoryService.findChildren(pid);
		String json = toJson(children);
		try {
			resp.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// {"cid":"fdsafdsa", "cname":"fdsafdas"}
	private String toJson(Category category) {
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"cid\"").append(":").append("\"").append(category.getCid())
				.append("\"");
		sb.append(",");
		sb.append("\"cname\"").append(":").append("\"")
				.append(category.getCname()).append("\"");
		sb.append("}");
		return sb.toString();
	}

	// [{"cid":"fdsafdsa", "cname":"fdsafdas"}, {"cid":"fdsafdsa",
	// "cname":"fdsafdas"}]
	private String toJson(List<Category> categoryList) {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < categoryList.size(); i++) {
			sb.append(toJson(categoryList.get(i)));
			if (i < categoryList.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public String findCategoryAll() {
		List<Category> parents = categoryService.findParents();
		ServletActionContext.getServletContext().setAttribute("parents",
				parents);
		return "category";
	}

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

	/**
	 * 截取url，页面中的分页导航中需要使用它做为超链接的目标！
	 * 
	 * @param req
	 * @return
	 */
	/*
	 * http://localhost:8080/goods/BookServlet?methed=findByCategory&cid=xxx&pc=3
	 * /goods/BookServlet + methed=findByCategory&cid=xxx&pc=3
	 */
	private String getUrl() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String url = req.getRequestURI() + "?" + req.getQueryString();
		/*
		 * 如果url中存在pc参数，截取掉，如果不存在那就不用截取。
		 */
		int index = url.lastIndexOf("&pc=");
		if (index != -1) {
			url = url.substring(0, index);
		}
		return url;
	}

	/**
	 * 按分类查
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCategory() {
		HttpServletRequest req = ServletActionContext.getRequest();
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc();
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl();
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String cid = req.getParameter("cid");

		Admin sessionAdmin = (Admin) req.getSession().getAttribute(
				"sessionAdmin");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */

		PageBean<Book> pb = bookService.findByCategory(cid, pc,
				sessionAdmin.getAdminId());
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
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
		String url = getUrl();
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String author = req.getParameter("author");

		Admin sessionAdmin = (Admin) req.getSession().getAttribute(
				"sessionAdmin");
		/*
		 * 4. 使用pc和cid调用service#author得到PageBean
		 */
		PageBean<Book> pb = bookService.findByAuthor(author, pc,
				sessionAdmin.getAdminId());
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
		String url = getUrl();
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String press = req.getParameter("press");

		Admin sessionAdmin = (Admin) req.getSession().getAttribute(
				"sessionAdmin");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByPress(press, pc,
				sessionAdmin.getAdminId());
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
		String url = getUrl();
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String bname = req.getParameter("bname");

		Admin sessionAdmin = (Admin) req.getSession().getAttribute(
				"sessionAdmin");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByBname(bname, pc,
				sessionAdmin.getAdminId());
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "success";
	}

	public String findByAdminId() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String adminId = (String) req.getParameter("adminId");
		PageBean<Book> pb = bookService.findByAdminId(adminId, getPc());
		String url = getUrl();
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "success";
	}

	public void validate() {
		HttpServletRequest req = ServletActionContext.getRequest();
		Admin sessionAdmin = (Admin) req.getSession().getAttribute(
				"sessionAdmin");
		String bid = req.getParameter("bid");
		Book book;
		if (bid == null) {
			book = null;
		} else
			book = bookService.load(bid);
		if (sessionAdmin == null | adminService.login(sessionAdmin) == null) {
			this.addFieldError("msg", "请登陆后再操作！");
			req.setAttribute("msg", "请登陆后再操作！");
		} else if (book != null) {
			if (!adminService.isManager(sessionAdmin.getAdminId())
					& !book.getAdmin().getAdminId()
							.equalsIgnoreCase(sessionAdmin.getAdminId())) {
				this.addFieldError("msg", "您无权限进行此操作操作！");
				req.setAttribute("msg", "您无权限进行此操作操作！");
			}
		}
	}

	public void validateEdit() {
		HttpServletRequest req = ServletActionContext.getRequest();
		Admin sessionAdmin = (Admin) req.getSession().getAttribute(
				"sessionAdmin");
		String bid = req.getParameter("bid");
		Book book;
		if (bid == null) {
			book = null;
		} else
			book = bookService.load(bid);
		if (adminService.isManager(sessionAdmin.getAdminId())) {
			this.addFieldError("msg", "管理员无权修改卖家的图书，但可以删除该图书，或联系卖家！");
			req.setAttribute("msg", "管理员无权修改卖家的图书，但可以删除该图书，或联系卖家！");
		}
	}

}
