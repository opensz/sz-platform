package org.sz.platform.bpm.service.flow;

import java.util.List;
import java.util.Set;

import org.sz.core.service.BaseService;
import org.sz.platform.bpm.model.flow.TaskUser;
import org.sz.platform.system.model.SysUser;

public interface TaskUserService extends BaseService<TaskUser>{

	List<TaskUser> getByTaskId(String taskId);

	Set<SysUser> getUserCandidateUsers(String taskId);

}