package org.sz.platform.bpm.service.flow.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.dao.flow.BpmNodeSetDao;
import org.sz.platform.bpm.model.flow.BpmNodeSet;
import org.sz.platform.bpm.service.flow.BpmNodeSetService;


@Service("bpmNodeSetService")
public class BpmNodeSetServiceImpl extends BaseServiceImpl<BpmNodeSet> implements BpmNodeSetService {

	@Resource
	private BpmNodeSetDao dao;

	protected IEntityDao<BpmNodeSet, Long> getEntityDao() {
		return this.dao;
	}

	public void save(Long defId, List<BpmNodeSet> nodeList) throws Exception {
		this.dao.delByStartGlobalDefId(defId);
		for (BpmNodeSet node : nodeList)
			if (node.getSetId() == null) {
				node.setSetId(Long.valueOf(UniqueIdUtil.genId()));
				this.dao.add(node);
			} else {
				this.dao.update(node);
			}
	}

	public List<BpmNodeSet> getByDefId(Long defId) {
		return this.dao.getByDefId(defId);
	}

	public BpmNodeSet getByDefIdNodeId(Long defId, String nodeId) {
		return this.dao.getByDefIdNodeId(defId, nodeId);
	}

	public Map<String, BpmNodeSet> getMapByDefId(Long defId) {
		return this.dao.getMapByDefId(defId);
	}

	public BpmNodeSet getByActDefIdNodeId(String actDefId, String nodeId) {
		return this.dao.getByActDefIdNodeId(actDefId, nodeId);
	}

	public BpmNodeSet getByActDefIdJoinTaskKey(String actDefId, String joinTaskKey) {
		return this.dao.getByActDefIdJoinTaskKey(actDefId, joinTaskKey);
	}

	public BpmNodeSet getBySetType(Long defId, Short setType) {
		return this.dao.getBySetType(defId, setType);
	}

	public List<BpmNodeSet> getByActDefId(String actDefId) {
		return this.dao.getByActDefId(actDefId);
	}

	public void updateIsJumpForDef(String nodeId, String actDefId, Short isJumpForDef) {
		this.dao.updateIsJumpForDef(nodeId, actDefId, isJumpForDef);
	}
}
