package org.sz.platform.bpm.service.flow;

import java.util.List;
import java.util.Map;

import org.sz.core.service.BaseService;
import org.sz.platform.bpm.model.form.BpmTableTemprights;

public interface BpmTableTemprightsService extends BaseService<BpmTableTemprights> {

	Map<String, Map<String, String>> getRights(Long id, int assignType);

	void saveRights(Long id, int assignType, String[] rightType,
			String[] ownerId, String[] ownerName) throws Exception;

	void add(List<BpmTableTemprights> rightList);

}