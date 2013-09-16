package org.sz.platform.bpm.service.form;

import java.util.List;
import java.util.Map;

import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;
import org.sz.platform.bpm.model.flow.ProcessRun;
import org.sz.platform.bpm.model.form.BpmFormData;
import org.sz.platform.bpm.model.form.BpmFormDef;
import org.sz.platform.bpm.model.form.BpmTableTemplate;
import org.sz.platform.system.model.SysUser;

public interface BpmFormHandlerService {

	String obtainHtml(BpmFormDef bpmFormDef, ProcessRun processRun,
			Long userId, String nodeId) throws Exception;

	String obtainHtml(BpmFormDef bpmFormDef, ProcessRun processRun,
			Long userId, String nodeId, Map<String, Object> map) throws Exception;
			
	String obtainHtml(BpmFormDef bpmFormDef, Long userId, String pkValue,
			String instanceId, String actDefId, String nodeId) throws Exception;

	void handFormData(BpmFormData bpmFormData) throws Exception;

	BpmFormData getByKey(long tableId, String pkValue) throws Exception;

	List<Map<String, Object>> getAll(BpmTableTemplate bpmTableTemplate,
			SysUser user, Map<String, Object> param, PageBean pageBean)
			throws Exception;
	
	List<Map<String, Object>> getQuery(BpmTableTemplate bpmTableTemplate,
			SysUser user, Map<String, Object> param, QueryFilter queryFilter)
			throws Exception;

	//void copyTask(BpmFormData bpmFormData, String sourceCaseId, Long sourceFlowRunId) throws Exception;
}