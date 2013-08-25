 package org.sz.platform.bpm.service.flow.impl;
 
 import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.engine.GroovyScriptEngine;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.StringUtil;
import org.sz.platform.bpm.dao.flow.BpmNodeSetDao;
import org.sz.platform.bpm.dao.flow.BpmNodeUserDao;
import org.sz.platform.bpm.dao.flow.ProcessRunDao;
import org.sz.platform.bpm.model.flow.BpmNodeSet;
import org.sz.platform.bpm.model.flow.BpmNodeUser;
import org.sz.platform.bpm.model.flow.ProcessRun;
import org.sz.platform.bpm.model.flow.TaskOpinion;
import org.sz.platform.bpm.service.flow.BpmNodeUserService;
import org.sz.platform.bpm.service.flow.TaskOpinionService;
import org.sz.platform.system.dao.SysUserOrgDao;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.model.SysUserOrg;
import org.sz.platform.system.service.SysUserOrgService;
import org.sz.platform.system.service.SysUserService;
import org.sz.platform.system.service.UserPositionService;
import org.sz.platform.system.service.UserRoleService;
import org.sz.platform.system.service.UserUnderService;
 
 @Service("bpmNodeUserService")
 public class BpmNodeUserServiceImpl extends BaseServiceImpl<BpmNodeUser> implements BpmNodeUserService
 {
 
   @Resource
   private BpmNodeUserDao dao;
 
   @Resource
   private ProcessRunDao processRunDao;
 
   @Resource
   private BpmNodeSetDao bpmNodeSetDao;
 
   @Resource
   private SysUserOrgService sysUserOrgService;
 
   @Resource
   private UserPositionService userPositionService;
 
   @Resource
   private UserRoleService userRoleService;
 
   @Resource
   private SysUserService sysUserService;
 
   @Resource
   private TaskOpinionService taskOpinionService;
 
   @Resource
   private HistoryService historyService;
 
   @Resource
   private UserUnderService userUnderService;
 
   @Resource
   private SysUserOrgDao sysUserOrgDao;
 
   @Resource
   private GroovyScriptEngine groovyScriptEngine;
 
   protected IEntityDao<BpmNodeUser, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public List<BpmNodeUser> getBySetId(Long setId)
   {
     return this.dao.getBySetId(setId);
   }
 
   public List<String> getExeUserIdsByInstance(String actInstId, String nodeId, String preTaskUser)
   {
     HistoricProcessInstance processInstance = (HistoricProcessInstance)this.historyService.createHistoricProcessInstanceQuery().processInstanceId(actInstId).singleResult();
     String startUserId = null;
     ProcessRun processRun = this.processRunDao.getByActInstanceId(processInstance.getId());
     if (processRun != null) {
       startUserId = processRun.getCreatorId().toString();
     }
     return getExeUserIds(processInstance.getProcessDefinitionId(), actInstId, nodeId, startUserId, preTaskUser);
   }
 
   public List<String> getExeUserIds(String actDefId, String nodeId, String startUserId)
   {
     return getExeUserIds(actDefId, null, nodeId, startUserId, "");
   }
 
   public List<String> getExeUserIds(String actDefId, String actInstId, String nodeId, String startUserId, String preTaskUser)
   {
     List userIds = new ArrayList();
     BpmNodeSet bpmNodeSet = this.bpmNodeSetDao.getByActDefIdNodeId(actDefId, nodeId);
 
     if (bpmNodeSet == null) return userIds;
 
     Set userIdSet = new HashSet();
 
     List<BpmNodeUser> nodeUsers = getBySetId(bpmNodeSet.getSetId());
 
     for (BpmNodeUser bpmNodeUser : nodeUsers) {
       Set uIdSet = new HashSet();
       short assignType = bpmNodeUser.getAssignType().shortValue();
       switch (assignType)
       {
       case 0:
         uIdSet = new HashSet();
         uIdSet.add(startUserId);
         break;
       case 11:
         uIdSet = getDirectLeaderByUserId(startUserId);
         break;
       case 9:
         uIdSet = getSameDepByStartUserId(startUserId);
         break;
       case 1:
         uIdSet = getByUids(bpmNodeUser);
         break;
       case 2:
         uIdSet = getByRole(bpmNodeUser);
         break;
       case 3:
         uIdSet = getByOrg(bpmNodeUser);
         break;
       case 4:
         uIdSet = getByOrgCharge(bpmNodeUser);
         break;
       case 5:
         uIdSet = getByPosition(bpmNodeUser);
         break;
       case 6:
         uIdSet = getByUpLow(bpmNodeUser, startUserId);
         break;
       case 10:
         uIdSet = getSameWithNode(actInstId, nodeId);
         break;
       case 7:
         uIdSet = getByUserAttr(bpmNodeUser);
         break;
       case 8:
         uIdSet = getByOrgAttr(bpmNodeUser);
         break;
       case 12:
         uIdSet = getByScript(bpmNodeUser.getCmpNames(), startUserId, preTaskUser);
         break;
       case 13:
         uIdSet = getDirectLeaderByUserId(preTaskUser);
         break;
       case 14:
         uIdSet = getMyLeader(startUserId);
         break;
       case 15:
         uIdSet = getMyLeader(preTaskUser);
       }
 
       if (userIdSet.size() == 0)
         userIdSet = uIdSet;
       else {
         userIdSet = computeUserSet(bpmNodeUser.getCompType().shortValue(), userIdSet, uIdSet);
       }
     }
 
     userIds.addAll(userIdSet);
     return userIds;
   }
 
   private Set<String> getDirectLeaderByUserId(String startUserId)
   {
     Set userSet = new HashSet();
     List<String> userList = this.sysUserOrgService.getLeaderByUserId(Long.valueOf(Long.parseLong(startUserId)));
     for (String user : userList) {
       userSet.add(user);
     }
     return userSet;
   }
 
   private Set<String> getSameDepByStartUserId(String startUserId)
   {
     List<SysUserOrg> orgIds = this.sysUserOrgDao.getOrgByUserId(new Long(startUserId));
     Set userIdSet = new HashSet();
     for (SysUserOrg sysUserOrg : orgIds) {
       List<SysUserOrg> userIdList = this.sysUserOrgDao.getByOrgId(sysUserOrg.getOrgId());
       for (SysUserOrg userOrg : userIdList) {
         String userId = userOrg.getUserId().toString();
         if (!userId.equals(startUserId)) {
           userIdSet.add(userId);
         }
       }
     }
     return userIdSet;
   }
 
   private Set<String> getByUids(BpmNodeUser bpmNodeUser)
   {
     Set userIdSet = new HashSet();
     String uids = bpmNodeUser.getCmpIds();
     if (StringUtil.isEmpty(uids)) {
       return userIdSet;
     }
     String[] uIds = bpmNodeUser.getCmpIds().split("[,]");
     userIdSet.addAll(Arrays.asList(uIds));
     return userIdSet;
   }
 
   private Set<String> getByRole(BpmNodeUser bpmNodeUser)
   {
     Set userIdSet = new HashSet();
     String uids = bpmNodeUser.getCmpIds();
     if (StringUtil.isEmpty(uids)) {
       return userIdSet;
     }
     String[] roleIds = bpmNodeUser.getCmpIds().split("[,]");
     for (int i = 0; i < roleIds.length; i++) {
       List<Long> userIdList = this.userRoleService.getUserIdsByRoleId(new Long(roleIds[i]));
       for (Long uId : userIdList) {
         userIdSet.add(uId.toString());
       }
     }
     return userIdSet;
   }
 
   private Set<String> getByOrg(BpmNodeUser bpmNodeUser)
   {
     Set userIdSet = new HashSet();
     String uids = bpmNodeUser.getCmpIds();
     if (StringUtil.isEmpty(uids)) {
       return userIdSet;
     }
     String[] orgIds = bpmNodeUser.getCmpIds().split("[,]");
     for (int i = 0; i < orgIds.length; i++) {
       List<SysUserOrg> userIdList = this.sysUserOrgDao.getByOrgId(new Long(orgIds[i]));
       for (SysUserOrg uId : userIdList) {
         userIdSet.add(uId.getUserId().toString());
       }
     }
     return userIdSet;
   }
 
   private Set<String> getByOrgCharge(BpmNodeUser bpmNodeUser)
   {
     Set userIdSet = new HashSet();
     String uids = bpmNodeUser.getCmpIds();
     if (StringUtil.isEmpty(uids)) {
       return userIdSet;
     }
     String[] orgIds = bpmNodeUser.getCmpIds().split("[,]");
     userIdSet = new HashSet();
     for (int i = 0; i < orgIds.length; i++) {
       List<SysUserOrg> userIdList = this.sysUserOrgDao.getChargeByOrgId(new Long(orgIds[i]));
       for (SysUserOrg uId : userIdList) {
         userIdSet.add(uId.getUserId().toString());
       }
     }
     return userIdSet;
   }
 
   private Set<String> getByPosition(BpmNodeUser bpmNodeUser)
   {
     Set userIdSet = new HashSet();
     String uids = bpmNodeUser.getCmpIds();
     if (StringUtil.isEmpty(uids)) {
       return userIdSet;
     }
     String[] posIds = bpmNodeUser.getCmpIds().split("[,]");
     for (int i = 0; i < posIds.length; i++) {
       List<Long> userIdList = this.userPositionService.getUserIdsByPosId(new Long(posIds[i]));
       for (Long uId : userIdList) {
         userIdSet.add(uId.toString());
       }
     }
     return userIdSet;
   }
 
   private Set<String> getByUpLow(BpmNodeUser bpmNodeUser, String startUserId)
   {
     Set userIdSet = new HashSet();
     List<SysUser> list = this.sysUserService.getByUserIdAndUplow(new Long(startUserId).longValue(), bpmNodeUser.getNodeUserId().longValue());
     for (SysUser user : list) {
       userIdSet.add(user.getUserId().toString());
     }
     return userIdSet;
   }
 
   private Set<String> getSameWithNode(String actInstId, String nodeId)
   {
     Set userIdSet = new HashSet();
     TaskOpinion taskOpinion = this.taskOpinionService.getLatestTaskOpinion(actInstId, nodeId);
     if (taskOpinion != null) {
       userIdSet.add(taskOpinion.getExeUserId().toString());
     }
     return userIdSet;
   }
 
   private Set<String> getByUserAttr(BpmNodeUser bpmNodeUser)
   {
     Set uIdSet = new HashSet();
     try
     {
       List<SysUser> sysUsers = this.sysUserService.getByUserParam(bpmNodeUser.getCmpIds());
       uIdSet = new HashSet();
       for (SysUser sysUser : sysUsers)
         uIdSet.add(sysUser.getUserId().toString());
     }
     catch (Exception e) {
       e.printStackTrace();
     }
     return uIdSet;
   }
 
   private Set<String> getByOrgAttr(BpmNodeUser bpmNodeUser)
   {
     Set uIdSet = new HashSet();
     try
     {
       List<SysUser> sysUsers = this.sysUserService.getByOrgParam(bpmNodeUser.getCmpIds());
       uIdSet = new HashSet();
       for (SysUser sysUser : sysUsers)
         uIdSet.add(sysUser.getUserId().toString());
     }
     catch (Exception e) {
       e.printStackTrace();
     }
     return uIdSet;
   }
 
   private Set<String> getMyLeader(String userId)
   {
     Set userSet = new HashSet();
     if (StringUtil.isEmpty(userId)) {
       return userSet;
     }
 
     userSet = this.userUnderService.getMyLeader(Long.valueOf(Long.parseLong(userId)));
     return userSet;
   }
 
   private Set<String> getByScript(String script, String startUser, String preTaskUser)
   {
     Set userSet = new HashSet();
 
     Map vars = new HashMap();
     vars.put("startUser", startUser);
     vars.put("prevUser", preTaskUser);
     Object result = this.groovyScriptEngine.executeObject(script, vars);
     if (result == null) {
       return userSet;
     }
     return (Set)result;
   }
 
   private Set<String> computeUserSet(short computeType, Set<String> userIdSet, Set<String> newUserSet)
   {
     if (newUserSet == null) return userIdSet;
     if (1 == computeType) {
       Set orLastSet = new HashSet();
       Iterator uIt = userIdSet.iterator();
       while (uIt.hasNext()) {
         String key = (String)uIt.next();
         if (newUserSet.contains(key)) {
           orLastSet.add(key);
         }
       }
       return orLastSet;
     }if (0 == computeType)
       userIdSet.addAll(newUserSet);
     else {
       for (String newUserId : newUserSet) {
         userIdSet.remove(newUserId);
       }
     }
     return userIdSet;
   }
 }

