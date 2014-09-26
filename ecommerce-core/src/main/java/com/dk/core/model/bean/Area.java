package com.dk.core.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.dk.core.model.bean.base.IntIdEntity;

/**
 * 地区省实体
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "t_sys_hat_area")
public class Area extends IntIdEntity
{
	private String	name;

	private String	code;

	private String  cityCode;

	@Column(name="_NAME")
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name="_CODE")
	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}
	
	@Column(name="_CITY_CODE")
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	


}
