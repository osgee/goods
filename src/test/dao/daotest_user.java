package test.dao;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.nudt.goods.bean.User;
import cn.nudt.goods.dao.UserDao;
import cn.nudt.goods.utils.Crypt;

public class daotest_user {
	static UserDao userDao;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		userDao=(UserDao)applicationContext.getBean("userDaoBean");
	}

	@Test
	public void add() {
		User user=new User();
		user.setUid("B50ADE921BF14F6EB5331777B1874790");
		user.setLoginname("fengtao");
		user.setLoginpass("199511");
		userDao.add(user);
	}
	@Test
	public void findByUidAndPassword() {

		System.out.println(userDao.findByUidAndPassword("E5C4F3F7F2814F6693D60ED3518F726B","123456"));
	}
	@Test
	public void findByLoginnameAndLoginpass() {
		
		System.out.println(userDao.findByLoginnameAndLoginpass("liuBei","1234"));
	}
	@Test
	public void encrypt() throws IOException {
		System.out.println(Crypt.encrypt("1234", "1234"));
	}

}
