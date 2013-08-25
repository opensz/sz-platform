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
import org.sz.core.customertable.colmap.SqlServerColumnMap;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.StringUtil;

public class SqlServerTableMeta extends BaseTableMeta
{
  private String sqlPk = "SELECT c.COLUMN_NAME COLUMN_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS pk ,INFORMATION_SCHEMA.KEY_COLUMN_USAGE c WHERE \tpk.TABLE_NAME LIKE '%s' and\tCONSTRAINT_TYPE = 'PRIMARY KEY' and\tc.TABLE_NAME = pk.TABLE_NAME and\tc.CONSTRAINT_NAME = pk.CONSTRAINT_NAME ";

  private String sqlTableComment = "select cast(b.value as varchar) comment from sys.tables a, sys.extended_properties b where a.type='U' and a.object_id=b.major_id and b.minor_id=0 and a.name='%s'";

  private String sqlColumn = "select a.name name, c.name typename, a.max_length length, a.is_nullable is_nullable,a.precision precision,a.scale scale,(select count(*) from sys.identity_columns where sys.identity_columns.object_id = a.object_id and a.column_id = sys.identity_columns.column_id) as autoGen,(select cast(value as varchar) from sys.extended_properties where sys.extended_properties.major_id = a.object_id and sys.extended_properties.minor_id = a.column_id) as description from sys.columns a, sys.tables b, sys.types c where a.object_id = b.object_id and a.system_type_id=c.system_type_id and b.name='%s' and c.name<>'sysname' order by a.column_id";

  private String sqlAllTables = "select name from sys.tables where type='U' and name<>'sysdiagrams'";

  public TableModel getTableByName(String tableName)
  {
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
    this.jdbcHelper.setCurrentDb(this.currentDb);
    String sql = String.format(this.sqlPk, new Object[] { tableName });
    Object rtn = this.jdbcHelper.queryForObject(sql, null, new RowMapper()
    {
      public String mapRow(ResultSet rs, int row)
        throws SQLException
      {
        return rs.getString("COLUMN_NAME");
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
        tableModel.setComment(rs.getString("comment"));
        return tableModel;
      }
    });
    if (BeanUtils.isEmpty(tableModel)) {
      tableModel = new TableModel();
    }
    tableModel.setName(tableName);

    return tableModel;
  }

  private List<ColumnModel> getColumnsByTableName(String tableName)
  {
    String sql = String.format(this.sqlColumn, new Object[] { tableName });
    this.jdbcHelper.setCurrentDb(this.currentDb);
    Map map = new HashMap();
    List list = this.jdbcHelper.queryForList(sql, map, new SqlServerColumnMap());

    return list;
  }

  public Map<String, String> getTablesByName(String tableName)
  {
    if (StringUtil.isNotEmpty(tableName)) {
      this.sqlAllTables = (this.sqlAllTables + " and  lower(name) like '%" + tableName.toLowerCase() + "%'");
    }
    this.jdbcHelper.setCurrentDb(this.currentDb);
    Map parameter = new HashMap();
    List list = this.jdbcHelper.queryForList(this.sqlAllTables, parameter, new RowMapper()
    {
      public Map<String, String> mapRow(ResultSet rs, int row)
        throws SQLException
      {
        String tableName = rs.getString("name");

        Map map = new HashMap();
        map.put("name", tableName);

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