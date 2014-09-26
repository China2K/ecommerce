package com.dk.core.dao.category.impl;

import org.springframework.stereotype.Repository;

import com.dk.core.common.base.dao.support.SimpleHibernateDao;
import com.dk.core.dao.category.ICategoryDao;
import com.dk.core.model.bean.Category;

@Repository
public class CategoryDaoImpl extends SimpleHibernateDao<Category, String> implements ICategoryDao
{
}
