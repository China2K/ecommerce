package com.dk.core.dao.user;

import com.dk.core.common.base.dao.IGenericDao;
import com.dk.core.model.bean.User;

public interface IUserDao extends IGenericDao<User, String>{
	public boolean isNoDeleteExist(String propertyName, Object value,
			String uuid);
}
