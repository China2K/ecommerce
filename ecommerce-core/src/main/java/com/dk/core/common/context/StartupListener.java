package com.dk.core.common.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class StartupListener extends ContextLoaderListener
{
	private Logger logger = Logger.getLogger(StartupListener.class);
	
	private ApplicationContext context;
	
	@Override
	public void contextInitialized(ServletContextEvent event)
	{
		super.contextInitialized(event);
		ServletContext servletContext = event.getServletContext(); 
		context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		logger.info("加载Spring容器到BeanFactory...");
		BeanFactory.getInstance().setContext(context);
	}
}
