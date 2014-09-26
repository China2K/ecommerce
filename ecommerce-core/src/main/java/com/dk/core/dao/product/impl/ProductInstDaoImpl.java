package com.dk.core.dao.product.impl;

import org.springframework.stereotype.Repository;

import com.dk.core.common.base.dao.support.SimpleHibernateDao;
import com.dk.core.dao.product.IProductInstDao;
import com.dk.core.model.bean.ProductInstance;

@Repository
public class ProductInstDaoImpl extends SimpleHibernateDao<ProductInstance, String> implements IProductInstDao
{
}
