package org.sz.core.dao.helper;

import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import org.sz.core.dao.helper.JdbcCommand;
import org.sz.core.dao.helper.ObjectHelper;

public class DbCmd<T> implements JdbcCommand {
	private ObjectHelper<T> helper;
	private T obj;
	private OperatorType type;

	public void setModel(T obj) {
		this.helper = new ObjectHelper();
		this.helper.setModel(obj);
		this.obj = obj;
	}

	public void setOperatorType(OperatorType type) {
		this.type = type;
	}

	public void execute(DataSource source) throws Exception {
		String sql = "";
		if (this.type == OperatorType.Add)
			sql = this.helper.getAddSql();
		else if (this.type == OperatorType.Upd)
			sql = this.helper.getUpdSql();
		else if (this.type == OperatorType.Del) {
			sql = this.helper.getDelSql();
		}
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(
				this.obj);
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(
				source);
		template.update(sql, namedParameters);
	}

	public static enum OperatorType {
		Add, Del, Upd, Get;
	}
}
