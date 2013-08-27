package org.sz.core.dao;

import java.io.Serializable;
import java.util.List;

import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;

public interface IEntityDao<E, PK extends Serializable> {
	public void add(E paramE);

	public int delById(PK paramPK);

	public int update(E paramE);

	public E getById(PK paramPK);

	public List<E> getList(String paramString, Object paramObject);

	public List<E> getList(String paramString, Object paramObject,
			PageBean paramPageBean);

	public List<E> getAll();

	public List<E> getAll(QueryFilter paramQueryFilter);

	public E getUnique(String paramString, Object paramObject);

	public List<E> getBySqlKey(String sqlKey);

	public List<E> getBySqlKey(String sqlKey, Object params);

	public List<E> getBySqlKey(String sqlKey, QueryFilter queryFilter);

	public List<E> getBySqlKey(String sqlKey, Object params, PageBean pageBean);

	public int delBySqlKey(String sqlKey, Object params);

	public List getMapList(String namespace, String sqlKey, Object params);
}
