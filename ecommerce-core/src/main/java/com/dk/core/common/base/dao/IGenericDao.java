package com.dk.core.common.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.dk.core.common.utils.search.SearchBean;

public interface IGenericDao<T, ID extends Serializable>
{
	/**
	 * 保存新增的对象.
	 */
	ID save(T entity);

	/**
	 * 保存或者更新对象
	 * 
	 * @param entity
	 * @throws
	 */
	void saveOrUpdate(T entity);

	/**
	 * 删除对象.
	 * 
	 * @param entity
	 *            对象必须是session中的对象或含id属性的transient对象.
	 */
	void delete(T entity);

	/**
	 * 按id删除对象.
	 */
	void delete(ID id);

	/**
	 * 按id获取对象.
	 */
	T findById(ID id);

	/**
	 * 按id列表获取对象列表.
	 */
	List<T> find(Collection<ID> ids);

	/**
	 * 获取全部对象.
	 */
	List<T> findAll();

	/**
	 * 获取全部对象, 支持按属性行序.
	 */
	List<T> findAll(String orderByProperty, boolean isAsc);

	/**
	 * 按属性查找对象列表, 匹配方式为相等.
	 */
	List<T> findBy(String propertyName, Object value);

	/**
	 * 按属性查找唯一对象, 匹配方式为相等.
	 */
	T findUniqueBy(String propertyName, Object value);

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	<X> List<X> find(String hql, Object... values);

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	<X> List<X> find(String hql, Map<String, ?> values);

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	<X> X findUnique(String hql, Object... values);

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	<X> X findUnique(String hql, Map<String, ?> values);

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 * @return 更新记录数.
	 */
	int batchExecute(String hql, Object... values);

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 * @return 更新记录数.
	 */
	int batchExecute(String hql, Map<String, ?> values);

	/**
	 * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	Query createQuery(String queryString, Object... values);

	/**
	 * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	Query createQuery(String queryString, Map<String, ?> values);

	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	List<T> find(Criterion... criterions);

	/**
	 * 按Criteria查询唯一对象.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	T findUnique(Criterion... criterions);

	/**
	 * 根据Criterion条件创建Criteria. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	Criteria createCriteria(Criterion... criterions);

	/**
	 * 初始化对象. 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化. 如果传入entity,
	 * 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性. 如需初始化关联属性,需执行:
	 * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
	 * Hibernate.initialize
	 * (user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
	 */
	void initProxyObject(Object proxy);

	/**
	 * Flush当前Session.
	 */
	void flush();

	/**
	 * 为Query添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 */
	Query distinct(Query query);

	/**
	 * 为Criteria添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 */
	Criteria distinct(Criteria criteria);

	/**
	 * 取得对象的主键名.
	 */
	String getIdName();

	/**
	 * 判断对象的属性值在数据库内是否唯一. 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	boolean isPropertyUnique(String propertyName, final Object newValue, final Object oldValue);

	/**
	 * 保存新增的对象.
	 */
	void update(T entity);
	
	/**
	 * 根据条件查询
	 * @param       
	 * @return     
	 * @throws
	 */
	List<T> findByCriteria(DetachedCriteria criteria);
	
	/**
	 * 根据条件进行分页查询
	 * @param       
	 * @return     
	 * @throws
	 */
	List<T> findByCriteria(final DetachedCriteria criteria, final int firstResult, final int maxResults);

	/**
	 * 高级查询
	 * @param       
	 * @return     
	 * @throws
	 */
	List<T> findByPage(Class<T> entity, SearchBean[] searchBean, Order[] order, int start, int limit, String... orderStrs);


	/**
	 * 获取全部记录数
	 * @return
	 */
	int count();
	
	
	int count(SearchBean... searchBeans);
	
	/**
	 * 高级查询取得记录数
	 * @param       
	 * @return     
	 * @throws
	 */
	int count(Class<T> entity, SearchBean[] searchBean);
	
	/**
	 * 合并对象
	 * @param       
	 * @return     
	 * @throws
	 */
	T merge(T obj);
	
	/**
	 * 根据map的<key,value>,返回查询list
	 * @param queryParams
	 * @return
	 */
	List<T> findBy(Map<String, Object> queryParams);
	
	/**
	 * 使用sql本地语法
	 * @param <X>
	 * @param sql
	 * @param values
	 * @return
	 */
	<X> List<X> findSQL(String sql, Object... values);
	
	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	Page<T> findAll(Pageable pageable, SearchBean... searchBeans);
	
	/**
	 * 排序查询
	 * @param sort
	 * @return
	 */
	List<T> findAll(Sort sort, SearchBean... searchBeans);
	
	/**
	 * 根据条件查询所有对象
	 * @param searchBeans
	 * @return
	 */
	List<T> findAll(SearchBean... searchBeans);
	
	long count(String hql, Object... values);
}
