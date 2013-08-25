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
import org.sz.core.customertable.colmap.MySqlColumnMap;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.StringUtil;

public class MySqlTableMeta extends BaseTableMeta
{
  String sqlColumns = "select column_name,is_nullable,data_type,character_octet_length length,numeric_precision precisions,numeric_scale scale,column_key,column_comment  from information_schema.columns where table_schema=DATABASE() and table_name='%s' ";

  String sqlComment = "select table_name,table_comment  from information_schema.tables t where t.table_schema=DATABASE() and table_name='%s' ";

  String sqlAllTable = "select table_name,table_comment from information_schema.tables t where t.table_schema=DATABASE()";

  public TableModel getTableByName(String tableName)
  {
    TableModel model = getTableModel(tableName);

    List columnList = getColumnsByTableName(tableName);
    model.setColumnList(columnList);

    return model;
  }

  private List<ColumnModel> getColumnsByTableName(String tableName)
  {
    String sql = String.format(this.sqlColumns, new Object[] { tableName });
    this.jdbcHelper.setCurrentDb(this.currentDb);
    Map map = new HashMap();
    List list = this.jdbcHelper.queryForList(sql, map, new MySqlColumnMap());
    return list;
  }

  private TableModel getTableModel(final String tableName)
  {
    this.jdbcHelper.setCurrentDb(this.currentDb);
    String sql = String.format(this.sqlComment, new Object[] { tableName });
    TableModel tableModel = (TableModel)this.jdbcHelper.queryForObject(sql, null, 
    		new RowMapper(){
      public TableModel mapRow(ResultSet rs, int row)
        throws SQLException
      {
        TableModel tableModel = new TableModel();
        String comments = rs.getString("table_comment");
        comments = MySqlTableMeta.getComments(comments, tableName);
        tableModel.setName(tableName);
        tableModel.setComment(comments);
        return tableModel;
      }
    });
    if (BeanUtils.isEmpty(tableModel)) {
      tableModel = new TableModel();
    }
    return tableModel;
  }

  public Map<String, String> getTablesByName(String tableName)
  {
    if (StringUtil.isNotEmpty(tableName))
      this.sqlAllTable = (this.sqlAllTable + " and table_name like '%" + tableName + "%'");
    this.jdbcHelper.setCurrentDb(this.currentDb);
    Map parameter = new HashMap();
    List list = this.jdbcHelper.queryForList(this.sqlAllTable, parameter, new RowMapper()
    {
      public Map<String, String> mapRow(ResultSet rs, int row) throws SQLException
      {
        String tableName = rs.getString("table_name");
        String comments = rs.getString("table_comment");
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
      comments = getComments(comments, name);
      map.put(name, comments);
    }

    return map;
  }

  public static String getComments(String comments, String defaultValue)
  {
    if (StringUtil.isEmpty(comments)) return defaultValue;
    int idx = comments.indexOf("InnoDB free");
    if (idx > -1) {
      comments = StringUtil.trimSufffix(comments.substring(0, idx).trim(), ";");
    }
    if (StringUtil.isEmpty(comments)) {
      comments = defaultValue;
    }
    return comments;
  }
}