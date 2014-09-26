package com.dk.core.common.utils.json;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface IJsonConvert
{
	
	public JSONObject toJson(Object o);
	
	public JSONArray toJson(List list);
	
	public JSONObject jsonWrap(List list,String prifix);

}
