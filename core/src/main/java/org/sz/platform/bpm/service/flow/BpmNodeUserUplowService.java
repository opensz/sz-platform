package org.sz.platform.bpm.service.flow;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.model.flow.BpmNodeUserUplow;

public interface BpmNodeUserUplowService extends BaseService<BpmNodeUserUplow> {

	void upd(long nodeUserId, List<BpmNodeUserUplow> uplowList)
			throws Exception;

	List<BpmNodeUserUplow> getByNodeUserId(long userId);

}