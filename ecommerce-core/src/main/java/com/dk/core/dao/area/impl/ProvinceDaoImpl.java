package com.dk.core.dao.area.impl;

import org.springframework.stereotype.Repository;

import com.dk.core.common.base.dao.support.SimpleHibernateDao;
import com.dk.core.dao.area.IProvinceDao;
import com.dk.core.model.bean.Province;

/**
 * dao实现类
 */
@Repository
public class ProvinceDaoImpl extends SimpleHibernateDao<Province, Integer>
		implements IProvinceDao {
}
