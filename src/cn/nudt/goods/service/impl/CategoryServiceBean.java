package cn.nudt.goods.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.nudt.goods.bean.Category;
import cn.nudt.goods.dao.CategoryDao;
import cn.nudt.goods.service.CategoryService;

@Service
@Transactional
public class CategoryServiceBean implements CategoryService {
	@Resource
	CategoryDao categoryDao;
	
	public int findChildrenCountByParent(String pid) {
		return categoryDao.findByParent(pid).size();
	}

	public void delete(String cid) {
			categoryDao.delete(cid);
	}

	public void edit(Category category) {
		categoryDao.update(category);
	}

	public Category load(String cid) {
		return categoryDao.find(cid);
	}

	public void add(Category category) {
		categoryDao.add(category);
	}

	public List<Category> findAll() {
		return categoryDao.findAll();
	}

	/**
	 * 获取所有父分类，不带子分类
	 * 
	 * @return
	 */
	public List<Category> findParents() {
		return categoryDao.findParents();
	}

	public List<Category> findChildren(String pid) {
		return categoryDao.findByParent(pid);
	}
}
