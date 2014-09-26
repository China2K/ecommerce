package com.dk.core.service.product.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dk.core.common.base.dao.IGenericDao;
import com.dk.core.common.base.service.support.GenericService;
import com.dk.core.dao.product.IProductInstDao;
import com.dk.core.model.bean.ProductInstance;
import com.dk.core.service.product.IProductInstService;

@Service
public class ProductInstServiceImpl extends GenericService<ProductInstance, String> implements IProductInstService
{

	@Resource
	private IProductInstDao productInstDao;
	
	@Override
	public IGenericDao<ProductInstance, String> getDao() {
		return productInstDao;
	}
}
