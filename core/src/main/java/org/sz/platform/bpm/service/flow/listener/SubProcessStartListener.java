package org.sz.platform.bpm.service.flow.listener;

import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

public class SubProcessStartListener implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {

		ExecutionEntity ent = (ExecutionEntity) execution;

		ActivityImpl activityImpl = ent.getActivity();
		List<ActivityImpl> list = activityImpl.getActivities();
		for (ActivityImpl actImpl : list) {
			if ("userTask".equals(actImpl.getProperty("type"))) {
				execution.setVariable("firstNode", actImpl.getId());
				break;
			}
		}

	}

}
