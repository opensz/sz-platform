package org.sz.core.customertable.impl;

import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sz.core.customertable.ColumnModel;
import org.sz.core.customertable.ITableOperator;
import org.sz.core.customertable.TableModel;

public class MysqlTableOperator implements ITableOperator {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate template) {
		this.jdbcTemplate = template;
	}

	public void createTable(TableModel model) throws SQLException {
		List columnList = model.getColumnList();

		StringBuffer sb = new StringBuffer();

		String pkColumn = null;

		sb.append("CREATE TABLE " + model.getName() + " (\n");
		for (int i = 0; i < columnList.size(); i++) {
			ColumnModel cm = (ColumnModel) columnList.get(i);
			sb.append(cm.getName()).append(" ");
			sb.append(getColumnType(cm.getColumnType(), cm.getCharLen(),
					cm.getIntLen(), cm.getDecimalLen()));
			sb.append(" ");

			if (!cm.getIsNull()) {
				sb.append(" NOT NULL ");
			}

			if (cm.getIsPk()) {
				if (pkColumn == null)
					pkColumn = cm.getName();
				else {
					pkColumn = pkColumn + "," + cm.getName();
				}
			}

			if ((cm.getComment() != null) && (cm.getComment().length() > 0)) {
				sb.append(" COMMENT '" + cm.getComment() + "'");
			}
			sb.append(",\n");
		}

		if (pkColumn != null) {
			sb.append(" PRIMARY KEY (" + pkColumn + ")");
		}

		sb.append("\n)");
		if ((model.getComment() != null) && (model.getComment().length() > 0)) {
			sb.append(" COMMENT='" + model.getComment() + "'");
		}

		sb.append(";");

		this.jdbcTemplate.execute(sb.toString());
	}

	public void updateTableComment(String tableName, String comment)
			throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("ALTER TABLE ");
		sb.append(tableName);
		sb.append(" COMMENT '");
		sb.append(comment);
		sb.append("';\n");

		this.jdbcTemplate.execute(sb.toString());
	}

	public void addColumn(String tableName, ColumnModel model)
			throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("ALTER TABLE ").append(tableName);
		sb.append(" ADD (");
		sb.append(model.getName()).append(" ");
		sb.append(getColumnType(model.getColumnType(), model.getCharLen(),
				model.getIntLen(), model.getDecimalLen()));

		if (!model.getIsNull()) {
			sb.append(" NOT NULL ");
		}
		if ((model.getComment() != null) && (model.getComment().length() > 0)) {
			sb.append(" COMMENT '" + model.getComment() + "'");
		}
		sb.append(");");

		this.jdbcTemplate.execute(sb.toString());
	}

	public void updateColumn(String tableName, String columnName,
			ColumnModel model) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("ALTER TABLE ").append(tableName);
		sb.append(" CHANGE " + columnName + " " + model.getName()).append(" ");
		sb.append(getColumnType(model.getColumnType(), model.getCharLen(),
				model.getIntLen(), model.getDecimalLen()));

		if (!model.getIsNull()) {
			sb.append(" NOT NULL ");
		}
		if ((model.getComment() != null) && (model.getComment().length() > 0)) {
			sb.append(" COMMENT '" + model.getComment() + "'");
		}
		sb.append(";");

		this.jdbcTemplate.execute(sb.toString());
	}

	private String getColumnType(String columnType, int charLen, int intLen,
			int decimalLen) {
		if ("varchar".equalsIgnoreCase(columnType))
			return "VARCHAR(" + charLen + ')';
		else if ("number".equalsIgnoreCase(columnType)) {
			return "DECIMAL(" + (intLen + decimalLen) + "," + decimalLen + ")";
		} else if ("date".equalsIgnoreCase(columnType)) {
			return "DATETIME";
		} else if ("int".equalsIgnoreCase(columnType)) {
			return "BIGINT(" + intLen + ")";
		} else if ("clob".equalsIgnoreCase(columnType)) {
			return "TEXT";
		}
		return "";
	}

	public void dropTable(String tableName) {
		String sql = "drop table if exists " + tableName;
		this.jdbcTemplate.execute(sql);
	}

	public void addForeignKey(String pkTableName, String fkTableName,
			String pkField, String fkField) {
		String sql = "ALTER TABLE " + fkTableName + " ADD CONSTRAINT fk_"
				+ fkTableName + " FOREIGN KEY (" + fkField + ") REFERENCES "
				+ pkTableName + " (" + pkField + ") ON DELETE CASCADE";
		this.jdbcTemplate.execute(sql);
	}

	public void dropForeignKey(String tableName, String keyName) {
		String sql = "ALTER TABLE " + tableName + " DROP FOREIGN KEY "
				+ keyName;

		this.jdbcTemplate.execute(sql);
	}

	public void createView(String viewName, String sqlStatement) {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE VIEW ").append(viewName).append(" AS ")
				.append(sqlStatement);
		this.jdbcTemplate.execute(sql.toString());
	}

	@Override
	public void dropView(String viewName) {
		StringBuilder sql = new StringBuilder();
		sql.append("DROP VIEW IF EXISTS ").append(viewName);
		this.jdbcTemplate.execute(sql.toString());
	}
}