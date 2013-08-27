package org.sz.core.service.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.core.dao.IEntityDao;
import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;
import org.sz.core.service.GenericService;
import org.sz.core.util.BeanUtils;

public abstract class GenericServiceImpl<E, PK extends Serializable> implements
		GenericService<E, PK> {
	protected Logger log = LoggerFactory.getLogger(GenericServiceImpl.class);

	protected abstract IEntityDao<E, PK> getEntityDao();

	public void add(E entity) {
		getEntityDao().add(entity);
	}

	public void delById(PK id) {
		getEntityDao().delById(id);
	}

	public void delByIds(PK[] ids) {
		if (BeanUtils.isEmpty(ids))
			return;
		for (Serializable p : ids)
			delById((PK) p);
	}

	public void update(E entity) {
		getEntityDao().update(entity);
	}

	public E getById(PK id) {
		return (E) getEntityDao().getById(id);
	}

	public List<E> getList(String statatementName, PageBean pb) {
		List list = getEntityDao().getList(statatementName, pb);
		return list;
	}

	public List<E> getAll() {
		return getEntityDao().getAll();
	}

	public List<E> getAll(QueryFilter queryFilter) {
		return getEntityDao().getAll(queryFilter);
	}

	@Override
	public List getMapList(String namespace, String sqlKey, Object params) {
		return this.getEntityDao().getMapList(namespace, sqlKey, params);
		// return null;
	}

}
