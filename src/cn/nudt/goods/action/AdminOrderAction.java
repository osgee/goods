package cn.nudt.goods.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.nudt.goods.bean.Admin;
import cn.nudt.goods.bean.Order;
import cn.nudt.goods.bean.PageBean;
import cn.nudt.goods.service.AdminService;
import cn.nudt.goods.service.OrderService;

@Controller
public class AdminOrderAction extends ActionSupport {
	@Resource
	OrderService orderService;
	@Resource
	AdminService adminService;

	/**
	 * 获取当前页码
	 * 
	 * @param req
	 * @return
	 */
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
	 * 查看所有订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll() {
		HttpServletRequest req = ServletActionContext.getRequest();
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc();
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl();

		Admin sessionAdmin = (Admin) req.getSession().getAttribute(
				"sessionAdmin");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Order> pb = orderService
				.findAll(pc, sessionAdmin.getAdminId());
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "success";
	}

	/**
	 * 按状态查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByStatus() {
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
		 * 3. 获取链接参数：status
		 */
		int status = Integer.parseInt(req.getParameter("status"));

		Admin sessionAdmin = (Admin) req.getSession().getAttribute(
				"sessionAdmin");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Order> pb = orderService.findByStatus(status, pc,
				sessionAdmin.getAdminId());
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "success";
	}

	/**
	 * 查看订单详细信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String oid = req.getParameter("oid");
		Order order = orderService.load(oid);
		req.setAttribute("order", order);
		String btn = req.getParameter("btn");// btn说明了用户点击哪个超链接来访问本方法的
		req.setAttribute("btn", btn);
		return "orderdesc";
	}

	/**
	 * 取消订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String cancel() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String oid = req.getParameter("oid");
		/*
		 * 校验订单状态
		 */
		int status = orderService.findStatus(oid);
		if (status != 1) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能取消！");
			return "message";
		}
		orderService.updateStatus(oid, 5);// 设置状态为取消！
		req.setAttribute("code", "success");
		req.setAttribute("msg", "您的订单已取消，您不后悔吗！");
		return "message";
	}

	/**
	 * 发货功能
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deliver() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String oid = req.getParameter("oid");
		/*
		 * 校验订单状态
		 */
		int status = orderService.findStatus(oid);
		if (status != 2) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能发货！");
			return "message";
		}
		orderService.updateStatus(oid, 3);// 设置状态为取消！
		req.setAttribute("code", "success");
		req.setAttribute("msg", "您的订单已发货，请查看物流，马上确认吧！");
		return "message";
	}

	public void validate() {
		HttpServletRequest req = ServletActionContext.getRequest();
		Admin sessionAdmin = (Admin) req.getSession().getAttribute(
				"sessionAdmin");
		String oid = req.getParameter("oid");
		Order order;
		if (oid == null) {
			order = null;
		} else
			order = orderService.load(oid);
		if (sessionAdmin == null | adminService.login(sessionAdmin) == null) {
			this.addFieldError("msg", "请登陆后再操作！");
			req.setAttribute("msg", "请登陆后再操作！");
		} else if (order != null) {
			if (!sessionAdmin.getAdminId().equalsIgnoreCase("admin")
					& !order.getAdmin().getAdminId()
							.equalsIgnoreCase(sessionAdmin.getAdminId())) {
				this.addFieldError("msg", "您无权限进行此操作操作！");
				req.setAttribute("msg", "您无权限进行此操作操作！");
			}
		}
	}
}
