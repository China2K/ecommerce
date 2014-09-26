package com.dk.core.service.area.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dk.core.common.base.dao.IGenericDao;
import com.dk.core.common.base.service.support.GenericService;
import com.dk.core.dao.area.IAreaDao;
import com.dk.core.dao.area.ICityDao;
import com.dk.core.dao.area.IProvinceDao;
import com.dk.core.model.bean.Area;
import com.dk.core.model.bean.City;
import com.dk.core.model.bean.Province;
import com.dk.core.service.area.IAreaService;

/**
 * 区级Service实现类
 */
@Service
public class AreaServiceImpl extends GenericService<Area, Integer> implements
		IAreaService {
	
	
	@Resource
	private IProvinceDao provinceDao;
	
	@Resource
	private ICityDao cityDao;
	
	@Resource
	private IAreaDao areaDao;

	@Override
	public IGenericDao<Area, Integer> getDao() {
		return areaDao;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Area> findAllAreas() {
		return areaDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<Province> findAllProvinces() {
		return provinceDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<City> findAllCities() {
		return cityDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<City> findCities(String provinceCode) {
		return cityDao.findBy("provinceCode", provinceCode);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Area> findAreas(String cityCode) {
		return areaDao.findBy("cityCode", cityCode);
	}

	@Override
	@Transactional(readOnly=true)
	public Area findAreaByCode(String code) {
		return areaDao.findUniqueBy("code", code);
	}

	@Override
	@Transactional(readOnly=true)
	public City findCityByCode(String code) {
		return cityDao.findUniqueBy("code", code);
	}

	@Override
	@Transactional(readOnly=true)
	public Province findProvinceByCode(String code) {
		return provinceDao.findUniqueBy("code", code);
	}


}
