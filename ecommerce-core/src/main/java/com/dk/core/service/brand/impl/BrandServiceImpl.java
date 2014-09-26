package com.dk.core.service.brand.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dk.core.common.base.dao.IGenericDao;
import com.dk.core.common.base.service.support.GenericService;
import com.dk.core.dao.brand.IBrandDao;
import com.dk.core.model.bean.Brand;
import com.dk.core.service.brand.IBrandService;

@Service
public class BrandServiceImpl extends GenericService<Brand, String> implements
		IBrandService {

	@Resource
	private IBrandDao brandDao;

	@Override
	public IGenericDao<Brand, String> getDao() {
		return brandDao;
	}

}
