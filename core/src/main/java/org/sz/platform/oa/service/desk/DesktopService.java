package org.sz.platform.oa.service.desk;

import java.util.List;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.sz.core.bpm.model.ProcessTask;
import org.sz.platform.bpm.model.flow.ProcessRun;
import org.sz.platform.oa.model.mail.OutMail;
import org.sz.platform.system.model.SysUser;

public interface DesktopService {

	SysUser getUser();

	List<?> getMessage();

	List<ProcessTask> forMe();

	List<ProcessRun> myAttend();

	List<ProcessRun> myStart();

	List<OutMail> myNewMail();

	List<TaskEntity> forAgent();

}