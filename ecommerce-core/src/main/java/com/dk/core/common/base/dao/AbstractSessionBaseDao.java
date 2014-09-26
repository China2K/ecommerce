package com.dk.core.common.base.dao;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class AbstractSessionBaseDao {

	private Logger logger = Logger.getLogger(AbstractSessionBaseDao.class);

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	@Resource(name = "dataSource")
	protected DataSource dataSource;

	private static final Map<String, SessionFactory> SESSIONFACTORIES = new HashMap<String, SessionFactory>();

	protected Session getCurrentSession(SessionFactoryName sessionFactoryName) {
		return getSessionFactoryByName(sessionFactoryName.getName())
				.getCurrentSession();
	}

	protected SessionFactory getCurrentSessionFactory(SessionFactoryName sessionFactoryName){
		return getSessionFactoryByName(sessionFactoryName.getName());
	}
	
	private SessionFactory getSessionFactoryByName(String name) {
		SessionFactory sessionFactory = SESSIONFACTORIES.get(name);
		if (sessionFactory == null) {
			try {
				Field f = AbstractSessionBaseDao.class.getDeclaredField(name);
				f.setAccessible(true);
				sessionFactory = SessionFactory.class.cast(f.get(this));
				SESSIONFACTORIES.put(name, sessionFactory);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		}
		return sessionFactory;
	}

}
