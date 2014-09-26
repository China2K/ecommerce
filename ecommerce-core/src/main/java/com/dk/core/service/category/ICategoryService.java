package com.dk.core.service.category;

import java.util.List;

import com.dk.core.common.base.service.IGenericService;
import com.dk.core.model.bean.Category;

public interface ICategoryService extends IGenericService<Category, String>{
	public List<Category> findAllIndexCategories();
}
