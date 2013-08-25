package org.sz.core.customertable;

public class ColumnModel {
	public static final String COLUMNTYPE_INT = "int";
	public static final String COLUMNTYPE_VARCHAR = "varchar";
	public static final String COLUMNTYPE_CLOB = "clob";
	public static final String COLUMNTYPE_NUMBER = "number";
	public static final String COLUMNTYPE_DATE = "date";
	private String name = "";

	private String comment = "";

	private boolean isPk = false;

	private boolean isFk = false;

	private boolean isNull = true;
	private String columnType;
	private int charLen = 0;

	private int decimalLen = 0;

	private int intLen = 0;

	private String fkRefTable = "";

	private String fkRefColumn = "";

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

	public boolean getIsPk() {
		return this.isPk;
	}

	public void setIsPk(boolean isPk) {
		this.isPk = isPk;
	}

	public boolean getIsNull() {
		return this.isNull;
	}

	public void setIsNull(boolean isNull) {
		this.isNull = isNull;
	}

	public String getColumnType() {
		return this.columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public int getCharLen() {
		if(this.charLen==4000){
			this.charLen=2000;
		}
		return this.charLen;
	}

	public void setCharLen(int charLen) {
		this.charLen = charLen;
	}

	public int getDecimalLen() {
		return this.decimalLen;
	}

	public void setDecimalLen(int decimalLen) {
		this.decimalLen = decimalLen;
	}

	public int getIntLen() {
		return this.intLen;
	}

	public void setIntLen(int intLen) {
		this.intLen = intLen;
	}

	public boolean getIsFk() {
		return this.isFk;
	}

	public void setIsFk(boolean isFk) {
		this.isFk = isFk;
	}

	public String getFkRefTable() {
		return this.fkRefTable;
	}

	public void setFkRefTable(String fkRefTable) {
		this.fkRefTable = fkRefTable;
	}

	public String getFkRefColumn() {
		return this.fkRefColumn;
	}

	public void setFkRefColumn(String fkRefColumn) {
		this.fkRefColumn = fkRefColumn;
	}

	/**
	 * 重写equals方法用于判断该列是否有做修改
	 */
	public boolean equals(Object obj) {
		if (obj instanceof ColumnModel) {
			ColumnModel columnModel = (ColumnModel) obj;
			if (this.name.equals(columnModel.getName())
					&& this.isFk == columnModel.getIsFk()
					&& this.comment.equals(columnModel.getComment())
					&& this.isPk == columnModel.getIsPk()
					&& this.isNull == columnModel.getIsNull()
					&& this.columnType.equals(columnModel.getColumnType())
					&& this.charLen == columnModel.getCharLen()
					&& this.decimalLen == columnModel.getDecimalLen()
					&& this.intLen == columnModel.getIntLen()) {
				return true;
			}
		}
		return false;
	}
}