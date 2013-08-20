 package org.sz.core.dao.helper;
 
 import org.sz.core.dao.helper.ColumnModel;

 import java.lang.reflect.Field;
 import java.util.ArrayList;
import java.util.List;

import org.sz.core.annotion.ClassDescription;
import org.sz.core.annotion.FieldDescription;
 
 public class ObjectHelper<T>
 {
   private T obj;
 
   public void setModel(T obj)
   {
     this.obj = obj;
   }
 
   public String getTableName()
   {
     Class cls = this.obj.getClass();
     ClassDescription clsDesc = (ClassDescription)cls.getAnnotation(ClassDescription.class);
     if (clsDesc == null)
       return cls.getSimpleName();
     return clsDesc.tableName();
   }
 
   public List<ColumnModel> getColumns()
   {
     List list = new ArrayList();
     Class cls = this.obj.getClass();
     Field[] fields = cls.getDeclaredFields();
     for (int i = 0; i < fields.length; i++)
     {
       Field fld = fields[i];
       ColumnModel column = new ColumnModel();
       column.setPropery(fld.getName());
 
       FieldDescription fldDesc = (FieldDescription)fld.getAnnotation(FieldDescription.class);
       if (fldDesc == null)
       {
         column.setColumnName(fld.getName());
         column.setPk(false);
       }
       else
       {
         column.setColumnName(fldDesc.columnName());
         column.setPk(fldDesc.pk());
         column.setCanUpd(fldDesc.canUpd());
       }
       list.add(column);
     }
     return list;
   }
 
   public ColumnModel getPk(List<ColumnModel> list)
   {
     ColumnModel columnModel = null;
     int len = list.size();
     for (int i = 0; i < len; i++)
     {
       ColumnModel model = (ColumnModel)list.get(i);
       if (model.getPk())
         return model;
     }
     return columnModel;
   }
 
   private List<ColumnModel> getCommonCols(List<ColumnModel> list)
   {
     List cols = new ArrayList();
     int len = list.size();
     for (int i = 0; i < len; i++)
     {
       ColumnModel model = (ColumnModel)list.get(i);
       if (!model.getPk()) {
         cols.add(model);
       }
     }
     return cols;
   }
 
   private String[] getInsertColumns()
   {
     List list = getColumns();
     String cols = "";
     String vals = "";
     int len = list.size();
     String[] aryStr = new String[2];
     for (int i = 0; i < len; i++)
     {
       ColumnModel column = (ColumnModel)list.get(i);
       if (i < len - 1)
       {
         cols = cols + column.getColumnName() + ",";
         vals = vals + ":" + column.getPropery() + ",";
       }
       else
       {
         cols = cols + column.getColumnName();
         vals = vals + ":" + column.getPropery();
       }
     }
     aryStr[0] = cols;
     aryStr[1] = vals;
     return aryStr;
   }
 
   public String getUpdSql()
   {
     List list = getColumns();
     List commonList = getCommonCols(list);
     ColumnModel pk = getPk(list);
     String tableName = getTableName();
     String sql = "update ";
 
     sql = sql + tableName + " set ";
 
     String tmp = "";
     int len = commonList.size();
     for (int i = 0; i < len; i++)
     {
       ColumnModel model = (ColumnModel)list.get(i);
       if (model.getCanUpd()) {
         tmp = tmp + model.getColumnName() + "=:" + model.getPropery() + ",";
       }
     }
     if (tmp.length() > 0) {
       tmp = tmp.substring(0, tmp.length() - 1);
     }
     sql = sql + tmp;
 
     sql = sql + " where " + pk.getColumnName() + "=:" + pk.getPropery();
 
     return sql;
   }
 
   public String getDelSql()
   {
     List list = getColumns();
     String tableName = getTableName();
     ColumnModel column = getPk(list);
     String sql = "delete from " + tableName + " where " + column.getColumnName() + "=:" + column.getPropery();
     return sql;
   }
 
   public String getDetailSql()
   {
     List list = getColumns();
     String tableName = getTableName();
     ColumnModel column = getPk(list);
     String sql = "select * from " + tableName + " where " + column.getColumnName() + "=:" + column.getPropery();
     return sql;
   }
 
   public String getAddSql()
   {
     String tableName = getTableName();
     String[] aryCol = getInsertColumns();
     StringBuffer sb = new StringBuffer();
     sb.append("insert into ");
     sb.append(tableName);
     sb.append("(");
     sb.append(aryCol[0]);
     sb.append(")");
     sb.append(" values (");
     sb.append(aryCol[1]);
     sb.append(")");
     return sb.toString();
   }
 }

