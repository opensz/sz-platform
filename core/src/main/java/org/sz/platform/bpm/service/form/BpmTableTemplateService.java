package org.sz.platform.bpm.service.form;

import java.util.List;

import org.sz.core.query.QueryFilter;
import org.sz.core.service.BaseService;
import org.sz.platform.bpm.model.form.BpmTableTemplate;

public interface BpmTableTemplateService extends BaseService<BpmTableTemplate> {

	List<BpmTableTemplate> getList(QueryFilter filter);

	void delByIds(Long[] ids);

	List<BpmTableTemplate> getByUserIdFilter(QueryFilter queryFilter);

}