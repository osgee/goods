package cn.nudt.goods.dao;

import cn.nudt.goods.bean.Admin;

public interface AdminDao {
	
	public void add(Admin admin);
	
	public void delete(Admin admin);
	
	public void update(Admin admin) ;

	public Admin find(String adminname, String adminpwd);
	
	public Admin findByAdminidAndPassword(String adminId, String password);
	
	public Admin findByAdminnameAndAdminpwd(String adminname, String adminpwd);
	
	public Admin findByActivationCode(String code);
	
	public Admin findBy(String key, String con);
}