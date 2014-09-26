package com.dk.core.dao.order.impl;

import org.springframework.stereotype.Repository;

import com.dk.core.common.base.dao.support.SimpleHibernateDao;
import com.dk.core.dao.order.IOrderDao;
import com.dk.core.model.bean.Order;

@Repository
public class OrderDaoImpl extends SimpleHibernateDao<Order, String> implements IOrderDao
{
}
