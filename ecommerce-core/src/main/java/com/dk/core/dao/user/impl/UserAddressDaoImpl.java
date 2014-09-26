package com.dk.core.dao.user.impl;

import org.springframework.stereotype.Repository;

import com.dk.core.common.base.dao.support.SimpleHibernateDao;
import com.dk.core.dao.user.IUserAddressDao;
import com.dk.core.model.bean.UserAddress;

@Repository
public class UserAddressDaoImpl extends SimpleHibernateDao<UserAddress, String> implements IUserAddressDao
{
	
	
}
