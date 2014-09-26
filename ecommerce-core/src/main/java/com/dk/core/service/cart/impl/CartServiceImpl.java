package com.dk.core.service.cart.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dk.core.common.base.dao.IGenericDao;
import com.dk.core.common.base.service.support.GenericService;
import com.dk.core.dao.cart.ICartDao;
import com.dk.core.model.bean.ShoppingCart;
import com.dk.core.service.cart.ICartService;

@Service
public class CartServiceImpl extends GenericService<ShoppingCart, String>
		implements ICartService {
	@Resource
	private ICartDao cartDao;

	@Override
	public IGenericDao<ShoppingCart, String> getDao() {
		return cartDao;
	}
}
