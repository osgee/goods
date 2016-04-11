package cn.nudt.goods.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.commons.CommonUtils;
import cn.nudt.goods.bean.User;
import cn.nudt.goods.dao.UserDao;
import cn.nudt.goods.service.UserService;

@Service
@Transactional
public class UserServiceBean implements UserService {
	@Resource
	UserDao userDao;

	public void updatePassword(String uid, String newPass, String oldPass) {
		User user = userDao.findByUidAndPassword(uid, oldPass);
		if (user != null) {
			user.setLoginpass(userDao.encrypt(newPass));
			userDao.update(user);
		} else {
			System.out.println("用户不存在！");
		}
	}

	public User login(User user) {
		if (user==null) {
			return null;
		}else
		return userDao.findByLoginnameAndLoginpass(user.getLoginname(),
				user.getLoginpass());
	}

	public void activatioin(String code) {
		/*
		 * 1. 通过激活码查询用户 2. 如果User为null，说明是无效激活码，抛出异常，给出异常信息（无效激活码） 3.
		 * 查看用户状态是否为true，如果为true，抛出异常，给出异常信息（请不要二次激活） 4. 修改用户状态为true
		 */
		try {
			User user = userDao.findBy("activationCode",code);
			if (user == null)
				throw new SQLException("无效的激活码！");
			if (user.isStatus())
				throw new SQLException("您已经激活过了，不要二次激活！");
			user.setStatus(true);
			userDao.add(user);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean ajaxValidateLoginname(String loginname) {
		User user = userDao.findBy("loginname", loginname);
		return user == null;
	}

	public boolean ajaxValidateEmail(String email) {
		User user =userDao.findBy("email", email);
		return user == null;
	}

	public void regist(User user) {
		/*
		 * 1. 数据的补齐
		 */
		user.setUid(CommonUtils.uuid());
		//测试环境下设为真，生产环境中设为假
		user.setStatus(true);
		user.setActivationCode(CommonUtils.uuid() + CommonUtils.uuid());
		userDao.add(user);
	}
}
