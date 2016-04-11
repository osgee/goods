package cn.nudt.goods.service;

import java.util.List;

import cn.nudt.goods.bean.CartItem;

public interface CartItemService {

	/*
	 * 加载多个CartItem
	 */
	public List<CartItem> loadCartItems(String... cartItemIds);

	public CartItem updateQuantity(String cartItemId, int quantity);

	public void batchDelete(String... cartItemIds);

	public void add(CartItem cartItem);
	
	public CartItem findByUidAndBid(String uid, String bid);

	public List<CartItem> myCart(String uid);

}