 package org.sz.platform.bpm.service.form.impl;
 
 import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.dao.flow.BpmDefinitionDao;
import org.sz.platform.bpm.dao.flow.BpmFormRunDao;
import org.sz.platform.bpm.dao.flow.BpmNodeSetDao;
import org.sz.platform.bpm.model.flow.BpmDefinition;
import org.sz.platform.bpm.model.flow.BpmFormRun;
import org.sz.platform.bpm.model.flow.BpmNodeSet;
import org.sz.platform.bpm.service.flow.BpmFormRunService;
import org.sz.platform.bpm.service.flow.ProcessRunService;
 
 @Service("bpmFormRunService")
 public class BpmFormRunServiceImpl extends BaseServiceImpl<BpmFormRun> implements BpmFormRunService
 {
 
   @Resource
   private BpmFormRunDao dao;
 
   @Resource
   private BpmNodeSetDao bpmNodeSetDao;
 
   @Resource
   private ProcessRunService processRunService;
 
   @Resource
   private BpmDefinitionDao bpmDefinitionDao;
 
   protected IEntityDao<BpmFormRun, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public void addFormRun(String actDefId, Long runId, String actInstanceId)
     throws Exception
   {
     List<BpmNodeSet> list = this.bpmNodeSetDao.getByActDefId(actDefId);
     for (BpmNodeSet bpmNodeSet : list) {
       BpmFormRun bpmFormRun = getByBpmNodeSet(runId, actInstanceId, bpmNodeSet);
       this.dao.add(bpmFormRun);
     }
   }
 
   private BpmNodeSet getDefalutStartForm(List<BpmNodeSet> list, Short setType)
   {
     BpmNodeSet bpmNodeSet = null;
     for (BpmNodeSet node : list) {
       if (node.getSetType().equals(setType)) {
         bpmNodeSet = node;
         break;
       }
     }
     return bpmNodeSet;
   }
 
   private BpmNodeSet getStartForm(List<BpmNodeSet> list)
   {
     BpmNodeSet bpmNodeSet = getDefalutStartForm(list, BpmNodeSet.SetType_StartForm);
     return bpmNodeSet;
   }
 
   private BpmNodeSet getGlobalForm(List<BpmNodeSet> list)
   {
     BpmNodeSet bpmNodeSet = getDefalutStartForm(list, BpmNodeSet.SetType_GloabalForm);
     return bpmNodeSet;
   }
 
   public Map<String, BpmNodeSet> getTaskForm(List<BpmNodeSet> list)
   {
     Map map = new HashMap();
     for (BpmNodeSet node : list) {
       if (node.getSetType().equals(BpmNodeSet.SetType_TaskNode)) {
         map.put(node.getNodeId(), node);
       }
     }
     return map;
   }
 
   private BpmFormRun getByBpmNodeSet(Long runId, String actInstanceId, BpmNodeSet bpmNodeSet)
     throws Exception
   {
     BpmFormRun bpmFormRun = new BpmFormRun();
     bpmFormRun.setId(Long.valueOf(UniqueIdUtil.genId()));
     bpmFormRun.setRunId(runId);
     bpmFormRun.setActInstanceId(actInstanceId);
     bpmFormRun.setActDefId(bpmNodeSet.getActDefId());
     bpmFormRun.setActNodeId(bpmNodeSet.getNodeId());
     bpmFormRun.setFormdefId(bpmNodeSet.getFormDefId());
     bpmFormRun.setFormdefKey(bpmNodeSet.getFormKey());
     bpmFormRun.setFormType(bpmNodeSet.getFormType());
     bpmFormRun.setFormUrl(bpmNodeSet.getFormUrl());
     bpmFormRun.setSetType(bpmNodeSet.getSetType());
     return bpmFormRun;
   }
 
   public BpmNodeSet getStartBpmNodeSet(String actDefId, Short toFirstNode)
   {
     String firstTaskName = this.processRunService.getFirstNodetByDefId(actDefId);
     List list = this.bpmNodeSetDao.getByActDefId(actDefId);
     BpmNodeSet bpmNodeSetStart = getStartForm(list);
     BpmNodeSet bpmNodeSetGlobal = getGlobalForm(list);
     Map taskMap = getTaskForm(list);
     BpmNodeSet firstBpmNodeSet = (BpmNodeSet)taskMap.get(firstTaskName);
     if (bpmNodeSetStart == null)
     {
       if (toFirstNode.shortValue() == 1)
       {
         if ((firstBpmNodeSet != null) && (firstBpmNodeSet.getFormType() != null) && (firstBpmNodeSet.getFormType().shortValue() != -1)) {
           return firstBpmNodeSet;
         }
 
         if ((bpmNodeSetGlobal != null) && 
           (bpmNodeSetGlobal.getFormType() != null) && (bpmNodeSetGlobal.getFormType().shortValue() != -1)) {
           return bpmNodeSetGlobal;
         }
 
       }
       else if ((bpmNodeSetGlobal != null) && 
         (bpmNodeSetGlobal.getFormType() != null) && (bpmNodeSetGlobal.getFormType().shortValue() != -1)) {
         return bpmNodeSetGlobal;
       }
     }
     else
     {
       return bpmNodeSetStart;
     }
     return null;
   }
 
   public boolean getCanDirectStart(Long defId)
   {
     BpmDefinition bpmDefinition = (BpmDefinition)this.bpmDefinitionDao.getById(defId);
 
     String actDefId = bpmDefinition.getActDefId();
     Short toFirstNode = bpmDefinition.getToFirstNode();
     Short needStartForm = bpmDefinition.getNeedStartForm();
 
//     List list = this.bpmNodeSetDao.getByActDefId(actDefId);
// 
//     BpmNodeSet bpmNodeSetStart = getStartForm(list);
//     BpmNodeSet bpmNodeSetGlobal = getGlobalForm(list);
// 
//     boolean hasStart = hasForm(bpmNodeSetStart);
//     boolean hasGlobal = hasForm(bpmNodeSetGlobal);
// 
     boolean canDirectStart = false;
 
     if (needStartForm.shortValue() == 0)
     {
//       if (toFirstNode.shortValue() == 0)
//       {
//         if ((!hasStart) && (!hasGlobal))
//           return true;
//       }
    	 return true;
     }
     return canDirectStart;
   }
 
   private boolean hasForm(BpmNodeSet nodeSet)
   {
     return (nodeSet != null) && (nodeSet.getFormType().shortValue() != -1);
   }
 
   public BpmFormRun getByInstanceAndNode(String actInstanceId, String actNodeId)
   {
     BpmFormRun bpmFormRun = this.dao.getByInstanceAndNode(actInstanceId, actNodeId);
     if ((bpmFormRun != null) && (bpmFormRun.getFormType() != null) && (bpmFormRun.getFormType().shortValue() != -1)) {
       return bpmFormRun;
     }
 
     bpmFormRun = this.dao.getGlobalForm(actInstanceId);
     if ((bpmFormRun != null) && (bpmFormRun.getFormType() != null) && (bpmFormRun.getFormType().shortValue() != -1)) {
       return bpmFormRun;
     }
     return null;
   }
 }

