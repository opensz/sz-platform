package org.sz.platform.bpm.dao.form;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;
import org.sz.platform.bpm.model.form.BpmFormData;
import org.sz.platform.bpm.model.form.BpmTableTemplate;
import org.sz.platform.bpm.model.form.PkValue;
import org.sz.platform.system.model.SysUser;

public interface BpmFormHandlerDao {

	void handFormData(BpmFormData bpmFormData) throws Exception;

	boolean getHasData(String tableName);

	BpmFormData getByKey(long tableId, String pkValue) throws Exception;

	Map<String, Object> getByKey(JdbcTemplate jdbcTemplate, String tableName,
			PkValue pk, Long tableId, int isExternal);

	List<Map<String, Object>> getByFk(JdbcTemplate jdbcTemplate,
			String tableName, String fk, String fkValue, Long tableId,
			int isExternal);

	List<Map<String, Object>> getAll(Long tableId, Map<String, Object> param)
			throws Exception;

	List<Map<String, Object>> getAll(BpmTableTemplate bpmTableTemplate,
			SysUser user, Map<String, Object> param, PageBean pageBean)
			throws Exception;

	List<Map<String, Object>> getQuery(BpmTableTemplate bpmTableTemplate,
			SysUser user, Map<String, Object> param,QueryFilter filter)
			throws Exception;
	public void createTask(Long tableId, String pkValue) throws Exception;

	public void offTask(Long tableId, String pkValue) throws Exception;

	public void suspendTask(Long tableId, String pkValue) throws Exception;
	
	public void invalidTask(Long tableId, String pkValue) throws Exception;
	
}