package org.sz.platform.service.system;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.MessageRead;
import org.sz.platform.model.system.SysUser;

public interface MessageReadService extends BaseService<MessageRead>{

	void addMessageRead(Long messageId, SysUser sysUser) throws Exception;

	List<MessageRead> getReadByMsgId(Long messageId);

}