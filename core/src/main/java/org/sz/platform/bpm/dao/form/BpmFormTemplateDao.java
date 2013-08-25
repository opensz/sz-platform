package org.sz.platform.bpm.dao.form;

import java.util.List;
import java.util.Map;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.form.BpmFormTemplate;

public interface BpmFormTemplateDao extends BaseDao<BpmFormTemplate>{

	List<BpmFormTemplate> getAll(Map params);

	void add(BpmFormTemplate bpmFormTemplate);

	void delSystem();

	BpmFormTemplate getByTemplateAlias(String alias);

	Integer getHasData();

}