package org.sz.platform.bpm.dao.form.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.sz.core.dao.JdbcHelper;
import org.sz.core.mybatis.Dialect;
import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.TimeUtil;
import org.sz.platform.bpm.dao.form.BpmFormFieldDao;
import org.sz.platform.bpm.dao.form.BpmFormHandlerDao;
import org.sz.platform.bpm.dao.form.BpmFormTableDao;
import org.sz.platform.bpm.model.form.BpmFormData;
import org.sz.platform.bpm.model.form.BpmFormField;
import org.sz.platform.bpm.model.form.BpmFormTable;
import org.sz.platform.bpm.model.form.BpmTableTemplate;
import org.sz.platform.bpm.model.form.PkValue;
import org.sz.platform.bpm.model.form.SqlModel;
import org.sz.platform.bpm.model.form.SubTable;
import org.sz.platform.bpm.model.form.TableRelation;
import org.sz.platform.bpm.util.FormDataUtil;
import org.sz.platform.system.dao.SysDataSourceDao;
import org.sz.platform.system.dao.SysOrgDao;
import org.sz.platform.system.model.SysDataSource;
import org.sz.platform.system.model.SysUser;


@Repository("bpmFormHandlerDao")
public class BpmFormHandlerDaoImpl implements BpmFormHandlerDao {
	private Log logger = LogFactory.getLog(BpmFormHandlerDao.class);

	@Resource
	private BpmFormTableDao bpmFormTableDao;

	@Resource
	private SysDataSourceDao sysDataSourceDao;

	@Resource
	private BpmFormFieldDao bpmFormFieldDao;

	@Resource
	private Dialect dialect;

	@Resource
	private SysOrgDao sysOrgDao;

//	@Resource
//	private SysUserDao sysUserDao;

	private JdbcTemplate getJdbcTemplate(long tableId) throws Exception {
		BpmFormTable bpmFormTable = (BpmFormTable) this.bpmFormTableDao
				.getById(Long.valueOf(tableId));
		String dataSource = bpmFormTable.getDsAlias();
		if (bpmFormTable.getIsExternal() == 1) {
			SysDataSource sysDataSource = this.sysDataSourceDao
					.getByAlias(dataSource);
			JdbcHelper jdbcHelper = initJdbcHelper(sysDataSource);
			return jdbcHelper.getJdbcTemplate();
		}
		JdbcTemplate jdbcTemplate = (JdbcTemplate) ContextUtil
				.getBean("jdbcTemplate");
		return jdbcTemplate;
	}

	private JdbcHelper initJdbcHelper(SysDataSource sysDataSource)
			throws Exception {
		JdbcHelper jdbcHelper = JdbcHelper.getInstance();
		String dsName = sysDataSource.getAlias();
		String className = sysDataSource.getDriverName();
		String url = sysDataSource.getUrl();
		String userName = sysDataSource.getUserName();
		String pwd = sysDataSource.getPassword();
		jdbcHelper.init(dsName, className, url, userName, pwd);
		return jdbcHelper;
	}

	public void handFormData(BpmFormData bpmFormData) throws Exception {
		long tableId = bpmFormData.getTableId();

		JdbcTemplate jdbcTemplate = getJdbcTemplate(tableId);
		List<SqlModel> list = FormDataUtil.parseSql(bpmFormData);
		for (SqlModel sqlModel : list)
			jdbcTemplate.update(sqlModel.getSql(), sqlModel.getValues());
	}

	public boolean getHasData(String tableName) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate) ContextUtil
				.getBean("jdbcTemplate");
		int rtn = jdbcTemplate.queryForInt("select count(*) from " + tableName);
		return rtn > 0;
	}

	public BpmFormData getByKey(long tableId, String pkValue) throws Exception {
		BpmFormTable mainFormTableDef = (BpmFormTable) this.bpmFormTableDao
				.getById(Long.valueOf(tableId));

		List<BpmFormTable> formTableList = this.bpmFormTableDao
				.getSubTableByMainTableId(Long.valueOf(tableId));

		JdbcTemplate jdbcTemplate = getJdbcTemplate(tableId);
		String tableName = mainFormTableDef.getTableName();
		String pkField = "ID";
		int isExternal = mainFormTableDef.getIsExternal();
		if (isExternal == 1)
			pkField = mainFormTableDef.getPkField();
		else {
			tableName = "W_" + tableName;
		}
		PkValue pk = new PkValue(pkField, pkValue);

		Map mainData = getByKey(jdbcTemplate, tableName, pk,
				Long.valueOf(tableId), isExternal);

		BpmFormData bpmFormData = new BpmFormData();
		for (BpmFormTable table : formTableList) {
			SubTable subTable = new SubTable();

			String fk = "REFID";
			String subPk = "ID";
			String subTableName = table.getTableName();
			if (isExternal == 1) {
				TableRelation tableRelation = mainFormTableDef
						.getTableRelation();
				fk = (String) tableRelation.getRelations().get(
						table.getTableName());
				subPk = table.getPkField();
			} else {
				subTableName = "W_" + subTableName;
			}
			Long subTableId = table.getTableId();

			List list = getByFk(jdbcTemplate, subTableName, fk, pk.getValue()
					.toString(), subTableId, isExternal);

			subTable.setTableName(table.getTableName());
			subTable.setDataList(list);
			subTable.setFkName(fk);
			subTable.setPkName(subPk);
			bpmFormData.addSubTable(subTable);
		}

		bpmFormData.setTableId(tableId);
		bpmFormData.setTableName(tableName);
		bpmFormData.setPkValue(pk);
		bpmFormData.addMainFields(mainData);

		return bpmFormData;
	}

	public Map<String, Object> getByKey(JdbcTemplate jdbcTemplate,
			String tableName, PkValue pk, Long tableId, int isExternal) {
		String sql = "select * from " + tableName + " where " + pk.getName()
				+ "='" + pk.getValue() + "'";
		List fieldList = this.bpmFormFieldDao.getByTableId(tableId);
		Map fieldMap = convertToMap(fieldList);
		Map map = null;
		try {
			map = jdbcTemplate.queryForMap(sql);
			map = handMap(isExternal, fieldMap, map);
		} catch (Exception ex) {
			this.logger.error(ex.getMessage());
			map = new HashMap();
		}
		return map;
	}

	private Map<String, BpmFormField> convertToMap(List<BpmFormField> fieldList) {
		Map map = new HashMap();
		for (BpmFormField field : fieldList) {
			String fieldName = field.getFieldName().toLowerCase();
			map.put(fieldName, field);
		}
		return map;
	}
	
	//创建工单
	public void createTask(Long tableId, String pkValue) throws Exception{
		//更新创建时间
		BpmFormTable mainFormTableDef = (BpmFormTable) this.bpmFormTableDao
		.getById(Long.valueOf(tableId));
		JdbcTemplate jdbcTemplate = getJdbcTemplate(tableId);
		String tableName = "W_" + mainFormTableDef.getTableName();
		
//		jdbcTemplate.execute("update " + tableName + " set status_="+org.sz.base.Constants.TASK_STATUS_RUNNING+", createTime_='" + TimeUtil.getCurrentTime()+ "' where id = " + pkValue);
	}
	
	//关闭工单
	public void offTask(Long tableId, String pkValue) throws Exception{
		BpmFormTable mainFormTableDef = (BpmFormTable) this.bpmFormTableDao
		.getById(Long.valueOf(tableId));

		JdbcTemplate jdbcTemplate = getJdbcTemplate(tableId);
		String tableName = "W_" + mainFormTableDef.getTableName();
		
//		jdbcTemplate.execute("update " + tableName + " set status_="+org.sz.base.Constants.TASK_STATUS_OFF+", offTime_='" + TimeUtil.getCurrentTime()+ "' where id = " + pkValue);
	}
	
	//挂起工单 
	public void suspendTask(Long tableId, String pkValue) throws Exception{
		BpmFormTable mainFormTableDef = (BpmFormTable) this.bpmFormTableDao
		.getById(Long.valueOf(tableId));

		JdbcTemplate jdbcTemplate = getJdbcTemplate(tableId);
		String tableName = "W_" + mainFormTableDef.getTableName();
		
//		jdbcTemplate.execute("update " + tableName + " set status_="+org.sz.base.Constants.TASK_STATUS_SUSPEND+" where id = " + pkValue);
	}
	
	//作废工单 
	public void invalidTask(Long tableId, String pkValue) throws Exception{
		BpmFormTable mainFormTableDef = (BpmFormTable) this.bpmFormTableDao
		.getById(Long.valueOf(tableId));
		
		JdbcTemplate jdbcTemplate = getJdbcTemplate(tableId);
		String tableName = "W_" + mainFormTableDef.getTableName();
		
//		jdbcTemplate.execute("update " + tableName + " set status_="+org.sz.base.Constants.TASK_STATUS_INVALID+" where id = " + pkValue);
	}
	
	public List<Map<String, Object>> getByFk(JdbcTemplate jdbcTemplate,
			String tableName, String fk, String fkValue, Long tableId,
			int isExternal) {
		String sql = "select * from " + tableName + " where " + fk + "='"
				+ fkValue + "'";
		List fieldList = this.bpmFormFieldDao.getByTableId(tableId);
		Map fieldMap = convertToMap(fieldList);

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		List rtnList = new ArrayList();

		for (Map<String, Object> map : list) {
			Map obj = handMap(isExternal, fieldMap, map);
			rtnList.add(obj);
		}
		return rtnList;
	}

	private Map<String, Object> handMap(int isExternal,
			Map<String, BpmFormField> fieldMap, Map<String, Object> map) {
		Map rtnMap = new HashMap();
		for (Map.Entry entry : map.entrySet()) {
			String key = ((String) entry.getKey()).toLowerCase();
			if ((isExternal == 0) && (key.indexOf("F_".toLowerCase()) == 0)) {
				key = key.replaceFirst("F_".toLowerCase(), "");
			}
			Object obj = entry.getValue();

			if ((obj instanceof Date)) {
				BpmFormField bpmFormField = (BpmFormField) fieldMap.get(key);
				String timeFormat = bpmFormField.getCtlProperty();
				String str = TimeUtil.getDateTimeString((Date) obj, timeFormat);
				rtnMap.put(key, str);
			} else {
				rtnMap.put(key, obj);
			}
		}
		return rtnMap;
	}

	public List<Map<String, Object>> getAll(Long tableId,
			Map<String, Object> param) throws Exception {
		BpmFormTable table = (BpmFormTable) this.bpmFormTableDao
				.getById(tableId);
		JdbcTemplate jdbcTemplate = getJdbcTemplate(tableId.longValue());

		StringBuffer where = new StringBuffer();
		List values = new ArrayList();
		for (Map.Entry entry : param.entrySet()) {
			where.append((String) entry.getKey()).append(" LIKE ? AND ");
			values.add(entry.getValue());
		}

		String tableName = table.getTableName();
		if (table.getIsExternal() != 1) {
			tableName = "W_" + tableName;
		}

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM ");
		sql.append(tableName);
		if (where.length() > 0) {
			sql.append(" WHERE ");
			sql.append(where.substring(0, where.length() - 5));
		}

		RowMapper rowMapper = new RowMapper() {
			public Map<String, Object> mapRow(ResultSet rs, int num)
					throws SQLException {
				Map map = new HashMap();
				ResultSetMetaData rsm = rs.getMetaData();
				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					String name = rsm.getColumnName(i);
					Object value = rs.getObject(name);
					map.put(name, value);
				}
				return map;
			}
		};
		return jdbcTemplate.query(sql.toString(), values.toArray(), rowMapper);
	}

	private String getAuthorTypeSql(int authorType, SysUser user) {
		String sqlStr = "";
		switch (authorType) {
		case 2:
			sqlStr = "CURENTUSERID_ = " + user.getUserId() + " and ";
			break;
		case 3:
			break;
		case 4:
			sqlStr = "CURENTUSERID_ in (select userid from sys_user_org where orgid in (select orgid from sys_user_org where userid="
					+ user.getUserId() + ")) and ";
		}

		return sqlStr;
	}

	public List<Map<String, Object>> getAll(BpmTableTemplate bpmTableTemplate, SysUser user, Map<String, Object> param, PageBean pageBean) throws Exception {
		BpmFormTable table = (BpmFormTable) this.bpmFormTableDao.getById(bpmTableTemplate.getTableId());
		JdbcTemplate jdbcTemplate = getJdbcTemplate(bpmTableTemplate.getTableId().longValue());

		StringBuffer where = new StringBuffer();
		List values = new ArrayList();
		for (Map.Entry entry : param.entrySet()) {
			where.append("F_" + (String) entry.getKey());
			where.append(" LIKE ? AND ");
			values.add("%" + entry.getValue() + "%");
		}

		String tableName = table.getTableName();
		if (table.getIsExternal() != 1) {
			tableName = "W_" + tableName;
		}

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM ");
		sql.append(tableName);
		sql.append(" WHERE 1=1 and ");
		sql.append(getAuthorTypeSql(bpmTableTemplate.getAuthorType(), user));
		if (where.length() > 0) {
			sql.append(where);
		}
		sql.delete(sql.length() - 5, sql.length());
		RowMapper rowMapper = new RowMapper() {
			public Map<String, Object> mapRow(ResultSet rs, int num) throws SQLException {
				Map map = new HashMap();
				ResultSetMetaData rsm = rs.getMetaData();
				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					String name = rsm.getColumnName(i);
					Object value = rs.getObject(name);
					map.put(name, value);
				}
				return map;
			}
		};
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		int offset = (currentPage - 1) * pageSize;
		String pageSql = this.dialect.getLimitString(sql.toString(), offset, pageSize);
		String totalSql = this.dialect.getCountSql(sql.toString());
		List<Map<String, Object>> list = jdbcTemplate.query(pageSql, values.toArray(), rowMapper);

		List retList = new ArrayList();
		for (Map<String, Object> maps : list) {
			Map newMap = new HashMap();
			for (Map.Entry map : maps.entrySet()) {
				String key = ((String) map.getKey()).toLowerCase().replaceFirst("F_".toLowerCase(), "");
				newMap.put(key, map.getValue());
			}
			retList.add(newMap);
		}
		int total = jdbcTemplate.queryForInt(totalSql,values.toArray());
		pageBean.setTotalCount(total);
		return retList;
	}
	
	public List<Map<String, Object>> getQuery(BpmTableTemplate bpmTableTemplate, SysUser user, Map<String, Object> param, QueryFilter filter) throws Exception {
		BpmFormTable table = (BpmFormTable) this.bpmFormTableDao.getById(bpmTableTemplate.getTableId());
		JdbcTemplate jdbcTemplate = getJdbcTemplate(bpmTableTemplate.getTableId().longValue());

		StringBuffer where = new StringBuffer();
		List values = new ArrayList();
		for (Map.Entry entry : param.entrySet()) {
			String str=(String) entry.getKey().toString();
			if(str!="" && str.equals("include")){
				where.append(entry.getValue()+" AND ");
			}
			else{
				where.append((String) entry.getKey());
				where.append(" LIKE ? AND ");
				values.add("%" + entry.getValue() + "%");
			}
			
		}

		String tableName = table.getTableName();
		if (table.getIsExternal() != 1) {
			tableName = "W_" + tableName;
		}

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM ");
		sql.append(tableName);
		sql.append(" WHERE 1=1 and ");
		//sql.append(getAuthorTypeSql(bpmTableTemplate.getAuthorType(), user));
		if (where.length() > 0) {
			sql.append(where);
		}
		sql.delete(sql.length() - 5, sql.length());
		RowMapper rowMapper = new RowMapper() {
			public Map<String, Object> mapRow(ResultSet rs, int num) throws SQLException {
				Map map = new HashMap();
				ResultSetMetaData rsm = rs.getMetaData();
				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					String name = rsm.getColumnName(i);
					Object value = rs.getObject(name);
					map.put(name, value);
				}
				return map;
			}
		};
		int currentPage = filter.getPageBean().getCurrentPage();
		int pageSize = filter.getPageBean().getPageSize();
		int offset = (currentPage - 1) * pageSize;
		String pageSql = this.dialect.getLimitString(sql.toString(), offset, pageSize);
		String totalSql = this.dialect.getCountSql(sql.toString());
		List<Map<String, Object>> list = jdbcTemplate.query(pageSql, values.toArray(), rowMapper);

		List retList = new ArrayList();
		for (Map<String, Object> maps : list) {
			Map newMap = new HashMap();
			for (Map.Entry map : maps.entrySet()) {
				String key = ((String) map.getKey()).toLowerCase().replaceFirst("F_".toLowerCase(), "");
				newMap.put(key, map.getValue());
			}
			retList.add(newMap);
		}
		int total = jdbcTemplate.queryForInt(totalSql,values.toArray());
		filter.getPageBean().setTotalCount(total);
		filter.setForWeb();
		return retList;
	}
}