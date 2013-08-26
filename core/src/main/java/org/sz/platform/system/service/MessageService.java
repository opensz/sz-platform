package org.sz.platform.system.service;

import java.util.List;
import java.util.Map;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.Message;

public interface MessageService extends BaseService<Message> {

	List<Message> getListByActDefIdNodeId(String actDefId, String nodeId);

	Map<Integer, Message> getMapByActDefIdNodeId(String actDefId, String nodeId);

}