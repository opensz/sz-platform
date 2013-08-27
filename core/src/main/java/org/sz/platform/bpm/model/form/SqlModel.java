package org.sz.platform.bpm.model.form;

public class SqlModel {
	private String sql = "";
	private Object[] values;

	public SqlModel() {
	}

	public SqlModel(String sql, Object[] values) {
		this.sql = sql;
		this.values = values;
	}

	public String getSql() {
		return this.sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getValues() {
		return this.values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}
}
