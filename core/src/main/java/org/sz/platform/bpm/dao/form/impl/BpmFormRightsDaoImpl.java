package org.sz.platform.bpm.dao.form.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.form.BpmFormRightsDao;
import org.sz.platform.bpm.model.form.BpmFormRights;

@Repository("bpmFormRightsDao")
public class BpmFormRightsDaoImpl extends BaseDaoImpl<BpmFormRights> implements
		BpmFormRightsDao {
	public Class getEntityClass() {
		return BpmFormRights.class;
	}

	public void delByFormDefId(Long formDefId) {
		delBySqlKey("delByFormDefId", formDefId);
	}

	public void delAllByFormDefId(Long formDefId) {
		delBySqlKey("delAllByFormDefId", formDefId);
	}
	

	public List<BpmFormRights> getByFormDefId(Long formDefId) {
		return getByFormDefId(formDefId, "", null);
	}
	
	public List<BpmFormRights> getByFormDefId(Long formDefId, String nodeId) {
		return getByFormDefId(formDefId, nodeId, null);
	}
	
	public List<BpmFormRights> getByFormDefId(Long formDefId, String nodeId, String actDefId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nodeId", nodeId);
		params.put("formDefId", formDefId);
		params.put("actDefId", actDefId);		
		return getBySqlKey("getByFormDefId", params);
	}

	public List<BpmFormRights> getByFlowFormNodeId(String actDefId,
			String nodeId) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);
		params.put("nodeId", nodeId);

		return getBySqlKey("getByFlowFormNodeId", params);
	}

	public void delByFlowFormNodeId(String actDefId, String nodeId) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);
		params.put("nodeId", nodeId);
		delBySqlKey("delByFlowFormNodeId", params);
	}
}
