 package org.sz.platform.bpm.service.form.impl;
 
  import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.platform.bpm.dao.form.BpmFormHandlerDao;
import org.sz.platform.bpm.model.form.BpmFormData;
import org.sz.platform.bpm.service.form.BpmFormTableDataService;
 
 @Service("bpmFormTableDataService")
 public class BpmFormTableDataServiceImpl implements BpmFormTableDataService
 {
 
   @Resource
   private BpmFormHandlerDao dao;
 
   public List<Map<String, Object>> getAll(Long tableId, Map<String, Object> param)
     throws Exception
   {
     return this.dao.getAll(tableId, param);
   }
 
   public BpmFormData getByKey(Long tableId, String pkValue)
     throws Exception
   {
     return this.dao.getByKey(tableId.longValue(), pkValue);
   }
 }

