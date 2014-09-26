package com.dk.core.common.utils.template.impl;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import com.dk.core.common.utils.template.ITemplate;

public class PropertyTemplateImpl implements ITemplate
{
	private boolean i18n = false;
	
	private String propPath = "";
	
	private String language = "zh";
	
	private String country = "CN";
	
	@Override
	public String processTemplate2String(String template, Object... args)
	{
		String text = template;
		
		if (i18n)
		{
			text = this.getLocaleMsg().getString(template);
		}
		
		if (args != null)
		{
			MessageFormat mf = new MessageFormat(text);
			text = (String) mf.format(args);
		}

		return text;
	}

	/**
	 * 获得国际化消息对象
	 * 
	 * @return
	 * @throws
	 */
	private ResourceBundle getLocaleMsg()
	{
		ResourceBundle messages = ResourceBundle.getBundle(propPath, new Locale(language, country));
		return messages;
	}

	public boolean isI18n()
	{
		return i18n;
	}

	public void setI18n(boolean i18n)
	{
		this.i18n = i18n;
	}

	public String getPropPath()
	{
		return propPath;
	}

	public void setPropPath(String propPath)
	{
		this.propPath = propPath;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}
}
