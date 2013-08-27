package org.sz.platform.bpm.dao.form;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.form.BpmFormField;

public interface BpmFormFieldDao extends BaseDao<BpmFormField>{

	List<BpmFormField> getByTableId(Long tableId);

	List<BpmFormField> getAllByTableId(Long tableId);

	int markDeletedByTableId(Long tableId);

	List<BpmFormField> getFlowVarByFlowDefId(Long defId);

	void delByTableId(Long tableId);

	Boolean hasFieldNameByTableId(Long tableId, String fieldName);
	
	List<BpmFormField> getByTableIdAndValueForm(Long tableId, Short valueFrom);
}