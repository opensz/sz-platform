package org.sz.platform.bpm.service.form.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.engine.FreemarkEngine;
import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.FileUtil;
import org.sz.core.util.StringUtil;
import org.sz.platform.bpm.dao.flow.TaskOpinionDao;
import org.sz.platform.bpm.dao.form.BpmFormHandlerDao;
import org.sz.platform.bpm.model.flow.ProcessRun;
import org.sz.platform.bpm.model.flow.TaskOpinion;
import org.sz.platform.bpm.model.form.BpmFormData;
import org.sz.platform.bpm.model.form.BpmFormDef;
import org.sz.platform.bpm.model.form.BpmFormField;
import org.sz.platform.bpm.model.form.BpmTableTemplate;
import org.sz.platform.bpm.service.flow.ProcessRunService;
import org.sz.platform.bpm.service.form.BpmFormControlService;
import org.sz.platform.bpm.service.form.BpmFormFieldService;
import org.sz.platform.bpm.service.form.BpmFormHandlerService;
import org.sz.platform.bpm.service.form.BpmFormRightsService;
import org.sz.platform.bpm.util.FormUtil;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.service.IdentityService;

import freemarker.template.TemplateException;

/**
 * 加载form表单数据
 * 
 *
 */
@Service("bpmFormHandlerService")
public class BpmFormHandlerServiceImpl implements BpmFormHandlerService {

	@Resource
	private BpmFormHandlerDao dao;

	@Resource
	private BpmFormFieldService bpmFormFieldService;

	@Resource
	private IdentityService identityService;

	@Resource
	private BpmFormRightsService bpmFormRightsService;

	@Resource
	private BpmFormControlService bpmFormControlService;

	@Resource
	private TaskOpinionDao taskOpinionDao;
	
	@Resource
	private ProcessRunService processRunService;

	@Resource
	private FreemarkEngine freemarkEngine;
	private String PageSplitor = "#page#";

	public String obtainHtml(BpmFormDef bpmFormDef, ProcessRun processRun,
			Long userId, String nodeId) throws Exception {

		return obtainHtml(bpmFormDef, processRun,userId, nodeId, null);
	}
	
	public String obtainHtml(BpmFormDef bpmFormDef, ProcessRun processRun,
			Long userId, String nodeId, Map<String, Object> map) throws Exception {
		String pkValue = processRun.getBusinessKey();
		String instanceId = processRun.getActInstId();
		String actDefId = processRun.getActDefId();
		
		return obtainHtml(bpmFormDef, userId, pkValue, instanceId, actDefId,
				nodeId, map);
	}
	
	public String obtainHtml(BpmFormDef bpmFormDef, Long userId,
			String pkValue, String instanceId, String actDefId, String nodeId)
			throws Exception {
		return obtainHtml(bpmFormDef, userId, pkValue, instanceId, actDefId, nodeId, null);
	}
			
	public String obtainHtml(BpmFormDef bpmFormDef, Long userId,
			String pkValue, String instanceId, String actDefId, String nodeId, Map<String, Object> paramMap)
			throws Exception {
		Long tableId = bpmFormDef.getTableId();

		String template = "<input id='tableId' name='tableId' type='hidden' value='"
				+ tableId + "'/>" + bpmFormDef.getTemplate();

		Long formKey = bpmFormDef.getFormKey();

		BpmFormData data = null;
		if (StringUtil.isNotEmpty(pkValue)) {
			data = this.dao.getByKey(tableId.longValue(), pkValue);

			//
			//因为特殊需求，增加了创建节点。此处验证流水号是否已经创建
			List<BpmFormField> list = bpmFormFieldService.getByTableIdAndValueForm(tableId, BpmFormField.VALUE_FROM_IDENTITY);
			if(list != null && list.size() > 0){
				for(BpmFormField field : list){
					String fieldName = field.getFieldName().toLowerCase();
					if(data.getMainFields().get(fieldName) == null){
						String id = this.identityService.nextId(field.getIdentity());
						data.getMainFields().put(fieldName, id);
					}
				}
			}
			
			if (StringUtil.isNotEmpty(instanceId)) {
				Map formOptions = getFormOptionsByInstance(instanceId);
				if (BeanUtils.isNotEmpty(formOptions))
					data.setOptions(formOptions);
			}
		} else {
			data = new BpmFormData();

			Map resultMap = new HashMap();
			List<BpmFormField> list = this.bpmFormFieldService.getByTableId(tableId);
			for (BpmFormField field : list) {
				String fieldName = field.getFieldName().toLowerCase();

				if (field.getValueFrom().shortValue() == BpmFormField.VALUE_FROM_IDENTITY) {
					String id = this.identityService
							.nextId(field.getIdentity());
					resultMap.put(fieldName, id);
				} else if (field.getValueFrom().shortValue() == BpmFormField.VALUE_FROM_SCRIPT_SHOW) {
					Object result = FormUtil.calcuteField(field.getScript(),
							data.getMainFields(), "F_");
					resultMap.put(fieldName, result);
				}
			}

			data.setMainFields(resultMap);
		}

		//设置bpmFormData到paramMap
		if(paramMap != null){
			paramMap.put("bpmFormData", data);
		}
		
		Map model = new HashMap();
		model.put("main", data.getMainFields());
		model.put("opinion", data.getOptions());
		model.put("sub", data.getSubTableMap());

		Map map = new HashMap();
		map.put("model", model);

		map.put("service", this.bpmFormControlService);

		map.put("permission", this.bpmFormRightsService.getByFormKeyAndUserId(
				formKey, userId, actDefId, nodeId));
		map.put("table", new HashMap());

		String output = this.freemarkEngine
				.parseByStringTemplate(map, template);

		String tabTitle = bpmFormDef.getTabTitle();

		if (tabTitle.indexOf(this.PageSplitor) > -1) {
			output = getTabHtml(tabTitle, output);
		}

		return output;
	}

	private Map<String, String> getFormOptionsByInstance(String instanceId)
			throws IOException, TemplateException {
		Map map = new HashMap();
		Map taskMap = new HashMap();
		List<TaskOpinion> list = this.taskOpinionDao.getFormOptionsByInstance(instanceId);
		for (TaskOpinion option : list) {
			if (StringUtil.isNotEmpty(option.getFieldName())) {
				String fieldName = option.getFieldName().toLowerCase();
				if (taskMap.containsKey(fieldName)) {
					List opinionList = (List) taskMap.get(fieldName);
					opinionList.add(option);
				} else {
					List opinionList = new ArrayList();
					opinionList.add(option);
					taskMap.put(fieldName, opinionList);
				}
			}
		}
		Set set = taskMap.entrySet();
		for (Iterator it = set.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			List<TaskOpinion> optionList = (List) entry.getValue();
			String options = "";
			for (TaskOpinion opinion : optionList) {
				Map model = new HashMap();
				model.put("opinion", opinion);
				options = options
						+ this.freemarkEngine.mergeTemplateIntoString(
								"opinion.ftl", model);
			}
			map.put(entry.getKey(), options);
		}
		return map;
	}

	private String getTabHtml(String tabTitle, String html)
			throws TemplateException, IOException {
		String[] aryTitle = tabTitle.split(this.PageSplitor);
		String[] aryHtml = html.split(this.PageSplitor);

		List list = new ArrayList();
		for (int i = 0; i < aryTitle.length; i++) {
			Map map = new HashMap();
			map.put("title", aryTitle[i]);
			map.put("html", aryHtml[i]);
			list.add(map);
		}
		String formPath = FormUtil.getFormTemplatePath() + "tab.ftl";
		String tabTemplate = FileUtil.readFile(formPath);
		Map map = new HashMap();
		map.put("tabList", list);
		String output = this.freemarkEngine.parseByStringTemplate(map,
				tabTemplate);
		return output;
	}

	public void handFormData(BpmFormData bpmFormData) throws Exception {
		this.dao.handFormData(bpmFormData);
	}

	public BpmFormData getByKey(long tableId, String pkValue) throws Exception {
		return this.dao.getByKey(tableId, pkValue);
	}

	public List<Map<String, Object>> getAll(BpmTableTemplate bpmTableTemplate,
			SysUser user, Map<String, Object> param, PageBean pageBean)
			throws Exception {
		return this.dao.getAll(bpmTableTemplate, user, param, pageBean);
	}
	
	
	public List<Map<String, Object>> getQuery(BpmTableTemplate bpmTableTemplate,
			SysUser user, Map<String, Object> param, QueryFilter filter)
			throws Exception {
		return this.dao.getQuery(bpmTableTemplate, user, param, filter);
	}
	
	public void copyTask(BpmFormData bpmFormData, String sourceCaseId, Long sourceFlowRunId) throws Exception{
		handFormData(bpmFormData);
		//
		//修改原工单状态(作废)
		
		this.dao.invalidTask(bpmFormData.getTableId(), sourceCaseId);
		
		if(sourceFlowRunId != null){			
			processRunService.delByIds(new Long[]{sourceFlowRunId});
		}
	}
}
