package org.sz.platform.bpm.service.flow;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.model.flow.BpmNodeUser;

public interface BpmNodeUserService extends BaseService<BpmNodeUser>{

	List<BpmNodeUser> getBySetId(Long setId);

	List<String> getExeUserIdsByInstance(String actInstId, String nodeId,
			String preTaskUser);

	List<String> getExeUserIds(String actDefId, String nodeId,
			String startUserId);

	List<String> getExeUserIds(String actDefId, String actInstId,
			String nodeId, String startUserId, String preTaskUser);

}