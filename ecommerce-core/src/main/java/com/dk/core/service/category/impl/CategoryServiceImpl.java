package com.dk.core.service.category.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dk.core.common.constant.IConstants;
import com.dk.core.common.utils.search.SearchBean;
import com.dk.core.common.base.dao.IGenericDao;
import com.dk.core.common.base.service.support.GenericService;
import com.dk.core.dao.category.ICategoryDao;
import com.dk.core.model.bean.Category;
import com.dk.core.service.category.ICategoryService;

@Service
public class CategoryServiceImpl extends GenericService<Category, String>
		implements ICategoryService {
	@Resource
	private ICategoryDao categoryDao;

	@Override
	public IGenericDao<Category, String> getDao() {
		return categoryDao;
	}

	@Override
	public List<Category> findAllIndexCategories() {
		SearchBean searchParam1 = new SearchBean("parent", null, "isNull");
		SearchBean searchParam2 = new SearchBean("status",
				IConstants.CATEGORY_STATUS.ACTIVE, "=");
		List<Category> categries = categoryDao.findAll(new Sort(
				Sort.Direction.ASC, "order"), searchParam1, searchParam2);
		return categries;
	}
}
