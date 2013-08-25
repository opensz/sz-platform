package org.sz.platform.bpm.service.flow;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.model.flow.BpmAgent;

public interface BpmAgentService  extends BaseService<BpmAgent> {

	List<BpmAgent> getByAgentId(Long agentid);

	List<String> getNotInByAgentId(Long agentid);

}