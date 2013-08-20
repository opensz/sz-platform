 package org.sz.platform.service.system;
 
 import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONArray;

import org.sz.core.util.BeanUtils;
import org.sz.platform.model.system.SysParam;
 
 public abstract class ParamSearch<T>
 {
   public abstract List<T> getFromDataBase(Map<String, String> paramMap);
 
   public List<T> getByParam(String json)
     throws Exception
   {
     if ((json == null) || (json.equals(""))) return null;
     JSONArray ja = JSONArray.fromObject(json);
     List ml = (List)JSONArray.toCollection(ja, Map.class);
     if (BeanUtils.isEmpty(ml)) return null;
 
     LinkedList<ParamResult> resultList = new LinkedList();
 
     for (int i = 0; i < ml.size(); i++) {
       Map m = (Map)ml.get(i);
       int type = ((Integer)m.get("type")).intValue();
       String typeName = (String)m.get("typeName");
 
       ParamResult res = new ParamResult(type, typeName);
 
       switch (type) {
       case 1:
         ArrayList<MorphDynaBean> children1 = (ArrayList)m.get("children");
         if (!BeanUtils.isNotEmpty(children1)) break;
         for (MorphDynaBean c1 : children1) {
           String expression = (String)c1.get("expression");
           String dataType = (String)c1.get("dataType");
 
           Map property = handlerParam(expression, dataType);
 
           List ul = getFromDataBase(property);
 
           res.add(expression, ul);
         }break;
       case 2:
         ArrayList<MorphDynaBean> children2 = (ArrayList)m.get("children");
         if (!BeanUtils.isNotEmpty(children2))
           break;
         for (MorphDynaBean c2 : children2) {
           String expression = (String)c2.get("expression");
           String dataType = (String)c2.get("dataType");
 
           Map property = handlerParam(expression, dataType);
 
           List ul = getFromDataBase(property);
 
           res.add(expression, ul);
         }break;
       case 3:
         String expression = (String)m.get("expression");
         String dataType = (String)m.get("dataType");
 
         Map property = handlerParam(expression, dataType);
 
         List ul = getFromDataBase(property);
 
         res.add(expression, ul);
       }
 
       resultList.addLast(res);
     }
 
     if (BeanUtils.isEmpty(resultList)) return null;
     if (resultList.size() % 2 == 0) throw new IllegalArgumentException("表达式逻辑错误");
 
     if (resultList.size() >= 3) {
       while (resultList.size() > 1) {
         ParamResult cur = (ParamResult)resultList.removeFirst();
         ParamResult mid = (ParamResult)resultList.removeFirst();
         ParamResult nex = (ParamResult)resultList.removeFirst();
         if ((cur != null) && (mid != null) && (nex != null) && (mid.getType() != 3) && (BeanUtils.isEmpty(mid.getUserList()))) {
           ParamResult count = new ParamResult(mid.getType(), mid.getTypeName());
 
           count.add("cur", cur.getUserList());
           count.add("nex", nex.getUserList());
 
           resultList.addFirst(count);
         } else {
           throw new IllegalArgumentException("表达式逻辑错误");
         }
       }
 
     }
 
     List returnList = new ArrayList();
     if ((resultList != null) && (resultList.size() > 0)) {
       for (ParamResult res : resultList) {
         if (res.getUserList() != null)
           returnList.addAll(res.getUserList());
       }
     }
     return returnList;
   }
 
   protected Map<String, String> handlerParam(String expression, String dataType)
     throws Exception
   {
     if (expression == null) return null;
     int m = -1;
 
     String condition = null;
     for (Map.Entry ent : SysParam.CONDITION_US.entrySet()) {
       condition = (String)ent.getKey();
       m = expression.indexOf(condition);
       if (m < 0) {
         condition = (String)ent.getValue();
         m = expression.indexOf(condition);
       }
       if (m > -1) break;
     }
     if (m < 0) return null;
 
     String[] tem = expression.split(condition);
     if (tem.length == 2) {
       String paramKey = tem[0].trim();
       String paramValue = tem[1].trim();
       String paramValueColumn = null;
 
       paramValueColumn = (String)SysParam.DATA_COLUMN_MAP.get(dataType);
       if (paramValueColumn == null) paramValueColumn = "paramValue";
       Map param = new HashMap();
       param.put("paramKey", paramKey);
       param.put("condition", condition);
       param.put("paramValueColumn", paramValueColumn);
       if ((condition == "like") || (condition == "LIKE"))
         param.put("paramValue", "%" + paramValue + "%");
       else
         param.put("paramValue", paramValue);
       System.out.print("[@_@]" + param.toString());
       return param;
     }
     throw new Exception("sql参数不是xxx" + condition + "x形式:" + expression);
   }
 }

