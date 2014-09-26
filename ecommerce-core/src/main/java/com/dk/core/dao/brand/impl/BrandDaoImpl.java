package com.dk.core.dao.brand.impl;

import org.springframework.stereotype.Repository;

import com.dk.core.common.base.dao.support.SimpleHibernateDao;
import com.dk.core.dao.brand.IBrandDao;
import com.dk.core.model.bean.Brand;

@Repository
public class BrandDaoImpl extends SimpleHibernateDao<Brand, String> implements
		IBrandDao {

}
