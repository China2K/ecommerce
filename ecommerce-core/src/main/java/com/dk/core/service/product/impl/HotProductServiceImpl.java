package com.dk.core.service.product.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dk.core.common.base.dao.IGenericDao;
import com.dk.core.common.base.service.support.GenericService;
import com.dk.core.dao.product.IHotProductDao;
import com.dk.core.model.bean.HotProduct;
import com.dk.core.service.product.IHotProductService;

@Service
public class HotProductServiceImpl extends GenericService<HotProduct, String> implements IHotProductService
{
	
	@Resource
	private IHotProductDao hotProductDao;

	@Override
	public IGenericDao<HotProduct, String> getDao() {
		return hotProductDao;
	}
}
