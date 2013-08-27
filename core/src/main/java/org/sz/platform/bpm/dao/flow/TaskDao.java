package org.sz.platform.bpm.dao.flow;

import java.util.Date;
import java.util.List;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.sz.core.bpm.model.ProcessTask;
import org.sz.core.dao.BaseDao;
import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;

public interface TaskDao extends BaseDao<TaskEntity> {

	String getIbatisMapperNamespace();

	List<TaskEntity> getMyTasks(Long userId, QueryFilter queryFilter);

	List<ProcessTask> getTasks(Long userId, String taskName, String subject,
			String processName, String orderField, String orderSeq, PageBean pb);

	List<TaskEntity> getMyEvents(Object param);

	List<TaskEntity> getAgentTasks(Long userId, String actDefId,
			QueryFilter queryFilter);

	List<Long> getAgentIdByTaskId(QueryFilter queryFilter);

	int setDueDate(String taskId, Date dueDate);

	void insertTask(ProcessTask task);

	List<Long> getCandidateUsers(Long taskId);

	List<TaskEntity> getAllAgentTask(Long userId, QueryFilter filter);

	List<ProcessTask> getReminderTask();

	List<ProcessTask> getTasksByRunId(Long runId);

	void updateTaskAssignee(String taskId, String userId);

	void updateTaskAssigneeNull(String taskId);

	void updateTaskOwner(String taskId, String userId);

	List<ProcessTask> getByTaskNameOrTaskIds(String userId, String taskName,
			String taskIds, PageBean pb);

	ProcessTask getByTaskId(String taskId);

	List<TaskEntity> getAllAgentTask(Long userId, PageBean pb);
	
	List<TaskEntity> getCcTasks(Long userId, QueryFilter queryFilter);
}