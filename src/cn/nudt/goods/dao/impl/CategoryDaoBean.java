package cn.nudt.goods.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.nudt.goods.bean.Category;
import cn.nudt.goods.dao.CategoryDao;

@Repository@Transactional
public class CategoryDaoBean implements CategoryDao {
	@Resource
	SessionFactory factory;

	public void add(Category category) {
			factory.getCurrentSession().persist(category);
	}

	public void delete(String cid) {
			factory.getCurrentSession().delete(
					(Category) factory.getCurrentSession().load(Category.class,
							cid));
	}

	public void update(Category  category) {
			factory.getCurrentSession().merge(category);
	}

	public Category find(String cid) {
		return (Category) factory.getCurrentSession().load(Category.class, cid);
	}
	@SuppressWarnings("unchecked")
	public List<Category> findAll() {
		List<Category> categories=(List<Category>) factory.getCurrentSession()
				.createQuery("FROM Category").list();
		for (Category category : categories) {
			if (category.getParent()==null) {
				loadChildren(category);
			}
		}
		return categories;
	}

	@SuppressWarnings("unchecked")
	public List<Category> findParents() {
		List<Category> categories=(List<Category>) factory.getCurrentSession()
				.createQuery("FROM Category WHERE pid is null").list();
		for (Category category : categories) {
			loadChildren(category);
		}
		return categories;
	}

	@SuppressWarnings("unchecked")
	public List<Category> findByParent(String pid) {
		return (List<Category>) factory.getCurrentSession()
				.createQuery("FROM Category WHERE pid=? ORDER BY orderBy ASC")
				.setParameter(0, pid).list();
	}
	private void loadChildren(Category category) {
		List<Category> children=findByParent(category.getCid());
		category.setChildren(children);
	}
}
