package cn.nudt.goods.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import cn.nudt.goods.bean.Book;
import cn.nudt.goods.bean.CartItem;
import cn.nudt.goods.bean.User;
import cn.nudt.goods.service.BookService;
import cn.nudt.goods.service.CartItemService;
import cn.nudt.goods.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

@Controller
public class CartItemAction extends ActionSupport {
	@Resource
	CartItemService cartItemService;
	@Resource
	BookService bookService;
	@Resource
	UserService userService;

	String bid;
	int quantity;

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String loadCartItems() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String cartItemIds = req.getParameter("cartItemIds");
		double total = Double.parseDouble(req.getParameter("total"));
		List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds
				.split(","));
		req.setAttribute("cartItemList", cartItemList);
		req.setAttribute("total", total);
		req.setAttribute("cartItemIds", cartItemIds);
		return "showitem";
	}

	public String updateQuantity() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		String cartItemId = req.getParameter("cartItemId");
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		CartItem cartItem = cartItemService
				.updateQuantity(cartItemId, quantity);

		// 给客户端返回一个json对象
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"quantity\"").append(":").append(cartItem.getQuantity());
		sb.append(",");
		sb.append("\"subtotal\"").append(":").append(cartItem.getSubtotal());
		sb.append("}");

		try {
			resp.getWriter().print(sb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String batchDelete() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String cartItemIds = req.getParameter("cartItemIds");
		cartItemService.batchDelete(cartItemIds.split(","));
		return "myCart";
	}

	public String add() {
		HttpServletRequest req = ServletActionContext.getRequest();
		User sessionUser = (User) req.getSession().getAttribute("sessionUser");
		Book book = bookService.load(bid);
		CartItem cartItem = new CartItem();
		cartItem.setBook(book);
		cartItem.setUser(sessionUser);
		cartItem.setQuantity(quantity);
		cartItemService.add(cartItem);
		return "myCart";
	}

	public String myCart() {
		HttpServletRequest req = ServletActionContext.getRequest();

		User user = (User) req.getSession().getAttribute("sessionUser");
		String uid = user.getUid();
		List<CartItem> cartItemList = cartItemService.myCart(uid);
		if (cartItemList != null) {
			boolean flag = true;
			for (int j = 0; j < cartItemList.size() - 1; j++) {
				CartItem cartItem1 = cartItemList.get(j);
				CartItem cartItem2 = cartItemList.get(j + 1);
				if (!cartItem1
						.getBook()
						.getAdmin()
						.getAdminId()
						.equalsIgnoreCase(
								cartItem2.getBook().getAdmin().getAdminId())) {
					flag = false;
				}
				if (flag == false) {
					break;
				}
			}
			if (flag == false) {
				req.setAttribute("msg", "您选择要购买的图书不是同一个卖家，不能同时下单！");
			}
		}
		req.setAttribute("cartItemList", cartItemList);
		return "success";
	}

	public void validateLoadCartItems() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String cartItemIds = req.getParameter("cartItemIds");
		if (cartItemIds == null) {
		} else {
			List<CartItem> cartItemList = cartItemService
					.loadCartItems(cartItemIds.split(","));
			boolean flag = true;
			for (int j = 0; j < cartItemList.size() - 1; j++) {
				CartItem cartItem1 = cartItemList.get(j);
				CartItem cartItem2 = cartItemList.get(j + 1);
				if (!cartItem1
						.getBook()
						.getAdmin()
						.getAdminId()
						.equalsIgnoreCase(
								cartItem2.getBook().getAdmin().getAdminId())) {
					flag = false;
				}
				if (flag == false) {
					break;
				}
			}
			if (flag == false) {
				this.addFieldError("msg", "您选择要购买的图书不是同一个卖家，不能下单！");
				req.setAttribute("code", "error");
				req.setAttribute("msg", "您选择要购买的图书不是同一个卖家，不能下单！");
			}
		}

	}

	public void validate() {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute("sessionUser");
		if (user == null | userService.login(user) == null) {
			this.addFieldError("msg", "您还未登陆，请登陆！");
			req.setAttribute("msg", "您还未登陆，请登陆！");
		}
	}

}
