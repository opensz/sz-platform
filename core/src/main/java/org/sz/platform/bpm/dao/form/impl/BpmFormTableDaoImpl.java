package org.sz.platform.bpm.dao.form.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.core.query.QueryFilter;
import org.sz.platform.bpm.dao.form.BpmFormTableDao;
import org.sz.platform.bpm.model.form.BpmFormTable;

@Repository("bpmFormTableDao")
public class BpmFormTableDaoImpl extends BaseDaoImpl<BpmFormTable> implements BpmFormTableDao {
	public Class getEntityClass() {
		return BpmFormTable.class;
	}

	public boolean isTableNameExisted(String tableName) {
		return ((Integer) getOne("isTableNameExisted", tableName)).intValue() > 0;
	}

	public boolean isTableNameExistedForUpd(Long tableId, String tableName) {
		Map params = new HashMap();
		params.put("tableId", tableId);
		params.put("tableName", tableName);
		return ((Integer) getOne("isTableNameExistedForUpd", params))
				.intValue() > 0;
	}

	public boolean isTableNameExternalExisted(String tableName, String dsAlias) {
		BpmFormTable bpmFormTable = new BpmFormTable();
		bpmFormTable.setTableName(tableName);
		bpmFormTable.setDsAlias(dsAlias);
		return ((Integer) getOne("isTableNameExternalExisted", bpmFormTable))
				.intValue() > 0;
	}

	public List<BpmFormTable> getSubTableByMainTableId(Long mainTableId) {
		return getBySqlKey("getSubTableByMainTableId", mainTableId);
	}

	public List<BpmFormTable> getAssignableMainTable() {
		return getBySqlKey("getAssignableMainTable");
	}

	public List<BpmFormTable> getAll(QueryFilter queryFilter) {
		String sqlKey = "getAll_" + getDbType();
		List list = getBySqlKey(sqlKey, queryFilter);
		return list;
	}

	public List<BpmFormTable> getAllUnpublishedMainTable() {
		return getBySqlKey("getAllUnpublishedMainTable");
	}

	public List<BpmFormTable> getAllUnassignedSubTable() {
		return getBySqlKey("getAllUnassignedSubTable", null);
	}

	public List<BpmFormTable> getAllMainTable(QueryFilter queryFilter) {
		String statementName = getIbatisMapperNamespace() + ".getAllMainTable";

		List list = getList(statementName, queryFilter);

		queryFilter.setForWeb();
		return list;
	}

	public List<BpmFormTable> getByDsSubTable(String dsName) {
		List list = getBySqlKey("getByDsSubTable", dsName);
		return list;
	}

	public void updateRelations(BpmFormTable bpmFormTable) {
		update("updateRelations", bpmFormTable);
	}

	public void updateMain(BpmFormTable bpmFormTable) {
		update("updateMain", bpmFormTable);
	}

	public void updateMainEmpty(Long mainTableId) {
		update("updateMainEmpty", mainTableId);
	}

	public void updPublished(BpmFormTable bpmFormTable) {
		update("updPublished", bpmFormTable);
	}

	public BpmFormTable getByDsTablename(String dsName, String tableName) {
		Map params = new HashMap();
		params.put("dsAlias", dsName);
		params.put("tableName", tableName);
		return (BpmFormTable) getOne("getByDsTablename", params);
	}

	public BpmFormTable getByTableName(String tableName) {
		return (BpmFormTable) getUnique("getByTableName", tableName);
	}
}