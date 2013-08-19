package org.sz.core.mybatis;

import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.dao.support.DaoSupport;
import org.springframework.util.Assert;

public abstract class BaseMyBatisDao extends DaoSupport {
	protected final Log log;
	private SqlSessionFactory sqlSessionFactory;
	private SqlSessionTemplate sqlSessionTemplate;

	public BaseMyBatisDao() {
		this.log = LogFactory.getLog(getClass());
	}

	protected void checkDaoConfig() throws IllegalArgumentException {
		Assert.notNull(this.sqlSessionFactory,
				"sqlSessionFactory must be not null");
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return this.sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return this.sqlSessionTemplate;
	}

	public IbatisSql getIbatisSql(String id, Object parameterObject) {
		IbatisSql ibatisSql = new IbatisSql();

		Collection coll = this.sqlSessionFactory.getConfiguration()
				.getMappedStatementNames();
		MappedStatement ms = this.sqlSessionFactory.getConfiguration()
				.getMappedStatement(id);
		BoundSql boundSql = ms.getBoundSql(parameterObject);

		List ResultMaps = ms.getResultMaps();
		if ((ResultMaps != null) && (ResultMaps.size() > 0)) {
			ResultMap ResultMap = (ResultMap) ms.getResultMaps().get(0);
			ibatisSql.setResultClass(ResultMap.getType());
		}

		ibatisSql.setSql(boundSql.getSql());

		List parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Object[] parameterArray = new Object[parameterMappings.size()];
			MetaObject metaObject = parameterObject == null ? null : MetaObject
					.forObject(parameterObject, new DefaultObjectFactory(),new DefaultObjectWrapperFactory());
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = (ParameterMapping) parameterMappings
						.get(i);
				if (parameterMapping.getMode() == ParameterMode.OUT)
					continue;
				String propertyName = parameterMapping.getProperty();
				PropertyTokenizer prop = new PropertyTokenizer(propertyName);
				Object value;
				if (parameterObject == null) {
					value = null;
				} else {
					if (ms.getConfiguration().getTypeHandlerRegistry()
							.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else {
						if (boundSql.hasAdditionalParameter(propertyName)) {
							value = boundSql
									.getAdditionalParameter(propertyName);
						} else if ((propertyName.startsWith("__frch_"))
								&& (boundSql.hasAdditionalParameter(prop
										.getName()))) {
							value = boundSql.getAdditionalParameter(prop
									.getName());
							if (value != null)
								value = MetaObject.forObject(value, new DefaultObjectFactory(),new DefaultObjectWrapperFactory()).getValue(
										propertyName.substring(prop.getName()
												.length()));
						} else {
							value = metaObject == null ? null : metaObject
									.getValue(propertyName);
						}
					}
				}
				parameterArray[i] = value;
			}

			ibatisSql.setParameters(parameterArray);
		}

		return ibatisSql;
	}

	public static abstract interface SqlSessionCallback {
		public abstract Object doInSession(SqlSession paramSqlSession);
	}

	public static class SqlSessionTemplate {
		SqlSessionFactory sqlSessionFactory;

		public SqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
			this.sqlSessionFactory = sqlSessionFactory;
		}

		public Object execute(BaseMyBatisDao.SqlSessionCallback action) {
			SqlSession session = null;
			try {
				session = this.sqlSessionFactory.openSession();
				Object result = action.doInSession(session);
				Object localObject1 = result;
				return localObject1;
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}

		public int insert(final String statement, final Object parameter) {
			return ((Integer) execute(new BaseMyBatisDao.SqlSessionCallback() {
				public Object doInSession(SqlSession session) {
					return Integer
							.valueOf(session.insert(statement, parameter));
				}
			})).intValue();
		}

		public int delete(final String statement, final Object parameter) {
			return ((Integer) execute(new BaseMyBatisDao.SqlSessionCallback() {
				public Object doInSession(SqlSession session) {
					return Integer
							.valueOf(session.delete(statement, parameter));
				}
			})).intValue();
		}

		public int update(final String statement, final Object parameter) {
			return ((Integer) execute(new BaseMyBatisDao.SqlSessionCallback() {
				public Object doInSession(SqlSession session) {
					return Integer
							.valueOf(session.update(statement, parameter));
				}
			})).intValue();
		}

		public Object selectOne(final String statement, final Object parameter) {
			return execute(new BaseMyBatisDao.SqlSessionCallback() {
				public Object doInSession(SqlSession session) {
					return session.selectOne(statement, parameter);
				}
			});
		}

		public List selectList(final String statement, final Object parameter) {
			return (List) execute(new BaseMyBatisDao.SqlSessionCallback() {
				public Object doInSession(SqlSession session) {
					return session.selectList(statement, parameter);
				}
			});
		}

		public List selectList(final String statement) {
			return (List) execute(new BaseMyBatisDao.SqlSessionCallback() {
				public Object doInSession(SqlSession session) {
					return session.selectList(statement);
				}
			});
		}

		public List selectList(final String statement, final Object parameter,
				final int offset, final int limit) {
			return (List) execute(new BaseMyBatisDao.SqlSessionCallback() {
				public Object doInSession(SqlSession session) {
					return session.selectList(statement, parameter,
							new RowBounds(offset, limit));
				}
			});
		}
	}
}
