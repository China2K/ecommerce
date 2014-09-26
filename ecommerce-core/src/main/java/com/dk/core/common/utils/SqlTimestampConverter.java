package com.dk.core.common.utils;

import java.sql.Timestamp;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SqlTimestampConverter implements Converter
{

	private Log	log	= LogFactory.getLog(this.getClass());

	public SqlTimestampConverter()
	{

		this.defaultValue = null;
		this.useDefault = false;
	}

	public SqlTimestampConverter(Object defaultValue)
	{

		this.defaultValue = defaultValue;
		this.useDefault = true;
	}

	private Object	defaultValue	= null;

	private boolean	useDefault		= true;

	public Object convert(Class type, Object value)
	{

		if (value == null || "".equals(value))
		{
			if (useDefault)
			{
				return (defaultValue);
			} else
			{
				throw new ConversionException("No value specified");
			}
		}

		if (value instanceof Timestamp)
		{
			return (value);
		}

		try
		{
			return (Timestamp.valueOf(value.toString()));
		} catch (Exception e)
		{
			log.error("convert error ocured.", e);
			if (useDefault)
			{
				return (defaultValue);
			} else
			{
				throw new ConversionException(e);
			}
		}
	}

}
