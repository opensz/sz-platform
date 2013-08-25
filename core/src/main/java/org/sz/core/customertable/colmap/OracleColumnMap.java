 package org.sz.core.customertable.colmap;
 
  import java.sql.ResultSet;
 import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.sz.core.customertable.ColumnModel;
 
 public class OracleColumnMap
   implements RowMapper<ColumnModel>
 {
   public ColumnModel mapRow(ResultSet rs, int row)
     throws SQLException
   {
     ColumnModel column = new ColumnModel();
     String name = rs.getString("NAME");
     String typeName = rs.getString("TYPENAME");
     int length = rs.getInt("LENGTH");
     int precision = rs.getInt("PRECISION");
     int scale = rs.getInt("SCALE");
     boolean isNull = rs.getString("NULLABLE").equals("Y");
     String comments = rs.getString("DESCRIPTION");
 
     column.setName(name);
     column.setComment(comments);
     column.setIsNull(isNull);
 
     setType(typeName, length, precision, scale, column);
 
     return column;
   }
 
   private void setType(String dbtype, int length, int precision, int scale, ColumnModel column)
   {
     if (dbtype.indexOf("CHAR") > -1) {
       column.setColumnType("varchar");
       column.setCharLen(length);
       return;
     }
     if (dbtype.equals("NUMBER")) {
       column.setColumnType("number");
       column.setIntLen(precision);
       column.setDecimalLen(scale);
       return;
     }
     if (dbtype.equals("DATE")) {
       column.setColumnType("date");
       return;
     }
     if (dbtype.equals("CLOB")) {
       column.setColumnType("clob");
       return;
     }
   }
 }