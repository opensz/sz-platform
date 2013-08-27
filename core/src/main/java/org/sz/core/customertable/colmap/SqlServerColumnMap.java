package org.sz.core.customertable.colmap;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.sz.core.customertable.ColumnModel;
import org.sz.core.util.StringUtil;

public class SqlServerColumnMap implements RowMapper<ColumnModel> {
	public ColumnModel mapRow(ResultSet rs, int row) throws SQLException {
		ColumnModel column = new ColumnModel();

		String name = rs.getString("name");
		String is_nullable = rs.getString("is_nullable");
		String data_type = rs.getString("typename");
		String length = rs.getString("length");
		String precisions = rs.getString("precision");
		String scale = rs.getString("scale");

		int iLength = StringUtil.isEmpty(length) ? 0 : Integer.parseInt(length);
		int iPrecisions = StringUtil.isEmpty(precisions) ? 0 : Integer
				.parseInt(precisions);
		int iScale = StringUtil.isEmpty(scale) ? 0 : Integer.parseInt(scale);

		column.setName(name);
		boolean isNull = is_nullable.equals("1");
		column.setIsNull(isNull);

		setType(data_type, iLength, iPrecisions, iScale, column);

		return column;
	}

	private void setType(String dbtype, int length, int precision, int scale,
			ColumnModel columnModel) {
		if (dbtype.equals("int")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
			columnModel.setDecimalLen(scale);
			return;
		}

		if (dbtype.equals("bigint")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
			columnModel.setDecimalLen(scale);
			return;
		}

		if (dbtype.equals("smallint")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
			columnModel.setDecimalLen(scale);
			return;
		}

		if (dbtype.equals("tinyint")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
			columnModel.setDecimalLen(scale);
			return;
		}

		if (dbtype.equals("bit")) {
			columnModel.setColumnType("number");
			return;
		}

		if (dbtype.equals("decimal")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
			columnModel.setDecimalLen(scale);
			return;
		}

		if (dbtype.equals("numeric")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
			columnModel.setDecimalLen(scale);
			return;
		}

		if (dbtype.equals("real")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
			return;
		}

		if (dbtype.equals("float")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
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

		if (dbtype.equals("varchar")) {
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(length);

			return;
		}

		if (dbtype.equals("nchar")) {
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(length);
			return;
		}

		if (dbtype.equals("nvarchar")) {
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(length);

			return;
		}

		if (dbtype.startsWith("datetime")) {
			columnModel.setColumnType("date");

			return;
		}

		if (dbtype.endsWith("money")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
			columnModel.setDecimalLen(scale);
			return;
		}

		if (dbtype.endsWith("smallmoney")) {
			columnModel.setColumnType("clob");
			columnModel.setIntLen(precision);
			columnModel.setDecimalLen(scale);
			return;
		}

		if (dbtype.endsWith("text")) {
			columnModel.setColumnType("clob");
			columnModel.setCharLen(length);
			return;
		}

		if (dbtype.endsWith("ntext")) {
			columnModel.setColumnType("clob");
			columnModel.setCharLen(length);
			return;
		}
	}
}