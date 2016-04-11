package cn.nudt.goods.bean;

import javax.persistence.Entity;


@Entity
public class Admin {
	private String adminId;// 主键
	private String adminname;// 管理员的登录名
	private String adminpwd;// 管理员的登录密码
	private int authority;
	private String email;// 邮箱
	private boolean status;// 状态，true表示已激活，或者未激活
	private String activationCode;// 激活码，它是唯一值！即每个用户的激活码是不同的！
	
	// 注册表单
	private String readminpwd;// 确认密码
	private String verifyCode;// 验证码

	// 修改密码表单
	private String newpass;// 新密码

	public Admin() {}
	
	public Admin(String adminId) {
		this.adminId=adminId;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminname() {
		return adminname;
	}

	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}

	public String getAdminpwd() {
		return adminpwd;
	}

	public void setAdminpwd(String adminpwd) {
		this.adminpwd = adminpwd;
	}

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public String getReadminpwd() {
		return readminpwd;
	}

	public void setReadminpwd(String reloginpass) {
		this.readminpwd = reloginpass;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getNewpass() {
		return newpass;
	}

	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}

	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", adminname=" + adminname
				+ ", adminpwd=" + adminpwd + "]";
	}

}
