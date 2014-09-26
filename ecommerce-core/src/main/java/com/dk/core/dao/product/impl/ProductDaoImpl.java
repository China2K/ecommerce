package com.dk.core.dao.product.impl;

import org.springframework.stereotype.Repository;

import com.dk.core.common.base.dao.support.SimpleHibernateDao;
import com.dk.core.dao.product.IProductDao;
import com.dk.core.model.bean.Product;

@Repository
public class ProductDaoImpl extends SimpleHibernateDao<Product, String> implements IProductDao
{
}
