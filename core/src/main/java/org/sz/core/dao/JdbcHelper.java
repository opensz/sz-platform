package org.sz.core.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.sz.core.dao.helper.ColumnModel;
import org.sz.core.dao.helper.DbCmd;
import org.sz.core.dao.helper.JdbcCommand;
import org.sz.core.dao.helper.ObjectHelper;
import org.sz.core.mybatis.Dialect;
import org.sz.core.query.PageBean;
import org.sz.core.util.BeanUtils;

public class JdbcHelper<T, PK> {
	private static Log log = LogFactory.getLog(JdbcHelper.class);

	private static final ThreadLocal<String> contextHolder = new ThreadLocal();
	private static final ThreadLocal<Dialect> dialectHolder = new ThreadLocal();

	private static JdbcHelper jdbcHelper = null;

	private Map<String, Map> dataSouceMap = new HashMap();

	public void setDialect(Dialect dialect) {
		dialectHolder.set(dialect);
	}

	private Dialect getDialect() {
		return (Dialect) dialectHolder.get();
	}

	public static synchronized JdbcHelper getInstance() {
		if (jdbcHelper == null)
			jdbcHelper = new JdbcHelper();
		return jdbcHelper;
	}

	public void setCurrentDb(String alias) {
		contextHolder.set(alias);
	}

	public String getCurrentDb() {
		String str = (String) contextHolder.get();
		return str;
	}

	public synchronized void init(String alias, String className, String url,
			String user, String pwd) throws Exception {
		alias = alias.toLowerCase();
		if (!this.dataSouceMap.containsKey(alias)) {
			Properties prop = new Properties();
			prop.put("driverClassName", className);
			prop.put("url", url);
			prop.put("username", user);
			prop.put("password", pwd);

			DataSource source = BasicDataSourceFactory.createDataSource(prop);
			JdbcTemplate jdbcTemplate = new JdbcTemplate(source);
			DataSourceTransactionManager tansactionManager = new DataSourceTransactionManager(
					source);
			TransactionTemplate tansactionTemplate = new TransactionTemplate(
					tansactionManager);

			Map map = new HashMap();
			map.put("source", source);
			map.put("jdbcTemplate", jdbcTemplate);
			map.put("tansactionTemplate", tansactionTemplate);

			this.dataSouceMap.put(alias, map);
		}
		setCurrentDb(alias);
	}

	public void removeAlias(String alias) {
		alias = alias.toLowerCase();
		if (this.dataSouceMap.containsKey(alias))
			this.dataSouceMap.remove(alias);
	}

	public JdbcTemplate getJdbcTemplate() {
		String alias = getCurrentDb();
		Map map = (Map) this.dataSouceMap.get(alias);
		JdbcTemplate jdbcTemplate = (JdbcTemplate) map.get("jdbcTemplate");
		return jdbcTemplate;
	}

	public void execute(final JdbcCommand cmd) {
		String alias = getCurrentDb();

		Map map = (Map) this.dataSouceMap.get(alias);
		TransactionTemplate tansactionTemplate = (TransactionTemplate) map
				.get("tansactionTemplate");
		JdbcTemplate jdbcTemplate = (JdbcTemplate) map.get("jdbcTemplate");

		final DataSource source = (DataSource) map.get("source");
		tansactionTemplate.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					cmd.execute(source);
				} catch (Exception e) {
					status.setRollbackOnly();
					JdbcHelper.log.error(e.getMessage());
				}
			}
		});
	}

	public int execute(String sql, Object[] aryPara) {
		String alias = getCurrentDb();
		Map map = (Map) this.dataSouceMap.get(alias);
		JdbcTemplate jdbcTemplate = (JdbcTemplate) map.get("jdbcTemplate");
		return jdbcTemplate.update(sql, aryPara);
	}

	public int queryForInt(String sql, Map parameter) {
		String alias = getCurrentDb();
		Map map = (Map) this.dataSouceMap.get(alias);
		DataSource source = (DataSource) map.get("source");
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(
				source);
		return template.queryForInt(sql, parameter);
	}

	public List queryForList(String sql, Map parameter, RowMapper rowMap) {
		String alias = getCurrentDb();
		Map map = (Map) this.dataSouceMap.get(alias);
		DataSource source = (DataSource) map.get("source");

		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(
				source);
		return template.query(sql, parameter, rowMap);
	}

	public List queryForList(String sql, Object[] parameter, RowMapper rowMap) {
		String alias = getCurrentDb();
		Map map = (Map) this.dataSouceMap.get(alias);
		JdbcTemplate jdbcTemplate = (JdbcTemplate) map.get("jdbcTemplate");

		return jdbcTemplate.query(sql, parameter, rowMap);
	}

	public List queryForList(String sql, Map parameter) {
		String alias = getCurrentDb();
		Map map = (Map) this.dataSouceMap.get(alias);
		DataSource source = (DataSource) map.get("source");

		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(
				source);
		return template.queryForList(sql, parameter);
	}

	public Object queryForObject(String sql, Map parameter, RowMapper rowMap) {
		String alias = getCurrentDb();
		Map map = (Map) this.dataSouceMap.get(alias);
		DataSource source = (DataSource) map.get("source");
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(
				source);
		List list = template.query(sql, parameter, rowMap);
		if (BeanUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	public T queryForObject(T obj, RowMapper rowMap) {
		String alias = getCurrentDb();
		Map map = (Map) this.dataSouceMap.get(alias);
		DataSource source = (DataSource) map.get("source");
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(
				source);

		ObjectHelper objHelper = new ObjectHelper();
		objHelper.setModel(obj);
		List list = objHelper.getColumns();
		ColumnModel column = objHelper.getPk(list);
		String sql = "select * from " + objHelper.getTableName() + " where "
				+ column.getColumnName() + "=:" + column.getPropery();

		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(
				obj);
		return (T) template.queryForObject(sql, namedParameters, rowMap);
	}

	public List<T> getPage(PageBean pageBean, String sql, RowMapper rowMap) {
		int pageSize = pageBean.getPageSize();

		int offset = pageBean.getFirst();

		Map map = new HashMap();

		String pageSql = getDialect().getLimitString(sql, offset, pageSize);
		String totalSql = getDialect().getCountSql(sql);
		List list = queryForList(pageSql, map, rowMap);
		int total = queryForInt(totalSql, map);

		pageBean.setTotalCount(total);
		return list;
	}

	public List<T> getPage(int currentPage, int pageSize, String sql,
			Map paraMap, PageBean pageBean) {
		int offset = (currentPage - 1) * pageSize;
		String pageSql = getDialect().getLimitString(sql, offset, pageSize);
		String totalSql = getDialect().getCountSql(sql);
		List list = queryForList(pageSql, paraMap);
		int total = queryForInt(totalSql, paraMap);
		pageBean.setTotalCount(total);

		return list;
	}

	public void add(T obj) {
		DbCmd cmd = new DbCmd();
		cmd.setModel(obj);
		cmd.setOperatorType(DbCmd.OperatorType.Add);
		jdbcHelper.execute(cmd);
	}

	public void upd(T obj) {
		DbCmd cmd = new DbCmd();
		cmd.setModel(obj);
		cmd.setOperatorType(DbCmd.OperatorType.Upd);
		jdbcHelper.execute(cmd);
	}

	public void delById(T obj) {
		DbCmd cmd = new DbCmd();
		cmd.setModel(obj);
		cmd.setOperatorType(DbCmd.OperatorType.Del);
		jdbcHelper.execute(cmd);
	}
}
