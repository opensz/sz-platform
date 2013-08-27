package org.sz.platform.bpm.service.flow;

import java.util.List;
import java.util.Map;

import org.sz.core.service.BaseService;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.model.flow.BpmDefRights;

public interface BpmDefRightsService extends BaseService<BpmDefRights>{

	List<BpmDefRights> getDefRight(Long defId, Short rightType);

	List<BpmDefRights> getTypeRight(Long typeId, Short rightType);

	Map<String, Map<String, String>> getRights(Long assignId, int assignType);

	Map<String, String> coverList2Map(List<BpmDefRights> list);

	void saveRights(Long assignId, int assignType, String[] rightType,
			String[] ownerId, String[] ownerName) throws Exception;

	void add(List<BpmDefRights> rightList);

}