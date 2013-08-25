package org.sz.core.customertable.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.sz.core.customertable.BaseTableMeta;
import org.sz.core.customertable.ColumnModel;
import org.sz.core.customertable.TableModel;
import org.sz.core.customertable.colmap.OracleColumnMap;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.StringUtil;

public class OracleTableMeta extends BaseTableMeta
{
  private String sqlPk = "select column_name from user_constraints c,user_cons_columns col where c.constraint_name=col.constraint_name and c.constraint_type='P'and c.table_name='%s'";

  private String sqlTableComment = "select TABLE_NAME,DECODE(COMMENTS,null,TABLE_NAME,comments) comments from user_tab_comments  where table_type='TABLE' AND table_name ='%s'";

  private String sqlColumn = "select    A.column_name NAME,A.data_type TYPENAME,A.data_length LENGTH,A.data_precision PRECISION,    A.Data_Scale SCALE,A.Data_default, A.NULLABLE, decode(B.comments,null,a.COLUMN_NAME,b.COMMENTS) DESCRIPTION  from  user_tab_columns A,user_col_comments B where a.COLUMN_NAME=b.column_name and    A.Table_Name = B.Table_Name and  A.Table_Name='%s' order by A.column_id";

  private String sqlAllTables = "select TABLE_NAME,DECODE(COMMENTS,null,TABLE_NAME,comments) comments from user_tab_comments where table_type='TABLE'  ";

  public TableModel getTableByName(String tableName)
  {
    tableName = tableName.toUpperCase();
    TableModel model = getTableModel(tableName);

    List<ColumnModel> columnList = getColumnsByTableName(tableName);
    model.setColumnList(columnList);

    String pk = getPkColumn(tableName);
    if (StringUtil.isNotEmpty(pk)) {
      for (ColumnModel column : columnList) {
        if (column.getName().equals(pk)) {
          column.setIsPk(true);
        }
      }
    }
    return model;
  }

  private String getPkColumn(String tableName)
  {
    tableName = tableName.toUpperCase();
    this.jdbcHelper.setCurrentDb(this.currentDb);
    String sql = String.format(this.sqlPk, new Object[] { tableName });
    Object rtn = this.jdbcHelper.queryForObject(sql, null, new RowMapper()
    {
      public String mapRow(ResultSet rs, int row) throws SQLException
      {
        return rs.getString("column_name");
      }
    });
    if (rtn == null) {
      return "";
    }
    return rtn.toString();
  }

  private TableModel getTableModel(final String tableName)
  {
    this.jdbcHelper.setCurrentDb(this.currentDb);
    String sql = String.format(this.sqlTableComment, new Object[] { tableName });
    TableModel tableModel = (TableModel)this.jdbcHelper.queryForObject(sql, null, new RowMapper()
    {
      public TableModel mapRow(ResultSet rs, int row)
        throws SQLException
      {
        TableModel tableModel = new TableModel();
        tableModel.setName(tableName);
        tableModel.setComment(rs.getString("comments"));
        return tableModel;
      }
    });
    if (BeanUtils.isEmpty(tableModel)) {
      tableModel = new TableModel();
    }
    return tableModel;
  }

  private List<ColumnModel> getColumnsByTableName(String tableName)
  {
    String sql = String.format(this.sqlColumn, new Object[] { tableName });
    this.jdbcHelper.setCurrentDb(this.currentDb);
    Map map = new HashMap();
    List list = this.jdbcHelper.queryForList(sql, map, new OracleColumnMap());
    return list;
  }

  public Map<String, String> getTablesByName(String tableName)
  {
    if (StringUtil.isNotEmpty(tableName))
      this.sqlAllTables = (this.sqlAllTables + " and  lower(table_name) like '%" + tableName.toLowerCase() + "%'");
    this.jdbcHelper.setCurrentDb(this.currentDb);
    Map parameter = new HashMap();
    List list = this.jdbcHelper.queryForList(this.sqlAllTables, parameter, new RowMapper()
    {
      public Map<String, String> mapRow(ResultSet rs, int row) throws SQLException
      {
        String tableName = rs.getString("table_name");
        String comments = rs.getString("comments");
        Map map = new HashMap();
        map.put("name", tableName);
        map.put("comments", comments);
        return map;
      }
    });
    Map map = new LinkedHashMap();
    for (int i = 0; i < list.size(); i++) {
      Map tmp = (Map)list.get(i);
      String name = (String)tmp.get("name");
      String comments = (String)tmp.get("comments");
      map.put(name, comments);
    }

    return map;
  }
}
