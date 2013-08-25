package org.sz.platform.bpm.service.flow.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.dao.flow.TaskOpinionDao;
import org.sz.platform.bpm.model.flow.TaskOpinion;
import org.sz.platform.bpm.service.flow.TaskOpinionService;

@Service("taskOpinionService")
public class TaskOpinionServiceImpl extends BaseServiceImpl<TaskOpinion>
		implements TaskOpinionService {

	@Resource
	private TaskOpinionDao dao;

	protected IEntityDao<TaskOpinion, Long> getEntityDao() {
		return this.dao;
	}

	public TaskOpinion getByTaskId(Long taskId) {
		return this.dao.getByTaskId(taskId);
	}

	public List<TaskOpinion> getByActInstId(String actInstId) {
		return this.dao.getByActInstId(actInstId);
	}

	public void delByActDefIdTaskOption(String actDefId) {
		this.dao.delByActDefIdTaskOption(actDefId);
	}

	public List<TaskOpinion> getByActInstIdTaskKey(String actInstId,
			String taskKey) {
		return this.dao.getByActInstIdTaskKey(actInstId, taskKey);
	}

	public TaskOpinion getLatestTaskOpinion(String actInstId, String taskKey) {
		List list = getByActInstIdTaskKey(actInstId, taskKey);
		if ((list != null) && (list.size() > 0)) {
			return (TaskOpinion) list.get(0);
		}
		return null;
	}

	public TaskOpinion getLatestUserOpinion(String actInstId, Long exeUserId) {
		List taskOpinions = this.dao.getByActInstIdExeUserId(actInstId,
				exeUserId);
		if (taskOpinions.size() == 0)
			return null;
		return (TaskOpinion) taskOpinions.get(0);
	}

	public void delByTaskId(Long taskId) {
		this.dao.delByTaskId(taskId);
	}
}
