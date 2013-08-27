package org.sz.core.customertable.impl;

import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sz.core.customertable.ColumnModel;
import org.sz.core.customertable.ITableOperator;
import org.sz.core.customertable.TableModel;

public class MsSqlTableOperator implements ITableOperator {
	public void setJdbcTemplate(JdbcTemplate template) {
	}

	public void createTable(TableModel model) throws SQLException {
	}

	public void updateTableComment(String tableName, String comment)
			throws SQLException {
	}

	public void addColumn(String tableName, ColumnModel model)
			throws SQLException {
	}

	public void updateColumn(String tableName, String columnName,
			ColumnModel model) throws SQLException {
	}

	public void dropTable(String tableName) {
	}

	public void addForeignKey(String pkTableName, String fkTableName,
			String pkField, String fkField) {
	}

	public void dropForeignKey(String tableName, String keyName) {
	}

	@Override
	public void createView(String viewName, String sqlStatement) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropView(String viewName) {
		// TODO Auto-generated method stub

	}
}
