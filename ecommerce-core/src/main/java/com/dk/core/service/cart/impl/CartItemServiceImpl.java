package com.dk.core.service.cart.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dk.core.common.base.dao.IGenericDao;
import com.dk.core.common.base.service.support.GenericService;
import com.dk.core.dao.cart.ICartItemDao;
import com.dk.core.model.bean.CartItem;
import com.dk.core.service.cart.ICartItemService;

@Service
public class CartItemServiceImpl extends GenericService<CartItem, String>
		implements ICartItemService {

	@Resource
	private ICartItemDao cartItemDao;
	
	@Override
	public IGenericDao<CartItem, String> getDao() {
		return cartItemDao;
	}
}
