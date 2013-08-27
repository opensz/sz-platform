package org.sz.platform.bpm.dao.form.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.core.query.QueryFilter;
import org.sz.platform.bpm.dao.form.BpmTableTemplateDao;
import org.sz.platform.bpm.model.form.BpmTableTemplate;

@Repository("bpmTableTemplateDao")
public class BpmTableTemplateDaoImpl extends BaseDaoImpl<BpmTableTemplate>
		implements BpmTableTemplateDao {
	public Class getEntityClass() {
		return BpmTableTemplate.class;
	}

	public List<BpmTableTemplate> getList(QueryFilter filter) {
		return getBySqlKey("getList", filter);
	}

	public List<BpmTableTemplate> getByUserIdFilter(QueryFilter queryFilter) {
		return getBySqlKey("getByUserIdFilter", queryFilter);
	}
}
