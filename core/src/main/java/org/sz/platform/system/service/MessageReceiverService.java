package org.sz.platform.system.service;

import java.util.List;
import java.util.Map;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.MessageReceiver;

public interface MessageReceiverService extends BaseService<MessageReceiver> {

	List<Map> getMessageReadReply(Long messageId);

}