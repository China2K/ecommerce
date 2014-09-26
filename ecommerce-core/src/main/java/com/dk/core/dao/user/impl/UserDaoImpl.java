package com.dk.core.dao.user.impl;

import org.springframework.stereotype.Repository;

import com.dk.core.common.constant.IConstants;
import com.dk.core.common.utils.GeneralUtils;
import com.dk.core.common.base.dao.support.SimpleHibernateDao;
import com.dk.core.dao.user.IUserDao;
import com.dk.core.model.bean.User;

@Repository
public class UserDaoImpl extends SimpleHibernateDao<User, String> implements IUserDao
{
	@Override
	public boolean isNoDeleteExist(String propertyName, Object value,
			String uuid) {
		StringBuffer hql = new StringBuffer(
				"from User where status!=? and ");
		hql.append(propertyName);
		hql.append("=?");

		// 如果是更新，则相同记录的值可以重复
		if (GeneralUtils.isNotNull(uuid)) {
			hql.append(" and id !='");
			hql.append(uuid);
			hql.append("'");
		}

		return !this.find(hql.toString(),
				IConstants.USER_STATUS.DELETED, value).isEmpty();
	}
	
}
