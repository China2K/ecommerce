package com.dk.core.service.product.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dk.core.common.base.dao.IGenericDao;
import com.dk.core.common.base.service.support.GenericService;
import com.dk.core.dao.product.IProductEnshrineDao;
import com.dk.core.model.bean.ProductEnshrineLog;
import com.dk.core.service.product.IProductEnshrineService;

@Service
public class ProductEnshrineServiceImpl extends GenericService<ProductEnshrineLog, String> implements IProductEnshrineService
{

	@Resource
	private IProductEnshrineDao productEnshrineDao;
	
	@Override
	public IGenericDao<ProductEnshrineLog, String> getDao() {
		return productEnshrineDao;
	}
}
