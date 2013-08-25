 package org.sz.platform.bpm.service.flow.impl;
 
 import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.sz.core.bpm.model.ProcessCmd;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.dao.flow.ExecutionStackDao;
import org.sz.platform.bpm.model.flow.ExecutionStack;
import org.sz.platform.bpm.model.flow.TaskFork;
import org.sz.platform.bpm.model.flow.TaskOpinion;
import org.sz.platform.bpm.service.flow.BpmService;
import org.sz.platform.bpm.service.flow.ExecutionStackService;
import org.sz.platform.bpm.service.flow.TaskOpinionService;
import org.sz.platform.bpm.service.flow.TaskUserAssignService;
import org.sz.platform.bpm.service.flow.thread.TaskThreadService;
import org.sz.platform.system.model.SysUser;
 
 @Service("executionStackService")
 public class ExecutionStackServiceImpl extends BaseServiceImpl<ExecutionStack> implements ExecutionStackService
 {
 
   @Resource
   private ExecutionStackDao dao;
 
   @Resource
   private TaskService taskService;
 
   @Resource
   private BpmService bpmService;
 
   @Resource
   private TaskOpinionService taskOpinionService;
 
   @Resource
   private TaskUserAssignService taskUserAssignService;
 
   protected IEntityDao<ExecutionStack, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public void initStack(String actInstId)
     throws Exception
   {
     List<Task> taskList = this.taskService.createTaskQuery().processInstanceId(actInstId).list();
     if (taskList.size() > 0)
     {
       Map nodeIdStackMap = new HashMap();
       for (Task task : taskList)
       {
         TaskEntity taskEntity = (TaskEntity)task;
         String nodeId = taskEntity.getTaskDefinitionKey();
         if (!nodeIdStackMap.containsKey(nodeId))
         {
           ExecutionStack stack = new ExecutionStack();
           stack.setActInstId(actInstId);
           stack.setActDefId(taskEntity.getProcessDefinitionId());
           stack.setAssignees(task.getAssignee());
           stack.setDepth(Integer.valueOf(1));
           stack.setParentId(Long.valueOf(0L));
           stack.setStartTime(new Date());
           stack.setNodeId(nodeId);
           stack.setNodeName(taskEntity.getName());
           stack.setTaskIds(taskEntity.getId());
           Long stackId = Long.valueOf(UniqueIdUtil.genId());
           stack.setStackId(stackId);
           stack.setNodePath("0." + stackId + ".");
           nodeIdStackMap.put(nodeId, stack);
         }
         else
         {
           ExecutionStack stack = (ExecutionStack)nodeIdStackMap.get(nodeId);
           stack.setIsMultiTask(ExecutionStack.MULTI_TASK);
           stack.setAssignees(stack.getAssignees() + "," + task.getAssignee());
           stack.setTaskIds(stack.getTaskIds() + "," + task.getId());
         }
       }
 
       Iterator stackIt = nodeIdStackMap.values().iterator();
       while (stackIt.hasNext())
       {
         ExecutionStack exeStack = (ExecutionStack)stackIt.next();
         this.dao.add(exeStack);
       }
     }
   }
 
   public ExecutionStack backPrepared(ProcessCmd processCmd, TaskEntity taskEntity, String taskToken)
   {
     ExecutionStack parentStack = null;
 
     if (processCmd.getStackId() != null)
     {
       parentStack = (ExecutionStack)this.dao.getById(processCmd.getStackId());
     }
     else if (StringUtils.isEmpty(processCmd.getDestTask()))
     {
       ExecutionStack executionStack = getLastestStack(taskEntity.getProcessInstanceId(), taskEntity.getTaskDefinitionKey(), taskToken);
       if ((executionStack != null) && (executionStack.getParentId() != null) && (executionStack.getParentId().longValue() != 0L))
       {
         parentStack = (ExecutionStack)this.dao.getById(executionStack.getParentId());
       }
     }
 
     if (parentStack != null)
     {
       processCmd.setDestTask(parentStack.getNodeId());
 
       this.taskUserAssignService.addNodeUser(parentStack.getNodeId(), parentStack.getAssignees());
 
       if (ExecutionStack.MULTI_TASK == parentStack.getIsMultiTask())
       {
         processCmd.setSignUserIds(parentStack.getAssignees());
       }
     }
 
     return parentStack;
   }
   
   
	public ExecutionStack firstPrepared(ProcessCmd processCmd,
			TaskEntity taskEntity) {
		ExecutionStack firstStack = this.dao.getFirstStack(
				taskEntity.getProcessInstanceId());
		if (firstStack != null) {
			processCmd.setDestTask(firstStack.getNodeId());

			this.taskUserAssignService.addNodeUser(firstStack.getNodeId(),
					firstStack.getAssignees());

			// if (ExecutionStack.MULTI_TASK == firstStack.getIsMultiTask())
			// {
			// processCmd.setSignUserIds(firstStack.getAssignees());
			// }
		}

		return firstStack;
	}
 
   public void pop(ExecutionStack parentStack, boolean isRecover)
   {
     List<ExecutionStack> subChilds = this.dao.getByParentId(parentStack.getStackId());
     List taskList = this.bpmService.getTasks(parentStack.getActInstId());
     SysUser curUser = ContextUtil.getCurrentUser();
     if ((subChilds != null) && (subChilds.size() > 0))
       for (Iterator i$ = taskList.iterator(); i$.hasNext(); ) { 
				TaskEntity task = (TaskEntity)i$.next();
         for (ExecutionStack stack : subChilds)
         {
           if (stack.getNodeId().equals(task.getTaskDefinitionKey())) {
             if (isRecover) {
               TaskOpinion taskOpinion = this.taskOpinionService.getByTaskId(new Long(task.getId()));
               taskOpinion.setExeUserId(curUser.getUserId());
               taskOpinion.setExeFullname(curUser.getFullname());
               taskOpinion.setEndTime(new Date());
               taskOpinion.setDurTime(Long.valueOf(taskOpinion.getEndTime().getTime() - taskOpinion.getStartTime().getTime()));
               taskOpinion.setCheckStatus(TaskOpinion.STATUS_RECOVER);
               this.taskOpinionService.update(taskOpinion);
             }
 
             this.taskService.deleteTask(task.getId());
             break;
           }
         }
       }
     delSubChilds(parentStack.getStackId(), parentStack.getNodePath());
   }
 
   public void pushNewTasks(String actInstId, String destNodeId, List<TaskEntity> newTasks, String oldTaskToken)
     throws Exception
   {
     String curUserId = ContextUtil.getCurrentUserId().toString();
 
     ExecutionStack curExeNode = this.dao.getLastestStack(actInstId, destNodeId, oldTaskToken);
     if (curExeNode != null)
     {
       if (curExeNode.getAssignees() == null) {
         curExeNode.setAssignees(curUserId.toString());
       }
       curExeNode.setEndTime(new Date());
       this.dao.update(curExeNode);
     }
     ProcessDefinitionEntity processDef = null;
 
     if (newTasks.size() > 0) {
       Map nodeIdStackMap = new HashMap();
       for (Task task : newTasks)
       {
         TaskEntity taskEntity = (TaskEntity)task;
         String nodeId = taskEntity.getTaskDefinitionKey();
 
         if (processDef == null) {
           processDef = this.bpmService.getProcessDefinitionEntity(taskEntity.getProcessDefinitionId());
         }
 
         ActivityImpl taskAct = processDef.findActivity(nodeId);
 
         if (taskAct == null)
           continue;
         String multiInstance = (String)taskAct.getProperty("multiInstance");
         ExecutionStack stack = (ExecutionStack)nodeIdStackMap.get(nodeId);
         if ((StringUtils.isEmpty(multiInstance)) || (stack == null)) {
           Long stackId = Long.valueOf(UniqueIdUtil.genId());
           stack = new ExecutionStack();
           stack.setActInstId(taskEntity.getProcessInstanceId());
           stack.setAssignees(taskEntity.getAssignee());
           stack.setActDefId(taskEntity.getProcessDefinitionId());
           if (curExeNode == null) {
             stack.setDepth(Integer.valueOf(1));
             stack.setParentId(Long.valueOf(0L));
             stack.setNodePath("0." + stackId + ".");
           } else {
             stack.setDepth(Integer.valueOf(curExeNode.getDepth() == null ? 1 : curExeNode.getDepth().intValue() + 1));
             stack.setParentId(curExeNode.getStackId());
             stack.setNodePath(curExeNode.getNodePath() + stackId + ".");
           }
 
           stack.setStartTime(new Date());
           stack.setNodeId(nodeId);
           stack.setNodeName(taskEntity.getName());
           stack.setTaskIds(taskEntity.getId());
           stack.setStackId(stackId);
 
           nodeIdStackMap.put(nodeId, stack);
         }
         else if (stack != null) {
           stack.setIsMultiTask(ExecutionStack.MULTI_TASK);
           stack.setAssignees(stack.getAssignees() + "," + task.getAssignee());
           stack.setTaskIds(stack.getTaskIds() + "," + task.getId());
         }
 
         if (stack != null) {
           String taskToken = (String)this.taskService.getVariableLocal(taskEntity.getId(), TaskFork.TAKEN_VAR_NAME);
           if (taskToken != null) {
             stack.setTaskToken(taskToken);
           }
         }
       }
 
       Iterator stackIt = nodeIdStackMap.values().iterator();
       while (stackIt.hasNext()) {
         ExecutionStack exeStack = (ExecutionStack)stackIt.next();
         this.dao.add(exeStack);
       }
     }
   }
 
   public void addStack(String actInstId, String destNodeId, String oldTaskToken)
     throws Exception
   {
     List taskList = TaskThreadService.getNewTasks();
     if (taskList != null)
       pushNewTasks(actInstId, destNodeId, taskList, oldTaskToken);
   }
 
   public List<ExecutionStack> getByParentId(Long parentId)
   {
     return this.dao.getByParentId(parentId);
   }
 
   public List<ExecutionStack> getByParentIdAndEndTimeNotNull(Long parentId)
   {
     return this.dao.getByParentIdAndEndTimeNotNull(parentId);
   }
 
   public List<ExecutionStack> getByActInstIdNodeId(String actInstId, String nodeId) {
     return this.dao.getByActInstIdNodeId(actInstId, nodeId);
   }
 
   public ExecutionStack getLastestStack(String actInstId, String nodeId) {
     return this.dao.getLastestStack(actInstId, nodeId);
   }
 
   public ExecutionStack getLastestStack(String actInstId, String nodeId, String taskToken) {
     return this.dao.getLastestStack(actInstId, nodeId, taskToken);
   }
 
   public Integer delSubChilds(Long stackId, String nodePath)
   {
     return this.dao.delSubChilds(stackId, nodePath);
   }
   
   public ExecutionStack getFirstStack(String actInstId){
	   return this.dao.getFirstStack(actInstId);
   }
 }

