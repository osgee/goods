package cn.nudt.goods.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.nudt.goods.bean.CartItem;
import cn.nudt.goods.dao.CartItemDao;

@Repository
@Transactional
public class CartItemDaoBean implements CartItemDao {
	@Resource
	SessionFactory factory;

	public void add(CartItem cartItem) {
		factory.getCurrentSession().persist(cartItem);
	}

	public void delete(String cartItemId) {
		factory.getCurrentSession().delete(
				(CartItem) factory.getCurrentSession().load(CartItem.class,
						cartItemId));
	}

	public void update(CartItem cartItem) {
		factory.getCurrentSession().merge(cartItem);
	}

	public CartItem findByCartItemId(String CartItemId) {
		CartItem cartItem = new CartItem();
		cartItem = (CartItem) factory.getCurrentSession().load(CartItem.class,
				CartItemId);
		return cartItem;
	}

	public CartItem findByUidAndBid(String uid, String bid) {
		return (CartItem) factory.getCurrentSession()
				.createQuery("FROM CartItem WHERE uid=? AND bid=?")
				.setParameter(0, uid).setParameter(1, bid).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<CartItem> findByUser(String uid) {
		return (List<CartItem>) factory.getCurrentSession()
				.createQuery("FROM CartItem WHERE uid=? ORDER BY orderBy DESC")
				.setParameter(0, uid).list();

	}

}
