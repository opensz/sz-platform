package org.sz.core.customertable.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sz.core.customertable.ColumnModel;
import org.sz.core.customertable.ITableOperator;
import org.sz.core.customertable.TableModel;

public class OracleTableOperator implements ITableOperator {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate template) {
		this.jdbcTemplate = template;
	}

	public void createTable(TableModel model) throws SQLException {
		List columnList = model.getColumnList();

		StringBuffer sb = new StringBuffer();

		String pkColumn = null;

		List<String> columnCommentList = new ArrayList();

		sb.append("CREATE TABLE " + model.getName() + " (\n");
		for (int i = 0; i < columnList.size(); i++) {
			ColumnModel cm = (ColumnModel) columnList.get(i);
			sb.append("    ").append(cm.getName()).append("    ");
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
				columnCommentList.add("COMMENT ON COLUMN " + model.getName()
						+ "." + cm.getName() + " IS '" + cm.getComment()
						+ "'\n");
			}
			sb.append(",\n");
		}

		if (pkColumn != null) {
			sb.append("    CONSTRAINT PK_").append(model.getName())
					.append(" PRIMARY KEY (").append(pkColumn).append(")");
		}

		sb.append("\n)");

		this.jdbcTemplate.execute(sb.toString());
		if ((model.getComment() != null) && (model.getComment().length() > 0)) {
			this.jdbcTemplate.execute("COMMENT ON TABLE " + model.getName()
					+ " IS '" + model.getComment() + "'\n");
		}
		for (String columnComment : columnCommentList)
			this.jdbcTemplate.execute(columnComment);
	}

	public void updateTableComment(String tableName, String comment)
			throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("COMMENT ON TABLE ");
		sb.append(tableName);
		sb.append(" IS '");
		sb.append(comment);
		sb.append("'\n");
		this.jdbcTemplate.execute(sb.toString());
	}

	public void addColumn(String tableName, ColumnModel model)
			throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("ALTER TABLE ").append(tableName);
		sb.append(" ADD ");
		sb.append(model.getName()).append(" ");
		sb.append(getColumnType(model.getColumnType(), model.getCharLen(),
				model.getIntLen(), model.getDecimalLen()));
		if (!model.getIsNull()) {
			sb.append(" NOT NULL ");
		}
		sb.append("\n");
		this.jdbcTemplate.execute(sb.toString());
		if ((model.getComment() != null) && (model.getComment().length() > 0))
			this.jdbcTemplate.execute("COMMENT ON COLUMN " + tableName + "."
					+ model.getName() + " IS '" + model.getComment() + "'");
	}

	public void updateColumn(String tableName, String columnName,
			ColumnModel model) throws SQLException {
		if (!columnName.equals(model.getName())) {
			StringBuffer modifyName = new StringBuffer("ALTER TABLE ")
					.append(tableName);
			modifyName.append(" RENAME COLUMN ").append(columnName)
					.append(" TO ").append(model.getName());
			this.jdbcTemplate.execute(modifyName.toString());
		}

		StringBuffer sb = new StringBuffer();

		sb.append("ALTER TABLE ").append(tableName);
		sb.append(" MODIFY(" + model.getName()).append(" ");
		sb.append(getColumnType(model.getColumnType(), model.getCharLen(),
				model.getIntLen(), model.getDecimalLen()));

		if (!model.getIsNull()) {
			sb.append(" NOT NULL ");
		}
		sb.append(")");

		this.jdbcTemplate.execute(sb.toString());

		if ((model.getComment() != null) && (model.getComment().length() > 0))
			this.jdbcTemplate.execute("COMMENT ON COLUMN " + tableName + "."
					+ model.getName() + " IS'" + model.getComment() + "'");
	}

	private String getColumnType(String columnType, int charLen, int intLen,
			int decimalLen) {
		if ("varchar".equals(columnType))
			return "VARCHAR2(" + charLen + ')';
		if ("number".equals(columnType))
			return "NUMBER(" + (intLen + decimalLen) + "," + decimalLen + ")";
		if ("date".equals(columnType))
			return "DATE";
		if ("int".equals(columnType))
			return "NUMBER(" + intLen + ")";
		if ("clob".equals(columnType)) {
			return "CLOB";
		}
		return "VARCHAR2(50)";
	}

	public void dropTable(String tableName) {
		String selSql = "select count(*) amount from user_objects where object_name = upper('"
				+ tableName + "')";
		int rtn = this.jdbcTemplate.queryForInt(selSql);
		if (rtn > 0) {
			String sql = "drop table " + tableName;
			this.jdbcTemplate.execute(sql);
		}
	}

	public void addForeignKey(String pkTableName, String fkTableName,
			String pkField, String fkField) {
		String sql = " ALTER TABLE " + fkTableName + " ADD CONSTRAINT fk_"
				+ fkTableName + " FOREIGN KEY (" + fkField + ") REFERENCES "
				+ pkTableName + " (" + pkField + ") ON DELETE CASCADE";
		this.jdbcTemplate.execute(sql);
	}

	public void dropForeignKey(String tableName, String keyName) {
		String sql = "ALTER   TABLE   " + tableName + "   DROP   CONSTRAINT  "
				+ keyName;
		this.jdbcTemplate.execute(sql);
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