package com.dk.core.dao.product.impl;

import org.springframework.stereotype.Repository;

import com.dk.core.common.base.dao.support.SimpleHibernateDao;
import com.dk.core.dao.product.IHotProductDao;
import com.dk.core.model.bean.HotProduct;

@Repository
public class HotProductDaoImpl extends SimpleHibernateDao<HotProduct, String> implements IHotProductDao
{
}
