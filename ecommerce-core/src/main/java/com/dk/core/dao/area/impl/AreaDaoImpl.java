package com.dk.core.dao.area.impl;

import org.springframework.stereotype.Repository;

import com.dk.core.common.base.dao.support.SimpleHibernateDao;
import com.dk.core.dao.area.IAreaDao;
import com.dk.core.model.bean.Area;

/**
 * 区级Dao实现类
 */
@Repository
public class AreaDaoImpl extends SimpleHibernateDao<Area, Integer> implements
		IAreaDao {
}
