package org.sz.platform.bpm.service.flow;

import java.util.List;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.sz.platform.bpm.model.flow.BpmDefVar;

public abstract interface IBpmActService
{
  public abstract List<BpmDefVar> getVarsByFlowDefId(Long paramLong);

  public abstract ExecutionEntity getExecution(String paramString);

  public abstract void endProcessByTaskId(String paramString);
}

