package test.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.nudt.goods.bean.User;
import cn.nudt.goods.service.UserService;

public class servicetest_user {
	static UserService userService;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		userService=(UserService)applicationContext.getBean("userServiceBean");
	}

	@Test
	public void updatePassword() {
		userService.updatePassword("6B807FC589684A7B8CD2CBC252D5AC86", "1234567", "1234");
	}
	@Test
	public void login() {
//		User user=new User();
//		user.setLoginname("liSi");
//		user.setLoginpass("123");
//		System.out.println(userService.login(user));
		System.out.println(userService.login(null));
	}
	@Test
	public void ajaxValidateLoginname() {

		System.out.println(userService.ajaxValidateLoginname("liSi"));
	}
	@Test
	public void ajaxValidateEmail() {
		
		System.out.println(userService.ajaxValidateEmail("940487182@qq.com"));
	}
	@Test
	public void regist() {
		User user=new User();
		user.setLoginname("liSi");
		user.setLoginpass("123");
		userService.regist(user);
	}

}
