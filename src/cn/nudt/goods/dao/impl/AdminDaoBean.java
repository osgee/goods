package cn.nudt.goods.dao.impl;

import java.io.IOException;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.nudt.goods.bean.Admin;
import cn.nudt.goods.dao.AdminDao;
import cn.nudt.goods.utils.Crypt;

@Repository
@Transactional
public class AdminDaoBean implements AdminDao {
	@Resource
	SessionFactory factory;

	public void add(Admin admin) {
		admin = encrypt(admin);
		factory.getCurrentSession().persist(admin);

	}

	public void delete(Admin admin) {
		admin = encrypt(admin);
		factory.getCurrentSession().delete(admin);

	}

	public void update(Admin admin) {
		admin = encrypt(admin);
		factory.getCurrentSession().merge(admin);
	}

	public Admin find(String adminname, String adminpwd) {
		Admin admin = (Admin) factory.getCurrentSession()
				.createQuery("FROM Admin WHERE adminname=? AND adminpwd=?")
				.setParameter(0, adminname).setParameter(1, encrypt(adminpwd))
				.uniqueResult();
		if (admin != null) {
			return decrypt(admin, adminpwd);
		}
		return null;
	}
	

	public Admin findByAdminidAndPassword(String adminId, String password) {
		Admin admin = (Admin) factory.getCurrentSession()
				.createQuery("FROM Admin WHERE adminId=? AND adminpwd=?")
				.setParameter(0, adminId).setParameter(1, encrypt(password))
				.uniqueResult();
		if (admin != null) {
			return decrypt(admin, password);
		} else
			return null;
	}

	public Admin findByAdminnameAndAdminpwd(String adminname, String adminpwd) {
		Admin admin = (Admin) factory.getCurrentSession()
				.createQuery("FROM Admin WHERE adminname=? AND adminpwd=?")
				.setParameter(0, adminname).setParameter(1, encrypt(adminpwd))
				.uniqueResult();
		if (admin != null) {
			return decrypt(admin, adminpwd);
		} else
			return null;
	}

	public Admin findByActivationCode(String code) {
		return (Admin) factory.getCurrentSession()
				.createQuery("FROM Admin WHERE activationCode=?")
				.setParameter(0, code).uniqueResult();
	}

	public Admin findBy(String key, String con) {
		Admin admin = new Admin();
		switch (key) {
		case "adminname":
			admin = (Admin) factory.getCurrentSession()
					.createQuery("FROM Admin WHERE adminname=?")
					.setParameter(0, con).uniqueResult();
			break;
		case "email":
			admin = (Admin) factory.getCurrentSession()
					.createQuery("FROM Admin WHERE email=?")
					.setParameter(0, con).uniqueResult();
			break;
		case "ActivationCode":
			admin = (Admin) factory.getCurrentSession()
					.createQuery("FROM Admin WHERE activationCode=?")
					.setParameter(0, con).uniqueResult();
			break;
		case "adminId":
			admin = (Admin) factory.getCurrentSession()
					.createQuery("FROM Admin WHERE adminId=?")
					.setParameter(0, con).uniqueResult();
			break;
		default:
			break;
		}
		return admin;
	}

	private Admin encrypt(Admin admin) {
			try {
				admin.setAdminpwd((new String(Crypt.encrypt(admin.getAdminpwd(),
						admin.getAdminpwd())).trim()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return admin;
	}

	private Admin decrypt(Admin admin, String Key) {
		try {
			admin.setAdminpwd(Crypt.decrypt(admin.getAdminpwd(), Key));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return admin;
	}

	private String encrypt(String Key) {
		String out = null;
		try {
			out = Crypt.encrypt(Key, Key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;

	}

}
