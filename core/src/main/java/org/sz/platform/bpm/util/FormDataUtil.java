 package org.sz.platform.bpm.util;
 
 import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sz.core.keygenerator.impl.TimeGenerator;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.DateUtil;
import org.sz.platform.bpm.dao.form.BpmFormFieldDao;
import org.sz.platform.bpm.dao.form.BpmFormTableDao;
import org.sz.platform.bpm.model.form.BpmFormData;
import org.sz.platform.bpm.model.form.BpmFormField;
import org.sz.platform.bpm.model.form.BpmFormTable;
import org.sz.platform.bpm.model.form.PkValue;
import org.sz.platform.bpm.model.form.SqlModel;
import org.sz.platform.bpm.model.form.SubTable;
import org.sz.platform.bpm.model.form.TableRelation;
 
 public class FormDataUtil
 {
   private static Log logger = LogFactory.getLog(FormDataUtil.class);
 
   public static List<SqlModel> parseSql(BpmFormData formData)
   {
     PkValue pkValue = formData.getPkValue();
     boolean isAdd = pkValue.getIsAdd();
     List list = new ArrayList();
     SqlModel sqlModel;
     if (isAdd)
     {
       sqlModel = getInsert(formData.getTableName(), formData.getMainFields());
       list.add(sqlModel);
     }
     else {
       sqlModel = getUpdate(formData);
       if(StringUtils.isNotBlank(sqlModel.getSql())){
	       list.add(sqlModel);
	 
	       for (SubTable subTable : formData.getSubTableList()) {
	         sqlModel = getDel(subTable, pkValue);
	         list.add(sqlModel);
	       }
       }
     }
 
     for (SubTable subTable : formData.getSubTableList()) {
       String tableName = subTable.getTableName();
       List<Map<String, Object>> dataList = subTable.getDataList();
       for (Map<String, Object> row : dataList) {
         sqlModel = getInsert(tableName, row);
         list.add(sqlModel);
       }
     }
     return list;
   }
 
   public static BpmFormData parseJson(String json, PkValue pkValue)
     throws Exception
   {
     BpmFormTableDao bpmFormTableDao = (BpmFormTableDao)ContextUtil.getBean("bpmFormTableDao");
     BpmFormFieldDao bmpFormFieldDao = (BpmFormFieldDao)ContextUtil.getBean("bpmFormFieldDao");
 
     JSONObject jsonObj = JSONObject.fromObject(json);
 
     JSONObject mainTable = jsonObj.getJSONObject("main");
 
     BpmFormData bpmFormData = new BpmFormData();
 
     long tableId = mainTable.getLong("tableId");
 
     BpmFormTable mainTableDef = (BpmFormTable)bpmFormTableDao.getById(Long.valueOf(tableId));
 
     String tableName = mainTableDef.getTableName();
 
     List listTable = bpmFormTableDao.getSubTableByMainTableId(Long.valueOf(tableId));
 
     if (pkValue == null) {
       pkValue = getPk(mainTableDef);
     }
     bpmFormData.setPkValue(pkValue);
 
     handleMain(jsonObj, bpmFormData, mainTableDef, bmpFormFieldDao);
 
     handSub(jsonObj, listTable, bmpFormFieldDao, mainTableDef, bpmFormData);
 
     handOpinion(bpmFormData, jsonObj);
 
     return bpmFormData;
   }
 
   public static BpmFormData parseJson(String json)
     throws Exception
   {
     return parseJson(json, null);
   }
 
   private static void handleMain(JSONObject jsonObj, BpmFormData bpmFormData, BpmFormTable mainTableDef, BpmFormFieldDao bmpFormFieldDao)
     throws Exception
   {
     JSONObject mainTable = jsonObj.getJSONObject("main");
 
     long tableId = mainTableDef.getTableId().longValue();
 
     List mainFields = bmpFormFieldDao.getByTableId(Long.valueOf(tableId));
 
     Map mainFieldTypeMap = convertToMap(mainFields);
 
     int isExternal = mainTableDef.getIsExternal();
 
     String tablePrefix = isExternal == 1 ? "" : "W_";
 
     String colPrefix = isExternal == 1 ? "" : "F_";
 
     String mainTableName = mainTableDef.getTableName();
 
     JSONObject mainFieldJson = mainTable.getJSONObject("fields");
 
     bpmFormData.setTableId(tableId);
     bpmFormData.setTableName(tablePrefix + mainTableName);
 
     Map mainFiledsData = handleRow(colPrefix, mainFieldTypeMap, mainFieldJson);
 
     bpmFormData.addMainFields(mainFiledsData);
 
     PkValue pkValue = bpmFormData.getPkValue();
 
     bpmFormData.addMainFields(pkValue.getName(), pkValue.getValue());
 
     if (pkValue.getIsAdd())
     {
       List mapFormField = getFieldsFromScript(mainFields);
 
       Map map = caculateField(colPrefix, mapFormField, bpmFormData.getMainFields());
 
       bpmFormData.addMainFields(map);
     }
 
     Map variables = getVariables(mainFieldJson, mainFields);
     bpmFormData.setVariables(variables);
   }
 
   private static Map<String, Object> getVariables(JSONObject jsonObject, List<BpmFormField> list)
   {
     Map map = new HashMap();
     Map fieldsMap = convertFieldToMap(list);
     for (Iterator it = jsonObject.keys(); it.hasNext(); ) {
       String key = (String)it.next();
       BpmFormField field = (BpmFormField)fieldsMap.get(key);
       if ((field != null) && (field.getIsFlowVar().shortValue() == 1)) {
         String value = (String)jsonObject.get(key);
         map.put(key, value);
       }
     }
     return map;
   }
 
   private static void handSub(JSONObject jsonObj, List<BpmFormTable> listTable, BpmFormFieldDao bmpFormFieldDao, BpmFormTable mainTableDef, BpmFormData bpmFormData)
     throws Exception
   {
     Map subTableMap = convertTable(listTable);
     Map formTableMap = convertTableMap(listTable);
 
     int isExternal = mainTableDef.getIsExternal();
 
     String tablePrefix = isExternal == 1 ? "" : "W_";
 
     String colPrefix = isExternal == 1 ? "" : "F_";
 
     JSONArray arySub = jsonObj.getJSONArray("sub");
 
     for (int i = 0; i < arySub.size(); i++) {
       SubTable subTable = new SubTable();
       JSONObject subTableObj = arySub.getJSONObject(i);
       String subName = subTableObj.getString("tableName");
       Long subTableId = (Long)subTableMap.get(subName);
       if (subTableId == null)
         continue;
       BpmFormTable bpmFormTable = (BpmFormTable)formTableMap.get(subName);
 
       List subTableFields = bmpFormFieldDao.getByTableId(subTableId);
       Map subTableTypeMap = convertToMap(subTableFields);
 
       List scriptFields = getFieldsFromScript(subTableFields);
 
       subTable.setTableName(tablePrefix + subName);
 
       if (isExternal == 1) {
         TableRelation tableRelation = mainTableDef.getTableRelation();
         Map mapRelation = tableRelation.getRelations();
         String fk = (String)mapRelation.get(subName);
         String pk = bpmFormTable.getPkField();
         subTable.setPkName(pk);
         subTable.setFkName(fk);
       }
       else {
         subTable.setPkName("ID");
         subTable.setFkName("REFID");
       }
 
       JSONArray arySubFields = subTableObj.getJSONArray("fields");
       for (int j = 0; j < arySubFields.size(); j++) {
         JSONObject subFieldObj = arySubFields.getJSONObject(j);
         Map subRow = handleRow(colPrefix, subTableTypeMap, subFieldObj);
 
         Map map = caculateField(colPrefix, scriptFields, subRow);
 
         subRow.putAll(map);
 
         handFkRow(mainTableDef, bpmFormTable, subRow, bpmFormData.getPkValue());
 
         subTable.addRow(subRow);
       }
       bpmFormData.addSubTable(subTable);
     }
   }
 
   private static void handOpinion(BpmFormData bpmFormData, JSONObject jsonObj)
   {
     JSONArray aryOpinion = jsonObj.getJSONArray("opinion");
 
     for (int i = 0; i < aryOpinion.size(); i++) {
       JSONObject opinion = aryOpinion.getJSONObject(i);
       String formName = opinion.getString("name");
       String value = opinion.getString("value");
       bpmFormData.addOpinion(formName, value);
     }
   }
 
   private static void handFkRow(BpmFormTable mainTabDef, BpmFormTable bpmFormTable, Map<String, Object> rowData, PkValue pkValue)
     throws Exception
   {
     int isExternal = bpmFormTable.getIsExternal();
 
     if (isExternal == 1) {
       PkValue pk = getPk(bpmFormTable);
       if (pk.getValue().toString().equals("-1")) {
         logger.debug("外键值不能为-1,请设置主表的主键生成规则!");
         throw new Exception("外键值不能为-1,请设置主表的主键生成规则!");
       }
       rowData.put(pk.getName(), pk.getValue());
 
       TableRelation tableRelation = mainTabDef.getTableRelation();
       Map relation = tableRelation.getRelations();
       String fk = (String)relation.get(bpmFormTable.getTableName());
       rowData.put(fk, pkValue.getValue());
     }
     else
     {
       Object pk = new TimeGenerator().nextId();
       rowData.put("ID", pk);
       rowData.put("REFID", pkValue.getValue());
     }
   }
 
   private static List<BpmFormField> getFieldsFromScript(List<BpmFormField> list)
   {
     List map = new ArrayList();
     for (BpmFormField field : list)
     {
       if (field.getValueFrom().shortValue() == 2)
         map.add(field);
     }
     return map;
   }
 
   private static Map<String, Object> caculateField(String colPrefix, List<BpmFormField> fields, Map<String, Object> data)
   {
     Map result = new HashMap();
     for (BpmFormField field : fields)
     {
       String name = colPrefix + field.getFieldName();
 
       String script = field.getScript();
       Object value = FormUtil.calcuteField(script, data, colPrefix);
       result.put(name, value);
     }
     return result;
   }
 
   public static PkValue getPk(BpmFormTable bpmFormTable)
     throws Exception
   {
     Object pkValue = null;
     String pkField = "ID";
     if (bpmFormTable.getIsExternal() == 1) {
       pkField = bpmFormTable.getPkField();
     }
 
     if (bpmFormTable.getIsExternal() == 1) {
       Short keyType = bpmFormTable.getKeyType();
       String keyValue = bpmFormTable.getKeyValue();
       pkValue = FormUtil.getKey(keyType.shortValue(), keyValue);
     }
     else {
       pkValue = new TimeGenerator().nextId();
     }
 
     PkValue pk = new PkValue();
     pk.setIsAdd(true);
     pk.setName(pkField);
     pk.setValue(pkValue);
     return pk;
   }
 
   private static Map<String, Object> handleRow(String colPrefix, Map<String, String> fieldTypeMap, JSONObject fieldsJsonObj)
   {
     Map row = new HashMap();
 
     for (Iterator it = fieldsJsonObj.keys(); it.hasNext(); ) {
       String key = (String)it.next();
       Object obj = fieldsJsonObj.get(key);
       String value = "";
       if (((obj instanceof JSONArray)) || ((obj instanceof JSONObject))) {
         value = obj.toString();
       }
       else {
         value = (String)obj;
       }
       String fieldType = (String)fieldTypeMap.get(key);
 
       Object convertValue = convertType(value, fieldType);
       row.put(colPrefix + key, convertValue);
     }
     return row;
   }
 
   private static Object convertType(String strValue, String type)
   {
     Object value = null;
     if ("date".equals(type)) {
       value = DateUtil.parseDate(strValue);
     }
     else {
       value = strValue;
     }
     return value;
   }
 
   private static Map<String, String> convertToMap(List<BpmFormField> list)
   {
     Map map = new HashMap();
     for (Iterator it = list.iterator(); it.hasNext(); ) {
       BpmFormField field = (BpmFormField)it.next();
       map.put(field.getFieldName(), field.getFieldType());
     }
     return map;
   }
 
   private static Map<String, BpmFormField> convertFieldToMap(List<BpmFormField> list)
   {
     Map map = new HashMap();
     for (Iterator it = list.iterator(); it.hasNext(); ) {
       BpmFormField field = (BpmFormField)it.next();
       map.put(field.getFieldName(), field);
     }
     return map;
   }
 
   private static Map<String, Long> convertTable(List<BpmFormTable> list)
   {
     Map map = new HashMap();
     for (BpmFormTable tb : list) {
       map.put(tb.getTableName(), tb.getTableId());
     }
     return map;
   }
 
   private static Map<String, BpmFormTable> convertTableMap(List<BpmFormTable> list)
   {
     Map map = new HashMap();
     for (BpmFormTable tb : list) {
       map.put(tb.getTableName(), tb);
     }
     return map;
   }
 
   private static SqlModel getInsert(String tableName, Map<String, Object> mapData)
   {
     StringBuffer fieldNames = new StringBuffer();
     StringBuffer params = new StringBuffer();
     List values = new ArrayList();
 
     for (Map.Entry entry : mapData.entrySet())
     {
       fieldNames.append((String)entry.getKey()).append(",");
       params.append("?,");
       values.add(entry.getValue());
     }
 
     StringBuffer sql = new StringBuffer();
     if (params.length() > 0)
     {
       sql.append(" INSERT INTO ");
       sql.append(tableName);
       sql.append("(");
       sql.append(fieldNames.substring(0, fieldNames.length() - 1));
       sql.append(")");
       sql.append(" VALUES (");
       sql.append(params.substring(0, params.length() - 1));
       sql.append(")");
     }
 
     SqlModel sqlModel = new SqlModel(sql.toString(), values.toArray());
     return sqlModel;
   }
 
   private static SqlModel getDel(SubTable subTable, PkValue pkValue)
   {
     String sql = "delete from " + subTable.getTableName() + " where " + subTable.getFkName() + "=?";
     Object[] values = new Object[1];
     values[0] = pkValue.getValue();
     SqlModel sqlModel = new SqlModel(sql, values);
     return sqlModel;
   }
 
   private static SqlModel getUpdate(BpmFormData bpmFormData)
   {
     PkValue pk = bpmFormData.getPkValue();
     String tableName = bpmFormData.getTableName();
     Map<String, Object> mapData = bpmFormData.getMainCommonFields();
     StringBuffer set = new StringBuffer();
 
     List values = new ArrayList();
     for (Map.Entry entry : mapData.entrySet()) {
       set.append((String)entry.getKey()).append("=?,");
       values.add(entry.getValue());
     }
 
     StringBuffer sql = new StringBuffer();
     if (set.length() > 0)
     {
       sql.append(" update ");
       sql.append(tableName);
       sql.append(" set ");
       sql.append(set.substring(0, set.length() - 1));
       sql.append(" where ");
       sql.append(pk.getName());
       sql.append("=?");
       values.add(pk.getValue());
     }
     SqlModel sqlModel = new SqlModel(sql.toString(), values.toArray());
     return sqlModel;
   }
 }

