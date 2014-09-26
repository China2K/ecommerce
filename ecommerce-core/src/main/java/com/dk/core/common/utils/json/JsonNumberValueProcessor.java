package com.dk.core.common.utils.json;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonNumberValueProcessor implements JsonValueProcessor
{

	@Override
	public Object processObjectValue(String key, Object value, JsonConfig config)
	{
		if (value instanceof Double)
		{
			return value;
		} else if (value instanceof Integer)
		{
			return value;
		} else
		{
			return value == null ? "" : value;
		}
	}

	@Override
	public Object processArrayValue(Object value, JsonConfig arg1)
	{
		String[] obj = {};

		if (value instanceof Double[])
		{
			return value;
		} else if (value instanceof Integer[])
		{
			return value;
		} else
		{
			return value == null ? null : value;
		}
	}
}