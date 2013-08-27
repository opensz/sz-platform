package org.sz.platform.bpm.dao.form;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.core.query.QueryFilter;
import org.sz.platform.bpm.model.form.BpmFormTable;


public interface BpmFormTableDao extends BaseDao<BpmFormTable> {

		boolean isTableNameExisted(String tableName);

		boolean isTableNameExistedForUpd(Long tableId, String tableName);

		boolean isTableNameExternalExisted(String tableName, String dsAlias);

		List<BpmFormTable> getSubTableByMainTableId(Long mainTableId);

		List<BpmFormTable> getAssignableMainTable();

		List<BpmFormTable> getAllUnpublishedMainTable();

		List<BpmFormTable> getAllUnassignedSubTable();

		List<BpmFormTable> getAllMainTable(QueryFilter queryFilter);

		List<BpmFormTable> getByDsSubTable(String dsName);

		void updateRelations(BpmFormTable bpmFormTable);

		void updateMain(BpmFormTable bpmFormTable);

		void updateMainEmpty(Long mainTableId);

		void updPublished(BpmFormTable bpmFormTable);

		BpmFormTable getByDsTablename(String dsName, String tableName);

		BpmFormTable getByTableName(String tableName);

	}