package org.sz.platform.bpm.service.form;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.model.form.BpmFormTemplate;

public interface BpmFormTemplateService extends BaseService<BpmFormTemplate>{

	BpmFormTemplate getByTemplateAlias(String alias);

	void initAllTemplate() throws Exception;
	
	void  initTemplate();
	

	void init() throws Exception;

	boolean isExistAlias(String alias);

	void backUpTemplate(Long id);

	List<BpmFormTemplate> getTemplateType(String type,String useType);

	List<BpmFormTemplate> getAllMainTableTemplate();

	List<BpmFormTemplate> getAllSubTableTemplate();

	List<BpmFormTemplate> getAllMacroTemplate();

	List<BpmFormTemplate> getListTemplate(String useType);

	List<BpmFormTemplate> getDetailTemplate(String useType);
	
	
	BpmFormTemplate getDefaultListTemplate(String useType);
	
	BpmFormTemplate getDefaultMainTemplate(String useType);
}