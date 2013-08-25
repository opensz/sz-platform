package org.sz.platform.bpm.service.flow;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.model.flow.TaskSignData;

public interface TaskSignDataService  extends BaseService<TaskSignData>{

	List<TaskSignData> getByActInstIdNodeIdSignNums(String actInstId,
			String nodeId, Integer signNums);

	Integer getMaxSignNums(String actInstId, String nodeId, Short isCompleted);

	TaskSignData getByTaskId(String taskId);

	void signVoteTask(String taskId, String content, Short isAgree);

	Integer getTotalVoteCount(String actInstId, String nodeId);

	Integer getAgreeVoteCount(String actInstId, String nodeId);

	Integer getRefuseVoteCount(String actInstId, String nodeId);

	Integer getAbortVoteCount(String actInstId, String nodeId);

	void batchUpdateCompleted(String actInstId, String nodeId);

	void addSign(String userIds, String taskId);

}