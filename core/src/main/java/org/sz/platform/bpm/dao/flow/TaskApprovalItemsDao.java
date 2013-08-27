package org.sz.platform.bpm.dao.flow;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.TaskApprovalItems;

public interface TaskApprovalItemsDao extends BaseDao<TaskApprovalItems> {

	TaskApprovalItems getFlowApproval(String actDefId, int isGlobal);

	TaskApprovalItems getTaskApproval(String actDefId, String nodeId,
			int isGlobal);

	void delFlowApproval(String actDefId, int isGlobal);

	void delTaskApproval(String actDefId, String nodeId, int isGlobal);

	List<TaskApprovalItems> getApprovalByActDefId(String actDefId, String nodeId);

}