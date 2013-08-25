package org.sz.platform.bpm.dao.flow;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.BpmAgent;

public interface BpmAgentDao extends BaseDao<BpmAgent>  {

	List<BpmAgent> getByAgentId(Long agentid);

	void delByAgentId(Long agentid);

	List<BpmAgent> getByDefId(Long defId);

	List<String> getNotInByAgentId(Long agentid);

}