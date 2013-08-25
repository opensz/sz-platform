package org.sz.platform.system.service;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.MessageRead;
import org.sz.platform.system.model.SysUser;

public interface MessageReadService extends BaseService<MessageRead>{

	void addMessageRead(Long messageId, SysUser sysUser) throws Exception;

	List<MessageRead> getReadByMsgId(Long messageId);

}