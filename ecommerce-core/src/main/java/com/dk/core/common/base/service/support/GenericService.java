package com.dk.core.common.base.service.support;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.dk.core.common.utils.search.SearchBean;
import com.dk.core.common.base.dao.IGenericDao;
import com.dk.core.common.base.service.IGenericService;

public abstract class GenericService<T, ID extends Serializable> implements
		IGenericService<T, ID> {
	
	public abstract IGenericDao<T, ID> getDao();

	@Override
	@Transactional
	public long count() {
		return getDao().count();
	}

	@Override
	@Transactional
	public void delete(ID id) {
		getDao().delete(id);
	}

	@Override
	@Transactional
	public void delete(T entity) {
		getDao().delete(entity);
	}


	@Override
	@Transactional
	public List<T> findAll() {
		return getDao().findAll();
	}

	@Override
	@Transactional
	public List<T> find(Collection<ID> ids) {
		return getDao().find(ids);
	}

	@Override
	@Transactional
	public List<T> findAll(Sort sort) {
		return getDao().findAll(sort);
	}

	@Override
	@Transactional
	public Page<T> findAll(Pageable pageable) {
		return getDao().findAll(pageable);
	}

	@Override
	@Transactional
	public T findOne(ID id) {
		return getDao().findById(id);
	}

	@Override
	@Transactional
	public ID save(T entity) {
		return getDao().save(entity);
	}

	@Override
	@Transactional
	public void update(T entity) {
		getDao().update(entity);
	}

	@Override
	@Transactional
	public Page<T> findByPage(Pageable pageable, SearchBean[] searchBeans){
		return getDao().findAll(pageable, searchBeans);
	}
	
	@Override
	@Transactional
	public List<T> findAll(SearchBean...searchBeans){
		return getDao().findAll(searchBeans);
	}

	@Override
	@Transactional
	public List<T> findAll(Sort sort, SearchBean... searchBeans){
		return getDao().findAll(sort, searchBeans);
	}
}
