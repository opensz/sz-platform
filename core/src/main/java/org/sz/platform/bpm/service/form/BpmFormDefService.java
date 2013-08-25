package org.sz.platform.bpm.service.form;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.sz.core.query.QueryFilter;
import org.sz.core.service.BaseService;
import org.sz.platform.bpm.model.flow.ProcessRun;
import org.sz.platform.bpm.model.form.BpmFormDef;

public interface BpmFormDefService extends BaseService<BpmFormDef> {

	Integer getCountByFormKey(Long formKey);

	BpmFormDef getDefaultVersionByFormKey(Long formKey);

	List<BpmFormDef> getByFormKey(Long formKey);

	void addForm(BpmFormDef bpmFormDef, JSONObject jsonObject) throws Exception;

	void updateForm(BpmFormDef bpmFormDef, JSONObject jsonObject)
			throws Exception;

	void publish(Long formDefId, String operator) throws Exception;

	void setDefaultVersion(Long formDefId, Long formKey);

	void newVersion(Long formDefId) throws Exception;

	Map loadForm(ProcessRun processRun, String nodeId, Long userId,
			String ctxPath) throws Exception;

	List<BpmFormDef> getPublished(QueryFilter queryFilter);

	int getFlowUsed(Long formKey);

	void delByFormKey(Long formKey);

}