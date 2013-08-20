package org.sz.platform.service.system;

import java.util.List;
import java.util.Map;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.Message;

public interface MessageService extends BaseService<Message>{

	List<Message> getListByActDefIdNodeId(String actDefId, String nodeId);

	Map<Integer, Message> getMapByActDefIdNodeId(String actDefId, String nodeId);

}