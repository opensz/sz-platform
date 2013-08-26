package org.sz.platform.bpm.model.form;

import java.util.LinkedHashMap;
import java.util.Map;

public class TableRelation {
	private String pk = "";

	private Map<String, String> relations = new LinkedHashMap();

	public TableRelation() {
	}

	public TableRelation(String pk) {
		this.pk = pk;
	}

	public String getPk() {
		return this.pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public Map<String, String> getRelations() {
		return this.relations;
	}

	public void setRelations(Map<String, String> relations) {
		this.relations = relations;
	}

	public void addRelation(String table, String fk) {
		this.relations.put(table, fk);
	}
}
