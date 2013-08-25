 package org.sz.platform.bpm.service.flow.impl;
 
 import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.StringUtil;
import org.sz.platform.bpm.model.flow.ForkUser;
import org.sz.platform.bpm.service.flow.BpmNodeUserService;
import org.sz.platform.bpm.service.flow.TaskUserAssignService;
 
 @Service("taskUserAssignService")
 public class TaskUserAssignServiceImpl implements TaskUserAssignService
 {
   private Logger logger = LoggerFactory.getLogger(TaskUserAssignServiceImpl.class);
 
   private static ThreadLocal<ForkUser> forkJoinUserLocal = new ThreadLocal<ForkUser>();
 
   private static ThreadLocal<Map<String, List<String>>> nodeUserMapLocal = new ThreadLocal<Map<String, List<String>>>();
 
   private static ThreadLocal<List<String>> signUserLocal = new ThreadLocal<List<String>>();
   
   //抄送人员数组 threadlocal变量
   private static ThreadLocal<Long[]> ccUserIdLocal = new ThreadLocal<Long[]>();
 
   @Resource
   BpmNodeUserService bpmNodeUserService;
 
   public void addForkUser(ForkUser forkTask)
   {
     forkJoinUserLocal.set(forkTask);
   }
 
   public ForkUser getForkUser() {
     return (ForkUser)forkJoinUserLocal.get();
   }
 
   public void clearForkUser() {
     forkJoinUserLocal.remove();
   }
 
   public void addNodeUserMap(String[] nodeIds, String[] userIds) {
     if ((userIds == null) || (nodeIds == null) || (nodeIds.length != userIds.length)) {
       return;
     }
     Map nodeUserMap = new HashMap();
     for (int i = 0; i < nodeIds.length; i++)
       if (!StringUtil.isEmpty(userIds[i])) {
         String[] uIds = userIds[i].split("[,]");
         if (uIds != null) {
           List uIdList = Arrays.asList(uIds);
           nodeUserMap.put(nodeIds[i], uIdList);
         }
       }
     nodeUserMapLocal.set(nodeUserMap);
   }
 
   public Map<String, List<String>> getNodeUserMap() {
     return (Map)nodeUserMapLocal.get();
   }
   
   public void clearNodeUserMap() {
     nodeUserMapLocal.remove();
   }

   /**
    * 增加抄送人员
    * @param userIds
    */
   public void addCcUserIds(String userIds){
	   if(StringUtils.isBlank(userIds)){
		   return;
	   }
	   String[] uIdAry = userIds.split("[,]");
	   if(uIdAry.length > 0){
		   Long[] uIdAryL = new Long[uIdAry.length];
		   int i = 0;
		   for(String id : uIdAry){
			   try{
				   uIdAryL[i++] = Long.valueOf(id);
			   }catch(Exception exc){
				   logger.error("userId 传入参数有错误!");
				   return;
			   }
		   }
		   ccUserIdLocal.set(uIdAryL);
	   }
   }
   
   public Long[] getCcUserIds(){
	   return ccUserIdLocal.get();
   }
   
   public void clearCcUserIds(){
	   ccUserIdLocal.remove();
   }
 
   
   public void addNodeUser(String nodeId, String userIds) {
     Map nodeUserMap = new HashMap();
 
     String[] uIds = userIds.split("[,]");
     if (uIds != null) {
       List uIdList = Arrays.asList(uIds);
       nodeUserMap.put(nodeId, uIdList);
     }
 
     nodeUserMapLocal.set(nodeUserMap);
   }
 
   public List<String> getSignUser(ActivityExecution execution) {
     String nodeId = execution.getActivity().getId();
     Map nodeUserMap = (Map)nodeUserMapLocal.get();
     this.logger.debug("id:" + nodeId);
 
     if ((nodeUserMap != null) && (nodeUserMap.get(nodeId) != null)) {
       List userIds = (List)nodeUserMap.get(nodeId);
       if (signUserLocal.get() == null) {
         setSignUser(userIds);
       }
       return userIds;
     }
 
     List users = getSignUser();
     if (BeanUtils.isNotEmpty(users)) {
       return users;
     }
 
     ExecutionEntity ent = (ExecutionEntity)execution;
     String actDefId = ent.getProcessDefinitionId();
     String startUserId = (String)execution.getVariable("startUser");
     List list = this.bpmNodeUserService.getExeUserIds(actDefId, nodeId, startUserId);
     setSignUser(list);
     return list;
   }
 
   public void setSignUser(List<String> users)
   {
     signUserLocal.set(users);
   }
 
   public List<String> getSignUser()
   {
     return (List)signUserLocal.get();
   }
 
   public void clearSignUser() {
     signUserLocal.remove();
   }
 }

