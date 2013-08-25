package org.sz.platform.bpm.service.flow;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.model.flow.TaskApprovalItems;

public interface TaskApprovalItemsService extends BaseService<TaskApprovalItems>{

	TaskApprovalItems getFlowApproval(String actDefId, int isGlobal);

	TaskApprovalItems getTaskApproval(String actDefId, String nodeId,
			int isGlobal);

	void delFlowApproval(String actDefId, int isGlobal);

	void delTaskApproval(String actDefId, String nodeId, int isGlobal);

	void addTaskApproval(String exp, String isGlobal, String actDefId,
			Long setId, String nodeId) throws Exception;

	List<String> getApprovalByActDefId(String actDefId, String nodeId);

}