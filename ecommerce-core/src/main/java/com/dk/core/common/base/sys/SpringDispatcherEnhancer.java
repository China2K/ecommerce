package com.dk.core.common.base.sys;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;

import com.dk.core.common.upload.FileUploadHandler;

public class SpringDispatcherEnhancer extends DispatcherServlet {

	private static final Logger LOGGER = Logger
			.getLogger(SpringDispatcherEnhancer.class);

	private static final long serialVersionUID = 1L;
	private static ApplicationContext wac;
	public static final String WebApplicationContextName = "WebApplicationContextEnhancer";
	private static final List<String> BEANFACTORY_KEYLIST = new ArrayList<String>();

	public static String imageUrlPrefix = "";

	static {
		InputStream is = SpringDispatcherEnhancer.class
				.getResourceAsStream("/upload.properties");
		if (is != null) {
			Properties properties = new Properties();
			try {
				properties.load(is);
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
				e.printStackTrace();
			}
			imageUrlPrefix = FileUploadHandler.UPLOAD_CONFIG.getPicUrlPrefix()
					+ FileUploadHandler.UPLOAD_CONFIG.getImgVirtualDir() + "?"
					+ FileUploadHandler.UPLOAD_CONFIG.getDownloadParamName()
					+ "=";
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String attrName = getServletContextAttributeName();
		wac = WebApplicationContextUtils.getWebApplicationContext(
				getServletContext(), attrName);
		config.getServletContext().setAttribute(WebApplicationContextName, wac);
		config.getServletContext().setAttribute("contextPath",
				config.getServletContext().getContextPath());
		config.getServletContext().setAttribute("imageUrlPrefix", imageUrlPrefix);
		try {
			Field bf = AbstractRefreshableApplicationContext.class
					.getDeclaredField("beanFactory");
			bf.setAccessible(true);
			Field objectMaps = DefaultSingletonBeanRegistry.class
					.getDeclaredField("singletonObjects");
			objectMaps.setAccessible(true);
			Field parentBF = AbstractBeanFactory.class
					.getDeclaredField("parentBeanFactory");
			parentBF.setAccessible(true);
			DefaultListableBeanFactory dbf = (DefaultListableBeanFactory) bf
					.get(wac);
			DefaultListableBeanFactory p_dbf = (DefaultListableBeanFactory) parentBF
					.get(dbf);
			while (p_dbf != null) {
				Map<String, Object> singletonObjects = (Map<String, Object>) objectMaps
						.get(p_dbf);
				Set<String> keyset = singletonObjects.keySet();
				Iterator<String> iterator = keyset.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					BEANFACTORY_KEYLIST.add(key);
					// System.out.println(key);
				}
				p_dbf = (DefaultListableBeanFactory) parentBF.get(p_dbf);
			}
			Map<String, Object> singletonObjects = (Map<String, Object>) objectMaps
					.get(dbf);
			Set<String> keyset = singletonObjects.keySet();
			Iterator<String> iterator = keyset.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				BEANFACTORY_KEYLIST.add(key);
				// System.out.println(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setWac(ApplicationContext wac) {
		SpringDispatcherEnhancer.wac = wac;
	}

	public static ApplicationContext getDispatcherApplicationContext() {
		return wac;
	}

	public static <T> T getOriginalBean(Class<T> interfaceClass) {
		if (wac == null) {
			return null;
		}
		return wac.getBean(interfaceClass);
	}

	public static Object getOriginalBean(String className) {
		if (wac == null) {
			return null;
		}
		return wac.getBean(className);
	}

}
