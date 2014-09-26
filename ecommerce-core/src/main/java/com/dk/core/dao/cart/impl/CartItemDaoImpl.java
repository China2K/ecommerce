package com.dk.core.dao.cart.impl;

import org.springframework.stereotype.Repository;

import com.dk.core.common.base.dao.support.SimpleHibernateDao;
import com.dk.core.dao.cart.ICartItemDao;
import com.dk.core.model.bean.CartItem;

@Repository
public class CartItemDaoImpl extends SimpleHibernateDao<CartItem, String>
		implements ICartItemDao {
}
