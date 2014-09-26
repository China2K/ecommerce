package com.dk.core.service.order.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dk.core.common.base.dao.IGenericDao;
import com.dk.core.common.base.service.support.GenericService;
import com.dk.core.dao.order.IOrderDao;
import com.dk.core.model.bean.Order;
import com.dk.core.service.order.IOrderService;

@Service
public class OrderServiceImpl extends GenericService<Order, String> implements IOrderService
{
	@Resource
	private IOrderDao orderDao;
	@Override
	public IGenericDao<Order, String> getDao() {
		return orderDao;
	}
}
