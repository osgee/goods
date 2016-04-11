package cn.nudt.goods.dao;

import cn.nudt.goods.bean.User;

public interface UserDao {

	public void add(User user);

	public void update(User user);

	public User findByUidAndPassword(String uid, String password);

	public User findByLoginnameAndLoginpass(String loginname, String loginpass);
	
	public User findByActivationCode(String code);
	
	public User findBy(String key, String con);
	
	public User encrypt(User user);
	
	public String encrypt(String in);
	
	public User decrypt(User user, String key);

}