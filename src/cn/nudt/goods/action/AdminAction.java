package cn.nudt.goods.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import cn.itcast.commons.CommonUtils;
import cn.nudt.goods.bean.Admin;
import cn.nudt.goods.service.AdminService;

import com.opensymphony.xwork2.ActionSupport;

@Controller
public class AdminAction extends ActionSupport {
	@Resource
	AdminService adminService;

	public String ajaxValidateAdminname() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		String adminname = req.getParameter("adminname");
		boolean b = adminService.ajaxValidateAdminname(adminname);
		try {
			resp.getWriter().print(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String ajaxValidateEmail() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		String email = req.getParameter("email");
		boolean b = adminService.ajaxValidateEmail(email);
		try {
			resp.getWriter().print(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String ajaxValidateVerifyCode() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		String verifyCode = req.getParameter("verifyCode");
		String vcode = (String) req.getSession().getAttribute("vCode");
		boolean b = verifyCode.equalsIgnoreCase(vcode);
		try {
			resp.getWriter().print(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String regist() {
		HttpServletRequest req = ServletActionContext.getRequest();
		Admin formAdmin = CommonUtils.toBean(req.getParameterMap(), Admin.class);
		adminService.regist(formAdmin);
		req.setAttribute("code", "success");
		req.setAttribute("msg", "注册成功，请马上到邮箱激活！");
		return "message";
	}

	public String activation() {
		HttpServletRequest req = ServletActionContext.getRequest();

		String code = req.getParameter("activationCode");
		adminService.activatioin(code);
		req.setAttribute("code", "success");// 通知msg.jsp显示对号
		req.setAttribute("msg", "恭喜，激活成功，请马上登录！");
		return "message";
	}

	public String updatePassword() {
		HttpServletRequest req = ServletActionContext.getRequest();
		Admin formAdmin = CommonUtils.toBean(req.getParameterMap(), Admin.class);
		Admin admin = (Admin) req.getSession().getAttribute("sessionAdmin");
		adminService.updatePassword(admin.getAdminId(), formAdmin.getNewpass(),
				formAdmin.getAdminpwd());
		req.setAttribute("msg", "修改密码成功");
		req.setAttribute("code", "success");
		return "message";
	}

	public String quit() {
		HttpServletRequest req = ServletActionContext.getRequest();
		req.getSession().invalidate();
		return "quit";
	}

	public String login() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		Admin formAdmin = CommonUtils.toBean(req.getParameterMap(), Admin.class);
		Admin admin = adminService.login(formAdmin);
		req.getSession().setAttribute("sessionAdmin", admin);
		String adminname = admin.getAdminname();
		try {
			adminname = URLEncoder.encode(adminname, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Cookie cookie = new Cookie("adminname", adminname);
		cookie.setMaxAge(60 * 60 * 24);// 保存1天
		resp.addCookie(cookie);
		return "success";// 重定向到主页
	}

	public void validateLogin() throws IOException {

		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();

		String verifyCode = req.getParameter("verifyCode");

		String vcode = (String) req.getSession().getAttribute("vCode");

		boolean b = verifyCode.equalsIgnoreCase(vcode);
		if (!b) {
			this.addFieldError("b", "验证码错误！");
			req.setAttribute("b", "验证码错误！");
			req.setAttribute("msg", "验证码错误！");
		}
		try {
			resp.getWriter().print(b);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Admin formAdmin = CommonUtils.toBean(req.getParameterMap(), Admin.class);

		Admin admin = adminService.login(formAdmin);

		if (admin == null) {
			this.addFieldError("msg", "用户名或密码错误！");
			req.setAttribute("msg", "用户名或密码错误！");
			req.setAttribute("admin", formAdmin);
		} else {
			if (!admin.isStatus()) {
				this.addFieldError("msg", "您还没有激活！");
				req.setAttribute("msg", "您还没有激活！");
				req.setAttribute("admin", formAdmin);
			}

		}
	}

	public void validateRegist() {
		HttpServletRequest req = ServletActionContext.getRequest();
		Admin formAdmin = CommonUtils.toBean(req.getParameterMap(), Admin.class);
		String adminname = formAdmin.getAdminname();
		if (adminname == null || adminname.trim().isEmpty()) {
			this.addFieldError("adminname", "用户名不能为空！");
			req.setAttribute("adminname", "用户名不能为空！");
		} else if (adminname.length() < 3 || adminname.length() > 20) {
			this.addFieldError("adminname", "用户名长度必须在3~20之间！");
			req.setAttribute("adminname", "用户名长度必须在3~20之间！");
		} else if (!adminService.ajaxValidateAdminname(adminname)) {
			this.addFieldError("adminname", "用户名已被注册！");
			req.setAttribute("adminname", "用户名已被注册！");
		}
		String adminpwd = formAdmin.getAdminpwd();
		if (adminpwd == null || adminpwd.trim().isEmpty()) {
			this.addFieldError("adminpwd", "密码不能为空！");
			req.setAttribute("adminpwd", "密码不能为空！");
		} else if (adminpwd.length() < 3 || adminpwd.length() > 20) {
			this.addFieldError("adminpwd", "密码长度必须在3~20之间！");
			req.setAttribute("adminpwd", "密码长度必须在3~20之间！");
		}

		String readminpwd = formAdmin.getReadminpwd();
		if (readminpwd == null || readminpwd.trim().isEmpty()) {
			this.addFieldError("readminpwd", "确认密码不能为空！");
			req.setAttribute("readminpwd", "确认密码不能为空！");
		} else if (!readminpwd.equals(adminpwd)) {
			this.addFieldError("readminpwd", "两次输入不一致！");
			req.setAttribute("readminpwd", "两次输入不一致！");
		}

		String email = formAdmin.getEmail();
		if (email == null || email.trim().isEmpty()) {
			this.addFieldError("email", "Email不能为空！");
			req.setAttribute("email", "Email不能为空！");
		} else if (!email
				.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			this.addFieldError("email", "Email格式错误！");
			req.setAttribute("email", "Email格式错误！");
		} else if (!adminService.ajaxValidateEmail(email)) {
			this.addFieldError("email", "Email已被注册！");
			req.setAttribute("email", "Email已被注册！");
		}

		String verifyCode = formAdmin.getVerifyCode();
		String vcode = (String) req.getSession().getAttribute("vCode");
		if (verifyCode == null || verifyCode.trim().isEmpty()) {
			this.addFieldError("verifyCode", "验证码不能为空！");
			req.setAttribute("verifyCode", "验证码不能为空！");
		} else if (!verifyCode.equalsIgnoreCase(vcode)) {
			this.addFieldError("verifyCode", "验证码错误！");
			req.setAttribute("verifyCode", "验证码错误！");
		}
	}

	public void validateUpdatePassword() {
		HttpServletRequest req = ServletActionContext.getRequest();
		Admin admin = (Admin) req.getSession().getAttribute("sessionAdmin");
		if (admin == null) {
			this.addFieldError("msg", "您还没有登录！");
			req.setAttribute("msg", "您还没有登录！");
		}else if (admin.getAdminId().equalsIgnoreCase("admin")) {
			this.addFieldError("msg", "无法修改管理员密码！");
			req.setAttribute("msg", "无法修改管理员密码！");
		}

	}

}
