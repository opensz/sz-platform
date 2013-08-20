package org.sz.core.service;

import java.io.Serializable;
import java.util.List;

import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;

public interface  GenericService<E, PK extends Serializable> {
//	abstract IEntityDao<E, PK> getEntityDao();

	public void add(E entity);

	public void delById(PK id);

	public void delByIds(PK[] ids);

	public void update(E entity);

	public E getById(PK id);

	public List<E> getList(String statatementName, PageBean pb);

	public List<E> getAll();

	public List<E> getAll(QueryFilter queryFilter);
	
	public List getMapList(String namespace,String sqlKey,Object params);
}
