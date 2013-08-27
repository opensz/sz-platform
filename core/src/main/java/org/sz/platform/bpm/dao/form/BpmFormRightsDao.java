package org.sz.platform.bpm.dao.form;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.form.BpmFormRights;

public interface BpmFormRightsDao extends BaseDao<BpmFormRights>{

	void delByFormDefId(Long formDefId);

	void delAllByFormDefId(Long formDefId);

	List<BpmFormRights> getByFormDefId(Long formDefId);

	List<BpmFormRights> getByFlowFormNodeId(String actDefId, String nodeId);

	void delByFlowFormNodeId(String actDefId, String nodeId);

	List<BpmFormRights> getByFormDefId(Long formDefId, String nodeId);
	
	public List<BpmFormRights> getByFormDefId(Long formDefId, String nodeId, String actDefId);
}