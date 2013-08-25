package org.sz.platform.bpm.dao.flow;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.TaskUser;

public interface TaskUserDao extends BaseDao<TaskUser> {

	List<TaskUser> getByTaskId(String taskId);

}