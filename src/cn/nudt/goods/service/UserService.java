package cn.nudt.goods.service;

import cn.nudt.goods.bean.User;

public interface UserService {

	public void updatePassword(String uid, String newPass, String oldPass);

	public User login(User user);

	public void activatioin(String code);

	public boolean ajaxValidateLoginname(String loginname);

	public boolean ajaxValidateEmail(String email);

	public void regist(User user);

}