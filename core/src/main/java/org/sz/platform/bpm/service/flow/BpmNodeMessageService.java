package org.sz.platform.bpm.service.flow;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.bpm.model.flow.BpmNodeMessage;
import org.sz.platform.system.model.Message;

public interface BpmNodeMessageService extends BaseService<BpmNodeMessage>{

	List<BpmNodeMessage> getListByActDefIdNodeId(String actDefId, String nodeId);

	void saveAndEdit(String actDefId, String nodeId, List<Message> messages)
			throws Exception;

}