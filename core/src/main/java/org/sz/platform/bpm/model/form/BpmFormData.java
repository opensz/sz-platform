package org.sz.platform.bpm.model.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BpmFormData {
	private long tableId = 0L;

	private String tableName = "";
	private PkValue pkValue;
	private List<SubTable> subTableList = new ArrayList();

	private Map<String, Object> mainFields = new HashMap();

	private Map<String, String> options = new HashMap();

	private Map<String, Object> variables = new HashMap();

	public PkValue getPkValue() {
		return this.pkValue;
	}

	public void setPkValue(PkValue pkValue) {
		this.pkValue = pkValue;
	}

	public long getTableId() {
		return this.tableId;
	}

	public void setTableId(long tableId) {
		this.tableId = tableId;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<SubTable> getSubTableList() {
		return this.subTableList;
	}

	public Map<String, SubTable> getSubTableMap() {
		Map map = new HashMap();
		for (Iterator it = this.subTableList.iterator(); it.hasNext();) {
			SubTable tb = (SubTable) it.next();
			map.put(tb.getTableName(), tb);
		}
		return map;
	}

	public void setSubTableList(List<SubTable> subTableList) {
		this.subTableList = subTableList;
	}

	public Map<String, Object> getMainFields() {
		return this.mainFields;
	}

	public void setMainFields(Map<String, Object> mainFields) {
		this.mainFields = mainFields;
	}

	public Map<String, String> getOptions() {
		return this.options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}

	public void addOpinion(String formName, String value) {
		this.options.put(formName, value);
	}

	public void addSubTable(SubTable table) {
		this.subTableList.add(table);
	}

	public void addMainFields(String key, Object obj) {
		this.mainFields.put(key, obj);
	}

	public void addMainFields(Map<String, Object> map) {
		this.mainFields.putAll(map);
	}

	public Map<String, Object> getMainCommonFields() {
		Map map = new HashMap();
		map.putAll(this.mainFields);
		map.remove(this.pkValue.getName());
		return map;
	}

	public Map<String, Object> getVariables() {
		return this.variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}
}
