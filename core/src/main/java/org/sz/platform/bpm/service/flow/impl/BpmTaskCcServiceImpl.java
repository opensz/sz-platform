package org.sz.platform.bpm.service.flow.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.dao.flow.BpmTaskCcDao;
import org.sz.platform.bpm.model.flow.BpmTaskCc;
import org.sz.platform.bpm.service.flow.BpmTaskCcService;

@Service("bpmTaskCcService")
public class BpmTaskCcServiceImpl extends BaseServiceImpl<BpmTaskCc> implements
		BpmTaskCcService {
	@Resource
	private BpmTaskCcDao dao;

	protected IEntityDao<BpmTaskCc, Long> getEntityDao() {
		return this.dao;
	}
}
