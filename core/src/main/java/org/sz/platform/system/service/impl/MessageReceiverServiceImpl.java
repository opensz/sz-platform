package org.sz.platform.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.StringUtil;
import org.sz.platform.system.dao.MessageReceiverDao;
import org.sz.platform.system.model.MessageReceiver;
import org.sz.platform.system.model.SysOrg;
import org.sz.platform.system.service.MessageReceiverService;
import org.sz.platform.system.service.SysOrgService;

@Service("messageReceiverService")
public class MessageReceiverServiceImpl extends
		BaseServiceImpl<MessageReceiver> implements MessageReceiverService {

	@Resource
	private MessageReceiverDao dao;

	@Resource
	private MessageReceiverDao redao;

	@Resource
	private SysOrgService orgSevice;

	protected IEntityDao<MessageReceiver, Long> getEntityDao() {
		return this.dao;
	}

	public List<Map> getMessageReadReply(Long messageId) {
		String path = "";
		List list = new ArrayList();
		List listByUser = new ArrayList();
		List listByOrg = new ArrayList();
		List<MessageReceiver> reList = this.redao
				.getMessageReceiverList(messageId);
		if ((reList == null) && (reList.size() == 0))
			return null;
		for (MessageReceiver reModel : reList) {
			if (reModel.getReceiveType().equals(MessageReceiver.TYPE_USER)) {
				listByUser = this.dao.getReadReplyByUser(messageId);
				list.addAll(listByUser);
			} else {
				SysOrg sysOrg = (SysOrg) this.orgSevice.getById(reModel
						.getReceiverId());
				if (sysOrg != null) {
					path = sysOrg.getPath();
					if (!StringUtil.isEmpty(path)) {
						listByOrg = this.dao
								.getReadReplyByPath(messageId, path);
						list.addAll(listByOrg);
					}
				}
			}
		}
		return list;
	}
}
