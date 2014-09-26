package com.dk.core.common.base.dao.support;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import com.dk.core.common.base.dao.AbstractSessionBaseDao;
import com.dk.core.common.base.dao.SessionFactoryName;
import com.dk.core.common.utils.ReflectionUtils;
import com.dk.core.common.utils.search.SearchBean;
import com.dk.core.common.utils.search.SearchFactory;
import com.dk.core.common.base.dao.IGenericDao;

/**
 * 封装Hibernate原生API的DAO泛型基类. 可在Service层直接使用, 也可以扩展泛型DAO子类使用, 见两个构造函数的注释.
 * 参考Spring2.5自带的Petlinc例子, 取消了HibernateTemplate, 直接使用Hibernate原生API.
 * 
 * @param <T>
 *            DAO操作的对象类型
 * @param <ID>
 *            主键类型
 */
@SuppressWarnings("unchecked")
public class SimpleHibernateDao<T, ID extends Serializable> extends AbstractSessionBaseDao implements IGenericDao<T, ID>
{

	protected Logger	logger	= LoggerFactory.getLogger(getClass());

	protected Class<T>	entityClass;

	/**
	 * 用于Dao层子类使用的构造函数. 通过子类的泛型定义取得对象类型Class. eg. public class UserDao extends
	 * SimpleHibernateDao<User, Long>
	 */
	public SimpleHibernateDao()
	{
		this.entityClass = ReflectionUtils.getClassGenricType(getClass());
	}

	/**
	 * 用于用于省略Dao层, 在Service层直接使用通用SimpleHibernateDao的构造函数. 在构造函数中定义对象类型Class.
	 * eg. SimpleHibernateDao<User, Long> userDao = new SimpleHibernateDao<User,
	 * Long>(sessionFactory, User.class);
	 */
	public SimpleHibernateDao(final SessionFactory sessionFactory, final Class<T> entityClass)
	{
		this.entityClass = entityClass;
	}

	protected Session getSession()
	{
		return getCurrentSession(SessionFactoryName.SessionFactory);
	}

	protected SessionFactory getSessionFactory()
	{
		return getCurrentSessionFactory(SessionFactoryName.SessionFactory);
	}

	public ID save(T entity)
	{
		Assert.notNull(entity, "entity不能为空");
		logger.debug("save entity: {}", entity);
		return (ID) getSession().save(entity);
	}

	public void saveOrUpdate(T entity)
	{
		Assert.notNull(entity, "entity不能为空");
		getSession().saveOrUpdate(entity);
		logger.debug("save entity: {}", entity);
	}

	public void delete(T entity)
	{
		Assert.notNull(entity, "entity不能为空");
		getSession().delete(entity);
		logger.debug("delete entity: {}", entity);
	}

	public void delete(ID id)
	{
		Assert.notNull(id, "id不能为空");
		delete(findById(id));
		logger.debug("delete entity {},id is {}", entityClass.getSimpleName(), id);
	}

	public T findById(ID id)
	{
		Assert.notNull(id, "id不能为空");
		return (T) getSession().get(entityClass, id);
	}

	public List<T> find(Collection<ID> ids)
	{
		return find(Restrictions.in(getIdName(), ids));
	}

	public List<T> findAll()
	{
		return find();
	}

	public List<T> findAll(String orderByProperty, boolean isAsc)
	{
		Criteria c = createCriteria();
		if (isAsc)
		{
			c.addOrder(Order.asc(orderByProperty));
		} else
		{
			c.addOrder(Order.desc(orderByProperty));
		}
		return c.list();
	}

	public List<T> findBy(String propertyName, Object value)
	{
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	public List<T> findBy(Map<String, Object> queryParams)
	{
		Criteria criteria = getSession().createCriteria(entityClass);

		for (String key : queryParams.keySet())
		{
			Object value = queryParams.get(key);
			Criterion criterion = Restrictions.eq(key, value);
			criteria.add(criterion);
		}

		return criteria.list();
	}

	public T findUniqueBy(String propertyName, Object value)
	{
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	public <X> List<X> find(String hql, Object... values)
	{
		return createQuery(hql, values).list();
	}

	public <X> List<X> findSQL(String sql, Object... values)
	{
		return createSQLQuery(sql, values).list();
	}

	public <X> List<X> find(String hql, Map<String, ?> values)
	{
		return createQuery(hql, values).list();
	}

	public <X> X findUnique(String hql, Object... values)
	{
		return (X) createQuery(hql, values).uniqueResult();
	}

	public <X> X findUnique(String hql, Map<String, ?> values)
	{
		return (X) createQuery(hql, values).uniqueResult();
	}

	public int batchExecute(String hql, Object... values)
	{
		return createQuery(hql, values).executeUpdate();
	}

	public int batchExecute(String hql, Map<String, ?> values)
	{
		return createQuery(hql, values).executeUpdate();
	}

	public Query createQuery(String queryString, Object... values)
	{
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null)
		{
			for (int i = 0; i < values.length; i++)
			{
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	public Query createSQLQuery(String queryString, Object... values)
	{
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createSQLQuery(queryString);
		if (values != null)
		{
			for (int i = 0; i < values.length; i++)
			{
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	public Query createQuery(String queryString, Map<String, ?> values)
	{
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null)
		{
			query.setProperties(values);
		}
		return query;
	}

	public List<T> find(Criterion... criterions)
	{
		return createCriteria(criterions).list();
	}

	public T findUnique(Criterion... criterions)
	{
		return (T) createCriteria(criterions).uniqueResult();
	}

	public Criteria createCriteria(Criterion... criterions)
	{
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions)
		{
			criteria.add(c);
		}
		return criteria;
	}

	public void initProxyObject(Object proxy)
	{
		Hibernate.initialize(proxy);
	}

	public void flush()
	{
		getSession().flush();
	}

	public Query distinct(Query query)
	{
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	public Criteria distinct(Criteria criteria)
	{
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	public String getIdName()
	{
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

	public boolean isPropertyUnique(String propertyName, final Object newValue, final Object oldValue)
	{
		if (newValue == null || newValue.equals(oldValue))
		{
			return true;
		}
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}

	public void update(T entity)
	{
		Assert.notNull(entity, "entity不能为空");
		getSession().update(entity);
		logger.debug("update entity: {}", entity);
	}

	public Session openSession()
	{
		return getSessionFactory().openSession();
	}

	public List<T> findByCriteria(DetachedCriteria criteria)
	{
		return findByCriteria(criteria, -1, -1);
	}

	public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults)
	{
		Criteria executableCriteria = criteria.getExecutableCriteria(getSession());
		if (firstResult >= 0)
		{
			executableCriteria.setFirstResult(firstResult);
		}
		if (maxResults > 0)
		{
			executableCriteria.setMaxResults(maxResults);
		}
		return executableCriteria.list();
	}

	@Override
	public T merge(T obj)
	{
		return (T) getSession().merge(obj);
	}

	@Override
	public int count()
	{
		Criteria criteria = createCriteria();
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.list().get(0);
	}

	@Override
	public int count(SearchBean... searchBeans)
	{
		return count(entityClass, searchBeans);
	}

	@Override
	public int count(Class<T> entity, SearchBean[] searchBean)
	{
		DetachedCriteria criteria = SearchFactory.generateCriteria(entity, searchBean);
		criteria.setProjection(Projections.rowCount());
		return ((Long) findByCriteria(criteria).get(0)).intValue();
	}

	@Override
	public Page<T> findAll(Pageable pageable, SearchBean... searchBeans)
	{
		if (pageable == null)
		{
			return new PageImpl<T>(findAll(searchBeans));
		}

		DetachedCriteria criteria = SearchFactory.generateCriteria(entityClass, searchBeans);
		Sort sort = pageable.getSort();
		if (sort != null)
		{
			addCriteriaOrder(criteria, sort);
		}
		long total = count(searchBeans);
		List<T> content = total > pageable.getOffset() ? findByCriteria(criteria, pageable.getOffset(), pageable.getPageSize()) : Collections.<T> emptyList();
		return new PageImpl<T>(content, pageable, total);
	}

	@Override
	public List<T> findAll(Sort sort, SearchBean... searchBeans)
	{
		DetachedCriteria criteria = SearchFactory.generateCriteria(entityClass, searchBeans);
		if (sort != null)
		{
			addCriteriaOrder(criteria, sort);
		}
		return findByCriteria(criteria);
	}

	@Override
	public List<T> findByPage(Class<T> entity, SearchBean[] searchBean, Order[] order, int start, int limit, String... orderStrs)
	{
		DetachedCriteria criteria = SearchFactory.generateCriteria(entity, searchBean);
		if (order != null && order.length > 0)
		{
			for (Order o : order)
			{
				criteria.addOrder(o);
			}
		}

		for (String orderStr : orderStrs)
		{
			criteria.addOrder(Property.forName(orderStr).desc());
		}

		return findByCriteria(criteria, start, limit);
	}

	@Override
	public List<T> findAll(SearchBean... searchBeans)
	{
		DetachedCriteria criteria = SearchFactory.generateCriteria(entityClass, searchBeans);
		return findByCriteria(criteria);
	}
	
	protected Criteria addCriteriaOrder(Criteria criteria, Sort sort)
	{
		Assert.notNull(criteria, "criteria不可以为NULL");
		if (sort != null)
		{
			for (org.springframework.data.domain.Sort.Order order : sort)
			{
				criteria.addOrder(order.isAscending() ? Order.asc(order.getProperty()) : Order.desc(order.getProperty()));
			}
		}
		return criteria;
	}

	protected DetachedCriteria addCriteriaOrder(DetachedCriteria criteria, Sort sort)
	{
		Assert.notNull(criteria, "criteria不可以为NULL");
		if (sort != null)
		{
			for (org.springframework.data.domain.Sort.Order order : sort)
			{
				criteria.addOrder(order.isAscending() ? Order.asc(order.getProperty()) : Order.desc(order.getProperty()));
			}
		}
		return criteria;
	}

	@Override
	public long count(String hql, Object... values)
	{
		Query query = createQuery(hql, values);
		return ((Number)query.uniqueResult()).longValue();
	}
}