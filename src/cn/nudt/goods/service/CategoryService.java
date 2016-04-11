package cn.nudt.goods.service;

import java.util.List;

import cn.nudt.goods.bean.Category;

public interface CategoryService {

	public int findChildrenCountByParent(String pid);

	public void delete(String cid);

	public void edit(Category category);

	public Category load(String cid);

	public void add(Category category);

	public List<Category> findAll();

	public List<Category> findParents();

	public List<Category> findChildren(String pid);

}