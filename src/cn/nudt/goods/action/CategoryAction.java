package cn.nudt.goods.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import cn.nudt.goods.bean.Category;
import cn.nudt.goods.service.CategoryService;

@Controller
public class CategoryAction {
	@Resource
	CategoryService categoryService;
	public String findAll() {
		List<Category> parents = categoryService.findParents();
		ServletActionContext.getServletContext().setAttribute("parents", parents);
		return "success";
	}

}
