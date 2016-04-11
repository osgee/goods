package cn.nudt.goods.dao.impl;

import java.io.IOException;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.nudt.goods.bean.User;
import cn.nudt.goods.dao.UserDao;
import cn.nudt.goods.utils.Crypt;

@Repository
@Transactional
public class UserDaoBean implements UserDao {
	@Resource
	SessionFactory factory;

	public void add(User user) {
		user = encrypt(user);
		factory.getCurrentSession().persist(user);
	}

	public void update(User user) {
		factory.getCurrentSession().merge(user);
	}

	public User findByUidAndPassword(String uid, String password) {
		User user = (User) factory.getCurrentSession()
				.createQuery("FROM User WHERE uid=? AND loginpass=?")
				.setParameter(0, uid).setParameter(1, encrypt(password))
				.uniqueResult();
		if (user != null) {
			return decrypt(user, password);
		} else
			return null;
	}

	public User findByLoginnameAndLoginpass(String loginname, String loginpass) {
		User user = (User) factory.getCurrentSession()
				.createQuery("FROM User WHERE loginname=? AND loginpass=?")
				.setParameter(0, loginname).setParameter(1, encrypt(loginpass))
				.uniqueResult();
		if (user != null) {
			return decrypt(user, loginpass);
		} else
			return null;
	}

	public User findByActivationCode(String code) {
		return (User) factory.getCurrentSession()
				.createQuery("FROM User WHERE activationCode=?")
				.setParameter(0, code).uniqueResult();
	}

	public User findBy(String key, String con) {
		User user = new User();
		switch (key) {
		case "loginname":
			user = (User) factory.getCurrentSession()
					.createQuery("FROM User WHERE loginname=?")
					.setParameter(0, con).uniqueResult();
			break;
		case "email":
			user = (User) factory.getCurrentSession()
					.createQuery("FROM User WHERE email=?")
					.setParameter(0, con).uniqueResult();
			break;
		case "ActivationCode":
			user = (User) factory.getCurrentSession()
					.createQuery("FROM User WHERE activationCode=?")
					.setParameter(0, con).uniqueResult();
			break;
		default:
			break;
		}
		return user;
	}

	public User encrypt(User user) {
			try {
				user.setLoginpass(new String(Crypt.encrypt(user.getLoginpass(),
						user.getLoginpass())).trim());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return user;
	}

	public String encrypt(String in) {
		String out = null;
		try {
			out = new String(Crypt.encrypt(in, in).trim());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}

	public User decrypt(User user, String in) {
		User voUser=new User();
		try {
			voUser.setLoginname(user.getLoginname());
			voUser.setLoginpass(new String(Crypt.decrypt(user.getLoginpass(), in))
					.trim());
			voUser.setStatus(user.isStatus());
			voUser.setUid(user.getUid());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return voUser;
	}

	public String decrypt(String in) {
		String out = null;
		try {
			out = new String(Crypt.decrypt(in, in).trim());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}
	
	
}
