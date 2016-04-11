package cn.nudt.goods.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.commons.CommonUtils;
import cn.nudt.goods.bean.CartItem;
import cn.nudt.goods.dao.CartItemDao;
import cn.nudt.goods.service.CartItemService;

@Service
@Transactional
public class CartItemServiceBean implements CartItemService {
	@Resource
	CartItemDao cartItemDao;
	public List<CartItem> loadCartItems(String... cartItemIds) {
		List<CartItem> cartItems=new ArrayList<>();
		for (String cartItemId : cartItemIds) {
			cartItems.add(cartItemDao.findByCartItemId(cartItemId));
		}
		return cartItems;
	}

	public CartItem updateQuantity(String cartItemId, int quantity) { 
		CartItem cartItem=cartItemDao.findByCartItemId(cartItemId);
		cartItem.setQuantity(quantity);
		cartItemDao.update(cartItem);
		return cartItem;
	}

	public void batchDelete(String... cartItemIds) {
		for (String cartItemId : cartItemIds) {
			
			cartItemDao.delete(cartItemId);
		}
	}

	public void add(CartItem cartItem) {
		CartItem _cartItem=findByUidAndBid(cartItem.getUser().getUid(),cartItem.getBook().getBid());
		if (_cartItem == null) {
			cartItem.setCartItemId(CommonUtils.uuid());
			cartItemDao.add(cartItem);
		} else {
			int quantity = cartItem.getQuantity() + _cartItem.getQuantity();

			updateQuantity(_cartItem.getCartItemId(), quantity);
		}
	}

	public CartItem findByUidAndBid(String uid, String bid) {
		return cartItemDao.findByUidAndBid(uid, bid);
	}

	public List<CartItem> myCart(String uid) {
		return cartItemDao.findByUser(uid);
	}
}
