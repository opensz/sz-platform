package org.sz.core.customertable.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sz.core.customertable.ColumnModel;
import org.sz.core.customertable.ITableOperator;
import org.sz.core.customertable.TableModel;

public class SqlserverTableOperator implements ITableOperator {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate template) {
		this.jdbcTemplate = template;
	}

	public void createTable(TableModel model) throws SQLException {
		List columnList = model.getColumnList();

		StringBuffer createTableSql = new StringBuffer();

		String pkColumn = null;

		List<String> columnCommentList = new ArrayList();

		createTableSql.append("CREATE TABLE " + model.getName() + " (\n");
		for (int i = 0; i < columnList.size(); i++) {
			ColumnModel cm = (ColumnModel) columnList.get(i);
			createTableSql.append("    ").append(cm.getName()).append("    ");
			createTableSql.append(getColumnType(cm.getColumnType(),
					cm.getCharLen(), cm.getIntLen(), cm.getDecimalLen()));
			createTableSql.append(" ");

			if (!cm.getIsNull()) {
				createTableSql.append(" NOT NULL ");
			}

			if (cm.getIsPk()) {
				if (pkColumn == null)
					pkColumn = cm.getName();
				else {
					pkColumn = pkColumn + "," + cm.getName();
				}

			}

			if ((cm.getComment() != null) && (cm.getComment().length() > 0)) {
				StringBuffer comment = new StringBuffer(
						"EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'");
				comment.append(cm.getComment())
						.append("' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'")
						.append(model.getName())
						.append("', @level2type=N'COLUMN', @level2name=N'")
						.append(cm.getName()).append("'");

				columnCommentList.add(comment.toString());
			}
			createTableSql.append(",\n");
		}

		if (pkColumn != null) {
			createTableSql.append("    CONSTRAINT PK_").append(model.getName())
					.append(" PRIMARY KEY (").append(pkColumn).append(")");
		}

		createTableSql.append("\n)");
		this.jdbcTemplate.execute(createTableSql.toString());

		if ((model.getComment() != null) && (model.getComment().length() > 0)) {
			StringBuffer tableComment = new StringBuffer(
					"EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'");
			tableComment
					.append(model.getComment())
					.append("' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'")
					.append(model.getName()).append("'");

			this.jdbcTemplate.execute(tableComment.toString());
		}
		for (String columnComment : columnCommentList)
			this.jdbcTemplate.execute(columnComment);
	}

	public void updateTableComment(String tableName, String comment)
			throws SQLException {
		StringBuffer commentSql = new StringBuffer(
				"EXEC sys.sp_updateextendedproperty @name=N'MS_Description', @value=N'");
		commentSql
				.append(comment)
				.append("' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'")
				.append(tableName).append("'");

		this.jdbcTemplate.execute(commentSql.toString());
	}

	public void addColumn(String tableName, ColumnModel model)
			throws SQLException {
		StringBuffer alterSql = new StringBuffer();
		alterSql.append("ALTER TABLE ").append(tableName);
		alterSql.append(" ADD ");
		alterSql.append(model.getName()).append(" ");
		alterSql.append(getColumnType(model.getColumnType(),
				model.getCharLen(), model.getIntLen(), model.getDecimalLen()));
		if (!model.getIsNull()) {
			alterSql.append(" NOT NULL ");
		}
		alterSql.append("\n");
		this.jdbcTemplate.execute(alterSql.toString());
		if ((model.getComment() != null) && (model.getComment().length() > 0)) {
			StringBuffer comment = new StringBuffer(
					"EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'");
			comment.append(model.getComment())
					.append("' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'")
					.append(tableName)
					.append("', @level2type=N'COLUMN', @level2name=N'")
					.append(model.getName()).append("'");

			this.jdbcTemplate.execute(comment.toString());
		}
	}

	public void updateColumn(String tableName, String columnName,
			ColumnModel model) throws SQLException {
		if (!columnName.equals(model.getName())) {
			StringBuffer modifyName = new StringBuffer("EXEC sp_rename '");
			modifyName.append(tableName).append(".[").append(columnName)
					.append("]','").append(model.getName())
					.append("', 'COLUMN'");

			this.jdbcTemplate.execute(modifyName.toString());
		}

		StringBuffer alterSql = new StringBuffer();
		alterSql.append("ALTER TABLE ").append(tableName);
		alterSql.append(" ALTER COLUMN " + model.getName()).append(" ");
		alterSql.append(getColumnType(model.getColumnType(),
				model.getCharLen(), model.getIntLen(), model.getDecimalLen()));

		if (!model.getIsNull()) {
			alterSql.append(" NOT NULL ");
		}

		this.jdbcTemplate.execute(alterSql.toString());

		if ((model.getComment() != null) && (model.getComment().length() > 0)) {
			StringBuffer comment = new StringBuffer(
					"EXEC sys.sp_updateextendedproperty @name=N'MS_Description', @value=N'");
			comment.append(model.getComment())
					.append("' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'")
					.append(tableName)
					.append("', @level2type=N'COLUMN', @level2name=N'")
					.append(model.getName()).append("'");

			this.jdbcTemplate.execute(comment.toString());
		}
	}

	private String getColumnType(String columnType, int charLen, int intLen,
			int decimalLen) {
		if ("varchar".equals(columnType))
			return "VARCHAR(" + charLen + ')';
		if ("number".equals(columnType))
			return "NUMERIC(" + (intLen + decimalLen) + "," + decimalLen + ")";
		if ("date".equals(columnType))
			return "DATETIME";
		if ("int".equals(columnType)) {
			return "NUMERIC(" + intLen + ")";
		}
		if ("clob".equals(columnType)) {
			return "TEXT";
		}

		return "";
	}

	public void dropTable(String tableName) {
		String sql = "IF OBJECT_ID(N'" + tableName
				+ "', N'U') IS NOT NULL  DROP TABLE " + tableName;
		this.jdbcTemplate.execute(sql);
	}

	public void addForeignKey(String pkTableName, String fkTableName,
			String pkField, String fkField) {
		String sql = "  ALTER TABLE " + fkTableName + " ADD CONSTRAINT fk_"
				+ fkTableName + " FOREIGN KEY (" + fkField + ") REFERENCES "
				+ pkTableName + " (" + pkField + ")   ON DELETE CASCADE";
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