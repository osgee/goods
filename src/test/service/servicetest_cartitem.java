package test.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.nudt.goods.bean.CartItem;
import cn.nudt.goods.service.CartItemService;

public class servicetest_cartitem {
	CartItemService cartItemService;
	
	@Before
	public void before(){
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		cartItemService=(CartItemService) applicationContext.getBean("cartItemServiceBean");
	}

	@Test
	public void loadCartItems(){
		List<CartItem> cartItems= cartItemService.loadCartItems("084701B6296340F294C75B2D33E3116F","4C8D7AE18DD3430D82FB5F1E8BC3D781");
		for (CartItem cartItem : cartItems) {
			System.out.println(cartItem);
		}
	}
	
	@Test
	public void updateQuantity(){
		cartItemService.updateQuantity("084701B6296340F294C75B2D33E3116F", 2);
	}
	@Test
	public void batchDelete(){
		cartItemService.batchDelete("A019B77376184F608B7DC934B28BFAB9,FE268CEB0E1245B39BEEA3A80087F95D".split(","));
	}
	@Test
	public void add(){
		CartItem cartItem=new CartItem();
		cartItem.setCartItemId("084701B6296340F294C75B2D33E3117F");
		cartItem.setQuantity(3);
		cartItemService.add(cartItem);
		cartItem.setCartItemId("084701B6296340F294C75B2D33E3117H");
		cartItem.setQuantity(5);
		cartItemService.add(cartItem);
	}
	@Test
	public void myCart(){
		List<CartItem> cartItems=cartItemService.myCart("65050F9ED3BE4285B510895AF44CD31A");
		for (CartItem cartItem : cartItems) {
			System.out.println(cartItem);
		}
	}

}
