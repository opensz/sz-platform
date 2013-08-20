package org.sz.platform.service.system;

import java.util.List;
import java.util.Map;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.MessageReceiver;

public interface MessageReceiverService extends BaseService<MessageReceiver> {

	List<Map> getMessageReadReply(Long messageId);

}