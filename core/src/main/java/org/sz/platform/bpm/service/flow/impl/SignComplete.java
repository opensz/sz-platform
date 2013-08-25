 package org.sz.platform.bpm.service.flow.impl;
 
 import javax.annotation.Resource;

import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.platform.bpm.model.flow.BpmNodeSign;
import org.sz.platform.bpm.service.flow.BpmNodeSignService;
import org.sz.platform.bpm.service.flow.BpmService;
import org.sz.platform.bpm.service.flow.ISignComplete;
import org.sz.platform.bpm.service.flow.TaskSignDataService;
 
 public class SignComplete
   implements ISignComplete
 {
   private Logger logger = LoggerFactory.getLogger(SignComplete.class);
 
   @Resource
   private BpmService bpmService;
 
   @Resource
   private TaskSignDataService taskSignDataService;
 
   @Resource
   private BpmNodeSignService bpmNodeSignService;
 
   public boolean isComplete(ActivityExecution execution) { this.logger.debug("entert the SignComplete isComplete method...");
 
     String nodeId = execution.getActivity().getId();
     String actInstId = execution.getProcessInstanceId();
 
     ProcessDefinition processDefinition = this.bpmService.getProcessDefinitionByProcessInanceId(actInstId);
 
     BpmNodeSign bpmNodeSign = this.bpmNodeSignService.getByDefIdAndNodeId(processDefinition.getId(), nodeId);
 
     Integer loopCounter = (Integer)execution.getVariable("loopCounter");
 
     Integer instanceOfNumbers = (Integer)execution.getVariable("nrOfInstances");
 
     boolean isCompleted = false;
 
     String signResult = null;
 
     if (bpmNodeSign != null)
     {
       Integer totalVoteCounts = this.taskSignDataService.getTotalVoteCount(actInstId, nodeId);
 
       Integer agreeVotesCounts = this.taskSignDataService.getAgreeVoteCount(actInstId, nodeId);
 
       Integer refuseVotesCounts = this.taskSignDataService.getRefuseVoteCount(actInstId, nodeId);
 
       if (totalVoteCounts.intValue() > 0)
       {
         if (BpmNodeSign.VOTE_TYPE_PERCENT.equals(bpmNodeSign.getVoteType()))
         {
           float percents = 0.0F;
 
           if (BpmNodeSign.DECIDE_TYPE_PASS.equals(bpmNodeSign.getDecideType()))
           {
             percents = agreeVotesCounts.intValue() / totalVoteCounts.intValue();
             if (percents >= (float)bpmNodeSign.getVoteAmount().longValue())
             {
               signResult = "pass";
               isCompleted = true;
             } else if (loopCounter.intValue() + 1 == totalVoteCounts.intValue())
             {
               signResult = "refuse";
               isCompleted = true;
             }
           }
           else
           {
             percents = refuseVotesCounts.intValue() / totalVoteCounts.intValue();
             if (percents >= (float)bpmNodeSign.getVoteAmount().longValue())
             {
               signResult = "refuse";
               isCompleted = true;
             }
             else if (loopCounter.intValue() + 1 == totalVoteCounts.intValue())
             {
               signResult = "pass";
               isCompleted = true;
             }
 
           }
 
         }
         else if (BpmNodeSign.DECIDE_TYPE_PASS.equals(bpmNodeSign.getDecideType()))
         {
           if (agreeVotesCounts.intValue() >= bpmNodeSign.getVoteAmount().longValue())
           {
             signResult = "pass";
             isCompleted = true;
           } else if (loopCounter.intValue() + 1 == totalVoteCounts.intValue())
           {
             signResult = "refuse";
             isCompleted = true;
           }
 
         }
         else if (refuseVotesCounts.intValue() >= bpmNodeSign.getVoteAmount().longValue())
         {
           signResult = "refuse";
           isCompleted = true;
         }
         else if (loopCounter.intValue() + 1 == totalVoteCounts.intValue())
         {
           signResult = "pass";
           isCompleted = true;
         }
 
       }
 
     }
 
     if ((signResult == null) && (loopCounter.intValue() + 1 == instanceOfNumbers.intValue()))
     {
       this.taskSignDataService.batchUpdateCompleted(actInstId, nodeId);
     }
 
     if (signResult != null)
     {
       this.logger.debug("set the sign result + " + signResult);
 
       this.taskSignDataService.batchUpdateCompleted(actInstId, nodeId);
       execution.setVariable("signResult_" + nodeId, signResult);
     }
 
     if (isCompleted)
     {
       this.bpmService.delLoopAssigneeVars(execution.getId());
     }
 
     return isCompleted;
   }
 
   private boolean isParallel(ActivityExecution execution)
   {
     String str = execution.getActivity().getProperty("multiInstance").toString();
 
     return "parallel".equals(str);
   }
 }

