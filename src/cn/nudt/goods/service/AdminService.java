package cn.nudt.goods.service;

import cn.nudt.goods.bean.Admin;

public interface AdminService {
	public void updatePassword(String adminId, String newPass, String oldPass);
	
	public Admin login(Admin admin);
	
	public void activatioin(String code);
	
	public boolean ajaxValidateAdminname(String loginname);
	
	public boolean ajaxValidateEmail(String email) ;
	
	public void regist(Admin admin);
	
	public Admin findByAdminId(String adminId);
	
	public boolean isManager(String adminId);
}