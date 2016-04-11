package cn.nudt.goods.dao;

import java.util.List;

import cn.nudt.goods.bean.Category;

public interface CategoryDao {

	public void add(Category category);

	public void delete(String cid);

	public void update(Category category);
	
	public Category find(String cid);

	public List<Category> findAll();

	public List<Category> findParents();

	public List<Category> findByParent(String pid);

}