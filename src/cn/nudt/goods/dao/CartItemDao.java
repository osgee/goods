package cn.nudt.goods.dao;

import java.util.List;

import cn.nudt.goods.bean.CartItem;

public interface CartItemDao {

	public void add(CartItem cartItem);

	public void delete(String cartItemId);

	public void update(CartItem cartItem);

	public CartItem findByCartItemId(String CartItemId);

	public CartItem findByUidAndBid(String uid, String bid);

	public List<CartItem> findByUser(String uid);

}