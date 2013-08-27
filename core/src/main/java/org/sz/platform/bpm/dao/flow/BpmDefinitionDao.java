package org.sz.platform.bpm.dao.flow;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.core.query.QueryFilter;
import org.sz.platform.bpm.model.flow.BpmDefinition;

public interface BpmDefinitionDao extends BaseDao<BpmDefinition> {

	int updateMain(Long defId, Short isMain);

	int updateSubVersions(Long parentDefId, String defKey);

	List<BpmDefinition> getByParentDefId(Long defId);

	List<BpmDefinition> getByParentDefIdIsMain(Long parentDefId, Short isMain);

	BpmDefinition getByActDefId(String actDefId);

	List<BpmDefinition> getByDefKey(String actDefKey);

	BpmDefinition getMainDefByActDefKey(String actDefKey);

	List<BpmDefinition> getByTypeId(Long typeId);

	List<BpmDefinition> getAllForAdmin(QueryFilter queryFilter);

	int saveParam(BpmDefinition bpmDefinition);

	void delByDeployId(String actDeployId);

	BpmDefinition getByDeployId(String actDeployId);

	boolean isActDefKeyExists(String key);

	List<BpmDefinition> getByUserId(QueryFilter queryFilter);

	List<BpmDefinition> getByUserIdFilter(QueryFilter queryFilter);
	
	List<BpmDefinition> getBpmDefinitions();

}