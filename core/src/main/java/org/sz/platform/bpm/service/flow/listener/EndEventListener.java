package org.sz.platform.bpm.service.flow.listener;

import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.sz.core.bpm.util.BpmConst;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.ContextUtil;
import org.sz.platform.bpm.model.flow.ProcessRun;
import org.sz.platform.bpm.service.flow.ProcessRunService;

public class EndEventListener extends BaseNodeEventListener
{
  protected void execute(DelegateExecution execution, String actDefId, String nodeId)
  {
    ExecutionEntity ent = (ExecutionEntity)execution;
    if (!ent.isEnded()) return;

    ProcessRunService processRunService = (ProcessRunService)ContextUtil.getBean("processRunService");
    ProcessRun processRun = processRunService.getByActInstanceId(ent.getProcessInstanceId());
    if (BeanUtils.isEmpty(processRun)) return;

    processRun.setStatus(ProcessRun.STATUS_FINISH);
    processRun.setEndTime(new Date());

    //TODO add Calendar Service
//    long userId = processRun.getCreatorId().longValue();
//    CalendarAssignService calAssignService = (CalendarAssignService)ContextUtil.getBean(CalendarAssignService.class);
//    Date startDate = processRun.getCreatetime();
//    Date endDate = processRun.getEndTime();
//    long durations = calAssignService.getTaskMillsTime(startDate, endDate, userId);
//
//    processRun.setDuration(Long.valueOf(durations));
    processRunService.update(processRun);
  }

  protected Integer getScriptType()
  {
    return BpmConst.EndScript;
  }
}