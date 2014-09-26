package com.dk.core.dao.cart.impl;

import org.springframework.stereotype.Repository;

import com.dk.core.common.base.dao.support.SimpleHibernateDao;
import com.dk.core.dao.cart.ICartDao;
import com.dk.core.model.bean.ShoppingCart;

@Repository
public class CartDaoImpl extends SimpleHibernateDao<ShoppingCart, String>
		implements ICartDao {
}
