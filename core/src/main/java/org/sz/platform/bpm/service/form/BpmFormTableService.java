package org.sz.platform.bpm.service.form;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.sz.core.query.QueryFilter;
import org.sz.core.service.BaseService;
import org.sz.platform.bpm.model.form.BpmFormField;
import org.sz.platform.bpm.model.form.BpmFormTable;

public interface BpmFormTableService extends BaseService<BpmFormTable>{

	void addExt(BpmFormTable table, BpmFormField[] fields) throws Exception;

	int add(BpmFormTable table, BpmFormField[] fields) throws Exception;

	int upd(BpmFormTable table, BpmFormField[] fields) throws Exception;

	boolean isTableNameExisted(String tableName);

	boolean isTableNameExistedForUpd(Long tableId, String tableName);

	boolean isTableNameExternalExisted(String tableName, String dsAlias);

	List<BpmFormTable> getSubTableByMainTableId(Long mainTableId);

	List<BpmFormTable> getAllUnassignedSubTable();

	List<BpmFormTable> getByDsSubTable(String dsName);

	void generateTable(Long tableId, String operator) throws Exception;

	void linkSubtable(Long mainTableId, Long subTableId) throws Exception;

	void unlinkSubTable(Long subTableId);

	List<BpmFormTable> getAssignableMainTable();

	List<BpmFormTable> getAllUnpublishedMainTable();

	List<BpmFormTable> getAllMainTable(QueryFilter queryFilter);

	void saveRelation(String dataSource, String tableName, String relationXml);

	BpmFormTable getByDsTablename(String dsName, String tableName);

	void delExtTableById(Long tableId);

	void delByTableId(Long tableId);

	String importXml(InputStream inputStream) throws Exception;

	String exportXml(Long[] tableId) throws FileNotFoundException, IOException;

}