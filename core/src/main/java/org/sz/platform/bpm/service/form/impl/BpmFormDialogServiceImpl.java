 package org.sz.platform.bpm.service.form.impl;
 
  import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.dao.JdbcHelper;
import org.sz.core.mybatis.Dialect;
import org.sz.core.mybatis.dialect.DB2Dialect;
import org.sz.core.mybatis.dialect.MySQLDialect;
import org.sz.core.mybatis.dialect.OracleDialect;
import org.sz.core.mybatis.dialect.SQLServer2005Dialect;
import org.sz.core.query.PageBean;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.dao.form.BpmFormDialogDao;
import org.sz.platform.bpm.model.form.BpmFormDialog;
import org.sz.platform.bpm.model.form.DialogField;
import org.sz.platform.bpm.service.form.BpmFormDialogService;
import org.sz.platform.system.dao.SysDataSourceDao;
import org.sz.platform.system.model.SysDataSource;
 
 @Service("bpmFormDialogService")
 public class BpmFormDialogServiceImpl extends BaseServiceImpl<BpmFormDialog> implements BpmFormDialogService
 {
 
   @Resource
   private BpmFormDialogDao dao;
 
   @Resource
   private SysDataSourceDao sysDataSourceDao;
 
   protected IEntityDao<BpmFormDialog, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public boolean isExistAlias(String alias)
   {
     return this.dao.isExistAlias(alias).intValue() > 0;
   }
 
   public boolean isExistAliasForUpd(Long id, String alias)
   {
     return this.dao.isExistAliasForUpd(id, alias).intValue() > 0;
   }
 
   public BpmFormDialog getByAlias(String alias)
   {
     return this.dao.getByAlias(alias);
   }
 
   public List getTreeData(String alias)
     throws Exception
   {
     BpmFormDialog bpmFormDialog = this.dao.getByAlias(alias);
     JdbcHelper jdbcHelper = getJdbcHelper(bpmFormDialog.getDsalias());
     String sql = getTreeSql(bpmFormDialog.getDisplayfield(), bpmFormDialog.getObjname());
     List list = jdbcHelper.queryForList(sql, null);
 
     return list;
   }
 
   public BpmFormDialog getData(String alias, Map<String, Object> params)
     throws Exception
   {
     BpmFormDialog bpmFormDialog = this.dao.getByAlias(alias);
     JdbcHelper jdbcHelper = getJdbcHelper(bpmFormDialog.getDsalias());
 
     List displayList = bpmFormDialog.getDisplayList();
     List conditionList = bpmFormDialog.getConditionList();
     String objectName = bpmFormDialog.getObjname();
 
     if (bpmFormDialog.getStyle().intValue() == 0)
     {
       if (bpmFormDialog.getNeedpage().intValue() == 1) {
         int currentPage = 1;
         Object pageObj = params.get("p");
         if (pageObj != null) {
           currentPage = Integer.parseInt(params.get("p").toString());
         }
         int pageSize = bpmFormDialog.getPagesize().intValue();
         Object pageSizeObj = params.get("z");
         if (pageSizeObj != null) {
           pageSize = Integer.parseInt(params.get("z").toString());
         }
         String sql = getSql(objectName, displayList, conditionList, params);
 
         PageBean pageBean = new PageBean(currentPage, pageSize);
 
         List list = jdbcHelper.getPage(currentPage, pageSize, sql, params, pageBean);
 
         bpmFormDialog.setList(list);
         bpmFormDialog.setPageBean(pageBean);
       }
       else {
         String sql = getSql(objectName, displayList, conditionList, params);
         List list = jdbcHelper.queryForList(sql, params);
         bpmFormDialog.setList(list);
       }
 
     }
 
     return bpmFormDialog;
   }
 
   private Dialect getDialect(String dbType)
     throws Exception
   {
     Dialect dialect = null;
     if (dbType.equals("oracle")) {
       dialect = new OracleDialect();
     }
     else
     {
       if (dbType.equals("sqlserver")) {
         dialect = new SQLServer2005Dialect();
       }
       else
       {
         if (dbType.equals("db2")) {
           dialect = new DB2Dialect();
         }
         else
         {
           if (dbType.equals("mysql")) {
             dialect = new MySQLDialect();
           }
           else
             throw new Exception("没有设置合适的数据库类型");
         }
       }
     }
     return dialect;
   }
 
   private String getTreeSql(String displayField, String objName)
   {
     JSONObject jsonObj = JSONObject.fromObject(displayField);
     String id = jsonObj.getString("id");
     String pid = jsonObj.getString("pid");
     String displayName = jsonObj.getString("displayName");
     String sql = "select " + id + "," + pid + "," + displayName + " from " + objName;
     return sql;
   }
 
   private String getSql(String objectName, List<DialogField> displayList, List<DialogField> conditionList, Map params)
   {
     String displayField = "";
     for (int i = 0; i < displayList.size(); i++) {
       DialogField field = (DialogField)displayList.get(i);
       if (i < displayList.size() - 1) {
         displayField = displayField + field.getFieldName() + ",";
       }
       else {
         displayField = displayField + field.getFieldName();
       }
     }
     String sql = "select * from " + objectName;
     Map conditionMap = convertToMap(conditionList);
     String where = getWhere(conditionMap, params);
     sql = sql + where;
     return sql;
   }
 
   private Map<String, DialogField> convertToMap(List<DialogField> list)
   {
     Map map = new HashMap();
     for (DialogField dialogField : list) {
       map.put(dialogField.getFieldName(), dialogField);
     }
     return map;
   }
 
   private String getWhere(Map<String, DialogField> conditionMap, Map<String, Object> params)
   {
     String strWhere = "";
     Set set = params.entrySet();
     for (Iterator it = set.iterator(); it.hasNext(); ) {
       Map.Entry entry = (Map.Entry)it.next();
       String field = (String)entry.getKey();
       if ((field.equals("p")) || (field.equals("z")))
         continue;
       if (conditionMap.containsKey(field)) {
         DialogField dialogField = (DialogField)conditionMap.get(field);
         if (dialogField.getFieldType().equals("varchar")) {
           String condition = dialogField.getCondition();
           String value = (String)params.get(field);
           if (condition.equals("=")) {
             strWhere = strWhere + " " + field + "='" + value + "' and";
           }
           else if (condition.equals("like")) {
             strWhere = strWhere + " " + field + " like '%" + value + "%' and";
           }
           else
             strWhere = strWhere + " " + field + " like '" + value + "%' and";
         }
         else
         {
           strWhere = strWhere + " " + field + dialogField.getCondition() + ":" + field + " and";
         }
       }
     }
 
     if (strWhere.length() > 0) {
       strWhere = strWhere.substring(0, strWhere.length() - 3);
       strWhere = " where " + strWhere;
     }
 
     return strWhere;
   }
 
   private JdbcHelper getJdbcHelper(String dsName)
     throws Exception
   {
     SysDataSource sysDataSource = this.sysDataSourceDao.getByAlias(dsName);
     JdbcHelper jdbcHelper = JdbcHelper.getInstance();
     jdbcHelper.init(dsName, sysDataSource.getDriverName(), sysDataSource.getUrl(), sysDataSource.getUserName(), sysDataSource.getPassword());
     jdbcHelper.setCurrentDb(dsName);
     Dialect dialect = getDialect(sysDataSource.getDbType());
     jdbcHelper.setDialect(dialect);
     return jdbcHelper;
   }
 }

