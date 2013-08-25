package org.sz.platform.bpm.dao.flow;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.BpmDefRights;

public interface BpmDefRightsDao extends BaseDao<BpmDefRights> {

	List<BpmDefRights> getDefRight(Long defId, Short rightType);

	List<BpmDefRights> getTypeRight(Long typeId, Short rightType);

	void delByTypeId(Long typeId);

	void delByDefId(Long defId);

	List<BpmDefRights> getByDefId(Long defId);

	List<BpmDefRights> getByTypeId(Long typeId);

}