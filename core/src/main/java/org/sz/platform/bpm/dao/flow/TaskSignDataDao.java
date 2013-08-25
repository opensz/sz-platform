package org.sz.platform.bpm.dao.flow;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.TaskSignData;

public interface TaskSignDataDao extends BaseDao<TaskSignData> {

	List<TaskSignData> getByActInstIdNodeIdSignNums(String actInstId,
			String nodeId, Integer signNums);

	Integer getMaxSignNums(String actInstId, String nodeId, Short isCompleted);

	TaskSignData getByTaskId(String taskId);

	Integer getTotalVoteCount(String actInstId, String nodeId);

	Integer getAgreeVoteCount(String actInstId, String nodeId);

	Integer getRefuseVoteCount(String actInstId, String nodeId);

	Integer getAbortVoteCount(String actInstId, String nodeId);

	TaskSignData getUserTaskSign(String actInstId, String nodeId,
			Integer signNums, Long voteUserId);

	void batchUpdateCompleted(String actInstId, String nodeId);

	void delByIdActDefId(String actDefId);

	List<TaskSignData> getExistUserTaskSign(String taskProId, String nodeId);

}