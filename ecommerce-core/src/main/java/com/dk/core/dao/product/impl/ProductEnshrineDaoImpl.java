package com.dk.core.dao.product.impl;

import org.springframework.stereotype.Repository;

import com.dk.core.common.base.dao.support.SimpleHibernateDao;
import com.dk.core.dao.product.IProductEnshrineDao;
import com.dk.core.model.bean.ProductEnshrineLog;

@Repository
public class ProductEnshrineDaoImpl extends SimpleHibernateDao<ProductEnshrineLog, String> implements IProductEnshrineDao
{
}
