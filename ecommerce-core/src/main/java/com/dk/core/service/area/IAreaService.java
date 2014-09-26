package com.dk.core.service.area;

import java.util.List;

import com.dk.core.common.base.service.IGenericService;
import com.dk.core.model.bean.Area;
import com.dk.core.model.bean.City;
import com.dk.core.model.bean.Province;

/**
 * 区级Service
 */
public interface IAreaService extends IGenericService<Area, Integer> {
	public List<Area> findAllAreas();
	public List<Province> findAllProvinces();
	public List<City> findAllCities();
	public List<City> findCities(String provinceCode);
	public List<Area> findAreas(String cityCode);
	public Area findAreaByCode(String code);
	public City findCityByCode(String code);
	public Province findProvinceByCode(String code);
}
