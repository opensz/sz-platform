package org.sz.platform.bpm.service.form;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public interface BpmFormRightsService {

	void save(long formKey, JSONObject permission) throws Exception;

	void save(String actDefId, String nodeId, long formKey,
			JSONObject permission) throws Exception;

	Map<String, List<JSONObject>> getPermissionByFormNode(String actDefId,
			String nodeId, Long formDefId);

	Map<String, List<JSONObject>> getPermissionByTableId(Long tableId,
			Long formDefId);

	Map<String, Map> getByFormKeyAndUserId(Long formKey, Long userId,
			String actDefId, String nodeId);

}