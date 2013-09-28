package org.sz.platform.bpm.service.form.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.sz.core.customertable.ColumnModel;
import org.sz.core.customertable.ITableOperator;
import org.sz.core.customertable.TableModel;
import org.sz.core.dao.IEntityDao;
import org.sz.core.query.QueryFilter;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.Dom4jUtil;
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.dao.form.BpmFormDefDao;
import org.sz.platform.bpm.dao.form.BpmFormFieldDao;
import org.sz.platform.bpm.dao.form.BpmFormHandlerDao;
import org.sz.platform.bpm.dao.form.BpmFormTableDao;
import org.sz.platform.bpm.model.form.BpmFormField;
import org.sz.platform.bpm.model.form.BpmFormTable;
import org.sz.platform.bpm.model.form.TableRelation;
import org.sz.platform.bpm.service.form.BpmFormTableService;
import org.sz.platform.system.dao.SysDataSourceDao;
import org.sz.platform.system.model.SysDataSource;

@Service("bpmFormTableService")
public class BpmFormTableServiceImpl extends BaseServiceImpl<BpmFormTable>
		implements BpmFormTableService {
	
	public static final String CUSTOMER_COLUMN_PREFIX = TableModel.CUSTOMER_COLUMN_PREFIX;
	public static final String CUSTOMER_TABLE_PREFIX = TableModel.CUSTOMER_TABLE_PREFIX;

	@Resource
	private BpmFormTableDao dao;

	@Resource
	private BpmFormFieldDao bmpFormFieldDao;

	@Resource
	private ITableOperator tableOperator;

	@Resource
	private SysDataSourceDao sysDataSourceDao;

	@Resource
	private BpmFormDefDao bpmFormDefDao;

	@Resource
	private BpmFormHandlerDao bpmFormHandlerDao;

	protected IEntityDao<BpmFormTable, Long> getEntityDao() {
		return this.dao;
	}

	public void addExt(BpmFormTable table, BpmFormField[] fields)
			throws Exception {
		long tableId = UniqueIdUtil.genId();

		table.setTableId(Long.valueOf(tableId));
		table.setIsPublished(Short.valueOf((short) 1));
		table.setIsExternal(1);

		if (StringUtil.isEmpty(table.getTableDesc())) {
			table.setTableDesc(table.getTableName());
		}

		String dsAlias = table.getDsAlias();
		SysDataSource sysDataSource = this.sysDataSourceDao.getByAlias(dsAlias);
		table.setDsName(sysDataSource.getName());
		this.dao.add(table);

		addFields(Long.valueOf(tableId), fields, true);
	}

	public int add(BpmFormTable table, BpmFormField[] fields) throws Exception {
		boolean isFieldExist = hasReserveFileds(fields);

		if (isFieldExist) {
			return -1;
		}

		long tableId = UniqueIdUtil.genId();

		table.setTableId(Long.valueOf(tableId));

		table.setIsExternal(0);

		if (StringUtil.isEmpty(table.getTableDesc())) {
			table.setTableDesc(table.getTableName());
		}
		this.dao.add(table);

		addFields(Long.valueOf(tableId), fields, false);

		return 0;
	}

	private Map<String, List<BpmFormField>> caculateFields(
			BpmFormField[] fields, List<BpmFormField> orginFieldList) {
		Map orginMap = new HashMap();
		Map curMap = new HashMap();

		Map resultMap = new HashMap();

		for (BpmFormField field : orginFieldList) {
			String fieldName = field.getFieldName().toLowerCase();
			orginMap.put(fieldName, field);
		}

		for (BpmFormField field : fields) {
			String fieldName = field.getFieldName().toLowerCase();
			curMap.put(fieldName, field);
		}

		//增加排序标识
		int i = 0;
		for (BpmFormField field : fields) {
			String fieldName = field.getFieldName().toLowerCase();
			List list;
			if (orginMap.containsKey(fieldName)) {

				if (resultMap.containsKey("upd")) {
					list = (List) resultMap.get("upd");
				} else {
					list = new ArrayList();
					resultMap.put("upd", list);
				}
				field.setSn(i+=2);
				list.add(field);
			} else {

				if (resultMap.containsKey("add")) {
					list = (List) resultMap.get("add");
				} else {
					list = new ArrayList();
					resultMap.put("add", list);
				}
				field.setSn(i+=2);
				list.add(field);
			}
		}

		for (BpmFormField field : orginFieldList) {
			String fieldName = field.getFieldName().toLowerCase();
			List list;
			if (!curMap.containsKey(fieldName)) {

				if (resultMap.containsKey("upd")) {
					list = (List) resultMap.get("upd");
				} else {
					list = new ArrayList();
					resultMap.put("upd", list);
				}
				field.setIsDeleted(Short.valueOf((short) 1));
				list.add(field);
			}
		}
		return resultMap;
	}

	private boolean hasReserveFileds(BpmFormField[] fields) {
		for (BpmFormField field : fields) {
			String fieldName = field.getFieldName().toLowerCase();
			if ((TableModel.CUSTOMER_COLUMN_CURRENTUSERID.toLowerCase().equals(fieldName))
					|| (TableModel.FlowRunId.toLowerCase().endsWith(fieldName))) {
				return true;
			}
		}
		return false;
	}

	private boolean hasNotNullField(List<BpmFormField> list) {
		for (BpmFormField field : list) {
			if (field.getIsRequired().shortValue() == 1) {
				return true;
			}
		}
		return false;
	}

	public int upd(BpmFormTable table, BpmFormField[] fields) throws Exception {
		//验证是否设置的有保留字段如:ID...
		boolean isFieldExists = hasReserveFileds(fields);

		if (isFieldExists)
			return -1;

		Long tableId = table.getTableId();
		String tableName = table.getTableName();

		BpmFormTable originTable = (BpmFormTable) this.dao.getById(tableId);

		Long mainTableId = tableId;
		int isMain = table.getIsMain().shortValue();
		if (isMain == 0) {
			mainTableId = table.getMainTableId();
		}

		//根据tableId 得到对应的field
		List<BpmFormField> originFieldList = this.bmpFormFieldDao.getAllByTableId(tableId);

		if (StringUtil.isEmpty(table.getTableDesc())) {
			table.setTableDesc(tableName);
		}

		boolean hasFormDef = false;
		if (mainTableId.longValue() > 0L)
			hasFormDef = this.bpmFormDefDao.isTableHasFormDef(mainTableId);
		if (hasFormDef) {
			boolean hasData = this.bpmFormHandlerDao.getHasData(CUSTOMER_TABLE_PREFIX
					+ tableName);

			Map resultMap = caculateFields(fields, originFieldList);

			List<BpmFormField> addList = (List) resultMap.get("add");
			if (BeanUtils.isNotEmpty(addList)) {
				addList = convertFields(addList, false);
				if (hasData) {
					boolean rtn = hasNotNullField(addList);
					if (rtn) {
						return -2;
					}
				}
			}else{
				addList = new ArrayList<BpmFormField>();
			}

			this.dao.update(table);

			List<BpmFormField> updList = (List) resultMap.get("upd");
			if(updList != null && updList.size() > 0){				
				for (BpmFormField field : updList) {
					if(field.isSelectControl()){
						String fieldName = field.getFieldName() + "Id";
						//判断是否已经存在该字段
						if(!this.bmpFormFieldDao.hasFieldNameByTableId(field.getTableId(), fieldName)){
							BpmFormField fieldHidden = (BpmFormField)field.clone();
							fieldHidden.setFieldId(Long.valueOf(UniqueIdUtil.genId()));
							fieldHidden.setFieldName(fieldName);
							fieldHidden.setFieldDesc(field.getFieldDesc());
							fieldHidden.setIsHidden(Short.valueOf((short) 1));
							addList.add(fieldHidden);
						}
					}
					this.bmpFormFieldDao.update(field);
				}
			}

			if (BeanUtils.isEmpty(addList))
				return 0;

			for (BpmFormField field : addList) {
				field.setFieldId(Long.valueOf(UniqueIdUtil.genId()));
				field.setTableId(tableId);
				this.bmpFormFieldDao.add(field);
				ColumnModel columnModel = getByField(field, 2);
				this.tableOperator.addColumn(CUSTOMER_TABLE_PREFIX + tableName, columnModel);
			}

		} else if (table.getIsPublished().shortValue() == 1) {
			String pkTableName;
			if (table.getIsMain().shortValue() == 1) {
				List<BpmFormTable> tableList = this.dao
						.getSubTableByMainTableId(tableId);

				for (BpmFormTable subTable : tableList) {
					String tabName = CUSTOMER_TABLE_PREFIX + subTable.getTableName();
					this.tableOperator.dropForeignKey(tabName, "fk_" + tabName);
				}
				this.dao.update(table);

				this.bmpFormFieldDao.delByTableId(tableId);

				addFields(tableId, fields, false);

				this.tableOperator.dropTable(CUSTOMER_TABLE_PREFIX + originTable.getTableName());

				List fieldList = convertFields(fields, false);
				createTable(table, fieldList);

				pkTableName = CUSTOMER_TABLE_PREFIX + table.getTableName();

				for (BpmFormTable subTable : tableList) {
					String tabName = CUSTOMER_TABLE_PREFIX + subTable.getTableName();
					this.tableOperator.addForeignKey(pkTableName, tabName,
							TableModel.PK_COLUMN_NAME, TableModel.FK_COLUMN_NAME);
				}
			} else {
				this.dao.update(table);

				this.bmpFormFieldDao.delByTableId(tableId);

				addFields(tableId, fields, false);

				this.tableOperator.dropTable(CUSTOMER_TABLE_PREFIX + originTable.getTableName());

				List fieldList = convertFields(fields, false);
				createTable(table, fieldList);
			}

		} else {
			this.dao.update(table);

			this.bmpFormFieldDao.delByTableId(tableId);

			addFields(tableId, fields, false);
		}

		return 0;
	}

	private void addFields(Long tableId, BpmFormField[] fields,
			boolean isExternal) throws Exception {
		List fieldList = convertFields(fields, isExternal);
		for (int i = 0; i < fieldList.size(); i++) {
			BpmFormField field = (BpmFormField) fieldList.get(i);
			Long fieldId = Long.valueOf(UniqueIdUtil.genId());
			field.setFieldId(fieldId);
			field.setSn(Integer.valueOf(i));
			field.setTableId(tableId);
			this.bmpFormFieldDao.add(field);
		}
	}

	private List<BpmFormField> convertFields(List<BpmFormField> fields,
			boolean isExternal) throws Exception {
		List list = new ArrayList();
		for (BpmFormField field : fields) {
			if (StringUtil.isEmpty(field.getFieldDesc())) {
				field.setFieldDesc(field.getFieldName());
			}
			list.add(field);
			if (!isExternal)
				if (field.getControlType() == null) {
					field.setControlType(Short.valueOf((short) 1));
				} else if (field.isSelectControl()) {
					BpmFormField fieldHidden = (BpmFormField) field.clone();
					fieldHidden.setFieldId(Long.valueOf(UniqueIdUtil.genId()));
					fieldHidden.setFieldName(fieldHidden.getFieldName() + "Id");
					fieldHidden.setFieldDesc(field.getFieldDesc());
					fieldHidden.setIsHidden(Short.valueOf((short) 1));

					list.add(fieldHidden);
				}
		}
		return list;
	}

	private List<BpmFormField> convertFields(BpmFormField[] fields,
			boolean isExternal) throws Exception {
		List list = new ArrayList();
		for (BpmFormField field : fields) {
			list.add(field);
		}
		list = convertFields(list, isExternal);
		return list;
	}

	public boolean isTableNameExisted(String tableName) {
		return this.dao.isTableNameExisted(tableName);
	}

	public boolean isTableNameExistedForUpd(Long tableId, String tableName) {
		return this.dao.isTableNameExistedForUpd(tableId, tableName);
	}

	public boolean isTableNameExternalExisted(String tableName, String dsAlias) {
		return this.dao.isTableNameExternalExisted(tableName, dsAlias);
	}

	public List<BpmFormTable> getSubTableByMainTableId(Long mainTableId) {
		return this.dao.getSubTableByMainTableId(mainTableId);
	}

	public List<BpmFormTable> getAllUnassignedSubTable() {
		return this.dao.getAllUnassignedSubTable();
	}

	public List<BpmFormTable> getByDsSubTable(String dsName) {
		return this.dao.getByDsSubTable(dsName);
	}

	public void generateTable(Long tableId, String operator) throws Exception {
		BpmFormTable mainTable = (BpmFormTable) this.dao.getById(tableId);

		publish(tableId, operator);

		List mainFields = this.bmpFormFieldDao.getAllByTableId(tableId);

		mainFields = convertFields(mainFields, false);

		createTable(mainTable, mainFields);

		List<BpmFormTable> subTables = this.dao
				.getSubTableByMainTableId(tableId);
		for (BpmFormTable subTable : subTables) {
			publish(subTable.getTableId(), operator);

			List subFields = this.bmpFormFieldDao.getAllByTableId(subTable
					.getTableId());
			subFields = convertFields(subFields, false);

			createTable(subTable, subFields);
		}
	}

	private void publish(Long tableId, String operator) {
		BpmFormTable table = new BpmFormTable();
		table.setTableId(tableId);
		table.setPublishedBy(operator);
		table.setIsPublished(Short.valueOf((short) 1));
		table.setPublishTime(new Date());
		this.dao.updPublished(table);
	}

	public void linkSubtable(Long mainTableId, Long subTableId)
			throws Exception {
		BpmFormTable mainTable = (BpmFormTable) this.dao.getById(mainTableId);
		BpmFormTable subTable = (BpmFormTable) this.dao.getById(subTableId);

		if (mainTable.getIsPublished().shortValue() == 1) {
			subTable.setMainTableId(mainTableId);
			this.dao.update(subTable);

			if (subTable.getIsPublished().shortValue() == 1) {
				this.tableOperator.dropTable(CUSTOMER_TABLE_PREFIX + subTable.getTableName());
			}

			publish(subTableId, mainTable.getPublishedBy());
			List subFields = this.bmpFormFieldDao.getByTableId(subTableId);
			subFields = convertFields(subFields, false);

			createTable(subTable, subFields);
		} else {
			subTable.setMainTableId(mainTableId);
			this.dao.update(subTable);
		}
	}

	public void unlinkSubTable(Long subTableId) {
		BpmFormTable table = (BpmFormTable) this.dao.getById(subTableId);
		table.setMainTableId(null);
		this.dao.update(table);
	}

	public List<BpmFormTable> getAssignableMainTable() {
		List list = this.dao.getAssignableMainTable();

		return list;
	}

	public List<BpmFormTable> getAllUnpublishedMainTable() {
		List list = this.dao.getAllUnpublishedMainTable();
		return list;
	}

	public List<BpmFormTable> getAllMainTable(QueryFilter queryFilter) {
		return this.dao.getAllMainTable(queryFilter);
	}

	private ColumnModel getByField(BpmFormField field, int columnType) {
		ColumnModel columnModel = new ColumnModel();
		switch (columnType) {
		case 1:
			columnModel.setName("ID");
			columnModel.setColumnType("int");
			columnModel.setIntLen(20);
			columnModel.setComment("主键");
			columnModel.setIsPk(true);
			columnModel.setIsNull(false);
			break;
		case 2:
			columnModel.setName(CUSTOMER_COLUMN_PREFIX + field.getFieldName());
			columnModel.setColumnType(field.getFieldType());
			columnModel.setCharLen(field.getCharLen().intValue());
			columnModel.setIntLen(field.getIntLen().intValue());
			columnModel.setDecimalLen(field.getDecimalLen().intValue());
			columnModel.setIsNull(field.getIsRequired().shortValue() == 0);
			columnModel.setComment(field.getFieldDesc());
			break;
		case 3:
			columnModel.setName("REFID");
			columnModel.setColumnType("int");
			columnModel.setIntLen(20);
			columnModel.setComment("外键");
			columnModel.setIsFk(true);
			columnModel.setIsNull(false);
			break;
		case 4:
			columnModel.setName("curentUserId_");
			columnModel.setColumnType("int");
			columnModel.setIntLen(20);
			columnModel.setComment("用户字段");
			columnModel.setIsFk(false);
			columnModel.setIsNull(false);
			break;
		case 5:
			columnModel.setName("flowRunId_");
			columnModel.setColumnType("int");
			columnModel.setIntLen(20);
			columnModel.setComment("用户字段");
			columnModel.setIsFk(false);
			columnModel.setIsNull(false);
			break;
		case 6:
			columnModel.setName("status_");
			columnModel.setColumnType("int");
			columnModel.setIntLen(6);
			columnModel.setComment("用户字段");
			columnModel.setIsFk(false);
			columnModel.setIsNull(true);
			break;
		case 7:
			columnModel.setName("createTime_");
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(19);
			columnModel.setComment("用户字段");
			columnModel.setIsFk(false);
			columnModel.setIsNull(true);
			break;
		case 8:
			columnModel.setName("offTime_");
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(19);
			columnModel.setComment("用户字段");
			columnModel.setIsFk(false);
			columnModel.setIsNull(true);
			break;
		case 9:
			columnModel.setName("sourceCaseId_");
			columnModel.setColumnType("int");
			columnModel.setIntLen(20);
			columnModel.setComment("用户字段");
			columnModel.setIsFk(false);
			columnModel.setIsNull(true);
			break;
		case 10:
			columnModel.setName("changeReason_");
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(255);
			columnModel.setComment("用户字段");
			columnModel.setIsFk(false);
			columnModel.setIsNull(true);
			break;
		case 11:
			columnModel.setName("sourceCaseNo_");
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(50);
			columnModel.setComment("用户字段");
			columnModel.setIsFk(false);
			columnModel.setIsNull(true);
			break;
		}

		return columnModel;
	}

	private void createTable(BpmFormTable table, List<BpmFormField> fields)
			throws SQLException {
		TableModel tableModel = new TableModel();
		tableModel.setName(CUSTOMER_TABLE_PREFIX + table.getTableName());
		tableModel.setComment(table.getTableDesc());

		ColumnModel pkModel = getByField(null, 1);
		tableModel.addColumnModel(pkModel);

		if (table.getIsMain().shortValue() == 1) {
			
			//当前用户
			ColumnModel userColumnModel = getByField(null, 4);
			tableModel.addColumnModel(userColumnModel);

			//流程ID
			ColumnModel runColumnModel = getByField(null, 5);
			tableModel.addColumnModel(runColumnModel);
			
			tableModel.addColumnModel(getByField(null, 6));
			
			tableModel.addColumnModel(getByField(null, 7));
			
			tableModel.addColumnModel(getByField(null, 8));			
			
			tableModel.addColumnModel(getByField(null, 9));
			
			tableModel.addColumnModel(getByField(null, 10));		

			tableModel.addColumnModel(getByField(null, 11));	
		}

		for (BpmFormField field : fields) {
			ColumnModel columnModel = getByField(field, 2);
			tableModel.addColumnModel(columnModel);
		}

		if (table.getIsMain().shortValue() == 0) {
			ColumnModel fkModel = getByField(null, 3);
			tableModel.addColumnModel(fkModel);
		}

		this.tableOperator.createTable(tableModel);

		if (table.getIsMain().shortValue() == 0) {
			Long mainTableId = table.getMainTableId();
			BpmFormTable bpmFormTable = (BpmFormTable) this.dao
					.getById(mainTableId);

			this.tableOperator.addForeignKey(
					CUSTOMER_TABLE_PREFIX + bpmFormTable.getTableName(),
					CUSTOMER_TABLE_PREFIX + table.getTableName(), TableModel.PK_COLUMN_NAME, TableModel.FK_COLUMN_NAME);
		}
	}

	public void saveRelation(String dataSource, String tableName,
			String relationXml) {
		BpmFormTable bpmFormTable = new BpmFormTable();
		bpmFormTable.setDsAlias(dataSource);
		bpmFormTable.setTableName(tableName);
		bpmFormTable.setRelation(relationXml);
		this.dao.updateRelations(bpmFormTable);

		BpmFormTable mainTable = this.dao.getByDsTablename(dataSource,
				tableName);

		this.dao.updateMainEmpty(mainTable.getTableId());

		TableRelation bableRelation = BpmFormTable
				.getRelationsByXml(relationXml);
		if (bableRelation == null)
			return;
		Map mapRelation = bableRelation.getRelations();
		Set setRelation = mapRelation.entrySet();

		for (Iterator tableIt = setRelation.iterator(); tableIt.hasNext();) {
			Map.Entry obj = (Map.Entry) tableIt.next();
			String key = (String) obj.getKey();
			BpmFormTable entity = new BpmFormTable();
			entity.setDsAlias(dataSource);
			entity.setMainTableId(mainTable.getTableId());
			entity.setTableName(key);
			this.dao.updateMain(entity);
		}
	}

	public BpmFormTable getByDsTablename(String dsName, String tableName) {
		return this.dao.getByDsTablename(dsName, tableName);
	}

	public void delExtTableById(Long tableId) {
		BpmFormTable bpmFormTable = (BpmFormTable) getById(tableId);
		int isExternal = bpmFormTable.getIsExternal();

		if (isExternal == 0)
			return;

		if (bpmFormTable.getIsMain().shortValue() == 0) {
			Long mainTableId = bpmFormTable.getMainTableId();
			if ((BeanUtils.isNotEmpty(mainTableId))
					&& (mainTableId.longValue() > 0L)) {
				BpmFormTable mainTable = (BpmFormTable) getById(mainTableId);
				String relation = mainTable.getRelation();
				if (StringUtil.isNotEmpty(relation)) {
					relation = BpmFormTable.removeTable(relation,
							bpmFormTable.getTableName());
					mainTable.setRelation(relation);
					this.dao.update(mainTable);
				}
			}
		}
		this.dao.delById(tableId);
	}

	public void delByTableId(Long tableId) {
		BpmFormTable bpmFormTable = (BpmFormTable) getById(tableId);
		String tableName = bpmFormTable.getTableName();
		if (bpmFormTable.getIsMain().shortValue() == 1) {
			List<BpmFormTable> subTableList = getSubTableByMainTableId(tableId);
			if (BeanUtils.isNotEmpty(subTableList)) {
				for (BpmFormTable subTable : subTableList) {
					this.tableOperator
							.dropTable(CUSTOMER_TABLE_PREFIX + subTable.getTableName());
					this.dao.delById(subTable.getTableId());
				}
			}
		}

		this.tableOperator.dropTable(CUSTOMER_TABLE_PREFIX + tableName);
		this.dao.delById(tableId);
	}

	public String importXml(InputStream inputStream) throws Exception {
		String msg = "";
		Document doc = Dom4jUtil.loadXml(inputStream);
		Element root = doc.getRootElement();
		List<Element> itemLists = root.elements();
		if ((itemLists != null) && (itemLists.size() > 0)) {
			for (Element elm : itemLists) {
				Long tableId = Long.valueOf(UniqueIdUtil.genId());
				String flag = addFormTableImport(elm.asXML(), tableId);

				if (flag.length() == 0) {
					List<Element> tableElms = elm.elements();
					for (Element tableElm : tableElms) {
						String tableElmStr = tableElm.asXML();

						if (tableElmStr.indexOf(BpmFormTable.parElmName) != -1) {
							Long subTableId = Long
									.valueOf(UniqueIdUtil.genId());
							flag = addFormTableImport(tableElmStr, subTableId);

							if (flag.length() == 0) {
								List<Element> subTableElms = tableElm
										.elements();
								for (Element subTableElm : subTableElms) {
									addFormFieldImport(subTableElm.asXML(),
											subTableId);
								}
							} else {
								msg = msg + "存在重复子表" + flag + "\n";
							}

						} else {
							addFormFieldImport(tableElmStr, tableId);
						}
					}
				} else {
					msg = msg + "存在重复主表" + flag + "\n";
				}
			}
		}
		return msg;
	}

	private String addFormTableImport(String xmlStr, Long tableId)
			throws Exception {
		Unmarshaller unmarshallerTable = JAXBContext.newInstance(
				new Class[] { BpmFormTable.class }).createUnmarshaller();

		BpmFormTable bpmFormTable = (BpmFormTable) unmarshallerTable
				.unmarshal(new StringReader(xmlStr));
		BpmFormTable bft = this.dao.getByTableName(bpmFormTable.getTableName());
		if (bft == null) {
			bpmFormTable.setTableId(tableId);
			bpmFormTable
					.setMainTableId(bpmFormTable.getIsMain().shortValue() == 1 ? new Long(
							0L) : tableId);
			bpmFormTable.setPublishedBy(ContextUtil.getCurrentUser()
					.getFullname());
			bpmFormTable.setPublishTime(new Date());
			bpmFormTable.setIsPublished(new Short("0"));
			bpmFormTable.setIsExternal(0);
			bpmFormTable.setKeyType(new Short("0"));
			this.dao.add(bpmFormTable);
		}

		return bft != null ? bpmFormTable.getTableName() : "";
	}

	private void addFormFieldImport(String xmlStr, Long tableId)
			throws Exception {
		Unmarshaller unmarshallerField = JAXBContext.newInstance(
				new Class[] { BpmFormField.class }).createUnmarshaller();

		BpmFormField field = (BpmFormField) unmarshallerField
				.unmarshal(new StringReader(xmlStr));
		Long fieldId = Long.valueOf(UniqueIdUtil.genId());
		field.setFieldId(fieldId);
		field.setTableId(tableId);

		if (StringUtil.isNotEmpty(field.getScript())) {
			String[] scripts = field.getScript().split("/n");
			String script = "";
			if (scripts.length > 1) {
				for (String srp : scripts) {
					script = script + srp + "\n";
				}
				script = script.substring(0, script.length() - 1);
				field.setScript(script);
			}
		}

		if (StringUtil.isNotEmpty(field.getOptions())) {
			String[] options = field.getOptions().split("/n");
			String option = "";
			if (options.length > 1) {
				for (String opt : options) {
					option = option + opt + "\n";
				}
				option = option.substring(0, option.length() - 1);
				field.setScript(option);
			}
		}
		this.bmpFormFieldDao.add(field);
	}

	public String exportXml(Long[] tableId) throws FileNotFoundException,
			IOException {
		String strXml = "";
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("bpm");
		for (int i = 0; i < tableId.length; i++) {
			BpmFormTable parTable = (BpmFormTable) this.dao.getById(tableId[i]);
			if (BeanUtils.isNotEmpty(parTable)) {
				exportTable(parTable, root, BpmFormTable.parElmName);
			}
		}
		strXml = doc.asXML();
		return strXml;
	}

	private void exportTable(BpmFormTable bpmFormTable, Element root,
			String nodeName) {
		Element elements = root.addElement(nodeName);
		if (StringUtil.isNotEmpty(bpmFormTable.getTableName())) {
			elements.addAttribute("tableName", bpmFormTable.getTableName());
		}
		if (StringUtil.isNotEmpty(bpmFormTable.getTableDesc())) {
			elements.addAttribute("tableDesc", bpmFormTable.getTableDesc());
		}
		if (BeanUtils.isNotEmpty(bpmFormTable.getIsMain())) {
			elements.addAttribute("isMain", bpmFormTable.getIsMain().toString());
		}
		if (BeanUtils.isNotEmpty(bpmFormTable.getIsPublished())) {
			elements.addAttribute("isPublished", bpmFormTable.getIsPublished()
					.toString());
		}

		List<BpmFormField> fields = this.bmpFormFieldDao
				.getByTableId(bpmFormTable.getTableId());
		for (BpmFormField field : fields) {
			exportField(field, elements);
		}

		List<BpmFormTable> subTables = this.dao
				.getSubTableByMainTableId(bpmFormTable.getTableId());
		if ((subTables != null) && (subTables.size() > 0))
			for (BpmFormTable subTable : subTables)
				exportTable(subTable, elements, BpmFormTable.parElmName);
	}

	private void exportField(BpmFormField bpmFormField, Element root) {
		Element elements = root.addElement(BpmFormField.elmName);
		if (StringUtil.isNotEmpty(bpmFormField.getFieldName())) {
			elements.addAttribute("fieldName", bpmFormField.getFieldName());
		}
		if (StringUtil.isNotEmpty(bpmFormField.getFieldType())) {
			elements.addAttribute("fieldType", bpmFormField.getFieldType());
		}
		if (StringUtil.isNotEmpty(bpmFormField.getFieldDesc())) {
			elements.addAttribute("fieldDesc", bpmFormField.getFieldDesc());
		}
		if (BeanUtils.isNotEmpty(bpmFormField.getIsRequired())) {
			elements.addAttribute("isRequired", bpmFormField.getIsRequired()
					.toString());
		}
		if (BeanUtils.isNotEmpty(bpmFormField.getIsList())) {
			elements.addAttribute("isList", bpmFormField.getIsList().toString());
		}
		if (BeanUtils.isNotEmpty(bpmFormField.getIsQuery())) {
			elements.addAttribute("isQuery", bpmFormField.getIsQuery()
					.toString());
		}
		if (BeanUtils.isNotEmpty(bpmFormField.getCharLen())) {
			elements.addAttribute("charLen", bpmFormField.getCharLen()
					.toString());
		}
		if (BeanUtils.isNotEmpty(bpmFormField.getIntLen())) {
			elements.addAttribute("intLen", bpmFormField.getIntLen().toString());
		}
		if (BeanUtils.isNotEmpty(bpmFormField.getDecimalLen())) {
			elements.addAttribute("decimalLen", bpmFormField.getDecimalLen()
					.toString());
		}
		if (BeanUtils.isNotEmpty(bpmFormField.getIsDeleted())) {
			elements.addAttribute("isDeleted", bpmFormField.getIsDeleted()
					.toString());
		}
		if (StringUtil.isNotEmpty(bpmFormField.getValidRule())) {
			elements.addAttribute("validRule", bpmFormField.getValidRule());
		}
		if (StringUtil.isNotEmpty(bpmFormField.getOriginalName())) {
			elements.addAttribute("originalName",
					bpmFormField.getOriginalName());
		}
		if (BeanUtils.isNotEmpty(bpmFormField.getValueFrom())) {
			elements.addAttribute("valueFrom", bpmFormField.getValueFrom()
					.toString());
		}
		if (StringUtil.isNotEmpty(bpmFormField.getScript())) {
			String[] scripts = bpmFormField.getScript().split("\n");
			String script = "";
			if (scripts.length > 1) {
				for (String srp : scripts) {
					script = script + srp + "/n";
				}
				script = script.substring(0, script.length() - 2);
			} else {
				script = scripts[0];
			}
			elements.addAttribute("script", script);
		}
		if (BeanUtils.isNotEmpty(bpmFormField.getControlType())) {
			elements.addAttribute("controlType", bpmFormField.getControlType()
					.toString());
		}
		if (BeanUtils.isNotEmpty(bpmFormField.getIsHidden())) {
			elements.addAttribute("isHidden", bpmFormField.getIsHidden()
					.toString());
		}
		if (BeanUtils.isNotEmpty(bpmFormField.getIsFlowVar())) {
			elements.addAttribute("isFlowVar", bpmFormField.getIsFlowVar()
					.toString());
		}
		if (StringUtil.isNotEmpty(bpmFormField.getOptions())) {
			String[] options = bpmFormField.getOptions().split("\n");
			String option = "";
			if (options.length > 1) {
				for (String opt : options) {
					option = option + opt + "/n";
				}
				option = option.substring(0, option.length() - 2);
			} else {
				option = options[0];
			}
			elements.addAttribute("script", option);
		}
		if (StringUtil.isNotEmpty(bpmFormField.getCtlProperty()))
			elements.addAttribute("ctlProperty", bpmFormField.getCtlProperty());
	}
}
