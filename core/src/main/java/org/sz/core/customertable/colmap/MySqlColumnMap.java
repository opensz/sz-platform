package org.sz.core.customertable.colmap;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.sz.core.customertable.ColumnModel;
import org.sz.core.customertable.impl.MySqlTableMeta;
import org.sz.core.util.StringUtil;

public class MySqlColumnMap implements RowMapper<ColumnModel> {
	public ColumnModel mapRow(ResultSet rs, int row) throws SQLException {
		ColumnModel column = new ColumnModel();

		String name = rs.getString("column_name");
		String is_nullable = rs.getString("is_nullable");
		String data_type = rs.getString("data_type");
		String length = rs.getString("length");
		String precisions = rs.getString("precisions");
		String scale = rs.getString("scale");
		String column_key = rs.getString("column_key");
		String column_comment = rs.getString("column_comment");
		column_comment = MySqlTableMeta.getComments(column_comment, name);

		int iLength = StringUtil.isEmpty(length) ? 0 : Integer.parseInt(length);
		int iPrecisions = StringUtil.isEmpty(precisions) ? 0 : Integer
				.parseInt(precisions);
		int iScale = StringUtil.isEmpty(scale) ? 0 : Integer.parseInt(scale);

		column.setName(name);
		column.setComment(column_comment);
		if ((StringUtil.isNotEmpty(column_key)) && ("PRI".equals(column_key)))
			column.setIsPk(true);
		boolean isNull = is_nullable.equals("YES");
		column.setIsNull(isNull);

		setType(data_type, iLength, iPrecisions, iScale, column);

		return column;
	}

	private void setType(String dbtype, int length, int precision, int scale,
			ColumnModel columnModel) {
		if (dbtype.equals("bigint")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(19);
			columnModel.setDecimalLen(0);
			return;
		}

		if (dbtype.equals("int")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(10);
			columnModel.setDecimalLen(0);
			return;
		}

		if (dbtype.equals("mediumint")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(7);
			columnModel.setDecimalLen(0);
			return;
		}

		if (dbtype.equals("smallint")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(5);
			columnModel.setDecimalLen(0);
			return;
		}

		if (dbtype.equals("tinyint")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(3);
			columnModel.setDecimalLen(0);
			return;
		}

		if (dbtype.equals("decimal")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
			columnModel.setDecimalLen(scale);
			return;
		}

		if (dbtype.equals("double")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(18);
			columnModel.setDecimalLen(4);
			return;
		}

		if (dbtype.equals("float")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(8);
			columnModel.setDecimalLen(4);
			return;
		}

		if (dbtype.equals("varchar")) {
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(length);

			return;
		}

		if (dbtype.equals("char")) {
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(length);
			return;
		}

		if (dbtype.startsWith("date")) {
			columnModel.setColumnType("date");

			return;
		}

		if (dbtype.startsWith("time")) {
			columnModel.setColumnType("date");

			return;
		}

		if (dbtype.endsWith("text")) {
			columnModel.setColumnType("clob");
			columnModel.setCharLen(65535);
			return;
		}
	}
}