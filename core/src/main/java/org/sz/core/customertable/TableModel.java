package org.sz.core.customertable;

import java.util.ArrayList;
import java.util.List;

import org.sz.core.customertable.ColumnModel;

public class TableModel {
	public static final String PK_COLUMN_NAME = "ID";
	public static final String FK_COLUMN_NAME = "REFID";
	public static final String CUSTOMER_COLUMN_PREFIX = "F_";
	public static final String CUSTOMER_TABLE_PREFIX = "W_";
	public static final String CUSTOMER_ASSET_TABLE_PREFIX = "Z_ASSET_";
	public static final String CUSTOMER_ASSET_VIEW_PREFIX = "V_Z_ASSET_";
	public static final String CUSTOMER_COLUMN_CURRENTUSERID = "curentUserId_";
	public static final String FlowRunId = "flowRunId_";
	public static final int CUSTOMER_TABLE_NAME_MAX_LENGTH = 50;
	public static final int CUSTOMER_COLUMN_NAME_MAX_LENGTH = 32;
	private String name = "";

	private String comment = "";

	private List<ColumnModel> columnList = new ArrayList<ColumnModel>();

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void addColumnModel(ColumnModel model) {
		this.columnList.add(model);
	}

	public List<ColumnModel> getColumnList() {
		return this.columnList;
	}

	public void setColumnList(List<ColumnModel> columnList) {
		this.columnList = columnList;
	}
}
