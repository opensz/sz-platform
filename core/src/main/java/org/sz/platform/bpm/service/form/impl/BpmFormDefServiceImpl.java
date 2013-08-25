 package org.sz.platform.bpm.service.form.impl;
 
 import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.sz.core.bpm.util.BpmConst;
import org.sz.core.dao.IEntityDao;
import org.sz.core.query.QueryFilter;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.dao.form.BpmFormDefDao;
import org.sz.platform.bpm.model.flow.BpmFormRun;
import org.sz.platform.bpm.model.flow.ProcessRun;
import org.sz.platform.bpm.model.form.BpmFormDef;
import org.sz.platform.bpm.service.flow.BpmFormRunService;
import org.sz.platform.bpm.service.form.BpmFormDefService;
import org.sz.platform.bpm.service.form.BpmFormHandlerService;
import org.sz.platform.bpm.service.form.BpmFormRightsService;
 
 @Service("bpmFormDefService")
 public class BpmFormDefServiceImpl extends BaseServiceImpl<BpmFormDef> implements BpmFormDefService
 {
 
   @Resource
   private BpmFormDefDao dao;
 
   @Resource
   private BpmFormRightsService bpmFormRightsService;
 
   @Resource
   private BpmFormHandlerService bpmFormHandlerService;
 
   @Resource
   private BpmFormRunService bpmFormRunService;
 
   protected IEntityDao<BpmFormDef, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public Integer getCountByFormKey(Long formKey)
   {
     return this.dao.getCountByFormKey(formKey);
   }
 
   public BpmFormDef getDefaultVersionByFormKey(Long formKey)
   {
     return this.dao.getDefaultVersionByFormKey(formKey);
   }
 
   public List<BpmFormDef> getByFormKey(Long formKey)
   {
     return this.dao.getByFormKey(formKey);
   }
 
   public void addForm(BpmFormDef bpmFormDef, JSONObject jsonObject)
     throws Exception
   {
     long id = UniqueIdUtil.genId();
     bpmFormDef.setFormDefId(Long.valueOf(id));
     bpmFormDef.setFormKey(Long.valueOf(id));
     bpmFormDef.setVersionNo(Integer.valueOf(1));
     bpmFormDef.setIsDefault((short)1);
     bpmFormDef.setIsPublished((short)0);
     this.dao.add(bpmFormDef);
 
     this.bpmFormRightsService.save(id, jsonObject);
   }
 
   public void updateForm(BpmFormDef bpmFormDef, JSONObject jsonObject)
     throws Exception
   {
     Long formKey = bpmFormDef.getFormKey();
 
     this.dao.update(bpmFormDef);
 
     this.bpmFormRightsService.save(formKey.longValue(), jsonObject);
   }
 
   public void publish(Long formDefId, String operator)
     throws Exception
   {
     BpmFormDef formDef = (BpmFormDef)this.dao.getById(formDefId);
     formDef.setIsPublished((short)1);
     formDef.setPublishedBy(operator);
     formDef.setPublishTime(new Date());
     this.dao.update(formDef);
   }
 
   public void setDefaultVersion(Long formDefId, Long formKey)
   {
     this.dao.setDefaultVersion(formKey, formDefId);
   }
 
   public void newVersion(Long formDefId)
     throws Exception
   {
     BpmFormDef formDef = (BpmFormDef)this.dao.getById(formDefId);
     Long newFormDefId = Long.valueOf(UniqueIdUtil.genId());
 
     BpmFormDef newVersion = (BpmFormDef)formDef.clone();
     newVersion.setFormDefId(newFormDefId);
     newVersion.setIsDefault((short)0);
     newVersion.setIsPublished((short)0);
     newVersion.setPublishedBy("");
 
     newVersion.setVersionNo(Integer.valueOf(newVersion.getVersionNo().intValue() + 1));
     this.dao.add(newVersion);
   }
 
   private Map getFormByBpmFormRun(BpmFormRun bpmFormRun, ProcessRun processRun, String nodeId, Long userId, String ctxPath)
     throws Exception
   {
     if (bpmFormRun == null) {
       Map map = new HashMap();
       map.put("form", "");
       map.put("isExtForm", Boolean.valueOf(false));
       map.put("isEmptyForm", Boolean.valueOf(true));
       return map;
     }
 
     String instanceId = processRun.getActInstId();
     String bussinessKey = processRun.getBusinessKey();
 
     String form = "";
     boolean isExtForm = false;
     Map map = new HashMap();
     if (BpmConst.OnLineForm.equals(bpmFormRun.getFormType())) {
       Long formDefId = bpmFormRun.getFormdefId();
       BpmFormDef bpmFormDef = (BpmFormDef)this.dao.getById(formDefId);
       if (bpmFormDef != null) {
         form = this.bpmFormHandlerService.obtainHtml(bpmFormDef, processRun, userId, nodeId, map);
       }
 
     }
     else if (BpmConst.UrlForm.equals(bpmFormRun.getFormType())) {
       String formUrl = bpmFormRun.getFormUrl();
 
       if ((StringUtil.isNotEmpty(formUrl)) && (StringUtil.isNotEmpty(bussinessKey)))
       {
         form = formUrl.replaceFirst("\\{pk\\}", bussinessKey);
         if (!formUrl.startsWith("http")) {
           form = ctxPath + form;
         }
 
       }
 
       isExtForm = true;
     }
     
     map.put("form", form);
     map.put("isExtForm", Boolean.valueOf(isExtForm));
     boolean isEmptyForm = false;
     if (StringUtil.isEmpty(form)) {
       isEmptyForm = true;
     }
     map.put("isEmptyForm", Boolean.valueOf(isEmptyForm));
     return map;
   }
 
   public Map loadForm(ProcessRun processRun, String nodeId, Long userId, String ctxPath)
     throws Exception
   {
     String instanceId = processRun.getActInstId();
 
     BpmFormRun bpmFormRun = this.bpmFormRunService.getByInstanceAndNode(instanceId, nodeId);
     Map map = getFormByBpmFormRun(bpmFormRun, processRun, nodeId, userId, ctxPath);
     return map;
   }
 
   public List<BpmFormDef> getPublished(QueryFilter queryFilter)
   {
     return this.dao.getPublished(queryFilter);
   }
 
   public int getFlowUsed(Long formKey)
   {
     int rtn = this.dao.getFlowUsed(formKey);
     return rtn;
   }
 
   public void delByFormKey(Long formKey)
   {
     this.dao.delByFormKey(formKey);
   }
 }

