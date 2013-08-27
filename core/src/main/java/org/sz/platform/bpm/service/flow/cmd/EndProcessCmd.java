 package org.sz.platform.bpm.service.flow.cmd;
 
 import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
 
 public class EndProcessCmd
   implements Command<Void>
 {
   private String taskId = "";
 
   public EndProcessCmd(String taskId) {
     this.taskId = taskId;
   }
 
   public Void execute(CommandContext context)
   {
     TaskEntity task = context.getTaskEntityManager().findTaskById(this.taskId);
     String executionId = task.getExecutionId();
 
     ExecutionEntity executionEntity = context.getExecutionEntityManager().findExecutionById(executionId);
     ExecutionEntity parentEnt = executionEntity.getParent();
     if (parentEnt != null) {
       executionEntity = parentEnt;
     }
     executionEntity.end();
     return null;
   }
 }

