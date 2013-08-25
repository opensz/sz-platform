package org.sz.platform.bpm.service.form.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.Dom4jUtil;
import org.sz.core.util.FileUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.dao.form.BpmFormTemplateDao;
import org.sz.platform.bpm.model.form.BpmFormTemplate;
import org.sz.platform.bpm.service.form.BpmFormTemplateService;
import org.sz.platform.bpm.util.FormUtil;

@Service("bpmFormTemplateService")
public class BpmFormTemplateServiceImpl extends
		BaseServiceImpl<BpmFormTemplate> implements BpmFormTemplateService {
	private static Log logger = LogFactory
			.getLog(BpmFormTemplateServiceImpl.class);

	@Resource
	private BpmFormTemplateDao dao;

	protected IEntityDao<BpmFormTemplate, Long> getEntityDao() {
		return this.dao;
	}

	public BpmFormTemplate getByTemplateAlias(String alias) {
		return this.dao.getByTemplateAlias(alias);
	}

	public void initAllTemplate() throws Exception {
		this.dao.delSystem();
		addTemplate();
	}

	public void init() throws Exception {
		Integer amount = this.dao.getHasData();
		if (amount.intValue() == 0)
			addTemplate();
	}

	public void initTemplate() {
		BpmFormTemplateService service = (BpmFormTemplateService) ContextUtil
				.getBean(BpmFormTemplateService.class);
		try {
			service.init();
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
	}

	private void addTemplate() throws Exception {
		String templatePath = FormUtil.getFormTemplatePath();
		String xml = FileUtil.readFile(templatePath + "templates.xml");
		Document document = Dom4jUtil.loadXml(xml);
		Element root = document.getRootElement();
		List<Element> list = root.elements();
		for (Element element : list) {
			String alias = element.attributeValue("alias");
			String name = element.attributeValue("name");
			String type = element.attributeValue("type");
			String templateDesc = element.attributeValue("templateDesc");
			String macroAlias = element.attributeValue("macroAlias");

			String fileName = alias + ".ftl";
			String html = FileUtil.readFile(templatePath + fileName);

			BpmFormTemplate bpmFormTemplate = new BpmFormTemplate();
			bpmFormTemplate.setTemplateId(Long.valueOf(UniqueIdUtil.genId()));
			bpmFormTemplate.setMacroTemplateAlias(macroAlias);
			bpmFormTemplate.setHtml(html);
			bpmFormTemplate.setTemplateName(name);
			bpmFormTemplate.setAlias(alias);
			bpmFormTemplate.setCanEdit(0);
			bpmFormTemplate.setTemplateType(type);
			bpmFormTemplate.setTemplateDesc(templateDesc);
			this.dao.add(bpmFormTemplate);
		}
	}

	public boolean isExistAlias(String alias) {
		List<BpmFormTemplate> list = this.dao.getAll();
		for (BpmFormTemplate bpmFormTemplate : list) {
			if (bpmFormTemplate.getAlias().equals(alias)) {
				return true;
			}
		}
		return false;
	}

	public void backUpTemplate(Long id) {
		BpmFormTemplate bpmFormTemplate = (BpmFormTemplate) this.dao
				.getById(id);
		String alias = bpmFormTemplate.getAlias();
		String name = bpmFormTemplate.getTemplateName();
		String desc = bpmFormTemplate.getTemplateDesc();
		String html = bpmFormTemplate.getHtml();
		String type = bpmFormTemplate.getTemplateType();
		String macroAlias = bpmFormTemplate.getMacroTemplateAlias();

		String templatePath = FormUtil.getFormTemplatePath();

		String xmlPath = templatePath + "templates.xml";
		String xml = FileUtil.readFile(xmlPath);

		Document document = Dom4jUtil.loadXml(xml);
		Element root = document.getRootElement();

		Element e = root.addElement("template");
		e.addAttribute("alias", alias);
		e.addAttribute("name", name);
		e.addAttribute("type", type);
		e.addAttribute("templateDesc", desc);
		e.addAttribute("macroAlias", macroAlias);
		String content = document.asXML();

		FileUtil.writeFile(xmlPath, content);
		FileUtil.writeFile(templatePath + alias + ".ftl", html);

		bpmFormTemplate.setCanEdit(0);
		this.dao.update(bpmFormTemplate);
	}

	public List<BpmFormTemplate> getTemplateType(String type, String useType) {

		Map params = new HashMap();
		params.put("templateType", type);
		if (useType != null && !useType.equals("")) {
			params.put("useType", useType);
		}
		//查询不是默认的
		params.put("isDefault", 0);
		return this.dao.getAll(params);
	}

	public List<BpmFormTemplate> getAllMainTableTemplate() {
		return getTemplateType("main", null);
	}

	public List<BpmFormTemplate> getAllSubTableTemplate() {
		return getTemplateType("subTable", null);
	}

	public List<BpmFormTemplate> getAllMacroTemplate() {
		return getTemplateType("macro", null);
	}

	public List<BpmFormTemplate> getListTemplate(String useType) {
		return getTemplateType("list", useType);
	}

	public List<BpmFormTemplate> getDetailTemplate(String useType) {
		return getTemplateType("detail", useType);
	}

	public BpmFormTemplate getDefaultListTemplate(String useType) {
		Map params = new HashMap();
		if (StringUtils.isNotBlank(useType)) {
			params.put("useType", useType);
		}
		params.put("isDefault", 1);
		params.put("templateType", "list");
		List<BpmFormTemplate> list = this.dao.getAll(params);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public BpmFormTemplate getDefaultMainTemplate(String useType) {
		Map params = new HashMap();
		if (StringUtils.isNotBlank(useType)) {
			params.put("useType", useType);
		}
		params.put("isDefault", 1);
		params.put("templateType", "main");
		List<BpmFormTemplate> list = this.dao.getAll(params);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
