package org.sz.platform.bpm.service.flow;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.sz.core.query.QueryFilter;
import org.sz.core.service.BaseService;
import org.sz.platform.bpm.model.flow.BpmDefinition;

public interface BpmDefinitionService extends BaseService<BpmDefinition> {

	void deploy(BpmDefinition bpmDefinition, String actFlowDefXml)
			throws Exception;

	void saveOrUpdate(BpmDefinition bpmDefinition, boolean isDeploy,
			String actFlowDefXml) throws Exception;

	/**
	 * 拷贝流程定义
	 * @param sourceBpmDefinition
	 * @param isDeploy
	 * @throws Exception
	 */
	void copyFormDef(Long sourceDefId, boolean isDeploy)
			throws Exception;

	List<BpmDefinition> getAllHistoryVersions(Long defId);

	BpmDefinition getByActDefId(String actDefId);

	List<BpmDefinition> getByTypeId(Long typeId);

	List<BpmDefinition> getAllForAdmin(QueryFilter queryFilter);

	int saveParam(BpmDefinition bpmDefinition);

	void delDefbyDeployId(Long flowDefId, boolean isOnlyVersion);

	void importXml(String fileStr) throws Exception;

	String exportXml(Long[] lActDeployId) throws FileNotFoundException,
			IOException;

	BpmDefinition getMainDefByActDefKey(String actDefKey);

	List<BpmDefinition> getByUserId(QueryFilter queryFilter);

	List<BpmDefinition> getByUserIdFilter(QueryFilter queryFilter);

	List<BpmDefinition> getBpmDefinitions();

}