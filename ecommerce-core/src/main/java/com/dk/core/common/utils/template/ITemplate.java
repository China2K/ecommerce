package com.dk.core.common.utils.template;


public interface ITemplate
{
	/**
	 * 根据模板转换成字符串
	 * @param       
	 * @return     
	 * @throws
	 */
	String processTemplate2String(String template, Object... args);
}
