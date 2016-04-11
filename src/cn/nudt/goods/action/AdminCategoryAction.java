package cn.nudt.goods.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import cn.itcast.commons.CommonUtils;
import cn.nudt.goods.bean.Admin;
import cn.nudt.goods.bean.Category;
import cn.nudt.goods.service.AdminService;
import cn.nudt.goods.service.BookService;
import cn.nudt.goods.service.CategoryService;

import com.opensymphony.xwork2.ActionSupport;

@Controller
public class AdminCategoryAction extends ActionSupport {
	@Resource
	CategoryService categoryService;
	@Resource
	AdminService adminService;
	@Resource
	BookService bookService;
	
	public String findAll() {
		List<Category> parents = categoryService.findParents();
		ServletActionContext.getServletContext().setAttribute("parents", parents);
		return "success";
	}



	/**
	 * 添加一级分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addParent() {
		HttpServletRequest req = ServletActionContext.getRequest();
		/*
		 * 1. 封装表单数据到Category中 2. 调用service的add()方法完成添加 3.
		 * 调用findAll()，返回list.jsp显示所有分类
		 */
		Category parent = CommonUtils.toBean(req.getParameterMap(),
				Category.class);
		parent.setCid(CommonUtils.uuid());// 设置cid
		categoryService.add(parent);
		return "success";
	}

	public String addChild() {
		HttpServletRequest req = ServletActionContext.getRequest();
		/*
		 * 1. 封装表单数据到Category中 2. 需要手动的把表单中的pid映射到child对象中 2.
		 * 调用service的add()方法完成添加 3. 调用findAll()，返回list.jsp显示所有分类
		 */
		Category child = CommonUtils.toBean(req.getParameterMap(),
				Category.class);
		child.setCid(CommonUtils.uuid());// 设置cid

		// 手动映射pid
		String pid = req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);

		categoryService.add(child);
		return "showlist";
	}

	/**
	 * 添加第二分类：第一步
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addChildPre() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String pid = req.getParameter("pid");// 当前点击的父分类id
		List<Category> parents = categoryService.findParents();
		req.setAttribute("pid", pid);
		req.setAttribute("parents", parents);

		return "addchild";
	}

	/**
	 * 修改一级分类：第一步
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParentPre() {
		HttpServletRequest req = ServletActionContext.getRequest();
		/*
		 * 1. 获取链接中的cid 2. 使用cid加载Category 3. 保存Category 4.
		 * 转发到edit.jsp页面显示Category
		 */
		String cid = req.getParameter("cid");
		Category parent = categoryService.load(cid);
		req.setAttribute("parent", parent);
		return "editparent";
	}

	/**
	 * 修改一级分类：第二步
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParent() {
		HttpServletRequest req = ServletActionContext.getRequest();
		/*
		 * 1. 封装表单数据到Category中 2. 调用service方法完成修改 3. 转发到list.jsp显示所有分类（return
		 * findAll();）
		 */
		Category parent = CommonUtils.toBean(req.getParameterMap(),
				Category.class);
		categoryService.edit(parent);
		return "showlist";
	}

	/**
	 * 修改二级分类：第一步
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChildPre() {
		HttpServletRequest req = ServletActionContext.getRequest();
		/*
		 * 1. 获取链接参数cid，通过cid加载Category，保存之 2. 查询出所有1级分类，保存之 3. 转发到edit2.jsp
		 */
		String cid = req.getParameter("cid");
		Category child = categoryService.load(cid);
		req.setAttribute("child", child);
		req.setAttribute("parents", categoryService.findParents());

		return "editchild";
	}

	/**
	 * 修改二级分类：第二步
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChild() {
		HttpServletRequest req = ServletActionContext.getRequest();
		/*
		 * 1. 封装表单参数到Category child 2. 把表单中的pid封装到child, ... 3.
		 * 调用service.edit()完成修改 4. 返回到list.jsp
		 */
		Category child = CommonUtils.toBean(req.getParameterMap(),
				Category.class);
		String pid = req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);

		categoryService.edit(child);
		return "showlist";
	}

	/**
	 * 删除一级分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteParent() {
		HttpServletRequest req = ServletActionContext.getRequest();
		/*
		 * 1. 获取链接参数cid，它是一个1级分类的id 2. 通过cid，查看该父分类下子分类的个数 3.
		 * 如果大于零，说明还有子分类，不能删除。保存错误信息，转发到msg.jsp 4. 如果等于零，删除之，返回到list.jsp
		 */
		String cid = req.getParameter("cid");
		int cnt = categoryService.findChildrenCountByParent(cid);
		if (cnt > 0) {
			req.setAttribute("msg", "该分类下还有子分类，不能删除！");
			return "message";
		} else {
			categoryService.delete(cid);
			return "showlist";
		}
	}

	/**
	 * 删除2级分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteChild() {
		HttpServletRequest req = ServletActionContext.getRequest();
		/*
		 * 1. 获取cid，即2级分类id 2. 获取该分类下的图书个数 3. 如果大于零，保存错误信息，转发到msg.jsp 4.
		 * 如果等于零，删除之，返回到list.jsp
		 */
		String cid = req.getParameter("cid");
		int cnt = bookService.findBookCountByCategory(cid);
		if (cnt > 0) {
			req.setAttribute("msg", "该分类下还存在图书，不能删除！");
			return "message";
		} else {
			categoryService.delete(cid);
			return "showlist";
		}
	}
	
	public void validate() {
		HttpServletRequest req=ServletActionContext.getRequest();
		Admin sessionAdmin=(Admin) req.getSession().getAttribute("sessionAdmin");
		if (sessionAdmin==null|adminService.login(sessionAdmin)==null) {
			this.addFieldError("msg", "请登陆后再操作！");
			req.setAttribute("msg", "请登陆后再操作！");
		}else if (!adminService.isManager(sessionAdmin.getAdminId())) {
			this.addFieldError("msg", "请登陆管理员账号后再操作！");
			req.setAttribute("msg", "请登陆管理员账号后再操作！");
		}
	}
}
