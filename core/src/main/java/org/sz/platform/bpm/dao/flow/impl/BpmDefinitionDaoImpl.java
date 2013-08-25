package org.sz.platform.bpm.dao.flow.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.core.query.QueryFilter;
import org.sz.platform.bpm.dao.flow.BpmDefinitionDao;
import org.sz.platform.bpm.model.flow.BpmDefinition;

@Repository("bpmDefinitionDao")
public class BpmDefinitionDaoImpl extends BaseDaoImpl<BpmDefinition> implements
		BpmDefinitionDao {
	public Class getEntityClass() {
		return BpmDefinition.class;
	}

	public int updateMain(Long defId, Short isMain) {
		Map params = new HashMap();
		params.put("defId", defId);
		params.put("isMain", isMain);

		return update("updateMain", params);
	}

	public int updateSubVersions(Long parentDefId, String defKey) {
		Map params = new HashMap();
		params.put("defKey", defKey);
		params.put("parentDefId", parentDefId);

		return update("updateSubVersions", params);
	}

	public List<BpmDefinition> getByParentDefId(Long defId) {
		return getBySqlKey("getByParentDefId", defId);
	}

	public List<BpmDefinition> getByParentDefIdIsMain(Long parentDefId,
			Short isMain) {
		Map params = new HashMap();

		params.put("parentDefId", parentDefId);
		params.put("isMain", isMain);

		return getBySqlKey("getByParentDefIdIsMain", params);
	}

	public BpmDefinition getByActDefId(String actDefId) {
		return (BpmDefinition) getUnique("getByActDefId", actDefId);
	}

	public List<BpmDefinition> getByDefKey(String actDefKey) {
		return getBySqlKey("getByDefKey", actDefKey);
	}

	public BpmDefinition getMainDefByActDefKey(String actDefKey) {
		Map params = new HashMap();
		params.put("actDefKey", actDefKey);
		params.put("isMain", BpmDefinition.MAIN);
		return (BpmDefinition) getUnique("getByActDefKeyIsMain", params);
	}

	public List<BpmDefinition> getByTypeId(Long typeId) {
		return getBySqlKey("getByTypeId", typeId);
	}

	public List<BpmDefinition> getAllForAdmin(QueryFilter queryFilter) {
		return getBySqlKey("getAllForAdmin", queryFilter);
	}

	public int saveParam(BpmDefinition bpmDefinition) {
		return update("saveParam", bpmDefinition);
	}

	public void delByDeployId(String actDeployId) {
		delBySqlKey("delByDeployId", actDeployId);
	}

	public BpmDefinition getByDeployId(String actDeployId) {
		return (BpmDefinition) getUnique("getByDeployId", actDeployId);
	}

	public boolean isActDefKeyExists(String key) {
		Integer rtn = (Integer) getOne("isActDefKeyExists", key);
		return rtn.intValue() > 0;
	}

	public List<BpmDefinition> getByUserId(QueryFilter queryFilter) {
		return getBySqlKey("getByUserId", queryFilter);
	}

	public List<BpmDefinition> getByUserIdFilter(QueryFilter queryFilter) {
		return getBySqlKey("getByUserIdFilter", queryFilter);
	}

	public List<BpmDefinition> getBpmDefinitions() {
		return getBySqlKey("getBpmDefinitions");
	}
}
