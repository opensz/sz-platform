package org.sz.platform.bpm.dao.flow;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.BpmNodeMessage;

public interface BpmNodeMessageDao  extends BaseDao<BpmNodeMessage> {

	List<BpmNodeMessage> getMessageByActDefIdNodeId(String actDefId,
			String nodeId);

	void delByActDefId(String actDefId);

	List<BpmNodeMessage> getByActDefId(String actDefId);

}