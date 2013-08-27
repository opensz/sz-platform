package org.sz.platform.bpm.model.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubTable {
	private String tableName = "";

	private String pkName = "";

	private String fkName = "";

	private List<Map<String, Object>> dataList = new ArrayList();

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<Map<String, Object>> getDataList() {
		return this.dataList;
	}

	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}

	public void addRow(Map<String, Object> row) {
		this.dataList.add(row);
	}

	public String getPkName() {
		return this.pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public String getFkName() {
		return this.fkName;
	}

	public void setFkName(String fkName) {
		this.fkName = fkName;
	}
}
