package org.sz.platform.bpm.dao.form;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.core.query.QueryFilter;
import org.sz.platform.bpm.model.form.BpmTableTemplate;

public interface BpmTableTemplateDao  extends BaseDao<BpmTableTemplate>{

	List<BpmTableTemplate> getList(QueryFilter filter);

	List<BpmTableTemplate> getByUserIdFilter(QueryFilter queryFilter);

}