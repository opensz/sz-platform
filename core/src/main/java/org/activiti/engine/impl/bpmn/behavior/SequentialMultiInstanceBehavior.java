
package org.activiti.engine.impl.bpmn.behavior;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.pvm.process.ActivityImpl;



public class SequentialMultiInstanceBehavior extends MultiInstanceActivityBehavior {
  
  public SequentialMultiInstanceBehavior(ActivityImpl activity, AbstractBpmnActivityBehavior innerActivityBehavior) {
    super(activity, innerActivityBehavior);
  }
  
  
  protected void createInstances(ActivityExecution execution) throws Exception {
    int nrOfInstances = resolveNrOfInstances(execution);
    if (nrOfInstances <= 0) {
      throw new ActivitiException("Invalid number of instances: must be positive integer value" 
              + ", but was " + nrOfInstances);
    }
    
    setLoopVariable(execution, NUMBER_OF_INSTANCES, nrOfInstances);
    setLoopVariable(execution, NUMBER_OF_COMPLETED_INSTANCES, 0);
    setLoopVariable(execution, LOOP_COUNTER, 0);
    setLoopVariable(execution, NUMBER_OF_ACTIVE_INSTANCES, 1);
    logLoopDetails(execution, "initialized", 0, 0, 1, nrOfInstances);
    
    executeOriginalBehavior(execution, 0);
  }
  
  
  public void leave(ActivityExecution execution) {
    callActivityEndListeners(execution);
    
    int loopCounter = getLoopVariable(execution, LOOP_COUNTER) + 1;
    int nrOfInstances = getLoopVariable(execution, NUMBER_OF_INSTANCES);
    int nrOfCompletedInstances = getLoopVariable(execution, NUMBER_OF_COMPLETED_INSTANCES) + 1;
    int nrOfActiveInstances = getLoopVariable(execution, NUMBER_OF_ACTIVE_INSTANCES);
    
    setLoopVariable(execution, LOOP_COUNTER, loopCounter);
    setLoopVariable(execution, NUMBER_OF_COMPLETED_INSTANCES, nrOfCompletedInstances);
    logLoopDetails(execution, "instance completed", loopCounter, nrOfCompletedInstances, nrOfActiveInstances, nrOfInstances);
    
    if (loopCounter == nrOfInstances || completionConditionSatisfied(execution)) {
      super.leave(execution);
    } else {
      try {
        executeOriginalBehavior(execution, loopCounter);
      } catch (BpmnError error) {
        // re-throw business fault so that it can be caught by an Error Intermediate Event or Error Event Sub-Process in the process
        throw error;
      } catch (Exception e) {
        throw new ActivitiException("Could not execute inner activity behavior of multi instance behavior", e);
      }
    }
  }
  
  @Override
  public void execute(ActivityExecution execution) throws Exception {
    super.execute(execution);
    
    if(innerActivityBehavior instanceof SubProcessActivityBehavior) {
      // ACT-1185: end-event in subprocess may have inactivated execution
      if(!execution.isActive() && execution.isEnded() && (execution.getExecutions() == null || execution.getExecutions().size() == 0)) {
        execution.setActive(true);
      }
    }
  }

}
