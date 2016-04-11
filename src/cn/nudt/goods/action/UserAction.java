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
import cn.nudt.goods.bean.User;
import cn.nudt.goods.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

@Controller
public class UserAction extends ActionSupport {
	@Resource
	UserService userService;

	public String ajaxValidateLoginname() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		String loginname = req.getParameter("loginname");
		boolean b = userService.ajaxValidateLoginname(loginname);
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
		boolean b = userService.ajaxValidateEmail(email);
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
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		userService.regist(formUser);
		req.setAttribute("code", "success");
		req.setAttribute("msg", "注册成功，请马上到邮箱激活！");
		return "message";
	}

	public String activation() {
		HttpServletRequest req = ServletActionContext.getRequest();

		String code = req.getParameter("activationCode");
		userService.activatioin(code);
		req.setAttribute("code", "success");// 通知msg.jsp显示对号
		req.setAttribute("msg", "恭喜，激活成功，请马上登录！");
		return "message";
	}

	public String updatePassword() {
		HttpServletRequest req = ServletActionContext.getRequest();
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		User user = (User) req.getSession().getAttribute("sessionUser");
		userService.updatePassword(user.getUid(), formUser.getNewpass(),
				formUser.getLoginpass());
		req.setAttribute("msg", "修改密码成功");
		req.setAttribute("code", "success");
		return "message";
	}

	public String quit() {
		HttpServletRequest req = ServletActionContext.getRequest();
		req.getSession().invalidate();
		return "success";
	}

	public String login() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		User user = userService.login(formUser);
		req.getSession().setAttribute("sessionUser", user);
		String loginname = user.getLoginname();
		try {
			loginname = URLEncoder.encode(loginname, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Cookie cookie = new Cookie("loginname", loginname);
		cookie.setMaxAge(60 * 60 * 24 * 10);// 保存10天
		resp.addCookie(cookie);
		return "success";// 重定向到主页
	}

	public void validateLogin() throws IOException {

		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();

		String verifyCode = req.getParameter("verifyCode");

		String vcode = (String) req.getSession().getAttribute("vCode");

		boolean b = verifyCode.equalsIgnoreCase(vcode);

		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);

		User user = userService.login(formUser);

		if (user == null) {
			this.addFieldError("msg", "用户名或密码错误！");
			req.setAttribute("msg", "用户名或密码错误！");
			req.setAttribute("user", formUser);
		} else {
			if (!user.isStatus()) {
				this.addFieldError("msg", "您还没有激活！");
				req.setAttribute("msg", "您还没有激活！");
				req.setAttribute("user", formUser);
			}

		}
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
	}

	public void validateRegist() {
		HttpServletRequest req = ServletActionContext.getRequest();
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		String loginname = formUser.getLoginname();
		if (loginname == null || loginname.trim().isEmpty()) {
			this.addFieldError("loginname", "用户名不能为空！");
			req.setAttribute("loginname", "用户名不能为空！");
		} else if (loginname.length() < 3 || loginname.length() > 20) {
			this.addFieldError("loginname", "用户名长度必须在3~20之间！");
			req.setAttribute("loginname", "用户名长度必须在3~20之间！");
		} else if (!userService.ajaxValidateLoginname(loginname)) {
			this.addFieldError("loginname", "用户名已被注册！");
			req.setAttribute("loginname", "用户名已被注册！");
		}
		String loginpass = formUser.getLoginpass();
		if (loginpass == null || loginpass.trim().isEmpty()) {
			this.addFieldError("loginpass", "密码不能为空！");
			req.setAttribute("loginpass", "密码不能为空！");
		} else if (loginpass.length() < 3 || loginpass.length() > 20) {
			this.addFieldError("loginpass", "密码长度必须在3~20之间！");
			req.setAttribute("loginpass", "密码长度必须在3~20之间！");
		}

		String reloginpass = formUser.getReloginpass();
		if (reloginpass == null || reloginpass.trim().isEmpty()) {
			this.addFieldError("reloginpass", "确认密码不能为空！");
			req.setAttribute("reloginpass", "确认密码不能为空！");
		} else if (!reloginpass.equals(loginpass)) {
			this.addFieldError("reloginpass", "两次输入不一致！");
			req.setAttribute("reloginpass", "两次输入不一致！");
		}

		String email = formUser.getEmail();
		if (email == null || email.trim().isEmpty()) {
			this.addFieldError("email", "Email不能为空！");
			req.setAttribute("email", "Email不能为空！");
		} else if (!email
				.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			this.addFieldError("email", "Email格式错误！");
			req.setAttribute("email", "Email格式错误！");
		} else if (!userService.ajaxValidateEmail(email)) {
			this.addFieldError("email", "Email已被注册！");
			req.setAttribute("email", "Email已被注册！");
		}

		String verifyCode = formUser.getVerifyCode();
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
		String verifyCode = req.getParameter("verifyCode");

		String vcode = (String) req.getSession().getAttribute("vCode");

		boolean b = verifyCode.equalsIgnoreCase(vcode);

		User user = (User) req.getSession().getAttribute("sessionUser");
		if (user == null|userService.login(user)==null) {
			this.addFieldError("msg", "您还没有登录！");
			req.setAttribute("msg", "您还没有登录！");
		}
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		String loginpass = formUser.getNewpass();
		String reloginpass = formUser.getReloginpass();
		if (reloginpass == null || reloginpass.trim().isEmpty()) {
			this.addFieldError("reloginpass", "确认密码不能为空！");
			req.setAttribute("msg", "确认密码不能为空！");
			req.setAttribute("user", formUser);
		} else if (!reloginpass.trim().equals(loginpass.trim())) {
			this.addFieldError("reloginpass", "两次输入不一致！");
			req.setAttribute("msg", "两次输入不一致！");
			req.setAttribute("user", formUser);
		}
		if (loginpass == null || loginpass.trim().isEmpty()) {
			this.addFieldError("loginpass", "密码不能为空！");
			req.setAttribute("msg", "密码不能为空！");
			req.setAttribute("user", formUser);
		} else if (loginpass.length() < 3 || loginpass.length() > 20) {
			this.addFieldError("loginpass", "密码长度必须在3~20之间！");
			req.setAttribute("msg", "密码长度必须在3~20之间！");
			req.setAttribute("user", formUser);
		}

		if (!b) {
			this.addFieldError("b", "验证码错误！");
			req.setAttribute("b", "验证码错误！");
			req.setAttribute("msg", "验证码错误！");
			req.setAttribute("user", formUser);
		}

	}

}
