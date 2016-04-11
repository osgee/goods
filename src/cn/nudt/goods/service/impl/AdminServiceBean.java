package cn.nudt.goods.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.commons.CommonUtils;
import cn.nudt.goods.bean.Admin;
import cn.nudt.goods.dao.AdminDao;
import cn.nudt.goods.dao.BookDao;
import cn.nudt.goods.service.AdminService;

@Service
@Transactional
public class AdminServiceBean implements AdminService {
	@Resource
	AdminDao adminDao;
	@Resource
	BookDao bookDao;
	final int managerLevel=1;
	
	public void updatePassword(String adminId, String newPass, String oldPass) {
		Admin admin = adminDao.findByAdminidAndPassword(adminId, oldPass);
		if (admin != null) {
			admin.setAdminpwd(newPass);
			adminDao.update(admin);
		} else {
			System.out.println("用户不存在！");
		}

	}

	
	public Admin login(Admin admin) {
		if (admin==null) {
			return null;
		}else
		return adminDao.find(admin.getAdminname(), admin.getAdminpwd());
	}
	
	

	public void activatioin(String code) {
		try {
			Admin admin = adminDao.findBy("activationCode",code);
			if (admin == null)
				throw new SQLException("无效的激活码！");
			if (admin.isStatus())
				throw new SQLException("您已经激活过了，不要二次激活！");
			admin.setStatus(true);
			adminDao.add(admin);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean ajaxValidateAdminname(String adminname) {
		Admin admin = adminDao.findBy("adminname", adminname);
		return admin == null;
	}

	public boolean ajaxValidateEmail(String email) {
		Admin admin =adminDao.findBy("email", email);
		return admin == null;
	}
	
	public void regist(Admin admin) {
		admin.setAdminId(CommonUtils.uuid());
		//测试环境下设为真，生产环境中设为假
		admin.setStatus(true);
		admin.setActivationCode(CommonUtils.uuid() + CommonUtils.uuid());
		adminDao.add(admin);
	}
	
	public Admin findByAdminId(String adminId) {
		return adminDao.findBy("adminId", adminId);
	}
	
	public boolean isManager(String adminId) {
		return adminDao.findBy("adminId", adminId).getAuthority()>=managerLevel;
	}
}
