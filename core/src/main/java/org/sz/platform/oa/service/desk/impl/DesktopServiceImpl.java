package org.sz.platform.oa.service.desk.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Service;
import org.sz.core.bpm.model.ProcessTask;
import org.sz.core.query.PageBean;
import org.sz.core.util.ContextUtil;
import org.sz.platform.bpm.dao.flow.ProcessRunDao;
import org.sz.platform.bpm.dao.flow.TaskDao;
import org.sz.platform.bpm.model.flow.ProcessRun;
import org.sz.platform.oa.model.mail.OutMail;
import org.sz.platform.oa.service.desk.DesktopService;
import org.sz.platform.system.dao.MessageSendDao;
import org.sz.platform.system.model.SysUser;

@Service("desktopService")
public class DesktopServiceImpl implements DesktopService {

	@Resource
	private MessageSendDao messageSendDao;

	@Resource
	private TaskDao taskDao;

	@Resource
	private ProcessRunDao processRunDao;

	// @Resource
	// private OutMailDao outMailDao;

	@Override
	public SysUser getUser() {
		SysUser u = ContextUtil.getCurrentUser();
		return u;
	}

	@Override
	public List<?> getMessage() {
		PageBean pb = new PageBean();
		pb.setCurrentPage(1);
		pb.setPagesize(10);
		List list = this.messageSendDao.getNotReadMsgByUserId(ContextUtil
				.getCurrentUserId().longValue(), pb);

		return list;
	}

	@Override
	public List<ProcessTask> forMe() {
		PageBean pb = new PageBean();
		pb.setCurrentPage(1);
		pb.setPagesize(10);
		List list = new ArrayList();
		list = this.taskDao.getTasks(ContextUtil.getCurrentUserId(), null,
				null, null, null, "desc", pb);

		return list;
	}

	@Override
	public List<ProcessRun> myAttend() {
		PageBean pb = new PageBean();
		pb.setCurrentPage(1);
		pb.setPagesize(10);
		List list = this.processRunDao.getMyAttend(
				ContextUtil.getCurrentUserId(), null, pb);

		return list;
	}

	@Override
	public List<ProcessRun> myStart() {
		PageBean pb = new PageBean();
		pb.setCurrentPage(1);
		pb.setPagesize(10);
		List list = this.processRunDao.myStart(ContextUtil.getCurrentUserId(),
				pb);

		return list;
	}

	@Override
	public List<OutMail> myNewMail() {
		// TODO
		PageBean pb = new PageBean();
		pb.setCurrentPage(1);
		pb.setPagesize(10);
		List list = null;// this.outMailDao.getMailByUserId(ContextUtil.getCurrentUserId().longValue(),
							// pb);
		return list;
	}

	@Override
	public List<TaskEntity> forAgent() {
		PageBean pb = new PageBean();
		pb.setCurrentPage(1);
		pb.setPagesize(10);
		List list = this.taskDao.getAllAgentTask(
				ContextUtil.getCurrentUserId(), pb);

		return list;
	}
}
