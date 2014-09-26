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
@Table(name = "T_SYS_HAT_CITY")
public class City extends IntIdEntity
{
	private String	name;

	private String	code;

	private String	provinceCode;

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

	@Column(name="_PROVINCE_CODE")
	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
}
