package com.dk.core.dao.area.impl;

import org.springframework.stereotype.Repository;

import com.dk.core.common.base.dao.support.SimpleHibernateDao;
import com.dk.core.dao.area.ICityDao;
import com.dk.core.model.bean.City;

/**
 * dao实现类
 */
@Repository
public class CityDaoImpl extends SimpleHibernateDao<City, Integer> implements
		ICityDao {

}
