package org.sz.core.customertable;

import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.sz.core.customertable.ColumnModel;
import org.sz.core.customertable.TableModel;

public interface ITableOperator {
	void setJdbcTemplate(JdbcTemplate paramJdbcTemplate);

	void createTable(TableModel paramTableModel) throws SQLException;

	void dropTable(String paramString);

	void updateTableComment(String paramString1, String paramString2)
			throws SQLException;

	void addColumn(String paramString, ColumnModel paramColumnModel)
			throws SQLException;

	void updateColumn(String paramString1, String paramString2,
			ColumnModel paramColumnModel) throws SQLException;

	void addForeignKey(String paramString1, String paramString2,
			String paramString3, String paramString4);

	void dropForeignKey(String paramString1, String paramString2);

	void createView(String viewName, String sqlStatement);

	void dropView(String viewName);
}