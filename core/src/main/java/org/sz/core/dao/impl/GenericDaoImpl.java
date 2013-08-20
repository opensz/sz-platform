package org.sz.core.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.sz.core.dao.GenericDao2;
import org.sz.core.dao.IEntityDao;
import org.sz.core.model.BaseModel;
import org.sz.core.mybatis.BaseMyBatisDao;
import org.sz.core.mybatis.IbatisSql;
import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.ContextUtil;

public abstract class GenericDaoImpl<E, PK extends Serializable> extends
		BaseMyBatisDao implements IEntityDao<E, PK>, GenericDao2<E, PK> {
	
	public static final String Oracle = "oracle";
	public static final String MySql = "mysql";
	public static final String SQLServer = "sqlserver";
	public static final String DB2 = "db2";

	@Resource
	protected JdbcTemplate jdbcTemplate;

	@Resource
	Properties configproperties;

	public abstract Class getEntityClass();

	protected String getDbType() {
		return this.configproperties.getProperty("jdbc.dbType");
	}

	public E getById(PK primaryKey) {
		String getStatement = getIbatisMapperNamespace() + ".getById";
		Object object = getSqlSessionTemplate().selectOne(getStatement,
				primaryKey);

		return (E) object;
	}

	public E getUnique(String sqlKey, Object params) {
		String getStatement = getIbatisMapperNamespace() + "." + sqlKey;
		Object object = getSqlSessionTemplate().selectOne(getStatement, params);
		return (E) object;
	}

	public Object getOne(String sqlKey, Object params) {
		String statement = getIbatisMapperNamespace() + "." + sqlKey;
		Object object = getSqlSessionTemplate().selectOne(statement, params);
		return object;
	}

	public List<E> getBySqlKey(String sqlKey, Object params, PageBean pageBean) {
		String statement = getIbatisMapperNamespace() + "." + sqlKey;

		List list = getList(statement, params, pageBean);

		return list;
	}

	public List<E> getBySqlKey(String sqlKey, QueryFilter queryFilter) {
		List list = new ArrayList();
		if (queryFilter.getPageBean() == null) {
			list = getBySqlKey(sqlKey, queryFilter.getFilters());
		} else {
			list = getBySqlKey(sqlKey, queryFilter.getFilters(),
					queryFilter.getPageBean());
		}

		queryFilter.setForWeb();
		return list;
	}

	public List<E> getBySqlKey(String sqlKey, Object params) {
		String statement = getIbatisMapperNamespace() + "." + sqlKey;

		List list = getSqlSessionTemplate().selectList(statement, params);

		return list;
	}

	public List<E> getBySqlKey(String sqlKey) {
		String statement = getIbatisMapperNamespace() + "." + sqlKey;

		List list = getSqlSessionTemplate().selectList(statement);

		return list;
	}

	public int delById(PK id) {
		String delStatement = getIbatisMapperNamespace() + ".delById";
		int affectCount = getSqlSessionTemplate().delete(delStatement, id);
		return affectCount;
	}

	public int delBySqlKey(String sqlKey, Object params) {
		String delStatement = getIbatisMapperNamespace() + "." + sqlKey;
		int affectCount = getSqlSessionTemplate().delete(delStatement, params);
		return affectCount;
	}

	public void add(E entity) {
		String addStatement = getIbatisMapperNamespace() + ".add";

		if ((entity instanceof BaseModel)) {
			BaseModel baseModel = (BaseModel) entity;
			baseModel.setCreatetime(new Date());
			baseModel.setUpdatetime(new Date());

			Long curUserId = ContextUtil.getCurrentUserId();
			if (curUserId != null) {
				baseModel.setCreateBy(curUserId);
				baseModel.setUpdateBy(curUserId);
			}
		}

		getSqlSessionTemplate().insert(addStatement, entity);
	}

	public int update(E entity) {
		String updStatement = getIbatisMapperNamespace() + ".update";

		if ((entity instanceof BaseModel)) {
			BaseModel baseModel = (BaseModel) entity;
			baseModel.setUpdatetime(new Date());

			Long curUserId = ContextUtil.getCurrentUserId();
			if (curUserId != null) {
				baseModel.setUpdateBy(curUserId);
			}
		}

		int affectCount = getSqlSessionTemplate().update(updStatement, entity);
		return affectCount;
	}

	public int update(String sqlKey, Object params) {
		String updStatement = getIbatisMapperNamespace() + "." + sqlKey;
		int affectCount = getSqlSessionTemplate().update(updStatement, params);
		return affectCount;
	}

	public String getIbatisMapperNamespace() {
		return getEntityClass().getName();
	}

	public List<E> getList(String statementName, Object params,
			PageBean pageBean) {
		if (pageBean == null)
			return getList(statementName, params);

		Map filters = new HashMap();

		if (params != null) {
			if ((params instanceof Map)) {
				filters.putAll((Map) params);
			} else {
				Map parameterObject = BeanUtils.describe(params);
				filters.putAll(parameterObject);
			}
		}

		IbatisSql ibatisSql = getIbatisSql(statementName, filters);

		this.log.info(ibatisSql.getSql());

		int totalCount = this.jdbcTemplate.queryForInt(ibatisSql.getCountSql(),
				ibatisSql.getParameters());

		pageBean.setTotalCount(totalCount);
		List list = getSqlSessionTemplate().selectList(statementName, filters,
				pageBean.getFirst(), pageBean.getPageSize());
		return list;
	}

	public List<E> getList(String statementName, Object params) {
		Map filters = new HashMap();
		if (params != null) {
			if ((params instanceof Map)) {
				filters.putAll((Map) params);
			} else {
				Map parameterObject = BeanUtils.describe(params);
				filters.putAll(parameterObject);
			}
		}

		IbatisSql ibatisSql = getIbatisSql(statementName, filters);
		this.log.info(ibatisSql.getSql());
		List list = getSqlSessionTemplate().selectList(statementName, filters);
		return list;
	}

	public List<E> getList(String statementName, QueryFilter queryFilter) {
		List list = null;
		PageBean pageBean = queryFilter.getPageBean();
		Object filters = queryFilter.getFilters();
		if (pageBean != null)
			list = getList(statementName, queryFilter.getFilters(), pageBean);
		else {
			list = getList(statementName, filters);
		}
		return list;
	}

	public List<E> getAll() {
		String statementName = getIbatisMapperNamespace() + ".getAll";
		return getSqlSessionTemplate().selectList(statementName, null);
	}

	public List<E> getAll(QueryFilter queryFilter) {
		String statementName = getIbatisMapperNamespace() + ".getAll";
		List list = getList(statementName, queryFilter);

		queryFilter.setForWeb();
		return list;
	}

	@Override
	public List getMapList(String namespace, String sqlKey, Object params) {
		List list = getSqlSessionTemplate().selectList(
				namespace + "." + sqlKey, params);
		return list;
	}

	static enum SortBy {
		ASC, DESC;
	}
}
