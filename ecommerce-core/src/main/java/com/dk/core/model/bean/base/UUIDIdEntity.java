package com.dk.core.model.bean.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;


@SuppressWarnings("serial")
@MappedSuperclass
public abstract class UUIDIdEntity implements IdEntity
{
	public String id;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	public String getId()
	{
		return id;
	}

	public void setId(final String id)
	{
		this.id = id;
	}
	
}
