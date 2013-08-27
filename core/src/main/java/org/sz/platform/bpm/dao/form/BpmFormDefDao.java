package org.sz.platform.bpm.dao.form;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.core.query.QueryFilter;
import org.sz.platform.bpm.model.form.BpmFormDef;

public interface BpmFormDefDao extends BaseDao<BpmFormDef> {

	Integer getCountByFormKey(Long formKey);

	BpmFormDef getDefaultVersionByFormKey(Long formKey);

	boolean isTableHasFormDef(Long tableId);

	List<BpmFormDef> getByFormKey(Long formKey);

	List<BpmFormDef> getPublished(QueryFilter queryFilter);

	int getFlowUsed(Long formKey);

	void delByFormKey(Long formKey);

	void setDefaultVersion(Long formKey, Long formDefId);

}