package test.service;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.nudt.goods.bean.Category;
import cn.nudt.goods.service.CategoryService;

public class servicetest_category {
	static CategoryService categoryService;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		categoryService=(CategoryService) applicationContext.getBean("categoryServiceBean");
	}

	@Test
	public void findChildrenCountByParent() {
		System.out.println(categoryService.findChildrenCountByParent("3"));
	}
	@Test
	public void findChildren() {
		List<Category> categories=categoryService.findChildren("3");
		for (Category category : categories) {
			System.out.println(category);
		}
	}
	@Test
	public void delete() {
		categoryService.delete("abcd");
	}
	@Test
	public void edit() {
		Category category=new Category();
		category.setCid("abcd");
		category.setCname("fafdsa");
		categoryService.edit(category);
	}
	@Test
	public void load() {

		System.out.println(categoryService.load("C3F9FAAF4EA64857ACFAB0D9C8D0E446"));
	}
	@Test
	public void add() {
		Category category=new Category();
		category.setCid("abcd");
		categoryService.add(category);
	}
	@Test
	public void findParents() {
		List<Category> categories=categoryService.findParents();
		for (Category category : categories) {
			System.out.println(category);
			List<Category> children=category.getChildren();
			for (Category child : children) {
				System.out.println(child);
			}
		}
	}
	@Test
	public void findAll() {
		List<Category> categories=categoryService.findAll();
		for (Category category : categories) {
			System.out.println(category);
		}
	}
	

}
